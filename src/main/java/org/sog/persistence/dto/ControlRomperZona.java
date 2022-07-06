package org.sog.persistence.dto;

import java.io.Serializable;

/**
 *
 * @author jeisson.junco
 */
public class ControlRomperZona implements Serializable {

    private boolean controlO;
    private boolean controlS;
    private boolean controlN;

    public boolean isControlO() {
        return controlO;
    }

    public void setControlO(boolean controlO) {
        this.controlO = controlO;
    }

    public boolean isControlS() {
        return controlS;
    }

    public void setControlS(boolean controlS) {
        this.controlS = controlS;
    }

    public boolean isControlN() {
        return controlN;
    }

    public void setControlN(boolean controlN) {
        this.controlN = controlN;
    }
    
    
    

}
