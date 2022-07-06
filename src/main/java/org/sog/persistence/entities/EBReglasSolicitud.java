package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_reglas Entidad
 *
 * @author jeisson.junco
 */
public class EBReglasSolicitud implements Serializable {

    private int id_reglas;
    private boolean multiselect;
    private boolean isfechaFinal;
    private boolean aplicaHoraInicio;
    private boolean aplicaHoraFinal;
    private int horaInicial;
    private int horaFinal;
    private boolean rompeZona;
    private boolean adjuntoObligatorio;
    private boolean renderedAdjunto;
    private boolean laboraFestivo;
    private boolean laborarDomingo;
    private int idTipoSolicitud;

    public int getId_reglas() {
        return id_reglas;
    }

    public void setId_reglas(int id_reglas) {
        this.id_reglas = id_reglas;
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
    }

    public boolean isIsfechaFinal() {
        return isfechaFinal;
    }

    public void setIsfechaFinal(boolean isfechaFinal) {
        this.isfechaFinal = isfechaFinal;
    }

    public boolean isAplicaHoraInicio() {
        return aplicaHoraInicio;
    }

    public void setAplicaHoraInicio(boolean aplicaHoraInicio) {
        this.aplicaHoraInicio = aplicaHoraInicio;
    }

    public boolean isAplicaHoraFinal() {
        return aplicaHoraFinal;
    }

    public void setAplicaHoraFinal(boolean aplicaHoraFinal) {
        this.aplicaHoraFinal = aplicaHoraFinal;
    }

    public int getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(int horaInicial) {
        this.horaInicial = horaInicial;
    }

    public int getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(int horaFinal) {
        this.horaFinal = horaFinal;
    }

    public boolean isRompeZona() {
        return rompeZona;
    }

    public void setRompeZona(boolean rompeZona) {
        this.rompeZona = rompeZona;
    }

    public boolean isAdjuntoObligatorio() {
        return adjuntoObligatorio;
    }

    public void setAdjuntoObligatorio(boolean adjuntoObligatorio) {
        this.adjuntoObligatorio = adjuntoObligatorio;
    }

    public boolean isRenderedAdjunto() {
        return renderedAdjunto;
    }

    public void setRenderedAdjunto(boolean renderedAdjunto) {
        this.renderedAdjunto = renderedAdjunto;
    }

    public boolean isLaboraFestivo() {
        return laboraFestivo;
    }

    public void setLaboraFestivo(boolean laboraFestivo) {
        this.laboraFestivo = laboraFestivo;
    }

    public boolean isLaborarDomingo() {
        return laborarDomingo;
    }

    public void setLaborarDomingo(boolean laborarDomingo) {
        this.laborarDomingo = laborarDomingo;
    }

    public int getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(int idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    

}
