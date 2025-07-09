package com.alexgymamarillo.demo.controladores.EquipoGimnasio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/equipos")

public class EquipoGimnasioController {
    @Autowired
    private final EquipoGimnasioService service;

    public EquipoGimnasioController(EquipoGimnasioService service) {
        this.service = service;
    }
    
    // Listar todos los equipos
    @GetMapping
    public List<EquipoGimnasio> listar() {
        return service.listar();
    }

    // Buscar equipo por serial
    @GetMapping("/{serial}")
    public EquipoGimnasio buscar(@PathVariable String serial) {
        return service.buscar(serial);
    }

    // Actualizar Equipo
   @PutMapping("/{serial}")
    public ResponseEntity<EquipoGimnasio> actualizarEquipo(@PathVariable String serial, @RequestBody EquipoGimnasio detalles) {
        EquipoGimnasio existente = service.buscar(serial);
        if (existente != null) {
            existente.setNombre(detalles.getNombre());
            existente.setModelo(detalles.getModelo());
            existente.setMarca(detalles.getMarca());
            existente.setFechaAdquisicion(detalles.getFechaAdquisicion());
            existente.setEstado(detalles.getEstado());
            existente.setImagen(detalles.getImagen()); 
            existente.setPeso(detalles.getPeso());
            existente.setZona(detalles.getZona());
            existente.setTipoEquipo(detalles.getTipoEquipo());
            existente.setGruposMusculares(detalles.getGruposMusculares());
            return ResponseEntity.ok(service.guardar(existente));
        }
        return ResponseEntity.notFound().build();
    }

    //Crear Equipo
    @PostMapping
    public EquipoGimnasio guardar(@RequestBody EquipoGimnasio equipo) {
        return service.guardar(equipo);
    }

    // Eliminar Equipo
    @DeleteMapping("/{serial}")
    public void eliminar(@PathVariable String serial) {
        service.eliminar(serial);
    }
}