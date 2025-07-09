package com.alexgymamarillo.demo.controladores.TipoEquipo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/tipos")
@CrossOrigin(origins = "http://localhost:5173")
public class TipoEquipoController {
    @Autowired
    private final TipoEquipoService service;

    public TipoEquipoController(TipoEquipoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TipoEquipo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TipoEquipo buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public TipoEquipo guardar(@RequestBody TipoEquipo tipo) {
        return service.guardar(tipo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEquipo> actualizarTipo(@PathVariable Long id, @RequestBody TipoEquipo detalles) {
        TipoEquipo existente = service.buscar(id);
        if (existente != null) {
            existente.setNombre(detalles.getNombre());
            return ResponseEntity.ok(service.guardar(existente));
        }
        return ResponseEntity.notFound().build();
    }
}
