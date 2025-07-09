package com.alexgymamarillo.demo.controladores.TipoEquipo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TipoEquipoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private TipoEquipoService service;

    @InjectMocks
    private TipoEquipoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGuardarTipoEquipo() throws Exception {
        TipoEquipo tipo = new TipoEquipo();
        tipo.setId(1L);
        tipo.setNombre("Cardio");

        when(service.guardar(any(TipoEquipo.class))).thenReturn(tipo);

        mockMvc.perform(post("/api/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Cardio"));

        verify(service, times(1)).guardar(any(TipoEquipo.class));
    }

    @Test
    void testListarTipoEquipo() throws Exception {
        TipoEquipo tipo = new TipoEquipo();
        tipo.setId(1L);
        tipo.setNombre("Fuerza");

        when(service.listar()).thenReturn(Collections.singletonList(tipo));

        mockMvc.perform(get("/api/tipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Fuerza"));

        verify(service, times(1)).listar();
    }

    @Test
    void testBuscarTipoEquipo() throws Exception {
        TipoEquipo tipo = new TipoEquipo();
        tipo.setId(1L);
        tipo.setNombre("Resistencia");

        when(service.buscar(1L)).thenReturn(tipo);

        mockMvc.perform(get("/api/tipos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Resistencia"));

        verify(service, times(1)).buscar(1L);
    }

    @Test
    void testEliminarTipoEquipo() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/tipos/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    void testActualizarTipoEquipo() throws Exception {
        TipoEquipo existente = new TipoEquipo();
        existente.setId(1L);
        existente.setNombre("Antiguo");

        TipoEquipo actualizado = new TipoEquipo();
        actualizado.setId(1L);
        actualizado.setNombre("Nuevo");

        when(service.buscar(1L)).thenReturn(existente);
        when(service.guardar(any(TipoEquipo.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/tipos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Nuevo"));

        verify(service, times(1)).buscar(1L);
        verify(service, times(1)).guardar(any(TipoEquipo.class));
    }
}

