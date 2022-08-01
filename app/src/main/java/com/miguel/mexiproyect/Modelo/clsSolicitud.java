package com.miguel.mexiproyect.Modelo;

public class clsSolicitud {

    String nombre;
    String direccion;
    int idSolicitud;

    public clsSolicitud(String  nombre, String dirección, int idSolicitud) {
        this.nombre = nombre;
        this.direccion = dirección;
        this.idSolicitud = idSolicitud;
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

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
