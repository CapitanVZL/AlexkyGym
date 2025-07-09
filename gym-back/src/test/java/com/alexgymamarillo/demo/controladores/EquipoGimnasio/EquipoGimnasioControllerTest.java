package com.alexgymamarillo.demo.controladores.EquipoGimnasio;

import com.fasterxml.jackson.databind.ObjectMapper;
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

class EquipoGimnasioControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private EquipoGimnasioService service;

    @InjectMocks
    private EquipoGimnasioController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); 
    }

    @Test
    void testGuardarEquipoGimnasio() throws Exception {
        EquipoGimnasio equipo = new EquipoGimnasio();
        equipo.setSerial("E123");
        equipo.setNombre("Máquina de Prensa");
        equipo.setModelo("PX-900");
        equipo.setMarca("GymTech");
        equipo.setEstado("Activo");
        equipo.setFechaAdquisicion(LocalDate.of(2023, 5, 10));
        equipo.setPeso(150.0);
        equipo.setImagen("imagen.png");

        when(service.guardar(any(EquipoGimnasio.class))).thenReturn(equipo);

        mockMvc.perform(post("/api/equipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serial").value("E123"))
                .andExpect(jsonPath("$.nombre").value("Máquina de Prensa"));

        verify(service, times(1)).guardar(any(EquipoGimnasio.class));
    }

    @Test
    void testListarEquipoGimnasio() throws Exception {
        EquipoGimnasio equipo = new EquipoGimnasio();
        equipo.setSerial("E123");
        equipo.setNombre("Máquina de Prensa");

        when(service.listar()).thenReturn(Collections.singletonList(equipo));

        mockMvc.perform(get("/api/equipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serial").value("E123"))
                .andExpect(jsonPath("$[0].nombre").value("Máquina de Prensa"));

        verify(service, times(1)).listar();
    }

    @Test
    void testBuscarEquipoGimnasio() throws Exception {
        EquipoGimnasio equipo = new EquipoGimnasio();
        equipo.setSerial("E123");
        equipo.setNombre("Máquina de Prensa");

        when(service.buscar("E123")).thenReturn(equipo);

        mockMvc.perform(get("/api/equipos/E123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Máquina de Prensa"));

        verify(service, times(1)).buscar("E123");
    }

    @Test
    void testEliminarEquipoGimnasio() throws Exception {
        doNothing().when(service).eliminar("E123");

        mockMvc.perform(delete("/api/equipos/E123"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminar("E123");
    }
    @Test
    void testActualizarEquipo() throws Exception {
        EquipoGimnasio existente = new EquipoGimnasio();
        existente.setSerial("E123");
        existente.setNombre("Máquina Antigua");
        existente.setModelo("PX-100");

        EquipoGimnasio actualizado = new EquipoGimnasio();
        actualizado.setSerial("E123");
        actualizado.setNombre("Máquina Nueva");
        actualizado.setModelo("PX-200");
        actualizado.setMarca("GymTech");
        actualizado.setEstado("Activo");
        actualizado.setFechaAdquisicion(LocalDate.of(2023, 5, 10));
        actualizado.setPeso(120.0);
        actualizado.setImagen("nueva.png");

        when(service.buscar("E123")).thenReturn(existente);
        when(service.guardar(any(EquipoGimnasio.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/equipos/E123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Máquina Nueva"))
                .andExpect(jsonPath("$.modelo").value("PX-200"))
                .andExpect(jsonPath("$.imagen").value("nueva.png"));

        verify(service, times(1)).buscar("E123");
        verify(service, times(1)).guardar(any(EquipoGimnasio.class));
    }
}
