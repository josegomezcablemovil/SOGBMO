/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sog.persistence.enums;

/**
 * protocolos para la transferencias de archivos SMB/FTP
 *
 */
public enum ProtocoloFileServerEnum {
    FTP("FTP", "FTP"),
    LOCAL("LOCAL", "LOCAL");

    private String name;

    private String value;

    private ProtocoloFileServerEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * devuelve un protocolo por el nombre
     *
     * @param value
     * @return
     */
public static ProtocoloFileServerEnum getProtocolo(String value) {
        switch (value) {
            case "FTP":
                return ProtocoloFileServerEnum.FTP;
            case "LOCAL":
                return ProtocoloFileServerEnum.LOCAL;
            default:
                return ProtocoloFileServerEnum.LOCAL;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }

}
