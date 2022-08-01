package com.miguel.mexiproyect.Modelo;

public class clsVacanteList {
    String vacante;
    String nombreEmpleador;
    int idVacante;
    int idTrabajador;

    public clsVacanteList(String vacante, String nombreEmpleador, int idVacante, int idTrabajador) {
        this.vacante = vacante;
        this.nombreEmpleador = nombreEmpleador;
        this.idVacante = idVacante;
        this.idTrabajador = idTrabajador;
    }

    public String getVacante() {
        return vacante;
    }

    public void setVacante(String vacante) {
        this.vacante = vacante;
    }

    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    public int getIdVacante() {
        return idVacante;
    }

    public void setIdVacante(int idVacante) {
        this.idVacante = idVacante;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

}
