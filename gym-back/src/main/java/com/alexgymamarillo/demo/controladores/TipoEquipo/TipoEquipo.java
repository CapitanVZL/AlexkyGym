package com.alexgymamarillo.demo.controladores.TipoEquipo;

import jakarta.persistence.*;

@Entity
@Table (name = "tipoequipo")
public class TipoEquipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public TipoEquipo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
