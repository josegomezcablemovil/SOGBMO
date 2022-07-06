package org.sog.controler.view.solicitudOperador;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.sog.controler.security.PlantillaController;
import org.sog.controler.view.GestionarSolicitud;
import org.sog.persistence.entities.EBSolicitudes;
import org.sog.persistence.sesion.AccesDao;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "solicitudOperador")
@ViewScoped
public class SolicitudOperador {

    private final AccesDao objAccesDao;
    private List<EBSolicitudes> listMaestro;
    private GestionarSolicitud gestionarSolicitud;
    private PlantillaController plantillaController;
    private EBSolicitudes objSolicitud;

    public SolicitudOperador() {
        listMaestro = new ArrayList<>();
        gestionarSolicitud = new GestionarSolicitud();
        plantillaController = new PlantillaController();
        objAccesDao = AccesDao.getSingletonInstance();
        objSolicitud = new EBSolicitudes();
        objSolicitud.setLimit(Boolean.TRUE);
        objSolicitud.setIdOperador(plantillaController.getIdOperador());
        listMaestro = gestionarSolicitud.findSolicitudes(objSolicitud);
    }

    public List<EBSolicitudes> getListMaestro() {
        return listMaestro;
    }

    public void setListMaestro(List<EBSolicitudes> listMaestro) {
        this.listMaestro = listMaestro;
    }

}
