package com.alexgymamarillo.demo.controladores.Zona;

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

class ZonaControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ZonaService service;

    @InjectMocks
    private ZonaController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }
    
    @Test
    void testGuardarZona() throws Exception {
        Zona zona = new Zona();
        zona.setId(1L);
        zona.setNombre("Zona 1");

        when(service.guardar(any(Zona.class))).thenReturn(zona);

        mockMvc.perform(post("/api/zonas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(zona)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Zona 1"));

        verify(service, times(1)).guardar(any(Zona.class));
    }

    @Test
    void testListarZonas() throws Exception {
        Zona zona = new Zona();
        zona.setId(1L);
        zona.setNombre("Zona 1");

        when(service.listar()).thenReturn(Collections.singletonList(zona));

        mockMvc.perform(get("/api/zonas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Zona 1"));

        verify(service, times(1)).listar();
    }

    @Test
    void testBuscarZona() throws Exception {
        Zona zona = new Zona();
        zona.setId(1L);
        zona.setNombre("Zona 1");

        when(service.buscar(1L)).thenReturn(zona);

        mockMvc.perform(get("/api/zonas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Zona 1"));

        verify(service, times(1)).buscar(1L);
    }

    @Test
    void testEliminarZona() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/zonas/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    void testActualizarZona() throws Exception {
        Zona existente = new Zona();
        existente.setId(1L);
        existente.setNombre("Zona Antigua");

        Zona detalles = new Zona();
        detalles.setNombre("Zona Nueva");

        when(service.buscar(1L)).thenReturn(existente);
        when(service.guardar(any(Zona.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/api/zonas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(detalles)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Zona Nueva"));

        verify(service, times(1)).buscar(1L);
        verify(service, times(1)).guardar(any(Zona.class));
    }
}
