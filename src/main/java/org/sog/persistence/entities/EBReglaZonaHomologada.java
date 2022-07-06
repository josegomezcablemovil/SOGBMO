package org.sog.persistence.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * sog_reglas_zonas_homologadas Entidad
 *
 * @author jeisson.junco
 */
public class EBReglaZonaHomologada implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_reglasZonas;
    private String codigo;
    private int patioId;
    private int patioAlternoId;
    private String porDefecto;
    private Boolean aledanas;
    private Boolean operadorNorHini;
    private Date norte_Hini;
    private Boolean operadorNorHfin;
    private Date norte_Hfin;
    private Boolean operadorSurHini;
    private Date sur_Hini;
    private Boolean operadorSurHfin;
    private Date sur_Hfin;
    private Boolean operadorOccHini;
    private Date occidente_Hini;
    private Boolean operadorOccHfin;
    private Date occidente_Hfin;
    private String descripcion;
    private Boolean estado;

    private String patioNombre;
    private String patioAlternoNombre;

    public int getId_reglasZonas() {
        return id_reglasZonas;
    }

    public void setId_reglasZonas(int id_reglasZonas) {
        this.id_reglasZonas = id_reglasZonas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getPatioId() {
        return patioId;
    }

    public void setPatioId(int patioId) {
        this.patioId = patioId;
    }

    public int getPatioAlternoId() {
        return patioAlternoId;
    }

    public void setPatioAlternoId(int patioAlternoId) {
        this.patioAlternoId = patioAlternoId;
    }

    public String getPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(String porDefecto) {
        this.porDefecto = porDefecto;
    }

    public Boolean getAledanas() {
        return aledanas;
    }

    public void setAledanas(Boolean aledanas) {
        this.aledanas = aledanas;
    }

    public Boolean getOperadorNorHini() {
        return operadorNorHini;
    }

    public void setOperadorNorHini(Boolean operadorNorHini) {
        this.operadorNorHini = operadorNorHini;
    }

    public Date getNorte_Hini() {
        return norte_Hini;
    }

    public void setNorte_Hini(Date norte_Hini) {
        this.norte_Hini = norte_Hini;
    }

    public Boolean getOperadorNorHfin() {
        return operadorNorHfin;
    }

    public void setOperadorNorHfin(Boolean operadorNorHfin) {
        this.operadorNorHfin = operadorNorHfin;
    }

    public Date getNorte_Hfin() {
        return norte_Hfin;
    }

    public void setNorte_Hfin(Date norte_Hfin) {
        this.norte_Hfin = norte_Hfin;
    }

    public Boolean getOperadorSurHini() {
        return operadorSurHini;
    }

    public void setOperadorSurHini(Boolean operadorSurHini) {
        this.operadorSurHini = operadorSurHini;
    }

    public Date getSur_Hini() {
        return sur_Hini;
    }

    public void setSur_Hini(Date sur_Hini) {
        this.sur_Hini = sur_Hini;
    }

    public Boolean getOperadorSurHfin() {
        return operadorSurHfin;
    }

    public void setOperadorSurHfin(Boolean operadorSurHfin) {
        this.operadorSurHfin = operadorSurHfin;
    }

    public Date getSur_Hfin() {
        return sur_Hfin;
    }

    public void setSur_Hfin(Date sur_Hfin) {
        this.sur_Hfin = sur_Hfin;
    }

    public Boolean getOperadorOccHini() {
        return operadorOccHini;
    }

    public void setOperadorOccHini(Boolean operadorOccHini) {
        this.operadorOccHini = operadorOccHini;
    }

    public Date getOccidente_Hini() {
        return occidente_Hini;
    }

    public void setOccidente_Hini(Date occidente_Hini) {
        this.occidente_Hini = occidente_Hini;
    }

    public Boolean getOperadorOccHfin() {
        return operadorOccHfin;
    }

    public void setOperadorOccHfin(Boolean operadorOccHfin) {
        this.operadorOccHfin = operadorOccHfin;
    }

    public Date getOccidente_Hfin() {
        return occidente_Hfin;
    }

    public void setOccidente_Hfin(Date occidente_Hfin) {
        this.occidente_Hfin = occidente_Hfin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getPatioNombre() {
        return patioNombre;
    }

    public void setPatioNombre(String patioNombre) {
        this.patioNombre = patioNombre;
    }

    public String getPatioAlternoNombre() {
        return patioAlternoNombre;
    }

    public void setPatioAlternoNombre(String patioAlternoNombre) {
        this.patioAlternoNombre = patioAlternoNombre;
    }

}
