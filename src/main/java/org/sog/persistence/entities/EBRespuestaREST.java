package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_empleado Entidad
 *
 * @author jeisson.junco
 */
public class EBRespuestaREST implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean control;
    private String mensajeDeError;

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public String getMensajeDeError() {
        return mensajeDeError;
    }

    public void setMensajeDeError(String mensajeDeError) {
        this.mensajeDeError = mensajeDeError;
    }

}
