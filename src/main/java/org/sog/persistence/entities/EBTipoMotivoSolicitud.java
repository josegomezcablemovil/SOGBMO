package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_tipo_motivo Entidad
 *
 * @author jjunco
 */
public class EBTipoMotivoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_tipomotivo;
    private int idTipo;
    private int idMotivo;
    private Boolean estado;

    private String nombreTipoSolicitud;
    private String nombreMotivoSolicitud;
    private int prioridadMotivo;

    public int getId_tipomotivo() {
        return id_tipomotivo;
    }

    public void setId_tipomotivo(int id_tipomotivo) {
        this.id_tipomotivo = id_tipomotivo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombreTipoSolicitud() {
        return nombreTipoSolicitud;
    }

    public void setNombreTipoSolicitud(String nombreTipoSolicitud) {
        this.nombreTipoSolicitud = nombreTipoSolicitud;
    }

    public String getNombreMotivoSolicitud() {
        return nombreMotivoSolicitud;
    }

    public void setNombreMotivoSolicitud(String nombreMotivoSolicitud) {
        this.nombreMotivoSolicitud = nombreMotivoSolicitud;
    }

    public int getPrioridadMotivo() {
        return prioridadMotivo;
    }

    public void setPrioridadMotivo(int prioridadMotivo) {
        this.prioridadMotivo = prioridadMotivo;
    }

}
