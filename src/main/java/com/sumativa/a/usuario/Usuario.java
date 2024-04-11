package com.sumativa.a.usuario;

import java.util.List;

public class Usuario {
    private int id;
    private String nombreCompleto;
    private String correo;
    private List<Rol> roles;
    private String[] direcciones;

    
    public Usuario(){
        
    }


    public Usuario(int id, String nombreCompleto, String correo, List<Rol> roles, String[] direcciones) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.roles = roles;
        this.direcciones = direcciones;
    }


    public int getId() {
        return id;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }


    public String getCorreo() {
        return correo;
    }


    public List<Rol> getRoles() {
        return roles;
    }


    public String[] getDirecciones() {
        return direcciones;
    }


    

    

}
