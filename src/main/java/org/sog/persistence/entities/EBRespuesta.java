package org.sog.persistence.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * sog_respuesta Entidad
 *
 * @author jjunco
 */
public class EBRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_respuesta;
    private Date fechaCreacion;
    private int idUsuarioCreacion;
    private String valor;
    private boolean esRespuestaOperador;
    private boolean visto;
    private int idSolicitud;
   

    public int getId_respuesta() {
        return id_respuesta;
    }

    public void setId_respuesta(int id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(int idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isEsRespuestaOperador() {
        return esRespuestaOperador;
    }

    public void setEsRespuestaOperador(boolean esRespuestaOperador) {
        this.esRespuestaOperador = esRespuestaOperador;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

}
