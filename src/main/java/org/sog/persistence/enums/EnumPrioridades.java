/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sog.persistence.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jjunco
 */
public enum EnumPrioridades {

    BAJA("3", "BAJA"),
    MEDIA("2", "MEDIA"),
    ALTA("1", "ALTA");

    private static final Map<String, EnumPrioridades> MY_ENUM_MAP = new HashMap<>();
    private static final Map<String, EnumPrioridades> MAP_BY_CODIGO = new HashMap<>();

    static {
        for (EnumPrioridades myEnum : values()) {
            MY_ENUM_MAP.put(myEnum.getDescripcion(), myEnum);
        }
    }

    static {
        for (EnumPrioridades myEnum : values()) {
            MAP_BY_CODIGO.put(myEnum.getCodigo(), myEnum);
        }
    }

    private final String codigo;
    private final String descripcion;

    private EnumPrioridades(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EnumPrioridades getByValue(String codigo) {
        return MY_ENUM_MAP.get(codigo);
    }

    public static EnumPrioridades getByCodigo(String codigo) {
        return MAP_BY_CODIGO.get(codigo);
    }

    @Override
    public String toString() {
        return codigo + ", " + descripcion + ';';
    }
}
