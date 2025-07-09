package com.alexgymamarillo.demo.controladores.Reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")
public class ReporteController {
    @Autowired
    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reporte> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Reporte buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Reporte guardar(@RequestBody Reporte reporte) {
        return service.guardar(reporte);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable Long id, @RequestBody Reporte detalles) {
        Reporte existente = service.buscar(id);
        if (existente != null) {
            existente.setTitulo(detalles.getTitulo());
            existente.setDescripcion(detalles.getDescripcion());
            existente.setFechaReporte(detalles.getFechaReporte());
            existente.setEquipo(detalles.getEquipo());
            return ResponseEntity.ok(service.guardar(existente));
        }
        return ResponseEntity.notFound().build();
    }
}