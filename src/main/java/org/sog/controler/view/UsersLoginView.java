package org.sog.controler.view;

import com.lowagie.text.pdf.codec.Base64;
import java.security.Key;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.sog.persistence.entities.EBUsuario;
import org.sog.persistence.sesion.AccesDao;

@ManagedBean
public class UsersLoginView {

    private String username;
    private String password;
    private String email;
    private Date fechaActual;
    private final AccesDao objAccesDao;

    private static String ENCRYPT_KEY = "grupoMovilBase64";

    public UsersLoginView() {
        objAccesDao = AccesDao.getSingletonInstance();
    }

    public void login(ActionEvent event) throws Exception {
        EBUsuario objEBMaestro = new EBUsuario();
        objEBMaestro.setUsername(username);
        objEBMaestro.setPassword(encript(password));

        if (validarFormularioMaestro(objEBMaestro)) {
            EBUsuario usuario = new EBUsuario();
            usuario = (EBUsuario) objAccesDao.finReturnObject("selectUsuario", objEBMaestro, 1);
            if (usuario != null) {
                if (usuario.getEstado() == Boolean.TRUE) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    usuario.setUltimoIngreso(fechaActual = new Date());
                    usuario.setPassword("pass");
                    objAccesDao.edit("editIngresoLogin", usuario, 1);
                    context.getExternalContext().getSessionMap().put("user", usuario);
                    context.getExternalContext().redirect("control/");
                } else {
                    generadorDeMensages("✘ Usuario inactivo, póngase en contacto con el administrador.", "");
                    username = "";
                    password = "";
                    email = "";
                }

            } else {
                generadorDeMensages("✘ Usuario o contraseña incorrectos.", "");
            }
        }
    }

    public boolean validarFormularioMaestro(EBUsuario objEBMaestro) {
        boolean control = true;

        if (objEBMaestro != null) {

            if ("".equals(objEBMaestro.getUsername()) || objEBMaestro.getUsername()== null) {
                generadorDeMensages("✘ Se requiere Usuario", "");
                control = false;
            }

            if ("".equals(objEBMaestro.getPassword()) || objEBMaestro.getPassword() == null) {
                generadorDeMensages("✘ Se requiere Contraseña", "");
                control = false;
            }
        } else {
            generadorDeMensages("✘ Debe ingresar los datos del formulario.", "");
            control = false;
        }
        return control;
    }

    public String encript(String text) throws Exception {
        Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.encodeBytes(encrypted);
    }

    public void generadorDeMensages(String funcion, String texto) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(funcion, new FacesMessage(FacesMessage.SEVERITY_ERROR, funcion, texto));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    

}
