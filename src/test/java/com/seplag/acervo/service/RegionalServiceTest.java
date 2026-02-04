package com.seplag.acervo.service;

import com.seplag.acervo.domain.Regional;
import com.seplag.acervo.dto.RegionalDto;
import com.seplag.acervo.repository.RegionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionalServiceTest {

    @InjectMocks
    private RegionalService regionalService;

    @Mock
    private RegionalArgusService regionalArgusService;

    @Mock
    private RegionalRepository regionalRepository;

    @Test
    void sincronizar() {
        RegionalDto argus = new RegionalDto();
        argus.setId(10);
        argus.setNome("Cuiaba");

        when(regionalArgusService.listarRegionaisArgus()).thenReturn(List.of(argus));
        when(regionalRepository.findByAtivoTrue()).thenReturn(List.of());

        when(regionalRepository.save(any(Regional.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(regionalRepository.findAllByOrderByCodigoAsc()).thenReturn(List.of());

        regionalService.sincronizar();

        verify(regionalRepository).save(any(Regional.class));

    }

    @Test
    void sincronizar_BancoAtivo_NaoTemArgus() {
        when(regionalArgusService.listarRegionaisArgus()).thenReturn(List.of());

        Regional regionalBanco = new Regional();
        regionalBanco.setId(1L);
        regionalBanco.setCodigo(20);
        regionalBanco.setNome("Regional 20");
        regionalBanco.setAtivo(true);

        when(regionalRepository.findByAtivoTrue()).thenReturn(List.of(regionalBanco));
        when(regionalRepository.findFirstByCodigoAndAtivoTrue(20)).thenReturn(Optional.of(regionalBanco));
        when(regionalRepository.save(any(Regional.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(regionalRepository.findAllByOrderByCodigoAsc()).thenReturn(List.of());

        regionalService.sincronizar();

        assertFalse(regionalBanco.getAtivo());
        verify(regionalRepository).findFirstByCodigoAndAtivoTrue(20);
        verify(regionalRepository, atLeastOnce()).save(any(Regional.class));
    }
}