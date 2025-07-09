package com.alexgymamarillo.demo.controladores.Reportes;

import com.alexgymamarillo.demo.controladores.EquipoGimnasio.EquipoGimnasio;
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
class ReporteServiceTest {

    @Mock
    private ReporteRepository repo;

    @InjectMocks
    private ReporteService service;

    private Reporte reporte1;
    private Reporte reporte2;

    @BeforeEach
    void setUp() {
        
        EquipoGimnasio equipo = new EquipoGimnasio();
        equipo.setSerial("EQ001");
        equipo.setNombre("Bicicleta");
        equipo.setMarca("MarcaX");
        equipo.setModelo("ModeloY");
        equipo.setEstado("Operativo");

        reporte1 = new Reporte();
        reporte1.setId(1L);
        reporte1.setTitulo("Pantalla rota");
        reporte1.setDescripcion("No enciende");
        reporte1.setFechaReporte(LocalDate.of(2024, 12, 1));
        reporte1.setEquipo(equipo);

        reporte2 = new Reporte();
        reporte2.setId(2L);
        reporte2.setTitulo("Pedal suelto");
        reporte2.setDescripcion("Pedal derecho se cae");
        reporte2.setFechaReporte(LocalDate.of(2024, 12, 2));
        reporte2.setEquipo(equipo);
    }


    @Test
    void testListarReporte() {
        when(repo.findAll()).thenReturn(Arrays.asList(reporte1, reporte2));

        List<Reporte> reportes = service.listar();

        assertEquals(2, reportes.size());
    }

    @Test
    void testGuardarReporte() {
        when(repo.save(reporte1)).thenReturn(reporte1);

        Reporte result = service.guardar(reporte1);

        assertNotNull(result);
        assertEquals("Pantalla rota", result.getTitulo());
    }

    @Test
    void testBuscarExistenteReporte() {
        when(repo.findById(1L)).thenReturn(Optional.of(reporte1));

        Reporte result = service.buscar(1L);

        assertNotNull(result);
        assertEquals("Pantalla rota", result.getTitulo());
    }

    @Test
    void testBuscarInexistenteReporte() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Reporte result = service.buscar(99L);

        assertNull(result);
    }

    @Test
    void testEliminarReporte() {
        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
