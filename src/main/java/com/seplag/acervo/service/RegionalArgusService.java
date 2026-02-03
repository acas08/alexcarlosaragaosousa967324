package com.seplag.acervo.service;

import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.dto.RegionalDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.repository.RegionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RegionalArgusService {

    private static final Logger log = LoggerFactory.getLogger(RegionalArgusService.class);

    @Value("${integracoes.argus.base-url}")
    private String urlArgus;

    public List<RegionalDto> listarRegionaisArgus() {
        RestClient restClient = RestClient.create();

        return restClient.get()
                .uri(urlArgus)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
