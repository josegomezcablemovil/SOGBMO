package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_motivosolicitud Entidad
 *
 * @author jjunco
 */
public class EBMotivoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_motivo;
    private String nombre;
    private int prioridad;
    private int estado;
    private int empresaId;

    public int getId_motivo() {
        return id_motivo;
    }

    public void setId_motivo(int id_motivo) {
        this.id_motivo = id_motivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

}
