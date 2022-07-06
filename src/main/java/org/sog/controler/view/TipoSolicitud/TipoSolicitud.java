package org.sog.controler.view.TipoSolicitud;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.sog.persistence.entities.EBSolicitudes;
import org.sog.persistence.entities.EBTipoSolicitud;
import org.sog.persistence.sesion.AccesDao;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "TipoSolicitud")
@ViewScoped
public class TipoSolicitud {

    private final AccesDao objAccesDao;
    private List<EBTipoSolicitud> listMaestro;
    private EBTipoSolicitud objTipoSolicitud;

    public TipoSolicitud() {
        objTipoSolicitud = new EBTipoSolicitud();
        objAccesDao = AccesDao.getSingletonInstance();
        listMaestro = findTipoSolicitudes();
    }

    public List<EBTipoSolicitud> findTipoSolicitudes() {
        List<EBTipoSolicitud> listaTipo = null;
        try {
            EBTipoSolicitud objsolicitud = new EBTipoSolicitud();
            listaTipo = (List<EBTipoSolicitud>) objAccesDao.find("selectTipoSolicitud", objsolicitud, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaTipo;
    }

    public List<EBTipoSolicitud> getListMaestro() {
        return listMaestro;
    }

    public void setListMaestro(List<EBTipoSolicitud> listMaestro) {
        this.listMaestro = listMaestro;
    }

    public EBTipoSolicitud getObjTipoSolicitud() {
        return objTipoSolicitud;
    }

    public void setObjTipoSolicitud(EBTipoSolicitud objTipoSolicitud) {
        this.objTipoSolicitud = objTipoSolicitud;
    }

}
