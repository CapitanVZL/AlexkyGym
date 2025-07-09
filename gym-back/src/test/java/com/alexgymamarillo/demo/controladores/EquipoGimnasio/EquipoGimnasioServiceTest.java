package com.alexgymamarillo.demo.controladores.EquipoGimnasio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoGimnasioServiceTest {

    @Mock
    private EquipoGimnasioRepository repo;

    @InjectMocks
    private EquipoGimnasioService service;

    private EquipoGimnasio equipo1;
    private EquipoGimnasio equipo2;

    @BeforeEach
    void setUp() {
        equipo1 = new EquipoGimnasio();
        equipo1.setSerial("EQ001");
        equipo1.setNombre("Bicicleta Estática");
        equipo1.setMarca("MarcaX");
        equipo1.setModelo("ModeloX");
        equipo1.setFechaAdquisicion(LocalDate.of(2022, 1, 1));
        equipo1.setEstado("Operativo");

        equipo2 = new EquipoGimnasio();
        equipo2.setSerial("EQ002");
        equipo2.setNombre("Caminadora");
        equipo2.setMarca("MarcaY");
        equipo2.setModelo("ModeloY");
        equipo2.setFechaAdquisicion(LocalDate.of(2023, 2, 2));
        equipo2.setEstado("En mantenimiento");
    }

    @Test
    void testListarEquipoGimnasio() {
        when(repo.findAll()).thenReturn(Arrays.asList(equipo1, equipo2));

        List<EquipoGimnasio> equipos = service.listar();

        assertEquals(2, equipos.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGuardarEquipoGimnasio() {
        when(repo.save(equipo1)).thenReturn(equipo1);

        EquipoGimnasio result = service.guardar(equipo1);

        assertNotNull(result);
        assertEquals("EQ001", result.getSerial());
        verify(repo, times(1)).save(equipo1);
    }

    @Test
    void testBuscarExistenteEquipoGimnasio() {
        when(repo.findById("EQ001")).thenReturn(Optional.of(equipo1));

        EquipoGimnasio result = service.buscar("EQ001");

        assertNotNull(result);
        assertEquals("Bicicleta Estática", result.getNombre());
    }

    @Test
    void testBuscarInexistenteEquipoGimnasio() {
        when(repo.findById("NO_EXISTE")).thenReturn(Optional.empty());

        EquipoGimnasio result = service.buscar("NO_EXISTE");

        assertNull(result);
    }

    @Test
    void testEliminarEquipoGimnasio() {
        service.eliminar("EQ001");

        verify(repo, times(1)).deleteById("EQ001");
    }
}
