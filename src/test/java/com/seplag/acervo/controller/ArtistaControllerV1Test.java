package com.seplag.acervo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.dto.ArtistaCompletoDto;
import com.seplag.acervo.dto.ArtistaDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.service.ArtistaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ArtistaControllerV1.class)
class ArtistaControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistaService artistaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar() throws Exception {
        ArtistaCompletoDto dto = new ArtistaCompletoDto();
        dto.setId(1L);
        dto.setNome("John Lennon");
        dto.setModalidade(ModalidadeEnum.CANTOR);

        Page<ArtistaCompletoDto> page = new PageImpl<>(List.of(dto));
        when(artistaService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/artistas")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(artistaService, times(1)).findAll(any());
    }

    @Test
    void buscarPorNome() throws Exception {
        when(artistaService.findByNomeContaining(eq("jo"), any()))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/api/v1/artistas/listar")
                        .param("nome", "John Lennon")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDir", "asc")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(artistaService, times(1)).findByNomeContaining(eq("John Lennon"), any());

    }

    @Test
    void incluir() throws Exception {
        ArtistaDto body = new ArtistaDto();
        body.setNome("John Lennon");
        body.setModalidade(ModalidadeEnum.CANTOR);

        Artista salvo = new Artista();
        salvo.setId(10L);
        salvo.setNome("John Lennon");
        salvo.setModalidade(ModalidadeEnum.CANTOR);

        when(artistaService.save(any(Artista.class))).thenReturn(salvo);

        mockMvc.perform(post("/api/v1/artistas")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("John Lennon"))
                .andExpect(jsonPath("$.modalidade").value("CANTOR"));

        verify(artistaService, times(1)).save(any(Artista.class));
    }

    @Test
    void alterarNome() throws Exception {
        ArtistaDto body = new ArtistaDto();
        body.setNome("John Lennon");
        body.setModalidade(ModalidadeEnum.CANTOR);

        Artista atualizado = new Artista();
        atualizado.setId(7L);
        atualizado.setNome("John Lennon");
        atualizado.setModalidade(ModalidadeEnum.CANTOR);

        when(artistaService.atualizar(eq(7L), any(ArtistaDto.class))).thenReturn(atualizado);

        mockMvc.perform(put("/api/v1/artistas/{id}/nome", 7L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.nome").value("John Lennon"))
                .andExpect(jsonPath("$.modalidade").value("CANTOR"));

        verify(artistaService, times(1)).atualizar(eq(7L), any(ArtistaDto.class));

    }
}