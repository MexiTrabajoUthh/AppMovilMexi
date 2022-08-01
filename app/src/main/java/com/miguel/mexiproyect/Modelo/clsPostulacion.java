package com.miguel.mexiproyect.Modelo;

public class clsPostulacion {

    String nombre;
    String direccion;
    int idPostulacion;
    int claseCandidato;

    public clsPostulacion(String nombre, String direccion, int idPostulacion, int claseCandidato) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.idPostulacion = idPostulacion;
        this.claseCandidato = claseCandidato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdPostulacion() {
        return idPostulacion;
    }

    public void setIdPostulacion(int idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    public int getClaseCandidato() {
        return claseCandidato;
    }

    public void setClaseCandidato(int claseCandidato) {
        this.claseCandidato = claseCandidato;
    }
}
