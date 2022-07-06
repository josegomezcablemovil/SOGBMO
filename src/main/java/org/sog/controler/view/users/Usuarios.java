package org.sog.controler.view.users;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.sog.controler.security.PlantillaController;
import org.sog.controler.view.UsersLoginView;
import org.sog.persistence.entities.EBEmpleados;
import org.sog.persistence.entities.EBRol;
import org.sog.persistence.entities.EBUsuario;
import org.sog.persistence.entities.EBUsuarioMovil;
import org.sog.persistence.sesion.AccesDao;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "Usuarios")
@ViewScoped
public class Usuarios {

    private final AccesDao objAccesDao;
    private List<EBUsuario> listMaestroUsuario;
    private List<EBUsuarioMovil> listMaestroUsuarioMovil;
    private List<EBRol> listMaestroRol;
    private List<EBUsuario> listMaestroUsuarioInactive;
    private PlantillaController plantillaController;
    private UsersLoginView usersLoginView;
    private EBUsuario objUsuario;
    private EBEmpleados objEmpleado;
    private Date fechaActual;
    private int user;

    private EBUsuario objUsuarioTemp;

    public Usuarios() {
        usersLoginView = new UsersLoginView();
        plantillaController = new PlantillaController();
        objEmpleado = new EBEmpleados();
        objUsuario = new EBUsuario();
        objUsuarioTemp = new EBUsuario();
        user = plantillaController.getIdUser();
        objAccesDao = AccesDao.getSingletonInstance();
        listMaestroUsuarioInactive = findUsuariosInactive();
        listMaestroUsuario = findUsuarios();
        listMaestroUsuarioMovil = findUsuariosMovil();
        listMaestroRol = findRol();
    }

