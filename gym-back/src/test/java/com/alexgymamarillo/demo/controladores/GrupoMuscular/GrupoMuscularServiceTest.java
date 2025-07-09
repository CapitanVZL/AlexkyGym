package com.alexgymamarillo.demo.controladores.GrupoMuscular;

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
class GrupoMuscularServiceTest {

    @Mock
    private GrupoMuscularRepository repo;

    @InjectMocks
    private GrupoMuscularService service;

    private GrupoMuscular grupoM1;
    private GrupoMuscular grupoM2;

    @BeforeEach
    void setUp() {
        grupoM1 = new GrupoMuscular();
        grupoM1.setId(1L);
        grupoM1.setNombre("Pectorales");

        grupoM2 = new GrupoMuscular();
        grupoM2.setId(2L);
        grupoM2.setNombre("Espalda");
    }

    @Test
    void testListarGrupoMuscular() {
        when(repo.findAll()).thenReturn(Arrays.asList(grupoM1, grupoM2));

        List<GrupoMuscular> grupos = service.listar();

        assertEquals(2, grupos.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGuardarGrupoMuscular() {
        when(repo.save(grupoM1)).thenReturn(grupoM1);

        GrupoMuscular result = service.guardar(grupoM1);

        assertNotNull(result);
        assertEquals("Pectorales", result.getNombre());
        verify(repo, times(1)).save(grupoM1);
    }

    @Test
    void testBuscarExistenteGrupoMuscular() {
        when(repo.findById(1L)).thenReturn(Optional.of(grupoM1));

        GrupoMuscular result = service.buscar(1L);

        assertNotNull(result);
        assertEquals("Pectorales", result.getNombre());
    }

    @Test
    void testBuscarInexistenteGrupoMuscular() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        GrupoMuscular result = service.buscar(99L);

        assertNull(result);
    }

    @Test
    void testEliminarGrupoMuscular() {
        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
