package org.sog.persistence.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * sog_restricciones Entidad
 *
 * @author jeisson.junco
 */
public class EBRestricciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_restriccion;
    private String titulo;
    private String descripcion;
    private Date fecha_desde;
    private Date fecha_hasta;
    private Boolean editable;
    private Boolean allDay;
    private String styleClass;
    private String url;
    private int usuario_creacion;
    private Date fecha_creacion;
    private int usuario_edicion;
    private Date fecha_edicion;
    private Boolean eliminado;

    public int getId_restriccion() {
        return id_restriccion;
    }

    public void setId_restriccion(int id_restriccion) {
        this.id_restriccion = id_restriccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUsuario_creacion() {
        return usuario_creacion;
    }

    public void setUsuario_creacion(int usuario_creacion) {
        this.usuario_creacion = usuario_creacion;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public int getUsuario_edicion() {
        return usuario_edicion;
    }

    public void setUsuario_edicion(int usuario_edicion) {
        this.usuario_edicion = usuario_edicion;
    }

    public Date getFecha_edicion() {
        return fecha_edicion;
    }

    public void setFecha_edicion(Date fecha_edicion) {
        this.fecha_edicion = fecha_edicion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

   
}
