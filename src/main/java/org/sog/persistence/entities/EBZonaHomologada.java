package org.sog.persistence.entities;

import java.io.Serializable;

/**
 * sog_empleado Entidad
 *
 * @author jeisson.junco
 */
public class EBZonaHomologada implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idzonaHomologada;
    private String zonaHomologada;
    private int articuladoTotal;
    private int biarticuladoTotal;
    private int busesTotal;
    private int reglaZonaHomologadaId;

    public int getIdzonaHomologada() {
        return idzonaHomologada;
    }

    public void setIdzonaHomologada(int idzonaHomologada) {
        this.idzonaHomologada = idzonaHomologada;
    }

    public String getZonaHomologada() {
        return zonaHomologada;
    }

    public void setZonaHomologada(String zonaHomologada) {
        this.zonaHomologada = zonaHomologada;
    }

    public int getArticuladoTotal() {
        return articuladoTotal;
    }

    public void setArticuladoTotal(int articuladoTotal) {
        this.articuladoTotal = articuladoTotal;
    }

    public int getBiarticuladoTotal() {
        return biarticuladoTotal;
    }

    public void setBiarticuladoTotal(int biarticuladoTotal) {
        this.biarticuladoTotal = biarticuladoTotal;
    }

    public int getBusesTotal() {
        return busesTotal;
    }

    public void setBusesTotal(int busesTotal) {
        this.busesTotal = busesTotal;
    }

    public int getReglaZonaHomologadaId() {
        return reglaZonaHomologadaId;
    }

    public void setReglaZonaHomologadaId(int reglaZonaHomologadaId) {
        this.reglaZonaHomologadaId = reglaZonaHomologadaId;
    }

}
