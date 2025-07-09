package com.alexgymamarillo.demo.controladores.TipoEquipo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEquipoService {
    @Autowired
    private final TipoEquipoRepository repo;

    public TipoEquipoService(TipoEquipoRepository repo) {
        this.repo = repo;
    }

    public List<TipoEquipo> listar() {
        return repo.findAll();
    }

    public TipoEquipo guardar(TipoEquipo tipo) {
        return repo.save(tipo);
    }

    public TipoEquipo buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
