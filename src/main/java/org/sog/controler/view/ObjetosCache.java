package org.sog.controler.view;

import java.util.List;
import java.io.Serializable;
import org.sog.persistence.entities.EBEstado;
import org.sog.persistence.entities.EBPatios;
import org.sog.persistence.sesion.OperacionesBD;

public class ObjetosCache implements Serializable {

    private static ObjetosCache instacia;

    /**
     * instancia de Objeto cache para objeto singleton
     */
    private OperacionesBD operacionesDd;

    private static List<EBEstado> listaEstados;
    private static List<EBPatios> listaPatios;

    /**
     * Creates a new instance of MBMainApplication Usar getInstacia() para
     * mantener objeto singleton
     */
    private ObjetosCache() {
        listaEstados = null;
        listaPatios = null;
        operacionesDd = new OperacionesBD();
    }

    public static ObjetosCache getInstacia() {
        if (instacia == null) {
            instacia = new ObjetosCache();
        }
        return instacia;
    }

    public List<EBEstado> getListEstados() {

        if (listaEstados == null) {
            listaEstados = operacionesDd.findEstados();
        }
        return listaEstados;
    }

    public List<EBPatios> getListPatios() {

        if (listaPatios == null) {
            listaPatios = operacionesDd.findPatios();
        }
        return listaPatios;
    }

}
