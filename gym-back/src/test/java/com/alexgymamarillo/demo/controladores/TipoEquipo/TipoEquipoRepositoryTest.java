package com.alexgymamarillo.demo.controladores.TipoEquipo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class TipoEquipoRepositoryTest {

    @Autowired
    private TipoEquipoRepository tipoEquipoRepository;
    private TipoEquipo tipoEquipo;
    private TipoEquipo tipoEquipo2;
    private TipoEquipo tipoEquipo3;
    
    @BeforeEach
    void setUp() {
        // Crear Tipo de Equipo
        tipoEquipo = new TipoEquipo();
        tipoEquipo.setNombre("Cardio");
    }

    @Test
    void guardarTipoEquipo() {
        // Guardar en la base de datos
        TipoEquipo tipoEquipoGuardado = tipoEquipoRepository.save(tipoEquipo);

        // Verificar que se guardó correctamente
        assertThat(tipoEquipoGuardado.getId()).isNotNull();
        assertThat(tipoEquipoGuardado.getNombre()).isEqualTo("Cardio");
    }
    
    @Test
    void buscarTipoEquipoPorId() {
        // Guardar la tipo de equipo
        TipoEquipo tipoEquipoGuardado = tipoEquipoRepository.save(tipoEquipo);

        // Buscar por ID
        TipoEquipo tipoEquipoPorID = tipoEquipoRepository.findById(tipoEquipoGuardado.getId()).orElse(null);

        // Verificar que se encontró correctamente
        assertThat(tipoEquipoPorID).isNotNull();
        assertThat(tipoEquipoPorID.getNombre()).isEqualTo("Cardio");
    }

    @Test
    void listarTipoEquipo() {
        // Guardar varios tipos de equipos
        tipoEquipo2 = new TipoEquipo();
        tipoEquipo2.setNombre("Fuerza");
        tipoEquipo3 = new TipoEquipo();
        tipoEquipo3.setNombre("Flexibilidad");
        
        tipoEquipoRepository.save(tipoEquipo2);
        tipoEquipoRepository.save(tipoEquipo3); 
        tipoEquipoRepository.save(tipoEquipo);

        // Listar todos los tipos de equipos
        List<TipoEquipo> tiposEquipos = tipoEquipoRepository.findAll();

        // Verificar que se listaron correctamente
        assertThat(tiposEquipos).hasSize(3);
    }

    @Test
    void eliminarTipoEquipo  () {
        // Guardar los tipos de equipo
        TipoEquipo tipoEquipoGuardado = tipoEquipoRepository.save(tipoEquipo);

        // Eliminar por ID
        tipoEquipoRepository.deleteById(tipoEquipoGuardado.getId());

        // Verificar que ya no existe
        TipoEquipo tipoEquipoEliminado = tipoEquipoRepository.findById(tipoEquipoGuardado.getId()).orElse(null);
        assertThat(tipoEquipoEliminado).isNull();
    }

    @Test
    void actualizarTipoEquipo() {
        // Guardar el tipo de equipo
        TipoEquipo tipoEquipoGuardado = tipoEquipoRepository.save(tipoEquipo);

        // Actualizar el nombre de la zona
        tipoEquipoGuardado.setNombre("Entrenamiento Funcional");
        TipoEquipo tipoEquipoActualizado = tipoEquipoRepository.save(tipoEquipoGuardado);

        // Verificar que se actualizó correctamente
        assertThat(tipoEquipoActualizado.getNombre()).isEqualTo("Entrenamiento Funcional");
    }

}
