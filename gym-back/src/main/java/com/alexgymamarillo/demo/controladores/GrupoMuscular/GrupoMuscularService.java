package com.alexgymamarillo.demo.controladores.GrupoMuscular;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoMuscularService {
    @Autowired
    private final GrupoMuscularRepository repo;

    public GrupoMuscularService(GrupoMuscularRepository repo) {
        this.repo = repo;
    }

    public List<GrupoMuscular> listar() {
        return repo.findAll();
    }

    public GrupoMuscular guardar(GrupoMuscular grupo) {
        return repo.save(grupo);
    }

    public GrupoMuscular buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
