package com.seplag.acervo.controller;

import com.seplag.acervo.service.RegionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RegionalController.class)
class RegionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegionalService regionalService;

    @Test
    void sincronizar() throws Exception {
        when(regionalService.sincronizar()).thenReturn(List.of());

        mockMvc.perform(post("/api/regionais"))
                .andExpect(status().isCreated());

        verify(regionalService, times(1)).sincronizar();

    }
}