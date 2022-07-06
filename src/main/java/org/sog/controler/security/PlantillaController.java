package org.sog.controler.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.sog.controler.view.GestionarSolicitud;
import org.sog.persistence.entities.EBUsuario;

/**
 *
 * @author jeisson.junco
 */
@Named
@SessionScoped
public class PlantillaController implements Serializable {

    private EBUsuario user;

    private static int idUser;
    private static int idOperador;

    public EBUsuario getUser() {
        return user;
    }

    public void setUser(EBUsuario user) {
        this.user = user;
    }

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        PlantillaController.idUser = idUser;
    }

    public static int getIdOperador() {
        return idOperador;
    }

    public static void setIdOperador(int idOperador) {
        PlantillaController.idOperador = idOperador;
    }

    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            user = (EBUsuario) context.getExternalContext().getSessionMap().get("user");
            if (user == null) {
                context.getExternalContext().redirect("../index.xhtml");
            } else {
                idUser = user.getIdsog_admon_usuarios();
                idOperador = user.getEmpleadoId();
            }
        } catch (Exception e) {
            System.out.println("Error Verificar Sesion" + e);
        }
    }

    public void redireccionar() {
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(GestionarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

}
