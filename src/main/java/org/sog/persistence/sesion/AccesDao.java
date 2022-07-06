package org.sog.persistence.sesion;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import java.text.ParseException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jjunco
 */
  public class AccesDao implements Serializable {

    private static ConexionBean objConexion;
    private static final long serialVersionUID = 1L;
    private String mensajeError;
    private List<Object> lista;

    private static AccesDao instance;

    private AccesDao() {
        try {
            objConexion = new ConexionBean();
        } catch (SQLException ex) {
            Logger.getLogger(AccesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static AccesDao getSingletonInstance() {
        if (instance == null) {
            instance = new AccesDao();
        }
        return instance;
    }

    public ConexionBean getObjConexion() {
        return objConexion;
    }

    public void setObjConexion(ConexionBean objConexion) {
        this.objConexion = objConexion;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public List<Object> getLista() {
        return lista;
    }

    public void setLista(List<Object> lista) {
        this.lista = lista;
    }

    public List<?> find(String function, Object obj, int db) throws SQLException,
            ParseException {
        lista = null;
        SqlSession session1 = null;
        try {
            switch (db) {
                case 1:
                    session1 = objConexion.getSqlMapper().openSession();
                    break;

                case 2:
                    session1 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            lista = session1.selectList(function, obj);

        } catch (Exception e) {
            System.out.println("Este es el error find: " + function + " : " + e);
        } finally {
            if (Objects.nonNull(session1)) {
                session1.close();
            }
        }

        return lista;
    }

    public List<?> findUnique(String function, int id, int db) throws SQLException,
            ParseException {
        lista = null;
        SqlSession session2 = null;
        try {

            switch (db) {
                case 1:
                    session2 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session2 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            lista = session2.selectList(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session2)) {
                session2.close();
            }
        }
        return lista;
    }

    public List<?> findUniqueString(String function, String id, int db)
            throws SQLException, ParseException {
        lista = null;
        SqlSession session3 = null;
        try {

            switch (db) {
                case 1:
                    session3 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session3 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            lista = session3.selectList(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session3)) {
                session3.close();
            }
        }
        return lista;
    }

    public Object finReturnObject(String function, Object obj, int db)
            throws SQLException, ParseException {

        SqlSession session4 = null;
        try {

            switch (db) {
                case 1:
                    session4 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session4 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            obj = session4.selectOne(function, obj);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session4)) {
                session4.close();
            }
        }
        return obj;
    }

    public Object findReturnObject(String function, int id, int db) throws SQLException,
            ParseException {
        Object obj = new Object();

        SqlSession session5 = null;
        try {

            switch (db) {
                case 1:
                    session5 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session5 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            obj = session5.selectOne(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session5)) {
                session5.close();
            }
        }
        return obj;
    }

    public double findUniqueValue(String function, int id, int db) throws SQLException,
            ParseException {

        SqlSession session6 = null;
        double resp = 0.0;

        try {

            switch (db) {
                case 1:
                    session6 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session6 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            resp = session6.selectOne(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session6)) {
                session6.close();
            }
        }
        return resp;
    }

    public String findUniqueValueString(String function, int id, int db)
            throws SQLException, ParseException {
        String resp = "";

        SqlSession session7 = null;
        try {

            switch (db) {
                case 1:
                    session7 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session7 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            resp = session7.selectOne(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session7)) {
                session7.close();
            }
        }
        return resp;
    }

    public long findUniqueValueLong(String function, int id, int db)
            throws SQLException, ParseException {
        long resp = 0;

        SqlSession session8 = null;
        try {

            switch (db) {
                case 1:
                    session8 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session8 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            resp = session8.selectOne(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session8)) {
                session8.close();
            }
        }
        return resp;
    }

    public int findUniqueValueInt(String function, int id, int db) throws SQLException,
            ParseException {
        int resp = 0;

        SqlSession session9 = null;
        try {

            switch (db) {
                case 1:
                    session9 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session9 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            resp = session9.selectOne(function, id);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session9)) {
                session9.close();
            }
        }
        return resp;
    }

    public int findPrimaryKeyInt(String function, Object obj, int db)
            throws SQLException, ParseException {
        int resp = 0;

        SqlSession session10 = null;
        try {

            switch (db) {
                case 1:
                    session10 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session10 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            resp = session10.selectOne(function, obj);

        } catch (Exception e) {

        } finally {
            if (Objects.nonNull(session10)) {
                session10.close();
            }
        }
        return resp;
    }

    public int count(String function, Object obj, int db)
            throws SQLException, ParseException {
        int resp = 0;
        try {
            switch (db) {
                case 1:
                    SqlSession session = objConexion.getSqlMapper().openSession();
                    resp = session.selectOne(function, obj);
                    session.close();
                    break;
                case 2:
                    SqlSession session2 = objConexion.getSqlMapperDB2().openSession();
                    resp = session2.selectOne(function, obj);
                    session2.close();
                    break;
            }
        } catch (Exception e) {

        }
        return resp;
    }

    public String create(String function, Object obj, int db) throws SQLException,
            ParseException {
        int resp = 0;
        SqlSession session11 = null;
        mensajeError = "OK";
        try {
            switch (db) {
                case 1:
                    session11 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session11 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            if (session11 != null) {
                resp = session11.insert(function, obj);
                session11.commit();
            }
        } catch (Exception e) {
            System.out.println("Error en crear:" + e);
            mensajeError = e.toString();
            resp = 0;
            if (session11 != null) {
                session11.rollback();
            }
        } finally {
            if (session11 != null) {
                session11.close();
                // this.session = objConexion.getSqlMapper().openSession();
            }

            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema. ";
            }
        }
        return mensajeError;
    }

    /**
     * Método para realizar commit a la base de datos el cual recibe como
     * parámetro la sesión.
     *
     * @param session
     * @method insertCommit
     * @author Jeisson Junco
     */
    public void insertCommit(SqlSession session) {
        if (session != null) {
            session.commit();
            session.close();
        }

    }

    /**
     * Método para realizar rollback en caso que se presente una excepción,
     * recibe como parámetro la sesión.
     *
     * @param session
     * @method insertRollback
     * @author Jeisson Junco
     */
    public void insertRollback(SqlSession session) {
        if (session != null) {
            session.rollback();
            session.close();
        }

    }

    public String createMasivo(String function, Object[] objVec, int db)
            throws SQLException, ParseException {
        int resp = 0;
        boolean bandera = true;
        SqlSession session12 = null;
        mensajeError = "OK";
        try {
            switch (db) {
                case 1:
                    session12 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session12 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }
            for (int i = 0; i < objVec.length; i++) {
                try {
                    resp = session12.insert(function, objVec[i]);
                } catch (Exception ex) {
                    mensajeError = ex.toString();
                    resp = 0;
                }
                if (resp == 0) {
                    bandera = false;
                    break;
                }
            }

            if (bandera) {
                session12.commit();
            } else {
                session12.rollback();
            }

        } catch (Exception e) {
            mensajeError = mensajeError + " - " + function + " : " + e.toString();
            resp = 0;
            session12.rollback();
        } finally {
            session12.close();
            //this.session = objConexion.getSqlMapper().openSession();
            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema.";
            }
        }
        return mensajeError;
    }

    public String createList(String[] function, Object objVec, int db)
            throws SQLException, ParseException {
        int resp = 0;
        boolean bandera = true;
        SqlSession session12 = null;
        mensajeError = "OK";
        try {
            switch (db) {
                case 1:
                    session12 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session12 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            if (session12 != null) {
                for (int i = 0; i < function.length; i++) {
                    resp = session12.insert(function[i], objVec);
                    if (resp == 0) {
                        bandera = false;
                        break;
                    }
                }
            }

            if (bandera) {
                if (session12 != null) {
                    session12.commit();
                }
            } else {
                if (session12 != null) {
                    session12.rollback();
                }
            }

        } catch (Exception e) {
            mensajeError = e.toString();
            resp = 0;
            session12.rollback();
        } finally {
            session12.close();
            //this.session = objConexion.getSqlMapper().openSession();
            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema.";
            }
        }
        return mensajeError;
    }

    public String createList(String[] function, Object[] objVec, int db)
            throws SQLException, ParseException {
        int resp = 0;
        boolean bandera = true;
        SqlSession session13 = null;
        mensajeError = "OK";
        try {
            if (db == 1) {
                session13 = objConexion.getSqlMapper().openSession();
            }
            for (int i = 0; i < function.length; i++) {
                resp = session13.insert(function[i], objVec[i]);
                if (resp == 0) {
                    bandera = false;
                    break;
                }
            }

            if (bandera) {
                session13.commit();
            } else {
                session13.rollback();
            }

        } catch (Exception e) {
            mensajeError = e.toString();
            resp = 0;
            session13.rollback();
        } finally {
            session13.close();
            //this.session = objConexion.getSqlMapper().openSession();
            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema.";
            }
        }
        return mensajeError;
    }

    public String createList(String function, Object[] objVec, int db)
            throws SQLException, ParseException {
        int resp = 0;
        boolean bandera = true;
        SqlSession session14 = null;
        mensajeError = "OK";
        try {
            if (db == 1) {
                session14 = objConexion.getSqlMapper().openSession();
            }
            for (int i = 0; i < objVec.length; i++) {

                if (objVec[i] == null) {
                    break;
                }

                resp = session14.insert(function, objVec[i]);

                if (resp == 0) {
                    bandera = false;
                    break;
                }

            }

            if (bandera) {
                session14.commit();
            } else {
                session14.rollback();
            }

        } catch (Exception e) {
            mensajeError = e.toString();
            resp = 0;
            if (session14 != null) {
                session14.rollback();
            }
        } finally {
            if (session14 != null) {
                session14.close();
            }
            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema.";
            }
        }
        return mensajeError;
    }

    public String edit(String function, Object obj, int db) throws SQLException,
            ParseException {
        int resp = 0;
        mensajeError = "OK";
        SqlSession session15 = null;
        try {
            switch (db) {
                case 1:
                    session15 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session15 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            if (session15 != null) {
                resp = session15.update(function, obj);
                session15.commit();
            }
        } catch (Exception e) {
            mensajeError = "Se ha presentado un error en la funcion: " + function + ", favor consultar el log de errores con el administrador del sistema. " + e.toString();
            resp = 0;
        } finally {
            if (session15 != null) {
                session15.close();
            }
            if (resp == 0) {
                mensajeError = "No se pudo realizar la operación";
            }
        }
        return mensajeError;
    }

    public String delete(String function, Object obj, int db) throws SQLException,
            ParseException {
        int resp = 0;
        mensajeError = "OK";
        SqlSession session16 = null;
        try {
            switch (db) {
                case 1:
                    session16 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session16 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }

            if (session16 != null) {
                resp = session16.update(function, obj);
                session16.commit();
            }
        } catch (Exception e) {
            System.err.println("Este es el error delete: " + function + " : " + e);
            mensajeError = e.toString();
            resp = 0;

            if (session16 != null) {
                session16.rollback();
            }
        } finally {
            if (session16 != null) {
                session16.close();
                //session = objConexion.getSqlMapper().openSession();
            }
            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema.";
            }
        }
        return mensajeError;
    }

    public String createArray(String function, Object obj, int cantidad, int db)
            throws SQLException, ParseException {
        int resp = 0;
        SqlSession session17 = null;
        mensajeError = "OK";
        try {
            switch (db) {
                case 1:
                    session17 = objConexion.getSqlMapper().openSession();
                    break;
                case 2:
                    session17 = objConexion.getSqlMapperDB2().openSession();
                    break;
                default:
                    break;
            }
            if (session17 != null) {
                for (int i = 0; i < cantidad; i++) {
                    resp = session17.insert(function, obj);
                }
                session17.commit();
            }
        } catch (Exception e) {
            mensajeError = e.toString();
            resp = 0;
            if (session17 != null) {
                session17.rollback();
            }
        } finally {
            if (session17 != null) {
                session17.close();
                //session = objConexion.getSqlMapper().openSession();
            }
            if (resp == 0) {
                mensajeError = "Se ha presentado un error, favor consultar el log de errores con el administrador del sistema.";
            }
        }
        return mensajeError;
    }
}
