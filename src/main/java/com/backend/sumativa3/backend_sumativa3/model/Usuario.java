package com.backend.sumativa3.backend_sumativa3.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Usuario {
    private int id;
    private String nombreCompleto;
    private String nombreUsuario;
    private String correoUsuario;
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private String contrasena;
    private String direccionDespacho;
    private boolean sesionIniciada;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String nombreCompleto, String nombreUsuario, String correoUsuario,
            Date fechaNacimiento, String contrasena, String direccionDespacho, boolean sesionIniciada,
            String rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.direccionDespacho = direccionDespacho;
        this.sesionIniciada = sesionIniciada;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccionDespacho() {
        return direccionDespacho;
    }

    public void setDireccionDespacho(String direccionDespacho) {
        this.direccionDespacho = direccionDespacho;
    }

    public boolean isSesionIniciada() {
        return sesionIniciada;
    }

    public void setSesionIniciada(boolean sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
    
}
