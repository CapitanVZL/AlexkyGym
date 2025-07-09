package com.alexgymamarillo.demo.controladores.Zona;

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
class ZonaRepositoryTest {

    @Autowired
    private ZonaRepository zonaRepository;
    private Zona zona;
    private Zona zona2;
    private Zona zona3;
    
    @BeforeEach
    void setUp() {
        // Crear zona
        zona = new Zona();
        zona.setNombre("Zona Sur");
    }

    @Test
    void guardarZona() {
        // Guardar en la base de datos
        Zona zonaGuarda = zonaRepository.save(zona);

        // Verificar que se guardó correctamente
        assertThat(zonaGuarda.getId()).isNotNull();
        assertThat(zonaGuarda.getNombre()).isEqualTo("Zona Sur");
    }

    @Test
    void buscarZonaPorId() {
        // Guardar la zona
        Zona zonaGuardada = zonaRepository.save(zona);

        // Buscar por ID
        Zona zonaPorID = zonaRepository.findById(zonaGuardada.getId()).orElse(null);

        // Verificar que se encontró correctamente
        assertThat(zonaPorID).isNotNull();
        assertThat(zonaPorID.getNombre()).isEqualTo("Zona Sur");
    }

    @Test
    void listarZonas() {
        // Guardar varias zonas
        zona2 = new Zona();
        zona2.setNombre("Zona Norte");

        zona3 = new Zona();
        zona3.setNombre("Zona Este");
        
        zonaRepository.save(zona2);
        zonaRepository.save(zona3);
        zonaRepository.save(zona);

        // Listar todas las zonas
        List<Zona> zonas = zonaRepository.findAll();

        // Verificar que se listaron correctamente
        assertThat(zonas).hasSize(3);
    }

    @Test
    void eliminarZona() {
        // Guardar la zona
        Zona zonaGuardada = zonaRepository.save(zona);

        // Eliminar por ID
        zonaRepository.deleteById(zonaGuardada.getId());

        // Verificar que ya no existe
        Zona zonaEliminada = zonaRepository.findById(zonaGuardada.getId()).orElse(null);
        assertThat(zonaEliminada).isNull();
    }

    @Test
    void actualizarZona() {
        // Guardar la zona
        Zona zonaGuardada = zonaRepository.save(zona);

        // Actualizar el nombre de la zona
        zonaGuardada.setNombre("Zona Oeste");
        Zona zonaActualizada = zonaRepository.save(zonaGuardada);

        // Verificar que se actualizó correctamente
        assertThat(zonaActualizada.getNombre()).isEqualTo("Zona Oeste");
    }
}
