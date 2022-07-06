package org.sog.files;

import java.io.InputStream;
import org.sog.persistence.enums.CanalFileServerEnum;
import org.sog.persistence.enums.ProtocoloFileServerEnum;

/**
 * implementacion para gestionar recursos al fileserver
 *
 */
public final class ConectorFileServerImpl implements ConectorFileServer {

    private static final long serialVersionUID = 1L;

    /**
     * url del servidor fileserver
     */
    private String server;

    /**
     * nombre de usuario para el fileserver
     */
    private String username;

    /**
     * password para el file server
     */
    private String password;

    /**
     * puerto
     */
    private int port;

    /**
     * ruta para el modulo
     */
    private String path;

    private ProtocoloFileServerEnum protocolo;

    private String pathCanal;
    private CanalFileServerEnum canal;

    
    
    private ConectorFileServer conectorManager;

    /**
     * Constructor por defecto, creara coneccion con los datos definidos en la
     * base de datos
     *
     * @param canal SMB/FTP
     */
    public ConectorFileServerImpl(CanalFileServerEnum canal) {
        this.canal = canal;
        protocolo = ProtocoloFileServerEnum.getProtocolo("FTP");

        System.out.println("Conectandose al fileserver con el protocolo: " + protocolo.getValue());
        obtenerDatosConeccion(protocolo);

        path = obtenetPathCanal(protocolo, this.canal);
        crearObjetoServidor(protocolo);
        conectorManager.setPath(path);

    }

    /**
     * creara una coneccion vacia, toca definir los datos uno a uno
     */
    public ConectorFileServerImpl() {
        server = "";
        username = "";
        password = "";
        port = 0;
        canal = null;
        path = "";
        protocolo = ProtocoloFileServerEnum.FTP;
        conectorManager = new ConectorLocalImpl();
    }

    /**
     * obtiene los datos apra la coneccion al fileserver basado en el protocolo
     *
     * @param protocolo
     */
    public void obtenerDatosConeccion(ProtocoloFileServerEnum protocolo) {
        if (ProtocoloFileServerEnum.FTP.getValue().equals(protocolo.getValue())) {
            server = "10.0.3.115";
            username = "admapps";
            password = "GMapps.0129";
            port = 22;
        } else if (ProtocoloFileServerEnum.LOCAL.getValue().equals(protocolo.getValue())) {
            server = "";
            username = "";
            password = "";
            port = 0;
        }
    }

    /**
     * crea una coneccion al fileserver por el protocolo definido
     *
     * @return "OK" si se estable la conección
     */
    @Override
    public String conectar() {
        return conectorManager.conectar();
    }

    /**
     * crea una coneccion al servidor usando el protocolo seleccionado y los
     * campos solicitados
     *
     * @param server
     * @param username
     * @param password
     * @param port
     * @return
     */
    @Override
    public String conectar(String server, String username, String password, int port) {
        return conectorManager.conectar(server, username, password, port);
    }

    /**
     * Cierra la coneccion al fileserver
     *
     * @return "OK" si se desconecta con exito
     */
    @Override
    public String desconectar() {
        return conectorManager.desconectar();
    }

    /**
     * Descarga un fichero del fileserver usando el protocolo definido
     *
     * @param path ruta en donde se ubica el fichero en el fileserver
     * @param fileName nombre del fichero a descargar
     * @return devuelve un inputstream, represenrta el fichero descargado
     */
    @Override
    public InputStream descargarArchivo(String path, String fileName) {
        return conectorManager.descargarArchivo(path, fileName);
    }

    /**
     * Suber un fichero a una ruta en el servidor
     *
     * @param fileStream inputStream con el fichero a subir
     * @param path ruta en el fileserver en donde se almacenara
     * @param fileName nombre con el que se almacenara en el fileserver el
     * fichero
     * @return
     */
    @Override
    public boolean subirArchivo(InputStream fileStream, String path, String fileName) {
        return conectorManager.subirArchivo(fileStream, path, fileName);
    }

    /**
     * crea un directorio en el fileserver con el protocolo indicado
     *
     * @param path ruta a crear
     * @return 0 si se creo con exito, otro numero en caso contrario
     */
    @Override
    public int crearDirectorio(String path) {
        return conectorManager.crearDirectorio(path);
    }

    /**
     * crea el objeto que se conectara al fileserver usando el protocolo
     * indicado
     *
     * @param protocolo
     */
    public void crearObjetoServidor(ProtocoloFileServerEnum protocolo) {

        if (ProtocoloFileServerEnum.FTP.getValue().equals(protocolo.getValue())) {
            conectorManager = new ConectorFTPImpl();
        } else if (ProtocoloFileServerEnum.LOCAL.getValue().equals(protocolo.getValue())) {
            conectorManager = new ConectorLocalImpl();
        }
    }

    /**
     * obtiene la ruta en el serivor de archivos, por el protocolo SMB/FTP y el
     * canal
     *
     * @param protocolo
     * @param canal
     */
    public String obtenetPathCanal(ProtocoloFileServerEnum protocolo, CanalFileServerEnum canal) {
        String path = "";

        String raiz = "/home/admapps/files/bmo/";

        String subdir = canal.getValue().concat("/");

        path = raiz.concat(subdir);

        return path;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public ProtocoloFileServerEnum getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(ProtocoloFileServerEnum protocolo) {
        this.protocolo = protocolo;
    }

    public CanalFileServerEnum getCanal() {
        return canal;
    }

    public void setCanal(CanalFileServerEnum canal) {
        this.canal = canal;
    }

    public String getPathCanal() {
        return pathCanal;
    }

    public void setPathCanal(String pathCanal) {
        this.pathCanal = pathCanal;
    }
}
