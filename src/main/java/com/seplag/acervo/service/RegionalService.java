package com.seplag.acervo.service;

import com.seplag.acervo.domain.Regional;
import com.seplag.acervo.dto.ArtistaCompletoDto;
import com.seplag.acervo.dto.RegionalDto;
import com.seplag.acervo.repository.RegionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionalService {

    private static final Logger log = LoggerFactory.getLogger(RegionalService.class);

    @Autowired
    private RegionalArgusService regionalArgusService;

    @Autowired
    private RegionalRepository regionalRepository;

    @Autowired
    private RegionalRepository repository;

    public List<RegionalDto> sincronizar() {
        List<RegionalDto> listRegionalArgusDto = regionalArgusService.listarRegionaisArgus();

        // Alteração para comparação com código único cadastrado no banco
        listRegionalArgusDto.stream()
                .forEach(regional -> {
                    regional.setCodigo(regional.getId());
                    regional.setId(null);
                    regional.setAtivo(true);
                });

        List<RegionalDto> ativosBanco = regionalRepository.findByAtivoTrue()
                .stream().map(RegionalDto::toDto).collect(Collectors.toList());

        List<RegionalDto> listaIncluir = compararListas(listRegionalArgusDto, ativosBanco);
        List<RegionalDto> listaInativar = compararListas(ativosBanco, listRegionalArgusDto);

        for (RegionalDto regionalArgusDto : listRegionalArgusDto) {
            for (RegionalDto regionalBancoDto : ativosBanco) {
                if(regionalArgusDto.getCodigo().equals(regionalBancoDto.getCodigo()) &&
                        !regionalArgusDto.getNome().equals(regionalBancoDto.getNome())) {
                        //Deve inativar o Regional do banco de dados
                        listaInativar.add(regionalBancoDto);
                        //Deve incluir o Regional do Argus
                        listaIncluir.add(regionalArgusDto);
                }
            }
        }

        //Incluir regionais
        for (RegionalDto regionalDto : listaIncluir) {
            Regional regional = RegionalDto.toEntity(regionalDto);
            regionalRepository.save(regional);
        }

        //Inativar regionais
        for (RegionalDto regionalDto : listaInativar) {
            Optional<Regional> regional = regionalRepository.findFirstByCodigoAndAtivoTrue(regionalDto.getCodigo());
            regional.get().setAtivo(false);
            regionalRepository.save(regional.get());
        }

        return regionalRepository.findAllByOrderByCodigoAsc()
                .stream()
                .map(regional -> RegionalDto.toDto(regional))
                .collect(Collectors.toList());
    }

    private List<RegionalDto> compararListas(List<RegionalDto> listaA, List<RegionalDto> listaB) {
        // 2. Encontrar itens na listaA que não estão na listaB (Diferença)
        return listaA.stream()
                .filter(a -> listaB.stream().noneMatch(b -> b.getCodigo().equals(a.getCodigo())))
                .collect(Collectors.toList());
    }
}
