package com.alexgymamarillo.demo.controladores.EquipoGimnasio;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
class EquipoGimnasioRepositoryTest {

    @Autowired
    private EquipoGimnasioRepository equipoRepository;

    @Autowired
    private EntityManager entityManager;

    private TipoEquipo tipo;
    private Zona zona;
    private GrupoMuscular pectorales;
    private GrupoMuscular piernas;
    private EquipoGimnasio equipo;

    @BeforeEach
    void setUp() {
        // Crear y guardar entidades relacionadas
        tipo = new TipoEquipo();
        tipo.setNombre("Cardio");
        entityManager.persist(tipo);

        zona = new Zona();
        zona.setNombre("Zona Norte");
        entityManager.persist(zona);

        pectorales = new GrupoMuscular();
        pectorales.setNombre("Pectorales");
        entityManager.persist(pectorales);

        piernas = new GrupoMuscular();
        piernas.setNombre("Piernas");
        entityManager.persist(piernas);

        // Crear equipo
        equipo = new EquipoGimnasio();
        equipo.setSerial("EQ001");
        equipo.setNombre("Caminadora");
        equipo.setMarca("FitTech");
        equipo.setModelo("FTX-500");
        equipo.setFechaAdquisicion(LocalDate.of(2022, 1, 1));
        equipo.setEstado("Disponible");
        equipo.setPeso(120.0);
        equipo.setImagen("caminadora.png");
        equipo.setTipoEquipo(tipo);
        equipo.setZona(zona);
        equipo.setGruposMusculares(new ArrayList<>(List.of(pectorales, piernas))); 

        // Guardar equipo
        entityManager.persist(equipo);
    }

    @Test
    void guardarEquipo() {
        // Guardar el equipo
        EquipoGimnasio equipoGuardado = equipoRepository.save(equipo);

        // Verificar que se guardó correctamente
        assertThat(equipoGuardado.getSerial()).isEqualTo("EQ001");
        assertThat(equipoGuardado.getNombre()).isEqualTo("Caminadora");
        assertThat(equipoGuardado.getTipoEquipo().getNombre()).isEqualTo("Cardio");
        assertThat(equipoGuardado.getZona().getNombre()).isEqualTo("Zona Norte");
        assertThat(equipoGuardado.getGruposMusculares()).hasSize(2);
        assertThat(equipoGuardado.getGruposMusculares())
            .extracting(GrupoMuscular::getNombre)
            .containsExactlyInAnyOrder("Pectorales", "Piernas");
    }

    @Test
    void buscarEquipoPorId() {
        // Guardar el equipo para asegurarnos que exista
        EquipoGimnasio equipoGuardado = equipoRepository.save(equipo);
        // Buscar por ID
        Optional<EquipoGimnasio> equipoPorID = equipoRepository.findById(equipoGuardado.getSerial());

        // Verificar que se encontró correctamente
        assertThat(equipoPorID).isPresent();
        assertThat(equipoPorID.get().getNombre()).isEqualTo("Caminadora");
    }

    @Test
    void listarEquipos() {
        // Crear equipo adicional para probar la lista
        EquipoGimnasio equipo2 = new EquipoGimnasio();
        equipo2.setSerial("EQ002");
        equipo2.setNombre("Bicicleta Estática");    
        equipo2.setMarca("FitTech");
        equipo2.setModelo("FTX-600");
        equipo2.setFechaAdquisicion(LocalDate.of(2023, 5, 15));
        equipo2.setEstado("Disponible");
        equipo2.setPeso(80.0);
        equipo2.setImagen("bicicleta_estatica.png");
        equipo2.setTipoEquipo(tipo);
        equipo2.setZona(zona);
        equipo2.setGruposMusculares(new ArrayList<>(List.of(pectorales, piernas)));
        entityManager.persist(equipo2);

        // Listar todos los equipos
        List<EquipoGimnasio> equipos = equipoRepository.findAll();

        // Verificar que se obtuvo la lista correcta
        assertThat(equipos).isNotEmpty();
        assertThat(equipos).hasSize(2);
        assertThat(equipos.get(0).getNombre()).isEqualTo("Caminadora");
    }

    @Test
    void eliminarEquipo() {
        // Primero guardamos el equipo para asegurarnos que exista
        EquipoGimnasio equipoGuardado = equipoRepository.save(equipo);

        // Ahora eliminamos el equipo por su serial
        equipoRepository.deleteById(equipoGuardado.getSerial());

        // Verificamos que ya no exista en la base de datos
        Optional<EquipoGimnasio> equipoEliminado = equipoRepository.findById(equipoGuardado.getSerial());
        assertThat(equipoEliminado).isEmpty();
    }

    @Test
    void actualizarEquipo() {
        // Guardar el equipo para asegurarnos que exista
        EquipoGimnasio equipoGuardado = equipoRepository.save(equipo);

        // Actualizar el nombre del equipo
        equipoGuardado.setNombre("Caminadora Pro");
        EquipoGimnasio equipoActualizado = equipoRepository.save(equipoGuardado);

        // Verificar que se actualizó correctamente
        assertThat(equipoActualizado.getNombre()).isEqualTo("Caminadora Pro");
    }   

    

}
