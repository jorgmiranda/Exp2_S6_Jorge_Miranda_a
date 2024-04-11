package com.sumativa.a.usuario;

public class Rol {
    private int id;
    private String nombreRol;
    private String descripcion;


    public Rol(int id, String nombreRol, String descripcion) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }


    public int getId() {
        return id;
    }


    public String getNombreRol() {
        return nombreRol;
    }


    public String getDescripcion() {
        return descripcion;
    }

    

    
}
