package org.sog.persistence.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * sog_solicitud Entidad
 *
 * @author jjunco
 */
public class EBSolicitudes implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_solicitud;
    private String guid;
    private int idEstado;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private Date horaInicio;
    private Date horaFinal;
    private Date fechaSolicitud;
    private Date fechaSolicitudFinal;
    private int idOperador;
    private int idUsuarioCreacion;
    private int idUsuarioModificacion;
    private int idMotivoSolicitud;
    private int idTipoSolicitud;
    private String observaciones;
    private int puntoInicio;
    private int puntoFinal;
    private int puntoAlterno;
    private boolean solicitarDocumentoAdjunto;
    private boolean esSolicitudSistema;
    private int prioridad;
    private boolean obligatorio;
    private boolean romperZona;
    private String defaultZona;
    private boolean exportadoFreeway;
    private boolean mostrar = true;
    private boolean notificacion = false;

    //adiccionales
    private String nombreTipo;
    private String nombreMotivo;
    private String nombreEstado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String codigoTM;

    //busqueda
    private Date fechaSolicitudIni;
    private String fechaSolicitudIniString;
    private Date fechaSolicitudFin;
    private String fechaSolicitudFinString;
    private int tipoSolicitudfind;
    private String nombrePatioInicio;
    private String nombrePatioFinal;
    private String nombrePatioAlterno;
    private Boolean limit = false;

    private int mes;
    private int ano;

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaSolicitudFinal() {
        return fechaSolicitudFinal;
    }

    public void setFechaSolicitudFinal(Date fechaSolicitudFinal) {
        this.fechaSolicitudFinal = fechaSolicitudFinal;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(int idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public int getIdMotivoSolicitud() {
        return idMotivoSolicitud;
    }

    public void setIdMotivoSolicitud(int idMotivoSolicitud) {
        this.idMotivoSolicitud = idMotivoSolicitud;
    }

    public int getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(int idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getPuntoInicio() {
        return puntoInicio;
    }

    public void setPuntoInicio(int puntoInicio) {
        this.puntoInicio = puntoInicio;
    }

    public int getPuntoFinal() {
        return puntoFinal;
    }

    public void setPuntoFinal(int puntoFinal) {
        this.puntoFinal = puntoFinal;
    }

    public int getPuntoAlterno() {
        return puntoAlterno;
    }

    public void setPuntoAlterno(int puntoAlterno) {
        this.puntoAlterno = puntoAlterno;
    }

    public boolean isSolicitarDocumentoAdjunto() {
        return solicitarDocumentoAdjunto;
    }

    public void setSolicitarDocumentoAdjunto(boolean solicitarDocumentoAdjunto) {
        this.solicitarDocumentoAdjunto = solicitarDocumentoAdjunto;
    }

    public boolean isEsSolicitudSistema() {
        return esSolicitudSistema;
    }

    public void setEsSolicitudSistema(boolean esSolicitudSistema) {
        this.esSolicitudSistema = esSolicitudSistema;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public boolean isRomperZona() {
        return romperZona;
    }

    public void setRomperZona(boolean romperZona) {
        this.romperZona = romperZona;
    }

    public String getDefaultZona() {
        return defaultZona;
    }

    public void setDefaultZona(String defaultZona) {
        this.defaultZona = defaultZona;
    }

    public boolean isExportadoFreeway() {
        return exportadoFreeway;
    }

    public void setExportadoFreeway(boolean exportadoFreeway) {
        this.exportadoFreeway = exportadoFreeway;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public boolean isNotificacion() {
        return notificacion;
    }

    public void setNotificacion(boolean notificacion) {
        this.notificacion = notificacion;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getNombreMotivo() {
        return nombreMotivo;
    }

    public void setNombreMotivo(String nombreMotivo) {
        this.nombreMotivo = nombreMotivo;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCodigoTM() {
        return codigoTM;
    }

    public void setCodigoTM(String codigoTM) {
        this.codigoTM = codigoTM;
    }

    public Date getFechaSolicitudIni() {
        return fechaSolicitudIni;
    }

    public void setFechaSolicitudIni(Date fechaSolicitudIni) {
        this.fechaSolicitudIni = fechaSolicitudIni;
    }

    public String getFechaSolicitudIniString() {
        return fechaSolicitudIniString;
    }

    public void setFechaSolicitudIniString(String fechaSolicitudIniString) {
        this.fechaSolicitudIniString = fechaSolicitudIniString;
    }

    public Date getFechaSolicitudFin() {
        return fechaSolicitudFin;
    }

    public void setFechaSolicitudFin(Date fechaSolicitudFin) {
        this.fechaSolicitudFin = fechaSolicitudFin;
    }

    public String getFechaSolicitudFinString() {
        return fechaSolicitudFinString;
    }

    public void setFechaSolicitudFinString(String fechaSolicitudFinString) {
        this.fechaSolicitudFinString = fechaSolicitudFinString;
    }

    public int getTipoSolicitudfind() {
        return tipoSolicitudfind;
    }

    public void setTipoSolicitudfind(int tipoSolicitudfind) {
        this.tipoSolicitudfind = tipoSolicitudfind;
    }

    public String getNombrePatioInicio() {
        return nombrePatioInicio;
    }

    public void setNombrePatioInicio(String nombrePatioInicio) {
        this.nombrePatioInicio = nombrePatioInicio;
    }

    public String getNombrePatioFinal() {
        return nombrePatioFinal;
    }

    public void setNombrePatioFinal(String nombrePatioFinal) {
        this.nombrePatioFinal = nombrePatioFinal;
    }

    public String getNombrePatioAlterno() {
        return nombrePatioAlterno;
    }

    public void setNombrePatioAlterno(String nombrePatioAlterno) {
        this.nombrePatioAlterno = nombrePatioAlterno;
    }

    public Boolean getLimit() {
        return limit;
    }

    public void setLimit(Boolean limit) {
        this.limit = limit;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

}
