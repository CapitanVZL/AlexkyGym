package com.alexgymamarillo.demo.controladores.Zona;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZonaServiceTest {

    @Mock
    private ZonaRepository repo;

    @InjectMocks
    private ZonaService service;

    private Zona zona1;
    private Zona zona2;

    @BeforeEach
    void setUp() {
        zona1 = new Zona();
        zona1.setId(1L);
        zona1.setNombre("Zona A");

        zona2 = new Zona();
        zona2.setId(2L);
        zona2.setNombre("Zona B");

    }

    @Test
    void testListarZona() {
        when(repo.findAll()).thenReturn(Arrays.asList(zona1, zona2));

        List<Zona> zonas = service.listar();

        assertEquals(2, zonas.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGuardarZona() {
        when(repo.save(zona1)).thenReturn(zona1);

        Zona result = service.guardar(zona1);

        assertNotNull(result);
        assertEquals("Zona A", result.getNombre());
        verify(repo, times(1)).save(zona1);
    }

    @Test
    void testBuscarExistenteZona() {
        when(repo.findById(1L)).thenReturn(Optional.of(zona1));

        Zona result = service.buscar(1L);

        assertNotNull(result);
        assertEquals("Zona A", result.getNombre());
    }

    @Test
    void testBuscarInexistenteZona() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Zona result = service.buscar(99L);

        assertNull(result);
    }

    @Test
    void testEliminarZona() {
        Long id = 1L;

        service.eliminar(id);

        verify(repo, times(1)).deleteById(id);
    }
}