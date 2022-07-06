package org.sog.persistence.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * sog_adjunto Entidad
 *
 * @author jeisson.junco
 */
public class EBAdjunto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_adjunto;
    private String path;
    private long tamano;
    private String extension;
    private String nombre;
    private String descrpcion;
    private Date fechaCreacion;
    private int id_solicitud;

    public int getId_adjunto() {
        return id_adjunto;
    }

    public void setId_adjunto(int id_adjunto) {
        this.id_adjunto = id_adjunto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTamano() {
        return tamano;
    }

    public void setTamano(long tamano) {
        this.tamano = tamano;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrpcion() {
        return descrpcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.descrpcion = descrpcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

}
