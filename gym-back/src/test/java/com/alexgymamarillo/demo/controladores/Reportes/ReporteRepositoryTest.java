package com.alexgymamarillo.demo.controladores.Reportes;

import com.alexgymamarillo.demo.controladores.EquipoGimnasio.EquipoGimnasio;
import com.alexgymamarillo.demo.controladores.TipoEquipo.TipoEquipo;
import com.alexgymamarillo.demo.controladores.Zona.Zona;
import com.alexgymamarillo.demo.controladores.GrupoMuscular.GrupoMuscular;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;
    private Reporte reporte;
    private Reporte reporte2;
    private Reporte reporte3;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Crear y persistir TipoEquipo
        TipoEquipo tipoEquipo = new TipoEquipo();
        tipoEquipo.setNombre("Cardio");
        entityManager.persist(tipoEquipo);

        // Crear y persistir Zona
        Zona zona = new Zona();
        zona.setNombre("Zona Norte");
        entityManager.persist(zona);

        // Crear y persistir GrupoMuscular
        GrupoMuscular grupo = new GrupoMuscular();
        grupo.setNombre("Piernas");
        entityManager.persist(grupo);

        // Crear y persistir EquipoGimnasio con relaciones
        EquipoGimnasio equipo = new EquipoGimnasio();
        equipo.setSerial("EQ001");
        equipo.setNombre("Bicicleta Estática");
        equipo.setMarca("GymTech");
        equipo.setModelo("GT-900");
        equipo.setFechaAdquisicion(LocalDate.of(2023, 5, 10));
        equipo.setEstado("Operativo");
        equipo.setPeso(95.0);
        equipo.setImagen("bicicleta.png");
        equipo.setTipoEquipo(tipoEquipo);
        equipo.setZona(zona);
        equipo.setGruposMusculares(List.of(grupo));

        entityManager.persist(equipo);

        // Crear y guardar Reporte
        reporte = new Reporte();
        reporte.setTitulo("Revisión mensual");
        reporte.setDescripcion("La bicicleta presenta un leve ruido en el pedaleo.");
        reporte.setFechaReporte(LocalDate.now());
        reporte.setEquipo(equipo);
        entityManager.persist(reporte);
    }

    @Test
    void guardarReporte() {

        // Guardar el reporte
        Reporte reporteGuardado = reporteRepository.save(reporte);

        // Verificar
        assertThat(reporteGuardado.getId()).isNotNull();
        assertThat(reporteGuardado.getTitulo()).isEqualTo("Revisión mensual");
        assertThat(reporteGuardado.getEquipo().getSerial()).isEqualTo("EQ001");
    }

    @Test
    void buscarReportePorId() {

        // Guardar el reporte
        Reporte reporteGuardado = reporteRepository.save(reporte);

        // Buscar por ID
        Optional<Reporte> reportePorID = reporteRepository.findById(reporteGuardado.getId());
        assertThat(reportePorID).isPresent();
    }

    @Test
    void listarReportes() {
        // Guardar varios reportes
        reporte2 = new Reporte();
        reporte2.setTitulo("Mantenimiento preventivo");
        reporte2.setDescripcion("Se recomienda lubricar la cadena.");
        reporte2.setFechaReporte(LocalDate.now());
        reporte2.setEquipo(reporte.getEquipo());
        entityManager.persist(reporte2);

        reporte3 = new Reporte();
        reporte3.setTitulo("Reparación urgente");
        reporte3.setDescripcion("El freno de la bicicleta está fallando.");
        reporte3.setFechaReporte(LocalDate.now());
        reporte3.setEquipo(reporte.getEquipo());
        entityManager.persist(reporte3);

        List<Reporte> reportes = reporteRepository.findAll();
        assertThat(reportes).hasSize(3);
    }

    @Test
    void eliminarReporte() {
        // Guardar el reporte
        Reporte reporteGuardado = reporteRepository.save(reporte);

        // Eliminar por ID
        reporteRepository.deleteById(reporteGuardado.getId());

        // Verificar que ya no existe
        Optional<Reporte> reporteEliminado = reporteRepository.findById(reporteGuardado.getId());
        assertThat(reporteEliminado).isNotPresent();
    }

    @Test
    void actualizarReporte() {
        // Guardar el reporte
        Reporte reporteGuardado = reporteRepository.save(reporte);

        // Actualizar el título del reporte
        reporteGuardado.setTitulo("Revisión trimestral");
        Reporte reporteActualizado = reporteRepository.save(reporteGuardado);

        // Verificar que se actualizó correctamente
        assertThat(reporteActualizado.getTitulo()).isEqualTo("Revisión trimestral");
    }

}