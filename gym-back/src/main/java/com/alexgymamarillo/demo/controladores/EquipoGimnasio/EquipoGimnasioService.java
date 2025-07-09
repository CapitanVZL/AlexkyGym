package com.alexgymamarillo.demo.controladores.EquipoGimnasio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoGimnasioService {
    @Autowired
    private EquipoGimnasioRepository repo;

    public EquipoGimnasioService(EquipoGimnasioRepository repo) {
        this.repo = repo;
    }

    public List<EquipoGimnasio> listar() {
        return repo.findAll();
    }

    public EquipoGimnasio guardar(EquipoGimnasio equipo) {
        return repo.save(equipo);
    }

    public EquipoGimnasio buscar(String serial) {
        return repo.findById(serial).orElse(null);
    }

    public void eliminar(String serial) {
        repo.deleteById(serial);
    }
}

