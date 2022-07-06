package org.sog.persistence.sesion;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import org.sog.persistence.entities.EBEstado;
import org.sog.persistence.entities.EBPatios;

public class OperacionesBD implements Serializable {

    private final AccesDao objAccesDao;

    public AccesDao getObjAccesDao() {
        return objAccesDao;
    }

    public OperacionesBD() {
        objAccesDao = AccesDao.getSingletonInstance();
    }

    public List<EBEstado> findEstados() {
        List<EBEstado> list = null;
        try {
            EBEstado obj = null;
            list = (List<EBEstado>) objAccesDao.find("selectEstadoSolicitud", obj, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error findEstados: " + e);
        } catch (Exception e) {
            System.out.println("Error findEstados: " + e);
        }
        return list;
    }

    public List<EBPatios> findPatios() {
        List<EBPatios> list = null;
        try {
            EBPatios obj = new EBPatios();
            list = (List<EBPatios>) objAccesDao.find("selectPatios", obj, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error selectPatios: " + e);
        } catch (Exception e) {
            System.out.println("Error selectPatios: " + e);
        }
        return list;
    }

}
