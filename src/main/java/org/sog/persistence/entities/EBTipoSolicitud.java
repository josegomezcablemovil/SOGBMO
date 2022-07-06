package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_tipo_motivo Entidad
 *
 * @author jjunco
 */
public class EBTipoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_tipo;
    private String nombre;
    private String descripcion;
    private int estado;
    private int empresaId;

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
