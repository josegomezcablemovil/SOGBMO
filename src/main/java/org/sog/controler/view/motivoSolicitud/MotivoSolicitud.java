package org.sog.controler.view.motivoSolicitud;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.sog.persistence.entities.EBMotivoSolicitud;
import org.sog.persistence.entities.EBSolicitudes;
import org.sog.persistence.sesion.AccesDao;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "motivoSolicitud")
@ViewScoped
public class MotivoSolicitud {

    private final AccesDao objAccesDao;
    private List<EBMotivoSolicitud> listMaestro;

    public MotivoSolicitud() {
        objAccesDao = AccesDao.getSingletonInstance();
        listMaestro = findSolicitudes();
    }

    public List<EBMotivoSolicitud> findSolicitudes() {
        List<EBMotivoSolicitud> listaMotivo = null;
        try {
            EBMotivoSolicitud objmotivo = new EBMotivoSolicitud();
            listaMotivo = (List<EBMotivoSolicitud>) objAccesDao.find("selectMotivoSolicitud", objmotivo, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaMotivo;
    }

    public List<EBMotivoSolicitud> getListMaestro() {
        return listMaestro;
    }

    public void setListMaestro(List<EBMotivoSolicitud> listMaestro) {
        this.listMaestro = listMaestro;
    }

}
