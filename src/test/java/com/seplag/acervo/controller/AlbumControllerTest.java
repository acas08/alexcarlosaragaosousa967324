package com.seplag.acervo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.dto.AlbumImagemDto;
import com.seplag.acervo.dto.AlbumDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.service.AlbumImagemService;
import com.seplag.acervo.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlbumController.class)
class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlbumService albumService;

    @MockitoBean
    private AlbumImagemService albumImagemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar() throws Exception {
        AlbumCompletoDto dto = new AlbumCompletoDto();
        dto.setId(1L);
        dto.setDescricao("Album 1");

        Page<AlbumCompletoDto> page = new PageImpl<>(List.of(dto));
        when(albumService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/albuns")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(albumService, times(1)).findAll(any());
    }

    @Test
    void incluir() throws Exception {
        AlbumDto body = new AlbumDto();
        body.setDescricao("Please Please Me");

        Album salvo = new Album();
        salvo.setId(10L);
        salvo.setDescricao("Please Please Me");

        when(albumService.save(any(Album.class))).thenReturn(salvo);

        mockMvc.perform(post("/api/albuns")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.descricao").value("Please Please Me"));

        verify(albumService, times(1)).save(any(Album.class));

    }

    @Test
    void listarPorModalidade() throws Exception {
        when(albumService.buscarPorModalidadeArtista(eq(ModalidadeEnum.CANTOR), any()))
                .thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/api/albuns/listarPorModalidade")
                        .param("modalidade", "CANTOR")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDir", "asc")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(albumService, times(1)).buscarPorModalidadeArtista(eq(ModalidadeEnum.CANTOR), any());

    }

    @Test
    void anexarImagens() throws Exception {
        AlbumImagemDto img = new AlbumImagemDto();
        img.setId(1L);
        img.setChaveRegistro("chaveRegistro");

        when(albumImagemService.armazenarImagens(eq(5L), anyList()))
                .thenReturn(List.of(img));

        MockMultipartFile file = new MockMultipartFile(
                "files",
                "imagem.jpg",
                IMAGE_JPEG_VALUE,
                "conteudo".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/albuns/{id}/imagens", 5L)
                        .file(file)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(albumImagemService, times(1)).armazenarImagens(eq(5L), anyList());

    }

    @Test
    void solicitarLink() throws Exception {
        when(albumImagemService.gerarLinkPreAssinado(7L))
                .thenReturn("http://localhost/linkPreAssinado");

        mockMvc.perform(get("/api/albuns/solicitarLink/{id}", 7L)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("http://localhost/linkPreAssinado"))
                .andExpect(jsonPath("$.expiresInSeconds").isNumber());

        verify(albumImagemService, times(1)).gerarLinkPreAssinado(7L);
    }
}