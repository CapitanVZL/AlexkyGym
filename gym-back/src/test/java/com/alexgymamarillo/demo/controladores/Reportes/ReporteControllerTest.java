package com.alexgymamarillo.demo.controladores.Reportes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReporteControllerTest {
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ReporteService service;

    @InjectMocks
    private ReporteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); 
    }

    @Test
    void testGuardarReporte() throws Exception {
        Reporte reporte = new Reporte();
        reporte.setId(1L);
        reporte.setTitulo("Fallo en la cinta");
        reporte.setDescripcion("La cinta no enciende");
        reporte.setFechaReporte(LocalDate.of(2024, 6, 1));

        when(service.guardar(any(Reporte.class))).thenReturn(reporte);

        mockMvc.perform(post("/api/reportes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Fallo en la cinta"))
                .andExpect(jsonPath("$.fechaReporte").value("2024-06-01"));

        verify(service, times(1)).guardar(any(Reporte.class));
    }

    @Test
    void testListarReportes() throws Exception {
        Reporte reporte = new Reporte();
        reporte.setId(1L);
        reporte.setTitulo("Fallo en la cinta");

        when(service.listar()).thenReturn(Collections.singletonList(reporte));

        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Fallo en la cinta"));

        verify(service, times(1)).listar();
    }

    @Test
    void testBuscarReporte() throws Exception {
        Reporte reporte = new Reporte();
        reporte.setId(1L);
        reporte.setTitulo("Fallo en la caminadora");

        when(service.buscar(1L)).thenReturn(reporte);

        mockMvc.perform(get("/api/reportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Fallo en la caminadora"));

        verify(service, times(1)).buscar(1L);
    }

    @Test
    void testEliminarReporte() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/reportes/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    void testActualizarReporte() throws Exception {
        Reporte existente = new Reporte();
        existente.setId(1L);
        existente.setTitulo("Antiguo");
        existente.setDescripcion("Antiguo desc");
        existente.setFechaReporte(LocalDate.of(2023, 1, 1));
        existente.setEquipo(null);

        Reporte detalles = new Reporte();
        detalles.setTitulo("Nuevo");
        detalles.setDescripcion("Descripción actualizada");
        detalles.setFechaReporte(LocalDate.of(2024, 6, 1));
        detalles.setEquipo(null);

        when(service.buscar(1L)).thenReturn(existente);
        when(service.guardar(any(Reporte.class))).thenReturn(detalles);

        mockMvc.perform(put("/api/reportes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(detalles)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo"))
                .andExpect(jsonPath("$.descripcion").value("Descripción actualizada"))
                .andExpect(jsonPath("$.fechaReporte").value("2024-06-01"));

        verify(service, times(1)).buscar(1L);
        verify(service, times(1)).guardar(any(Reporte.class));
    }
}
