package org.sog.controler.view.Operadores;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.sog.persistence.entities.EBEmpleados;
import org.sog.persistence.entities.EBTipoSolicitud;
import org.sog.persistence.sesion.AccesDao;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "Operadores")
@ViewScoped
public class Operadores {

    private final AccesDao objAccesDao;
    private List<EBEmpleados> listMaestroOperadores;
    private List<EBEmpleados> listMaestroMasters;
    private EBEmpleados objEmpleado;

    public Operadores() {
        objEmpleado = new EBEmpleados();
        objAccesDao = AccesDao.getSingletonInstance();
        listMaestroOperadores = findOperadores();
        listMaestroMasters = findMasters();
    }

    public List<EBEmpleados> findOperadores() {
        List<EBEmpleados> listaOperador = null;
        try {
            EBTipoSolicitud objsolicitud = new EBTipoSolicitud();
            listaOperador = (List<EBEmpleados>) objAccesDao.find("selectEmpleados", objsolicitud, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaOperador;
    }

    public List<EBEmpleados> findMasters() {
        List<EBEmpleados> listaOperador = null;
        try {
            EBTipoSolicitud objsolicitud = new EBTipoSolicitud();
            listaOperador = (List<EBEmpleados>) objAccesDao.find("selectEmpleadosMaster", objsolicitud, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaOperador;
    }

    public List<EBEmpleados> getListMaestroOperadores() {
        return listMaestroOperadores;
    }

    public void setListMaestroOperadores(List<EBEmpleados> listMaestroOperadores) {
        this.listMaestroOperadores = listMaestroOperadores;
    }

    public List<EBEmpleados> getListMaestroMasters() {
        return listMaestroMasters;
    }

    public void setListMaestroMasters(List<EBEmpleados> listMaestroMasters) {
        this.listMaestroMasters = listMaestroMasters;
    }

}
