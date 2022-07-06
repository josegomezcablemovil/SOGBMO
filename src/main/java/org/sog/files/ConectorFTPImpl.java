package org.sog.files;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.sog.persistence.enums.CanalFileServerEnum;

/**
 * Conector a file server con protocolo FTP
 *
 * @author jjunco
 */
public class ConectorFTPImpl implements ConectorFileServer {

    //Datos de conexion
    private String server;
    private int port;
    private String username;
    private String password;

    //Session al servidor ftp
    private Session session;

    //canal de apertura del canal al servidor
    private ChannelSftp channelSftp = null;
    private Channel channel = null;

    private String path;

    public ConectorFTPImpl(String server, String username, String password, int port) {
        this.server = server;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public ConectorFTPImpl() {
        this.server = "10.0.3.115";
        this.username = "admapps";
        this.password = "GMapps.0129";
        this.port = 22;
    }

    @Override
    public String conectar(String server, String username, String password, int port) {
        this.server = server;
        this.username = username;
        this.password = password;
        this.port = port;
        return conectar();
    }

    /**
     * Crea una coneccion a un servidor sftp
     *
     * @return OK, si se conecta con exito, mensaje de error de haber uno
     */
    @Override
    public String conectar() {
        String msgResult = "";
        try {
            System.out.println("Entra a conectar: " + session);
            if (session == null || !session.isConnected()) {
                JSch jsch = new JSch();

                session = jsch.getSession(username, server, port);
                session.setPassword(password);

                // Parametro para no validar key de conexion.
                session.setConfig("StrictHostKeyChecking", "no");

                session.connect();

                System.out.println("OK");

            } else {
                System.out.println("Error,La sesion ya se ha iniciada");
            }
        } catch (Exception e) {
            System.out.println("Error,Error al tratar de conectar al servidor. Error: %1$s" + e.getMessage());
        }
        return msgResult;
    }

    /**
     * Abre una conexion de los canales sftp para la comunicacion con el
     * servidor ftp
     *
     * @return OK, si se conecta con exito, mensaje de error de haber uno
     */
    public String conectarCanales() {
        String msgResult = "OK";
        try {
            // Abrimos un canal SFTP. Es como abrir una consola.

            if (channel == null || !channel.isConnected()) {
                System.out.println("channel == null || !channel.isConnected()");
                channel = this.session.openChannel("sftp");
                channel.connect();
            }

            if (channelSftp == null || !channelSftp.isConnected()) {
                System.out.println("channelSftp == null || !channelSftp.isConnected()");
                channelSftp = (ChannelSftp) channel;
            }
        } catch (Exception e) {
            System.out.println("Error!,Error al tratar de conectar al servidor: " + e.getMessage());
        }
        return msgResult;
    }

    /**
     * Se desconecta de un servidor sftp
     *
     * @return OK, si se desconecta con exito, mensaje de error de haber uno
     */
    @Override
    public String desconectar() {
        String msg = "";
        try {
            if (session != null || session.isConnected()) {
                session.disconnect();
                channel.disconnect();
                channelSftp.exit();
                channelSftp.disconnect();
                System.out.println("OK");
            } else {
                System.out.println("Error,La sesion no ha sido iniciada");
            }
        } catch (Exception e) {
            System.out.println("Error,La sesion no se pudo cerrar %1$s" + e.getMessage());
        }
        return msg;
    }

    /**
     * Desacarga un archivo del FTP
     *
     * @param path url en el servidor FTP
     * @param fileName nombre del archivo
     * @return InputStream con la representacion del archivo descargado
     */
    @Override
    public InputStream descargarArchivo(String path, String fileName) {
        try {
            if (this.session != null && this.session.isConnected()) {
                String resConec = conectarCanales();
                System.out.println("descargarArchivo resConec: " + resConec);
                if ("OK".equals(resConec)) {
                    if (validaExiste(path)) {
                        // Nos ubicamos en el directorio del FTP.
                        channelSftp.cd(path);
                        //se captura el InputStream del ftp
                        InputStream isFtp = channelSftp.get(path + "/" + fileName);

                        //se clona el inputStream
                        InputStream isSinFtp = clonarInputStream(isFtp);

                        return isSinFtp;
                    } else {
                        throw new JSchException("Error,La sesion no ha sido iniciada");
                    }
                } else {
                    throw new JSchException(resConec);
                }
            } else {
                throw new JSchException("Error,La sesion no ha sido iniciada");
            }
        } catch (JSchException | SftpException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Clona un inputStream, creando dos independientes
     *
     * @param inputStream a clonar
     * @return InputStream clonado
     */
    public static InputStream clonarInputStream(InputStream inputStream) {
        InputStream is1 = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int read = 0;
            byte[] bytes = new byte[1024 * 1024 * 2];
            while ((read = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, read);
            }
            bos.close();
            is1 = new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return is1;
    }

    /**
     * Sube un archivo al servidor FTP
     *
     * @param fileStream Inputstream, representa el archivo a subir
     * @param path ruta en donde se va a subir en el servidor
     * @param fileName nombre que se le dara al archivo en el servidor
     * @return true si se sube el archivo, false en caso contrario
     */
    @Override
    public boolean subirArchivo(InputStream fileStream, String path, String fileName) {
        try {
            if (this.session != null && this.session.isConnected()) {
                String resConec = conectarCanales();
                if ("OK".equals(resConec)) {
                    int creado = -1;
                    if (validaExiste(path)) {
                        creado = 0;
                    } else {
                        creado = crearDirectorio(path);
                    }
                    if (creado == 0) {
                        // Nos ubicamos en el directorio del FTP.
                        channelSftp.cd(path);
                        //se captura el InputStream del ftp
                        channelSftp.put(fileStream, fileName);
                    } else {
                        throw new Exception("Error, No existe el directorio en el servidor FTP");
                    }

                } else {
                    throw new Exception(resConec);
                }
            } else {
                throw new Exception("Error,La sesion no ha sido iniciada");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            try {
                fileStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return true;
    }

    /**
     * Crear un directorio dentro del servidor FTP
     *
     * @param path directorio a crear
     * @return 0 si se crea sin problema; 2: no existe; 4: Error al crear
     * verificar permisos, permisos o ya existe; 5: excepcion
     */
    public int crearDirectorioUnico(String path) {
        try {
            if (this.session != null && this.session.isConnected()) {
                String resConec = conectarCanales();
                if ("OK".equals(resConec)) {
                    if (!validaExiste(path)) {
                        channelSftp.mkdir(path);
                    }
                } else {
                    throw new Exception(resConec);
                }
            } else {
                throw new Exception("Error,La sesion no ha sido iniciada");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 5;
        }
        return 0;
    }

    /**
     * Crea la ruta dinamica
     *
     * @param path
     * @return 0 si se crea sin problema; 2: no existe; 4: Error al crear
     * verificar permisos, permisos o ya existe; 5: excepcion; 6: no creo el
     * canal
     */
    @Override
    public int crearDirectorio(String path) {
        int res = 2;
        if (this.session != null && this.session.isConnected()) {
            String resConec = conectarCanales();
            if ("OK".equals(resConec)) {
                String pathParent = pathParent(path);
                System.out.println("ruta a crear: " + path);
                if (!validaExiste(pathParent)) {
                    System.out.println("No existe parent: " + pathParent);
                    crearDirectorio(pathParent);
                }
                System.out.println("se va a crear: " + path);
                res = crearDirectorioUnico(path);
            } else {
                res = 6;
            }
        }
        return res;
    }

    /**
     * Retorna el path parent de la ruta
     *
     * @param path ruta dada
     * @return parent de la ruta
     */
    private String pathParent(String path) {
        String[] directorios = path.split("/");
        String pathParent = "/";
        for (int i = 0; i < directorios.length - 1; i++) {
            if (!directorios[i].trim().isEmpty()) {
                pathParent = pathParent.concat(directorios[i].concat("/"));
            }
        }
        return pathParent;
    }

    /**
     * Valida si el path existe en el servidor ftp la validacion se realiza
     * haciendo un ls al directorio, por esto debe estar todos los canales
     * abiertos
     *
     * @param path directorio a validar
     * @return true si existe, false si no existe, o se presenta algun problema
     * al validar
     */
    public boolean validaExiste(String path) {
        try {
            channelSftp.ls(path);
            return true;
        } catch (SftpException ex) {
            return false;
        }
    }

    /**
     * puerto para establecer la conexion
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * @see getPort()
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * servidor a conectar
     *
     * @return
     */
    public String getServer() {
        return server;
    }

    /**
     * @see getServer()
     * @param server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * nombre de usuario
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @see getUsername()
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * contrasenia
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @see getPassword()
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public CanalFileServerEnum getCanal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCanal(CanalFileServerEnum cfse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
