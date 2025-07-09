package com.alexgymamarillo.demo.controladores.Zona;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaService {
    @Autowired
    private final ZonaRepository repo;

    public ZonaService(ZonaRepository repo) {
        this.repo = repo;
    }

    public List<Zona> listar() {
        return repo.findAll();
    }

    public Zona guardar(Zona zona) {
        return repo.save(zona);
    }

    public Zona buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}