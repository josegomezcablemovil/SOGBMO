package org.sog.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.sog.persistence.enums.CanalFileServerEnum;

/**
 * Conector a file server en ruta local
 *
 * @author jjunco
 */
public class ConectorLocalImpl implements ConectorFileServer {

    private String path;

    @Override
    public String conectar(String server, String username, String password, int port) {
        return "OK";
    }

    @Override
    public String conectar() {
        return "OK";
    }

    @Override
    public String desconectar() {
        return "OK";
    }

    @Override
    public InputStream descargarArchivo(String path, String fileName) {
        InputStream is = null;
        InputStream isSinSmb = null;
        try {
            String urlFile = String.format("%1$s/%2$s", path, fileName);
            File f = new File(urlFile);
            is = new FileInputStream(f);
            isSinSmb = clonarInputStream(is);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return isSinSmb;
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

    @Override
    public boolean subirArchivo(InputStream fileStream, String path, String fileName) {

        try {
            String urlFile = String.format("%1$s/%2$s", path, fileName);
            File fdir = new File(path);
            File f = new File(urlFile);
            OutputStream outs = null;

            if (!fdir.exists()) {
                fdir.mkdirs();
            }

            outs = new FileOutputStream(f);

            final byte[] buf = new byte[16 * 1024 * 1024];

            int len;
            while ((len = fileStream.read(buf)) > 0) {
                outs.write(buf, 0, len);
            }

            outs.close();
            fileStream.close();
            return true;

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public int crearDirectorio(String path) {

        try {
            File fdir = new File(path);

            if (!fdir.exists()) {
                fdir.mkdir();
                return 0;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }

    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Este metodo no se debe usar en la implementacion.
     *
     * @return
     */
    @Override
    public CanalFileServerEnum getCanal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Este metodo no se debe usar en la implementacion.
     *
     * @param canal
     */
    @Override
    public void setCanal(CanalFileServerEnum canal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