    public List<EBUsuario> findUsuarios() {
        List<EBUsuario> listaUsuarios = null;
        try {
            EBUsuario objusuario = new EBUsuario();
            listaUsuarios = (List<EBUsuario>) objAccesDao.find("selectUsuariosFind", objusuario, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaUsuarios;
    }

    public List<EBUsuarioMovil> findUsuariosMovil() {
        List<EBUsuarioMovil> listaUsuarios = null;
        try {
            EBUsuarioMovil objusuario = new EBUsuarioMovil();
            listaUsuarios = (List<EBUsuarioMovil>) objAccesDao.find("selectUsuariosFindMovil", objusuario, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaUsuarios;
    }

    public List<EBUsuario> findUsuariosInactive() {
        List<EBUsuario> listaUsuarios = null;
        try {
            EBUsuario objusuario = new EBUsuario();
            listaUsuarios = (List<EBUsuario>) objAccesDao.find("selectUsuariosInactive", objusuario, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaUsuarios;
    }

    public List<EBRol> findRol() {
        List<EBRol> listaRol = null;
        try {
            EBRol objrol = new EBRol();
            listaRol = (List<EBRol>) objAccesDao.find("selectRol", objrol, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaRol;
    }

    public void editEstado(EBUsuario item) {
        try {
            item.setUsuario_modificacion(user);
            item.setFecha_modificacion(fechaActual = new Date());
            objAccesDao.edit("editStatusUser", item, 1);
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editStatusUser :  " + ex);
        }
    }

    public void createUserNew() throws Exception {
        if (validateForm(objUsuario)) {
            try {
                objUsuario.setPassword(usersLoginView.encript(objUsuario.getPassword()));
                objUsuario.setFecha_creacion(fechaActual = new Date());
                objUsuario.setUsuario_creacion(user);
                objEmpleado = createOperator(objUsuario.getEmpleadoId());
                objUsuario.setNombres(objEmpleado.getNombres().toUpperCase() + " " + objEmpleado.getApellidos().toUpperCase());
                objUsuario.setEmail(objEmpleado.getEmail());
                objUsuario.setEstado(Boolean.TRUE);
                objAccesDao.create("createUser", objUsuario, 1);
                redireccionar();
            } catch (ParseException | SQLException ex) {
                System.out.println("Error en createUser :  " + ex);
            }

        }
    }

    public EBEmpleados createOperator(int idEmpleado) {
        EBEmpleados colaborador = new EBEmpleados();
        try {
            colaborador = (EBEmpleados) objAccesDao.finReturnObject("selectEmpleadosUnico", idEmpleado, 1);
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en createUser :  " + ex);
        }
        return colaborador;
    }

    public void cambiarContraseña() throws Exception {
        if (objUsuarioTemp != null) {
            try {
                objUsuarioTemp.setPassword(usersLoginView.encript(objUsuarioTemp.getPassword()));
                objUsuarioTemp.setUsuario_modificacion(user);
                objUsuarioTemp.setFecha_modificacion(fechaActual = new Date());
                objAccesDao.edit("editPassUserMovil", objUsuarioTemp, 1);
                redireccionar();
            } catch (ParseException | SQLException ex) {
                System.out.println("Error en editRespuestaSolicitud :  " + ex);
            }

        }
    }

    public boolean validateForm(EBUsuario obj) {
        boolean control = true;

        if (obj != null) {
            if ("".equals(obj.getPassword()) || obj.getPassword() == null) {
                generadorDeMensages("●", "Debe ingresar una contraseña.");
                control = false;
            }
            if (obj.getEmpleadoId() == 0) {
                generadorDeMensages("●", "Debe ingresar un colaborador.");
                control = false;
            }
            if (obj.getRol() == 0) {
                generadorDeMensages("●", "Debe ingresar Rol.");
                control = false;
            }
        } else {
            generadorDeMensages("●", "Debe ingresar los datos del formulario.");
            control = false;
        }

        return control;
    }

    public void redireccionar() {
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("usuarios.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword(EBUsuario usuario) {
        objUsuarioTemp = new EBUsuario();
        objUsuarioTemp = usuario;
        String openDialog = "PF('dlg2').show();";
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(openDialog);
    }

    public void createUser() {
        String openDialog = "PF('dlg1').show();";
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(openDialog);
    }

    public void generadorDeMensages(String funcion, String texto) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(funcion, new FacesMessage(FacesMessage.SEVERITY_ERROR, funcion, texto));
    }

    public List<EBUsuario> getListMaestroUsuario() {
        return listMaestroUsuario;
    }

    public void setListMaestroUsuario(List<EBUsuario> listMaestroUsuario) {
        this.listMaestroUsuario = listMaestroUsuario;
    }

    public List<EBUsuario> getListMaestroUsuarioInactive() {
        return listMaestroUsuarioInactive;
    }

    public void setListMaestroUsuarioInactive(List<EBUsuario> listMaestroUsuarioInactive) {
        this.listMaestroUsuarioInactive = listMaestroUsuarioInactive;
    }

    public List<EBRol> getListMaestroRol() {
        return listMaestroRol;
    }

    public void setListMaestroRol(List<EBRol> listMaestroRol) {
        this.listMaestroRol = listMaestroRol;
    }

    public EBUsuario getObjUsuario() {
        return objUsuario;
    }

    public void setObjUsuario(EBUsuario objUsuario) {
        this.objUsuario = objUsuario;
    }

    public EBUsuario getObjUsuarioTemp() {
        return objUsuarioTemp;
    }

    public void setObjUsuarioTemp(EBUsuario objUsuarioTemp) {
        this.objUsuarioTemp = objUsuarioTemp;
    }

    public List<EBUsuarioMovil> getListMaestroUsuarioMovil() {
        return listMaestroUsuarioMovil;
    }

    public void setListMaestroUsuarioMovil(List<EBUsuarioMovil> listMaestroUsuarioMovil) {
        this.listMaestroUsuarioMovil = listMaestroUsuarioMovil;
    }

}
