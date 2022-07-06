package org.sog.files;

import java.io.InputStream;
import java.io.Serializable;
import org.sog.persistence.enums.CanalFileServerEnum;

/**
 * interface para el control de archivos al servidor
 */
public interface ConectorFileServer extends Serializable {

    /**
     * crea la conección al servidor de archivos, con los datos parametrisados
     *
     * @param server url del servidor
     * @param username nombre de usuario
     * @param password contraseña
     * @param port puerto
     * @return 'Ok' si se estable coneccón, mensaje de error en caso contrario
     */
    public String conectar(String server, String username, String password, int port);

    /**
     * crea la conección al servidor de archivos, con los datos por defecto
     *
     * @return
     */
    public String conectar();

    /**
     * se desconecta de un servidor de archivos
     *
     * @return 'OK' si se desconecta con exito
     */
    public String desconectar();

    /**
     * Descarga el archivo fileName de la ruta path del servidor de archivos
     *
     * @param path ruta en el servidor
     * @param fileName archivo a descargar
     * @return InputStream con el flujo del archivo descargado
     */
    public InputStream descargarArchivo(String path, String fileName);

    /**
     * Sube un archivo representado en un stream, fileStream, a una ruta path en
     * el servidor con el nombre fileName
     *
     * @param fileStream stream con el flujo del archivo
     * @param path ruta en el servidor a cargar el fichero
     * @param fileName nombre del fichero a cargar
     * @return True si se sube con exito, False en caso contrario
     */
    public boolean subirArchivo(InputStream fileStream, String path, String fileName);

    /**
     * Crear un directorio dentro del servidor de archivos
     *
     * @param path directorio a crear
     * @return 0 si se crea sin problema; 2: no existe; 4: Error al crear
     * verificar permisos, permisos o ya existe; 5: excepcion
     */
    public int crearDirectorio(String path);

    /**
     * Ruta por defecto en el fileserver
     *
     * @return ruta por defecto
     */
    public String getPath();

    /**
     * Define un path por defecto
     *
     * @param path
     */
    public void setPath(String path);

    /**
     * Define un canal por defecto
     *
     * @return canal por defecto
     */
    public CanalFileServerEnum getCanal();

    /**
     * Define un canal por defecto
     *
     * @param canal
     */
    public void setCanal(CanalFileServerEnum canal);

}
