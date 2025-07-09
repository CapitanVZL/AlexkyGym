package com.alexgymamarillo.demo.controladores.Zona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/zonas")
@CrossOrigin(origins = "http://localhost:5173")
public class ZonaController {
    @Autowired
    private final ZonaService service;

    public ZonaController(ZonaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Zona> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Zona buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Zona guardar(@RequestBody Zona zona) {
        return service.guardar(zona);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zona> actualizarZona(@PathVariable Long id, @RequestBody Zona detalles) {
        Zona existente = service.buscar(id);
        if (existente != null) {
            existente.setNombre(detalles.getNombre());
            return ResponseEntity.ok(service.guardar(existente));
        }
        return ResponseEntity.notFound().build();
    }
}