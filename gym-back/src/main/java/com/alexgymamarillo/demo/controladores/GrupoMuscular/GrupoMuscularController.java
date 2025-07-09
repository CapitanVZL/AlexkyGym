package com.alexgymamarillo.demo.controladores.GrupoMuscular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "http://localhost:5173")
public class GrupoMuscularController {
    @Autowired
    private final GrupoMuscularService service;

    public GrupoMuscularController(GrupoMuscularService service) {
        this.service = service;
    }

    @GetMapping
    public List<GrupoMuscular> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public GrupoMuscular buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public GrupoMuscular guardar(@RequestBody GrupoMuscular grupo) {
        return service.guardar(grupo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoMuscular> actualizarGrupo(@PathVariable Long id, @RequestBody GrupoMuscular detalles) {
        GrupoMuscular existente = service.buscar(id);
        if (existente != null) {
            existente.setNombre(detalles.getNombre());
            return ResponseEntity.ok(service.guardar(existente));
        }
        return ResponseEntity.notFound().build();
    }


}
