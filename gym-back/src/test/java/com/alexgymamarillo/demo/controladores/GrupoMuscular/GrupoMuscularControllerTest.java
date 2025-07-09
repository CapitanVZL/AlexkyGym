package com.alexgymamarillo.demo.controladores.GrupoMuscular;

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

class GrupoMuscularControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private GrupoMuscularService service;

    @InjectMocks
    private GrupoMuscularController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGuardarGrupoMuscular() throws Exception {
        GrupoMuscular grupo = new GrupoMuscular();
        grupo.setId(1L);
        grupo.setNombre("Pecho");

        when(service.guardar(any(GrupoMuscular.class))).thenReturn(grupo);

        mockMvc.perform(post("/api/grupos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(grupo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Pecho"));

        verify(service, times(1)).guardar(any(GrupoMuscular.class));
    }

    @Test
    void testListarGrupoMuscular() throws Exception {
        GrupoMuscular grupo = new GrupoMuscular();
        grupo.setId(1L);
        grupo.setNombre("Espalda");

        when(service.listar()).thenReturn(Collections.singletonList(grupo));

        mockMvc.perform(get("/api/grupos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Espalda"));

        verify(service, times(1)).listar();
    }

    @Test
    void testBuscarGrupoMuscular() throws Exception {
        GrupoMuscular grupo = new GrupoMuscular();
        grupo.setId(1L);
        grupo.setNombre("Piernas");

        when(service.buscar(1L)).thenReturn(grupo);

        mockMvc.perform(get("/api/grupos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Piernas"));

        verify(service, times(1)).buscar(1L);
    }

    @Test
    void testEliminarGrupoMuscular() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/grupos/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    void testActualizarGrupoMuscular() throws Exception {
        GrupoMuscular existente = new GrupoMuscular();
        existente.setId(1L);
        existente.setNombre("Tríceps");

        GrupoMuscular actualizado = new GrupoMuscular();
        actualizado.setId(1L);
        actualizado.setNombre("Bíceps");

        when(service.buscar(1L)).thenReturn(existente);
        when(service.guardar(any(GrupoMuscular.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/grupos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Bíceps"));

        verify(service, times(1)).buscar(1L);
        verify(service, times(1)).guardar(any(GrupoMuscular.class));
    }
}
