package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_festivos Entidad
 * 
 * @author jeisson.junco
 */
public class EBFestivos implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_festivo;
    private String nombre;
    private int dia;
    private int mes;
    private int diaSemana;

    public int getId_festivo() {
        return id_festivo;
    }

    public void setId_festivo(int id_festivo) {
        this.id_festivo = id_festivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

}
