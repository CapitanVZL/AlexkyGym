package com.alexgymamarillo.demo.controladores.GrupoMuscular;

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

public class GrupoMuscularRepositoryTest {
    @Autowired
    private GrupoMuscularRepository grupoMuscularRepository;
    private GrupoMuscular grupoMuscular;    
    private GrupoMuscular grupoMuscular2;
    private GrupoMuscular grupoMuscular3;   
    
    @BeforeEach
    void setUp() {
        // Crear Grupo Muscular
        grupoMuscular = new GrupoMuscular();
        grupoMuscular.setNombre("Pecho");
    }

    @Test
    void guardarGrupoMuscular() {
        // Guardar en la base de datos
        GrupoMuscular grupoMuscularGuardado = grupoMuscularRepository.save(grupoMuscular);

        // Verificar que se guardó correctamente
        assertThat(grupoMuscularGuardado.getId()).isNotNull();
        assertThat(grupoMuscularGuardado.getNombre()).isEqualTo("Pecho");
    }
    
    @Test
    void buscarGrupoMuscularPorId() {
        // Guardar el grupo muscular
        GrupoMuscular grupoMuscularGuardado = grupoMuscularRepository.save(grupoMuscular);

        // Buscar por ID
        GrupoMuscular grupoMuscularPorID = grupoMuscularRepository.findById(grupoMuscularGuardado.getId()).orElse(null);

        // Verificar que se encontró correctamente
        assertThat(grupoMuscularPorID).isNotNull();
        assertThat(grupoMuscularPorID.getNombre()).isEqualTo("Pecho");
    }

    @Test
    void listarGrupoMuscular() {
        // Guardar varios grupos musculares
        grupoMuscular2 = new GrupoMuscular();
        grupoMuscular2.setNombre("Espalda");
        grupoMuscular3 = new GrupoMuscular();
        grupoMuscular3.setNombre("Piernas");
        
        grupoMuscularRepository.save(grupoMuscular2);
        grupoMuscularRepository.save(grupoMuscular3); 
        grupoMuscularRepository.save(grupoMuscular);

        // Listar todos los grupos musculares
        List<GrupoMuscular> gruposMusculares = grupoMuscularRepository.findAll();

        // Verificar que se listaron correctamente
        assertThat(gruposMusculares).hasSize(3);
    }

    @Test
    void eliminarGrupoMuscular() {
        // Guardar los grupos musculares
        GrupoMuscular grupoMuscularGuardado = grupoMuscularRepository.save(grupoMuscular);

        // Eliminar por ID
        grupoMuscularRepository.deleteById(grupoMuscularGuardado.getId());

        // Verificar que ya no existe
        GrupoMuscular grupoMuscularEliminado = grupoMuscularRepository.findById(grupoMuscularGuardado.getId()).orElse(null);
        assertThat(grupoMuscularEliminado).isNull();
    }

    @Test
    void actualizarGrupoMuscular() {
        // Guardar el grupo muscular
        GrupoMuscular grupoMuscularGuardado = grupoMuscularRepository.save(grupoMuscular);

        // Actualizar el nombre del grupo muscular
        grupoMuscularGuardado.setNombre("Hombros");
        GrupoMuscular grupoMuscularActualizado = grupoMuscularRepository.save(grupoMuscularGuardado);

        // Verificar que se actualizó correctamente
        assertThat(grupoMuscularActualizado.getNombre()).isEqualTo("Hombros");
    }
}
