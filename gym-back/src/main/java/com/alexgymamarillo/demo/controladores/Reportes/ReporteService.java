package com.alexgymamarillo.demo.controladores.Reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private final ReporteRepository repo;

    public ReporteService(ReporteRepository repo) {
        this.repo = repo;
    }

    public List<Reporte> listar() {
        return repo.findAll();
    }

    public Reporte guardar(Reporte reporte) {
        return repo.save(reporte);
    }

    public Reporte buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
