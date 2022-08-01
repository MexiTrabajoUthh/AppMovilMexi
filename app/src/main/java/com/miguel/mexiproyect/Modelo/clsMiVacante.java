package com.miguel.mexiproyect.Modelo;

public class clsMiVacante {

    String vacante;
    String turno;
    int idVacante;

    public clsMiVacante(String  vacante, String turno, int idVacante) {
        this.vacante = vacante;
        this.turno = turno;
        this.idVacante = idVacante;
    }

    public String getVacante() {
        return vacante;
    }

    public void setVacante(String vacante) {
        this.vacante = vacante;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getIdVacante() {
        return idVacante;
    }

    public void setIdVacante(int idVacante) {
        this.idVacante = idVacante;
    }


}
