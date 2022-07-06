package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_festivos  Entidad
 * 
 * @author jjunco
 */
public class EBEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_estado;
    private String nombre;

    public EBEstado(int id_estado, String nombre) {
        this.id_estado = id_estado;
        this.nombre = nombre;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
