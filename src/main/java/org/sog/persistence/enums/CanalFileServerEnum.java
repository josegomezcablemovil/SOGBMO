package org.sog.persistence.enums;

public enum CanalFileServerEnum {
    MOVIL("MOVIL", "movil"),
    WEB("WEB", "web");

    private String name;

    private String value;

    private CanalFileServerEnum(String name, String value) {
        this.name = name;
        this.value = value;
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
