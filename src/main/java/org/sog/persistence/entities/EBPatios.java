package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_festivos Entidad
 *
 * @author jjunco
 */
public class EBPatios implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idsog_patios;
    private String nombre;
    private Boolean estado;

    public int getIdsog_patios() {
        return idsog_patios;
    }

    public void setIdsog_patios(int idsog_patios) {
        this.idsog_patios = idsog_patios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
