package com.alexgymamarillo.demo.controladores.EquipoGimnasio;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alexgymamarillo.demo.controladores.GrupoMuscular.GrupoMuscular;
import com.alexgymamarillo.demo.controladores.Reportes.Reporte;
import com.alexgymamarillo.demo.controladores.TipoEquipo.TipoEquipo;
import com.alexgymamarillo.demo.controladores.Zona.Zona;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table (name = "equipogimnasio")
public class EquipoGimnasio {

    @Id
    private String serial;

    private String nombre;
    private String modelo;
    private String marca;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_adquisicion;
    private String estado;
    private String imagen;
    private Double peso;

    @ManyToOne
    @JoinColumn(name = "tipo_equipo_id")
    private TipoEquipo tipoEquipo;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;

    @ManyToMany
    @JoinTable(
        name = "equipogrupomuscular",
        joinColumns = @JoinColumn(name = "equipo_serial"),
        inverseJoinColumns = @JoinColumn(name = "grupo_muscular_id")
    )
    private List<GrupoMuscular> gruposMusculares = new ArrayList<>();

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<Reporte> reportes = new ArrayList<>();

    // Constructor vacío
    public EquipoGimnasio() {
    }

    // Getters y setters

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaAdquisicion() {
        return fecha_adquisicion;
    }

    public void setFechaAdquisicion(LocalDate fecha_adquisicion) {
        this.fecha_adquisicion = fecha_adquisicion;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public List<GrupoMuscular> getGruposMusculares() {
        return gruposMusculares;
    }

    public void setGruposMusculares(List<GrupoMuscular> gruposMusculares) {
        this.gruposMusculares = gruposMusculares;
    }

    public List<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }
}