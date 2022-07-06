package org.sog.persistence.entities;

import java.io.Serializable;

/**
 *
 * @author JSON
 */
public class EBUserMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id_user_menu;
    private Integer user_id;
    private Boolean M1;
    private Boolean H2;
    private Boolean M3;
    private Boolean M4;

    public Integer getId_user_menu() {
        return id_user_menu;
    }

    public void setId_user_menu(Integer id_user_menu) {
        this.id_user_menu = id_user_menu;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Boolean getM1() {
        return M1;
    }

    public void setM1(Boolean M1) {
        this.M1 = M1;
    }

    public Boolean getH2() {
        return H2;
    }

    public void setH2(Boolean H2) {
        this.H2 = H2;
    }

    public Boolean getM3() {
        return M3;
    }

    public void setM3(Boolean M3) {
        this.M3 = M3;
    }

    public Boolean getM4() {
        return M4;
    }

    public void setM4(Boolean M4) {
        this.M4 = M4;
    }

}
