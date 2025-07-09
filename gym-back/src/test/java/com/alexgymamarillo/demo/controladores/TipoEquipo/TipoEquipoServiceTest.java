package com.alexgymamarillo.demo.controladores.TipoEquipo;

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
class TipoEquipoServiceTest {

    @Mock
    private TipoEquipoRepository repo;

    @InjectMocks
    private TipoEquipoService service;

    private TipoEquipo tipoEquipo1;
    private TipoEquipo tipoEquipo2;

    @BeforeEach
    void setUp() {
        tipoEquipo1 = new TipoEquipo();
        tipoEquipo1.setId(1L);
        tipoEquipo1.setNombre("Caminadora");

        tipoEquipo2 = new TipoEquipo();
        tipoEquipo2.setId(2L);
        tipoEquipo2.setNombre("Bicicleta Estática");
    
    }

    @Test
    void testListarTipoEquipo() {
        when(repo.findAll()).thenReturn(Arrays.asList(tipoEquipo1, tipoEquipo2));

        List<TipoEquipo> tipos = service.listar();

        assertEquals(2, tipos.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGuardarTipoEquipo() {
        when(repo.save(tipoEquipo1)).thenReturn(tipoEquipo1);

        TipoEquipo result = service.guardar(tipoEquipo1);

        assertNotNull(result);
        assertEquals("Caminadora", result.getNombre());
        verify(repo, times(1)).save(tipoEquipo1);
    }

    @Test
    void testBuscarExistenteTipoEquipo() {
        when(repo.findById(1L)).thenReturn(Optional.of(tipoEquipo1));

        TipoEquipo result = service.buscar(1L);

        assertNotNull(result);
        assertEquals("Caminadora", result.getNombre());
    }

    @Test
    void testBuscarInexistenteTipoEquipo() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        TipoEquipo result = service.buscar(99L);

        assertNull(result);
    }

    @Test
    void testEliminarTipoEquipo() {
        Long id = 1L;

        service.eliminar(id);

        verify(repo, times(1)).deleteById(id);
    }
}
