package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_empleado Entidad
 *
 * @author jeisson.junco
 */
public class EBEmpleados implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_empleado;
    private int codigoTM;
    private String nombres;
    private String apellidos;
    private String documento;
    private String email;
    private String genero;
    private String zona;
    private int zonaHomologada;
    private Boolean contratado;
    private String tipoConductor;
    private int master;
    private String observaciones;
    private Boolean estado;

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getCodigoTM() {
        return codigoTM;
    }

    public void setCodigoTM(int codigoTM) {
        this.codigoTM = codigoTM;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getZonaHomologada() {
        return zonaHomologada;
    }

    public void setZonaHomologada(int zonaHomologada) {
        this.zonaHomologada = zonaHomologada;
    }

    public Boolean getContratado() {
        return contratado;
    }

    public void setContratado(Boolean contratado) {
        this.contratado = contratado;
    }

    public String getTipoConductor() {
        return tipoConductor;
    }

    public void setTipoConductor(String tipoConductor) {
        this.tipoConductor = tipoConductor;
    }

    public int getMaster() {
        return master;
    }

    public void setMaster(int master) {
        this.master = master;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
