package org.sog.persistence.services.file.common;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Vector;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "sftPBean")
@ViewScoped
public class SFTPBean {

// variable deja uso para canal sftp
    private JSch mJschSession = null;
    private Session mSSHSession = null;

    // canal sftp
    private ChannelSftp mChannelSftp = null;

//// la función de conexión permite conectarse al servidor sftp
//     // aquí o en cualquier protocolo, recuerde alguna otra variable muy importante
//     // tiempo de espera pero en esta demostración solo demuestro con función normal.
//     // todavía tiene muchas funciones que puede agregar para sftp: crear, eliminar, configurar correctamente ... para el servidor sftp
    public boolean connect(String strHostAddress, int iPort, String strUserName, String strPassword) {
        boolean blResult = false;

        try {
// creando una nueva sesión jsch
            this.mJschSession = new JSch();

// configura el servidor sftp sin clave de verificación al iniciar sesión
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            this.mJschSession.setConfig(config);

// crear sesión con usuario, puerto host
            this.mSSHSession = mJschSession.getSession(strUserName, strHostAddress, iPort);

//configurar la clave
            this.mSSHSession.setPassword(strPassword);

// conectarse al host
            this.mSSHSession.connect();

// abrir canal sftp
            this.mChannelSftp = (ChannelSftp) this.mSSHSession.openChannel("sftp");

// conectarse a la sesión sftp
            this.mChannelSftp.connect();
            if (this.mChannelSftp != null) {
                blResult = true;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return blResult;
    }

// lista los archivos en el servidor sftp
    public Vector<LsEntry> listFile(String strPath) {
        Vector<LsEntry> vtFile = null;

        try {
            vtFile = this.mChannelSftp.ls(strPath);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return vtFile;
    }

//descargar archivo
    public boolean downloadFile(String strSftpFile, String strLocalFile) {
        boolean blResult = false;

        try {
            this.mChannelSftp.get(strSftpFile, strLocalFile);
            blResult = true;
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return blResult;
    }

//subir archivo
    public boolean uploadFile(String strLocalFile, String strSftpFile) {
        boolean blResult = false;

        try {
            this.mChannelSftp.put(strLocalFile, strSftpFile);
            blResult = true;
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return blResult;
    }

//Cerrar la sesión
    public void close() {
        try {
            this.mChannelSftp.disconnect();
        } catch (Exception exp) {

        }

        try {
            this.mSSHSession.disconnect();
        } catch (Exception exp) {

        }

        this.mChannelSftp = null;
        this.mSSHSession = null;
        this.mJschSession = null;
    }
}
