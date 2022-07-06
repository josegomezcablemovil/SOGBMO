package org.sog.persistence.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jeisson.junco
 */
public class PropiedadesConfiguracionDTO implements Serializable {

    private Date fechaIncial;
    private Date fechaFinal;
    private String mensajeConfirmacionAdjunto;

    public Date getFechaIncial() {
        return fechaIncial;
    }

    public void setFechaIncial(Date fechaIncial) {
        this.fechaIncial = fechaIncial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getMensajeConfirmacionAdjunto() {
        return mensajeConfirmacionAdjunto;
    }

    public void setMensajeConfirmacionAdjunto(String mensajeConfirmacionAdjunto) {
        this.mensajeConfirmacionAdjunto = mensajeConfirmacionAdjunto;
    }

}
