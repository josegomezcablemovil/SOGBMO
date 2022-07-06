package org.sog.controler.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.sog.persistence.dto.PropiedadesConfiguracionDTO;
import org.sog.persistence.entities.EBEmpleados;
import org.sog.persistence.entities.EBEstado;
import org.sog.persistence.entities.EBFestivos;
import org.sog.persistence.entities.EBMotivoSolicitud;
import org.sog.persistence.entities.EBReglasSolicitud;
import org.sog.persistence.entities.EBRespuesta;
import org.sog.persistence.entities.EBSolicitudes;
import org.sog.persistence.entities.EBTipoMotivoSolicitud;
import org.sog.persistence.entities.EBTipoSolicitud;
import org.sog.persistence.sesion.AccesDao;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.sog.controler.security.PlantillaController;
import org.sog.files.ConectorFileServer;
import org.sog.files.ConectorFileServerImpl;
import org.sog.persistence.entities.EBAdjunto;
import org.sog.persistence.entities.EBPatios;
import org.sog.persistence.entities.EBReglaZonaHomologada;
import org.sog.persistence.enums.CanalFileServerEnum;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "gestionarSolicitud")
@ViewScoped
public class GestionarSolicitud {
    
    private Date fechaActual;
    private final AccesDao objAccesDao;
    private EBSolicitudes objSolicitud;
    private EBSolicitudes selectedMaestro;
    private EBSolicitudes tempMaestro;
    private EBSolicitudes solicitudSabado;
    private EBReglasSolicitud objRegla;
    private EBRespuesta objRespueta;
    private EBSolicitudes objfindSolcitud;
    private PropiedadesConfiguracionDTO objConfig;
    private PlantillaController plantillaController;
    
    private List<EBSolicitudes> listMaestro;
    private List<EBSolicitudes> selectedMaestros;
    private List<EBSolicitudes> listFindMaestro;
    private List<EBSolicitudes> listReportMaestro;
    private List<EBTipoSolicitud> listaTipoSolicitud;
    private List<EBFestivos> listaFestivos;
    private List<EBTipoMotivoSolicitud> listaMenuSolicitud;
    private List<EBMotivoSolicitud> listaMotivos;
    private List<EBReglasSolicitud> listaReglas;
    private List<EBPatios> listPatios;
    private List<Date> listFechas;
    private List<EBRespuesta> listRespuesta;
    private List<EBEmpleados> listEmpleados;
    private List<EBEstado> listEstados;
    private List<EBAdjunto> listAdjuntos;
    private List<Date> listFechasSolicutud;
    private String respuesta;
    private String format = "yyyy/MM/dd HH:mm:ss";
    
    private int estadoSolcitud;
    private int user;
    private int userAdjunto = 0;
    private double minimoAprobado = 6.5;
    private double difHoras;
    private boolean validador;
    private StreamedContent pageStatsFile;
    
    private Date startDate;
    private Date endDate;
    private ObjetosCache oc;
    private Date now;
    
    public StreamedContent getPageStatsFile() {
        return pageStatsFile;
    }
    
    public void setPageStatsFile(StreamedContent pageStatsFile) {
        this.pageStatsFile = pageStatsFile;
    }
    
    private final static String[] prioridades;
    
    public GestionarSolicitud() {
        
        plantillaController = new PlantillaController();
        selectedMaestros = new ArrayList<>();
        user = plantillaController.getIdUser();
        respuesta = "";
        tempMaestro = new EBSolicitudes();
        objRespueta = new EBRespuesta();
        objRegla = new EBReglasSolicitud();
        selectedMaestro = new EBSolicitudes();
        objSolicitud = new EBSolicitudes();
        objfindSolcitud = new EBSolicitudes();
        objConfig = new PropiedadesConfiguracionDTO();
        listAdjuntos = new ArrayList<>();
        listMaestro = new ArrayList<>();
        listFindMaestro = new ArrayList<>();
        listReportMaestro = new ArrayList<>();
        listaMotivos = new ArrayList<>();
        listFechas = new ArrayList<>();
        listEmpleados = new ArrayList<>();
        solicitudSabado = new EBSolicitudes();
        listFechasSolicutud = new ArrayList<>();
        objAccesDao = AccesDao.getSingletonInstance();
        oc = ObjetosCache.getInstacia();
        
        now = new Date();
        objSolicitud.setAno(2022);
        listMaestro = findSolicitudes(objSolicitud);
        this.listEstados = oc.getListEstados();
        this.listPatios = oc.getListPatios();
        
    }
    
    static {
        prioridades = new String[3];
        prioridades[0] = "1";
        prioridades[1] = "2";
        prioridades[2] = "3";
    }
    
    public void crearSolicitud() {
        for (EBReglasSolicitud regla : listaReglas) {
            if (regla.getIdTipoSolicitud() == objSolicitud.getIdTipoSolicitud()) {
                
                if (regla.isMultiselect()) {
                    for (Date fechaSolicitud1 : listFechasSolicutud) {
                        objSolicitud.setFechaSolicitud(null);
                        objSolicitud.setId_solicitud(0);
                        objSolicitud.setFechaSolicitud(fechaSolicitud1);
                        crearSolicitudInsert();
                    }
                    if (validador) {
                        limpiarObjetos();
                        redireccionar();
                    }
                    
                } else {
                    crearSolicitudInsert();
                    if (validador) {
                        limpiarObjetos();
                        redireccionar();
                    }
                }
                break;
            }
            
        }
    }
    
    public Boolean crearSolicitudInsert() {
        validador = true;
        if (validarFormularioMaestro(objSolicitud)) {
            try {
                objSolicitud.setFechaCreacion(fechaActual = new Date());
                objSolicitud.setEsSolicitudSistema(true);
                objSolicitud.setIdEstado(4);
                objSolicitud.setObligatorio(false);
                objSolicitud.setIdUsuarioCreacion(user);
                for (EBMotivoSolicitud motivo : listaMotivos) {
                    if (motivo.getId_motivo() == objSolicitud.getIdMotivoSolicitud()) {
                        objSolicitud.setPrioridad(motivo.getPrioridad());
                        break;
                    }
                }
                
                objAccesDao.create("createSolicitudes", objSolicitud, 1);
                
                objSolicitud.setGuid(crearGuid(objSolicitud.getId_solicitud()));
                objAccesDao.edit("editGuidsolicitud", objSolicitud, 1);
                if ((objRegla.isLaborarDomingo() && objSolicitud.getFechaSolicitud() != null)) {
                    if (objSolicitud.getFechaSolicitud().getDay() == 0) {
                        solicitudDescansoFestivo();
                    }
                    
                    if (objRegla.isLaboraFestivo()) {
                        solicitudDescansoFestivo();
                    }
                }
                //redireccionar();

            } catch (ParseException | SQLException ex) {
                System.out.println("Error en createSolicitud :  " + ex);
            }
        } else {
            validador = false;
        }
        return validador;
    }
    
    public boolean validarFormularioMaestro(EBSolicitudes objEBMaestro) {
        boolean control = true;
        
        if (objEBMaestro != null) {
            
            for (EBReglasSolicitud regla : listaReglas) {
                if (regla.getIdTipoSolicitud() == objSolicitud.getIdTipoSolicitud()) {
                    objRegla = new EBReglasSolicitud();
                    objRegla = regla;
                    
                    if (objRegla.isAplicaHoraFinal() && objSolicitud.getHoraFinal() != null && objSolicitud.getIdOperador() != 0) {
                        for (EBEmpleados empleado : listEmpleados) {
                            if (empleado.getId_empleado() == objSolicitud.getIdOperador()) {
                                try {
                                    EBReglaZonaHomologada zonaHomologada;
                                    zonaHomologada = (EBReglaZonaHomologada) objAccesDao.findReturnObject("selectZonaHomologada", empleado.getZonaHomologada(), 1);
                                    //objSolicitud.setPuntoInicio(zonaHomologada.getPatioId());
                                    //objSolicitud.setPuntoAlterno(zonaHomologada.getPatioAlternoId());
                                    double horaIngresada = Double.parseDouble(objSolicitud.getHoraFinal().getHours() + "." + objSolicitud.getHoraFinal().getMinutes());
                                    if (objSolicitud.isRomperZona()) {
                                        Date fecha = new Date();
                                        fecha.setHours(0);
                                        fecha.setMinutes(0);
                                        objSolicitud.setHoraInicio(fecha);
                                    } else {
                                        switch (zonaHomologada.getPorDefecto()) {
                                            case "O":
                                                
                                                double horalimiteSalidaO = Double.parseDouble(zonaHomologada.getOccidente_Hfin().getHours() + "." + zonaHomologada.getOccidente_Hfin().getMinutes());
                                                double horalimiteEntradaO = Double.parseDouble(zonaHomologada.getOccidente_Hini().getHours() + "." + zonaHomologada.getOccidente_Hini().getMinutes());
                                                if (zonaHomologada.getOperadorOccHini()) {
                                                    if ((horaIngresada >= horalimiteEntradaO) && (horaIngresada <= horalimiteSalidaO)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraFinal(), -6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraInicio(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraInicio(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora < horalimiteEntradaO || calculoHora > horalimiteSalidaO) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraInicio(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "La hora seleccionada no cumple tu zona homologada.");
                                                        control = false;
                                                    }
                                                } else {
                                                    if ((horaIngresada <= 24 && horaIngresada <= horalimiteEntradaO && horaIngresada >= 0) || (horaIngresada <= 24 && horaIngresada >= horalimiteSalidaO && horaIngresada >= 0)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraFinal(), -6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraInicio(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraInicio(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora < horalimiteSalidaO || calculoHora > horalimiteSalidaO) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraInicio(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "La hora seleccionada no aplica a la homologación horaria.");
                                                        control = false;
                                                    }
                                                }
                                                
                                                break;
                                            
                                            case "S":
                                                
                                                double horalimiteSalidaS = Double.parseDouble(zonaHomologada.getSur_Hfin().getHours() + "." + zonaHomologada.getSur_Hfin().getMinutes());
                                                double horalimiteEntradaS = Double.parseDouble(zonaHomologada.getSur_Hini().getHours() + "." + zonaHomologada.getSur_Hini().getMinutes());
                                                if (zonaHomologada.getOperadorSurHini()) {
                                                    if ((horaIngresada >= horalimiteEntradaS) && (horaIngresada <= horalimiteSalidaS)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraFinal(), -6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraInicio(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraInicio(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora < horalimiteEntradaS || calculoHora > horalimiteSalidaS) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraInicio(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "La hora seleccionada no cumple tu zona homologada.");
                                                        control = false;
                                                    }
                                                } else {
                                                    if ((horaIngresada <= 24 && horaIngresada <= horalimiteEntradaS && horaIngresada >= 0) || (horaIngresada <= 24 && horaIngresada >= horalimiteSalidaS && horaIngresada >= 0)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraFinal(), -6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraInicio(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraInicio(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora < horalimiteSalidaS) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraInicio(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "La hora seleccionada no aplica a la homologación horaria.");
                                                        control = false;
                                                    }
                                                }
                                                
                                                break;
                                            
                                            case "N":
                                                
                                                double horalimiteSalidaN = Double.parseDouble(zonaHomologada.getNorte_Hfin().getHours() + "." + zonaHomologada.getNorte_Hfin().getMinutes());
                                                double horalimiteEntradaN = Double.parseDouble(zonaHomologada.getNorte_Hini().getHours() + "." + zonaHomologada.getNorte_Hini().getMinutes());
                                                if (zonaHomologada.getOperadorSurHini()) {
                                                    if ((horaIngresada >= horalimiteEntradaN) && (horaIngresada <= horalimiteSalidaN)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraFinal(), -6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraInicio(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraInicio(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora < horalimiteEntradaN || calculoHora > horalimiteSalidaN) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraInicio(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "La hora seleccionada no cumple tu zona homologada.");
                                                        control = false;
                                                    }
                                                } else {
                                                    if ((horaIngresada <= 24 && horaIngresada <= horalimiteEntradaN && horaIngresada >= 0) || (horaIngresada <= 24 && horaIngresada >= horalimiteSalidaN && horaIngresada >= 0)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraFinal(), -6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraInicio(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraInicio(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora < horalimiteSalidaN) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraInicio(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "La hora seleccionada no aplica a la homologación horaria.");
                                                        control = false;
                                                    }
                                                }
                                                
                                                break;
                                            
                                        }
                                        objSolicitud.setDefaultZona(zonaHomologada.getPorDefecto());
                                    }
                                } catch (ParseException | SQLException ex) {
                                    System.out.println("Error en createSolicitud :  " + ex);
                                }
                                break;
                            }
                        }
                    }
                    
                    if (objRegla.isAplicaHoraInicio() && objSolicitud.getHoraInicio() != null && objSolicitud.getIdOperador() != 0) {
                        for (EBEmpleados empleado : listEmpleados) {
                            if (empleado.getId_empleado() == objSolicitud.getIdOperador()) {
                                try {
                                    EBReglaZonaHomologada zonaHomologada;
                                    zonaHomologada = (EBReglaZonaHomologada) objAccesDao.findReturnObject("selectZonaHomologada", empleado.getZonaHomologada(), 1);
                                    // objSolicitud.setPuntoFinal(zonaHomologada.getPatioId());
                                    // objSolicitud.setPuntoAlterno(zonaHomologada.getPatioAlternoId());
                                    double horaIngresada = Double.parseDouble(objSolicitud.getHoraInicio().getHours() + "." + objSolicitud.getHoraInicio().getMinutes());
                                    if (objSolicitud.isRomperZona()) {
                                        Date fecha = new Date();
                                        fecha.setHours(0);
                                        fecha.setMinutes(0);
                                        objSolicitud.setHoraFinal(fecha);
                                    } else {
                                        switch (zonaHomologada.getPorDefecto()) {
                                            case "O":
                                                
                                                double horalimiteSalidaO = Double.parseDouble(zonaHomologada.getOccidente_Hfin().getHours() + "." + zonaHomologada.getOccidente_Hfin().getMinutes());
                                                double horalimiteEntradaO = Double.parseDouble(zonaHomologada.getOccidente_Hini().getHours() + "." + zonaHomologada.getOccidente_Hini().getMinutes());
                                                if (zonaHomologada.getOperadorOccHini()) {
                                                    if ((horaIngresada >= horalimiteEntradaO) && (horaIngresada <= horalimiteSalidaO)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraInicio(), 6, 30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraFinal(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraFinal(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora > horalimiteSalidaO || calculoHora < horalimiteEntradaO) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraFinal(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "La hora seleccionada no cumple tu zona homologada.");
                                                        control = false;
                                                    }
                                                } else {
                                                    if ((horaIngresada <= 24 && horaIngresada <= horalimiteEntradaO && horaIngresada >= 0) || (horaIngresada <= 24 && horaIngresada >= horalimiteSalidaO && horaIngresada >= 0)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraInicio(), 6, 30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraFinal(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraFinal(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora > horalimiteEntradaO) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraFinal(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getOccidente_Hini().getHours() + ":" + zonaHomologada.getOccidente_Hini().getMinutes() + " - " + zonaHomologada.getOccidente_Hfin().getHours() + ":" + zonaHomologada.getOccidente_Hfin().getMinutes(), "La hora seleccionada no aplica a la homologación horaria.");
                                                        control = false;
                                                    }
                                                }
                                                
                                                break;
                                            
                                            case "S":
                                                
                                                double horalimiteSalidaS = Double.parseDouble(zonaHomologada.getSur_Hfin().getHours() + "." + zonaHomologada.getSur_Hfin().getMinutes());
                                                double horalimiteEntradaS = Double.parseDouble(zonaHomologada.getSur_Hini().getHours() + "." + zonaHomologada.getSur_Hini().getMinutes());
                                                if (zonaHomologada.getOperadorSurHini()) {
                                                    if ((horaIngresada >= horalimiteEntradaS) && (horaIngresada <= horalimiteSalidaS)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraInicio(), 6, 30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraFinal(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraFinal(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora > horalimiteSalidaS || calculoHora < horalimiteEntradaS) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraFinal(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "La hora seleccionada no cumple tu zona homologada.");
                                                        control = false;
                                                    }
                                                } else {
                                                    if ((horaIngresada <= 24 && horaIngresada <= horalimiteEntradaS && horaIngresada >= 0) || (horaIngresada <= 24 && horaIngresada >= horalimiteSalidaS && horaIngresada >= 0)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraInicio(), 6, -30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraFinal(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraFinal(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora > horalimiteEntradaS) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraFinal(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getSur_Hini().getHours() + ":" + zonaHomologada.getSur_Hini().getMinutes() + " - " + zonaHomologada.getSur_Hfin().getHours() + ":" + zonaHomologada.getSur_Hfin().getMinutes(), "La hora seleccionada no aplica a la homologación horaria.");
                                                        control = false;
                                                    }
                                                }
                                                
                                                break;
                                            
                                            case "N":
                                                
                                                double horalimiteSalidaN = Double.parseDouble(zonaHomologada.getNorte_Hfin().getHours() + "." + zonaHomologada.getNorte_Hfin().getMinutes());
                                                double horalimiteEntradaN = Double.parseDouble(zonaHomologada.getNorte_Hini().getHours() + "." + zonaHomologada.getNorte_Hini().getMinutes());
                                                if (zonaHomologada.getOperadorSurHini()) {
                                                    if ((horaIngresada >= horalimiteEntradaN) && (horaIngresada <= horalimiteSalidaN)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraInicio(), 6, 30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraFinal(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraFinal(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora > horalimiteSalidaN || calculoHora < horalimiteEntradaN) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraFinal(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "La hora seleccionada no cumple tu zona homologada.");
                                                        control = false;
                                                    }
                                                } else {
                                                    if ((horaIngresada <= 24 && horaIngresada <= horalimiteEntradaN && horaIngresada >= 0) || (horaIngresada <= 24 && horaIngresada >= horalimiteSalidaN && horaIngresada >= 0)) {
                                                        Date calculoHoras = sumarRestarHorasFecha(objSolicitud.getHoraInicio(), 6, 30);
                                                        if (zonaHomologada.getAledanas()) {
                                                            objSolicitud.setHoraFinal(calculoHoras);
                                                        } else {
                                                            Date fecha = new Date();
                                                            fecha.setHours(0);
                                                            fecha.setMinutes(0);
                                                            objSolicitud.setHoraFinal(fecha);
                                                        }
                                                        double calculoHora = Double.parseDouble(calculoHoras.getHours() + "." + calculoHoras.getMinutes());
                                                        if (calculoHora > horalimiteEntradaN) {
                                                            generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "No cumple con el límite de horas establecido.");
                                                            control = false;
                                                            objSolicitud.setHoraFinal(null);
                                                        }
                                                    } else {
                                                        generadorDeMensages("● Franja horaria: " + zonaHomologada.getNorte_Hini().getHours() + ":" + zonaHomologada.getNorte_Hini().getMinutes() + " - " + zonaHomologada.getNorte_Hfin().getHours() + ":" + zonaHomologada.getNorte_Hfin().getMinutes(), "La hora seleccionada no aplica a la homologación horaria.");
                                                        control = false;
                                                    }
                                                }
                                                
                                                break;
                                            
                                        }
                                        objSolicitud.setDefaultZona(zonaHomologada.getPorDefecto());
                                    }
                                } catch (ParseException | SQLException ex) {
                                    System.out.println("Error en createSolicitud :  " + ex);
                                }
                                break;
                            }
                        }
                    }
                    
                    if (objRegla.isLaborarDomingo() && objSolicitud.getFechaSolicitud() != null) {
                        if (objSolicitud.getFechaSolicitud().getDay() != 0) {
                            generadorDeMensages("●", "No se ha seleccionado el día domingo en el calendario.");
                            control = false;
                        }
                    }
                    
                    if (objRegla.isLaboraFestivo()) {
                        EBFestivos validarFestivo = new EBFestivos();
                        validarFestivo.setMes(objSolicitud.getFechaSolicitud().getMonth());
                        validarFestivo.setDia(objSolicitud.getFechaSolicitud().getDate());
                        try {
                            EBFestivos resultadoFestivo = (EBFestivos) objAccesDao.finReturnObject("selectfestivos", validarFestivo, 1);
                            if (resultadoFestivo == null) {
                                generadorDeMensages("●", "Debe ingresar un día festivo.  ");
                                control = false;
                            }
                        } catch (ParseException | SQLException ex) {
                            System.out.println("Error en createSolicitud :  " + ex);
                        }
                        
                    }
                    
                    if (objRegla.isAplicaHoraInicio() && objSolicitud.getHoraInicio() == null) {
                        generadorDeMensages("●", "Debe Ingresar hora de inicio.");
                        control = false;
                    }
                    
                    if (objRegla.isAplicaHoraFinal() && objSolicitud.getHoraFinal() == null) {
                        generadorDeMensages("●", "Debe Ingresar hora de salida.");
                        control = false;
                    }
                    break;
                }
            }
            if (objEBMaestro.getIdOperador() == 0) {
                generadorDeMensages("●", "Debe ingresar el Operador.");
                control = false;
            }
            
            if (objEBMaestro.getIdTipoSolicitud() == 0) {
                generadorDeMensages("●", "Debe ingresar el Tipo de solicitud.");
                control = false;
            }
            
            if (objEBMaestro.getIdMotivoSolicitud() == 0) {
                generadorDeMensages("●", "Debe ingresar el Motivo de la solicitud.");
                control = false;
            }
            
            if (objEBMaestro.getFechaSolicitud() == null) {
                generadorDeMensages("●", "Ingrese Fecha de la solicitud.");
                control = false;
            } else {
                objEBMaestro.setFechaSolicitud(sumarRestarMinutosFecha(objEBMaestro.getFechaSolicitud(), 1));
                if (objEBMaestro.getFechaSolicitud().before(objConfig.getFechaIncial())
                        || objEBMaestro.getFechaSolicitud().after(objConfig.getFechaFinal())
                        || objEBMaestro.getFechaSolicitud().equals(objConfig.getFechaIncial())
                        || objEBMaestro.getFechaSolicitud().equals(objConfig.getFechaFinal())) {
                    generadorDeMensages("●", "Ingrese una fecha de solicitud valida.");
                    control = false;
                }
                
            }
            
            if ("".equals(objEBMaestro.getObservaciones()) || objEBMaestro.getObservaciones() == null) {
                generadorDeMensages("●", "Ingrese una breve descripción de la solicitud.");
                control = false;
            }
            
        } else {
            generadorDeMensages("●", "Debe ingresar los datos del formulario.");
            control = false;
        }
        return control;
    }
    
    public void solicitudDescansoFestivo() {
        try {
            solicitudSabado = new EBSolicitudes();
            solicitudSabado = objSolicitud;
            solicitudSabado.setId_solicitud(0);
            solicitudSabado.setMostrar(Boolean.FALSE);
            solicitudSabado.setFechaSolicitud(calcularFecha(objSolicitud.getFechaSolicitud(), Calendar.DAY_OF_YEAR, -1, 1));
            solicitudSabado.setIdTipoSolicitud(1);
            solicitudSabado.setIdMotivoSolicitud(26);
            solicitudSabado.setObservaciones("Se asigna descanso el día sábado, motivo laborar Domingo o festivo.");
            objAccesDao.create("createSolicitudes", solicitudSabado, 1);
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editAdjuntoSolicitud :  " + ex);
        }
        
    }
    
    public static Date getDifferenceBetwenDates(Date dateInicio, Date dateFinal) {
        long milliseconds = dateFinal.getTime() - dateInicio.getTime();
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, seconds);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }
    
    public void accionDialog(String dlg) {
        limpiarObjetos();
        listaTipoSolicitud = findTipoSolicitud();
        listaMenuSolicitud = findTipoMotivoSolicitud();
        listaReglas = findReglasSolicitud();
        listaFestivos = findFestivos();
        listEmpleados = findEmpleados();
        ReglaFechas();
        String openDialog = "PF('" + dlg + "').show();";
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(openDialog);
    }
    
    public void accionDialog(EBSolicitudes item, String dlg) {
        switch (dlg) {
            case "dlg3":
                objConfig.setMensajeConfirmacionAdjunto("Desea solicitar un documento adjunto de la solicitud " + item.getGuid());
                break;
            case "dlg5":
                listarRespuestaId(item);
                break;
            case "dlg6":
                listarHistoricoOperador(item);
                break;
            case "dlg10":
                listarAdjuntosId(item);
                break;
        }
        selectedMaestro = new EBSolicitudes();
        objRespueta = new EBRespuesta();
        selectedMaestro = item;
        tempMaestro = item;
        String openDialog = "PF('" + dlg + "').show();";
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(openDialog);
    }
    
    public void enviarSolicitudAdjunto() {
        try {
            selectedMaestro.setSolicitarDocumentoAdjunto(true);
            if ("OK".equals(objAccesDao.edit("editAdjuntoSolicitud", selectedMaestro, 1))) {
                objRespueta.setIdSolicitud(selectedMaestro.getId_solicitud());
                objRespueta.setEsRespuestaOperador(false);
                objRespueta.setIdUsuarioCreacion(user);
                objRespueta.setFechaCreacion(fechaActual = new Date());
                objRespueta.setValor("El sistema a solicitado un archivo adjunto de tu solicitud.");
                objAccesDao.create("createRespuesta", objRespueta, 1);
            }
            listMaestro = findSolicitudesUnique();
            cerrarDialogo("dlg3");
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editAdjuntoSolicitud :  " + ex);
        }
        
    }
    
    public void listarRespuestaId(EBSolicitudes item) {
        listRespuesta = new ArrayList<>();
        listRespuesta = findReglasSolicitud(item.getId_solicitud());
    }
    
    public void listarAdjuntosId(EBSolicitudes item) {
        userAdjunto = 0;
        userAdjunto = item.getId_solicitud();
        listAdjuntos = new ArrayList<>();
        listAdjuntos = findArchivosAdjuntos(item.getId_solicitud());
    }
    
    private UploadedFile file;
    private StreamedContent fileDownload;
    
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public StreamedContent getFileDownload() {
        return fileDownload;
    }
    
    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }
    
    public void downloadFile(EBAdjunto item, int id) {
        
        listAdjuntos = new ArrayList<>();
        listAdjuntos = findArchivosAdjuntos(item.getId_solicitud());
        
        for (EBAdjunto adjunto : listAdjuntos) {
            if (adjunto.getId_adjunto() == id) {
                ConectorFileServer cfs = new ConectorFileServerImpl(CanalFileServerEnum.WEB);
                cfs.conectar();
                InputStream is = cfs.descargarArchivo(cfs.getPath().concat(tempMaestro.getIdOperador() + "/" + tempMaestro.getId_solicitud()), adjunto.getNombre());
                fileDownload = new DefaultStreamedContent(is, adjunto.getExtension(), adjunto.getNombre());
                cfs.desconectar();
                
            }
        }
        
    }
    
    public void fileUploadListener(FileUploadEvent e) throws IOException {
        this.file = e.getFile();
        SubirArchivo(file.getFileName(), file);
        System.out.println("Nombre del archivo cargado :: " + file.getFileName() + " :: Tamaño de archivo cargado :: " + file.getSize());
        listAdjuntos = findArchivosAdjuntos(userAdjunto);
    }
    
    public void SubirArchivo(String nameFile, UploadedFile file) {
        try {
            ConectorFileServer cfs = new ConectorFileServerImpl(CanalFileServerEnum.WEB);
            cfs.conectar();
            if (cfs.subirArchivo(file.getInputstream(), cfs.getPath().concat(tempMaestro.getIdOperador() + "/" + tempMaestro.getGuid() + "/"), nameFile)) {
                EBAdjunto objAdjunto = new EBAdjunto();
                objAdjunto.setNombre(file.getFileName());
                objAdjunto.setFechaCreacion(fechaActual = new Date());
                objAdjunto.setDescrpcion("Adjunto creado vía web");
                objAdjunto.setTamano(file.getSize());
                objAdjunto.setPath(cfs.getPath());
                objAdjunto.setExtension(file.getContentType());
                objAdjunto.setId_solicitud(tempMaestro.getId_solicitud());
                objAccesDao.create("createAdjunto", objAdjunto, 1);
            }
            cfs.desconectar();
            
        } catch (Exception e) {
            System.out.println("Error createAdjunto: " + e.getLocalizedMessage());
        }
    }
    
    public void listarHistoricoOperador(EBSolicitudes item) {
        objfindSolcitud.setIdOperador(item.getIdOperador());
        objfindSolcitud.setNombreEmpleado(item.getNombreEmpleado());
        objfindSolcitud.setApellidoEmpleado(item.getApellidoEmpleado());
        objfindSolcitud.setCodigoTM(item.getCodigoTM());
        objfindSolcitud.setIdEstado(0);
        listFindMaestro = findSolicitudes(objfindSolcitud);
        
    }
    
    public List<EBRespuesta> findReglasSolicitud(int id) {
        List<EBRespuesta> listaReglasSolicitud = null;
        try {
            EBRespuesta objRespuesta = new EBRespuesta();
            objRespuesta.setIdSolicitud(id);
            listaReglasSolicitud = (List<EBRespuesta>) objAccesDao.find("selectRespuesta", objRespuesta, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
        return listaReglasSolicitud;
    }
    
    public List<EBAdjunto> findArchivosAdjuntos(int id) {
        List<EBAdjunto> listaAdjuntosSolicitud = null;
        try {
            EBAdjunto objAdjunto = new EBAdjunto();
            objAdjunto.setId_solicitud(id);
            listaAdjuntosSolicitud = (List<EBAdjunto>) objAccesDao.find("selectAdjunto", objAdjunto, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
        return listaAdjuntosSolicitud;
    }
    
    public List<EBEmpleados> findEmpleados() {
        List<EBEmpleados> listaEBEmpleados = null;
        try {
            EBEmpleados objEBEmpleados = new EBEmpleados();
            listaEBEmpleados = (List<EBEmpleados>) objAccesDao.find("selectEmpleados", objEBEmpleados, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
        return listaEBEmpleados;
    }
    
    public Date sumarRestarMinutosFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }
    
    public Date sumarRestarMesFecha(Date fecha, int mes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, mes);
        return calendar.getTime();
    }
    
    public void generadorDeMensages(String funcion, String texto) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(funcion, new FacesMessage(FacesMessage.SEVERITY_ERROR, funcion, texto));
    }
    
    public void limpiarObjetos() {
        objSolicitud = new EBSolicitudes();
        selectedMaestro = new EBSolicitudes();
        objRespueta = new EBRespuesta();
    }
    
    public void redireccionar() {
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(GestionarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editRespuesta(EBSolicitudes item) {
        try {
            item.setFechaModificacion(fechaActual = new Date());
            item.setIdUsuarioModificacion(user);
            item.setMostrar(Boolean.TRUE);
            if (objAccesDao.edit("editRespuestaSolicitud", item, 1).equals("OK") && item.getIdEstado() != 5) {
                EBRespuesta r = new EBRespuesta();
                r.setEsRespuestaOperador(Boolean.FALSE);
                r.setValor("La solicitud " + item.getGuid() + " cambio de estado.");
                r.setIdSolicitud(item.getId_solicitud());
                r.setVisto(Boolean.FALSE);
                r.setFechaCreacion(fechaActual = new Date());
                r.setIdUsuarioCreacion(user);
                objAccesDao.create("createRespuesta", r, 1);
            }
            
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editRespuestaSolicitud :  " + ex);
        }
    }
    
    public void editRespuestaUnico(EBSolicitudes item) {
        try {
            item.setFechaModificacion(fechaActual = new Date());
            item.setIdUsuarioModificacion(user);
            item.setMostrar(Boolean.TRUE);
            if (objAccesDao.edit("editRespuestaSolicitud", item, 1).equals("OK") && item.getIdEstado() != 5) {
                EBRespuesta r = new EBRespuesta();
                r.setEsRespuestaOperador(Boolean.FALSE);
                r.setValor("La solicitud " + item.getGuid() + " cambio de estado.");
                r.setIdSolicitud(item.getId_solicitud());
                r.setVisto(Boolean.FALSE);
                r.setFechaCreacion(fechaActual = new Date());
                r.setIdUsuarioCreacion(user);
                objAccesDao.create("createRespuesta", r, 1);
            }
            listMaestro = findSolicitudesUnique();
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editRespuestaSolicitud :  " + ex);
        }
    }
    
    public void editPatioInicio(EBSolicitudes item) {
        try {
            objAccesDao.edit("editPatioInicioSolicitud", item, 1);
            listMaestro = findSolicitudesUnique();
            limpiarObjetos();
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editPatioInicioSolicitud :  " + ex);
        }
    }
    
    public void editPatioFin(EBSolicitudes item) {
        try {
            objAccesDao.edit("editPatioFinSolicitud", item, 1);
            listMaestro = findSolicitudesUnique();
            limpiarObjetos();
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editPatioFinSolicitud :  " + ex);
        }
    }
    
    public void editObligatorio(EBSolicitudes item) {
        try {
            objAccesDao.edit("editObligatorioSolicitud", item, 1);
            listMaestro = findSolicitudesUnique();
            limpiarObjetos();
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editObligatorioSolicitud :  " + ex);
        }
    }
    
    public void editVisto(EBRespuesta item) {
        try {
            item.setVisto(Boolean.TRUE);
            objAccesDao.edit("editVistoRespueta", item, 1);
            listMaestro = findSolicitudesUnique();
            limpiarObjetos();
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editVistoRespueta :  " + ex);
        }
    }
    
    public String crearGuid(int id) {
        Random aleatorio = new Random();
        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        String guid = "";
        int numero;
        int forma;
        forma = (int) (aleatorio.nextDouble() * alfa.length() - 1 + 0);
        numero = (int) (aleatorio.nextDouble() * 99 + 100);
        guid = "SOL" + guid + alfa.charAt(forma) + numero + id;
        return guid;
    }
    
    public void motivoSolicitud() {
        if (objSolicitud.getIdTipoSolicitud() != 0) {
            listaMotivos = new ArrayList<>();
            for (EBTipoMotivoSolicitud menu : listaMenuSolicitud) {
                if (menu.getIdTipo() == objSolicitud.getIdTipoSolicitud()) {
                    EBMotivoSolicitud objMotivo = new EBMotivoSolicitud();
                    objMotivo.setId_motivo(menu.getIdMotivo());
                    objMotivo.setNombre(menu.getNombreMotivoSolicitud());
                    objMotivo.setPrioridad(menu.getPrioridadMotivo());
                    listaMotivos.add(objMotivo);
                    for (EBReglasSolicitud regla : listaReglas) {
                        if (regla.getIdTipoSolicitud() == objSolicitud.getIdTipoSolicitud()) {
                            objRegla = new EBReglasSolicitud();
                            objRegla = regla;
                            if (objRegla.isLaboraFestivo()) {
                                List<Date> listFechasTemp = new ArrayList<Date>();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(objConfig.getFechaIncial());
                                listFechasTemp.add(objConfig.getFechaIncial());
                                while (cal.getTime().before(objConfig.getFechaFinal())) {
                                    cal.add(Calendar.DATE, 1);
                                    listFechasTemp.add(cal.getTime());
                                }
                                for (Date fecha : listFechasTemp) {
                                    if (fecha.getDay() == 0) {
                                        listFechas.add(fecha);
                                    }
                                    for (EBFestivos festivo : listaFestivos) {
                                        if (festivo.getMes() == fecha.getMonth() && festivo.getDia() == fecha.getDay()) {
                                            listFechas.add(fecha);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void findReport() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        startDate = cal.getTime();
        
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        endDate = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        objfindSolcitud = new EBSolicitudes();
        objfindSolcitud.setIdEstado(6);
        objfindSolcitud.setFechaSolicitudIniString(sdf.format(startDate));
        objfindSolcitud.setFechaSolicitudFinString(sdf.format(endDate));
        listReportMaestro = findSolicitudes(objfindSolcitud);
    }
    
    public void gestionarMultiple() {
        ArrayList<String> i = new ArrayList<String>();
        for (EBSolicitudes solicitud : selectedMaestros) {
            if (solicitud.getIdEstado() == 4) {
                solicitud.setIdEstado(1);
                editRespuesta(solicitud);
            } else {
                i.add(solicitud.getGuid());
            }
        }
        if (i.size() > 0) {
            
            for (String s : i) {
                addMessage(FacesMessage.SEVERITY_WARN, "No se puede cambiar el estado de: ", s);
            }
        }
        listMaestro = findSolicitudesUnique();
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void aceptarMultiple() {
        ArrayList<String> i = new ArrayList<String>();
        for (EBSolicitudes solicitud : selectedMaestros) {
            if (solicitud.getIdEstado() == 1) {
                solicitud.setIdEstado(2);
                editRespuesta(solicitud);
            } else {
                i.add(solicitud.getGuid());
            }
        }
        if (i.size() > 0) {
            
            for (String s : i) {
                addMessage(FacesMessage.SEVERITY_WARN, "No se puede cambiar el estado de: ", s);
            }
        }
        listMaestro = findSolicitudesUnique();
    }
    
    public void rechazarMultiple() {
        ArrayList<String> i = new ArrayList<String>();
        for (EBSolicitudes solicitud : selectedMaestros) {
            if (solicitud.getIdEstado() == 1 || solicitud.getIdEstado() == 2) {
                solicitud.setIdEstado(3);
                editRespuesta(solicitud);
            } else {
                i.add(solicitud.getGuid());
            }
        }
        if (i.size() > 0) {
            
            for (String s : i) {
                addMessage(FacesMessage.SEVERITY_WARN, "No se puede cambiar el estado de: ", s);
            }
        }
        listMaestro = findSolicitudesUnique();
    }
    
    public List<EBSolicitudes> findSolicitudes(EBSolicitudes item) {
        List<EBSolicitudes> listaSolicitudes = null;
        List<EBRespuesta> listaRespuestas = null;
        try {
            listaSolicitudes = (List<EBSolicitudes>) objAccesDao.find("selectSolicitudes", item, 1);
            listaRespuestas = (List<EBRespuesta>) objAccesDao.find("selectNotitificaciones", item, 1);
            for (EBSolicitudes e : listaSolicitudes) {
                for (EBRespuesta r : listaRespuestas) {
                    if (e.getId_solicitud() == r.getIdSolicitud()) {
                        e.setNotificacion(Boolean.TRUE);
                    }
                }
            }
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaSolicitudes;
    }
    
    public List<EBSolicitudes> findSolicitudesUnique() {
        EBSolicitudes item = new EBSolicitudes();
        List<EBSolicitudes> listaSolicitudes = null;
        try {
            listaSolicitudes = (List<EBSolicitudes>) objAccesDao.find("selectSolicitudes", item, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaSolicitudes;
    }
    
    public List<EBSolicitudes> findSolicitudesGrupo(EBSolicitudes item) {
        List<EBSolicitudes> listaSolicitudes = null;
        try {
            listaSolicitudes = (List<EBSolicitudes>) objAccesDao.find("selectSolicitudesGrupo", item, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listaSolicitudes;
    }
    
    public List<EBFestivos> findFestivos() {
        List<EBFestivos> listafestivos = null;
        try {
            EBFestivos objsolicitud = new EBFestivos();
            listafestivos = (List<EBFestivos>) objAccesDao.find("selectfestivos", objsolicitud, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return listafestivos;
    }
    
    public List<EBTipoSolicitud> findTipoSolicitud() {
        List<EBTipoSolicitud> listaTipoSolicitud = null;
        try {
            EBTipoSolicitud objsolicitud = new EBTipoSolicitud();
            listaTipoSolicitud = (List<EBTipoSolicitud>) objAccesDao.find("selectTipoSolicitud", objsolicitud, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
        return listaTipoSolicitud;
    }
    
    public List<EBTipoMotivoSolicitud> findTipoMotivoSolicitud() {
        List<EBTipoMotivoSolicitud> listaTipoMotivoSolicitud = null;
        try {
            EBTipoMotivoSolicitud objsolicitud = new EBTipoMotivoSolicitud();
            listaTipoMotivoSolicitud = (List<EBTipoMotivoSolicitud>) objAccesDao.find("selectMenuTipoMotivo", objsolicitud, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
        return listaTipoMotivoSolicitud;
    }
    
    public List<EBReglasSolicitud> findReglasSolicitud() {
        List<EBReglasSolicitud> listaReglasSolicitud = null;
        try {
            EBReglasSolicitud objRegla = new EBReglasSolicitud();
            listaReglasSolicitud = (List<EBReglasSolicitud>) objAccesDao.find("selectReglaSolicitud", objRegla, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
        return listaReglasSolicitud;
    }
    
    public void enviarRespuesta() {
        selectedMaestro = tempMaestro;
        tempMaestro = new EBSolicitudes();
        objRespueta.setIdSolicitud(selectedMaestro.getId_solicitud());
        objRespueta.setEsRespuestaOperador(Boolean.FALSE);
        objRespueta.setIdUsuarioCreacion(user);
        objRespueta.setVisto(Boolean.FALSE);
        objRespueta.setFechaCreacion(fechaActual = new Date());
        objRespueta.setValor(objRespueta.getValor() + " - " + selectedMaestro.getGuid());
        try {
            objAccesDao.create("createRespuesta", objRespueta, 1);
            cerrarDialogo("dlg4");
            limpiarObjetos();
        } catch (ParseException | SQLException ex) {
            System.out.println("Error en editAdjuntoSolicitud :  " + ex);
        }
    }
    
    public void reporteFreeway(String dlg) {
        objfindSolcitud = new EBSolicitudes();
        objfindSolcitud.setIdEstado(6);
        listReportMaestro = findSolicitudes(objfindSolcitud);
        accionDialog(objfindSolcitud, dlg);
    }
    
    public void ReglaFechas() {
        fechaActual = new Date();
        Date fechaInicial = new Date();
        Date fechaFinal = new Date();
        
        switch (fechaActual.getDay()) {
            case 0:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 6, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
            
            case 1:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 5, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
            
            case 2:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 4, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
            case 3:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 10, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
            case 4:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 9, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
            case 5:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 8, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
            case 6:
                fechaInicial = calcularFecha(fechaActual, Calendar.DAY_OF_YEAR, 7, 0);
                fechaFinal = calcularFecha(fechaInicial, Calendar.DAY_OF_YEAR, 29, 1);
                objConfig.setFechaIncial(fechaInicial);
                objConfig.setFechaFinal(fechaFinal);
                break;
        }
        
    }
    
    public static Date calcularFecha(Date fecha, int campo, int valor, int punto) {
        if (valor == 0) {
            return fecha;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(campo, valor);
        if (punto == 0) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        } else if (punto == 1) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }
        return calendar.getTime();
    }
    
    public void cerrarDialogo(String dlg) {
        String closeDialog = "PF('" + dlg + "').hide();";
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(closeDialog);
        limpiarObjetos();
    }
    
    public String dateToFormatString(Date fecha, String formato) {
        String dateStr = "";
        DateFormat strFormat = new SimpleDateFormat(formato);
        dateStr = strFormat.format(fecha);
        return dateStr;
    }
    
    public Date sumarRestarHorasFecha(Date fecha, int horas, int minuto) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
        calendar.add(Calendar.MINUTE, minuto);
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }
    
    public List<String> getPrioridades() {
        return Arrays.asList(prioridades);
    }
    
    public List<EBSolicitudes> getListMaestro() {
        return listMaestro;
    }
    
    public void setListMaestro(List<EBSolicitudes> listMaestro) {
        this.listMaestro = listMaestro;
    }
    
    public List<EBTipoSolicitud> getListaTipoSolicitud() {
        return listaTipoSolicitud;
    }
    
    public void setListaTipoSolicitud(List<EBTipoSolicitud> listaTipoSolicitud) {
        this.listaTipoSolicitud = listaTipoSolicitud;
    }
    
    public EBSolicitudes getObjSolicitud() {
        return objSolicitud;
    }
    
    public void setObjSolicitud(EBSolicitudes objSolicitud) {
        this.objSolicitud = objSolicitud;
    }
    
    public List<EBMotivoSolicitud> getListaMotivos() {
        return listaMotivos;
    }
    
    public void setListaMotivos(List<EBMotivoSolicitud> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }
    
    public EBReglasSolicitud getObjRegla() {
        return objRegla;
    }
    
    public void setObjRegla(EBReglasSolicitud objRegla) {
        this.objRegla = objRegla;
    }
    
    public PropiedadesConfiguracionDTO getObjConfig() {
        return objConfig;
    }
    
    public void setObjConfig(PropiedadesConfiguracionDTO objConfig) {
        this.objConfig = objConfig;
    }
    
    public List<Date> getListFechas() {
        return listFechas;
    }
    
    public void setListFechas(List<Date> listFechas) {
        this.listFechas = listFechas;
    }
    
    public EBSolicitudes getSelectedMaestro() {
        return selectedMaestro;
    }
    
    public void setSelectedMaestro(EBSolicitudes selectedMaestro) {
        this.selectedMaestro = selectedMaestro;
    }
    
    public List<EBRespuesta> getListRespuesta() {
        return listRespuesta;
    }
    
    public void setListRespuesta(List<EBRespuesta> listRespuesta) {
        this.listRespuesta = listRespuesta;
    }
    
    public String getRespuesta() {
        return respuesta;
    }
    
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public EBRespuesta getObjRespueta() {
        return objRespueta;
    }
    
    public void setObjRespueta(EBRespuesta objRespueta) {
        this.objRespueta = objRespueta;
    }
    
    public List<EBEstado> getListEstados() {
        return listEstados;
    }
    
    public void setListEstados(List<EBEstado> listEstados) {
        this.listEstados = listEstados;
    }
    
    public List<EBEmpleados> getListEmpleados() {
        return listEmpleados;
    }
    
    public void setListEmpleados(List<EBEmpleados> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }
    
    public int getEstadoSolcitud() {
        return estadoSolcitud;
    }
    
    public void setEstadoSolcitud(int estadoSolcitud) {
        this.estadoSolcitud = estadoSolcitud;
    }
    
    public Date getFechaActual() {
        return fechaActual;
    }
    
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
    public EBSolicitudes getTempMaestro() {
        return tempMaestro;
    }
    
    public void setTempMaestro(EBSolicitudes tempMaestro) {
        this.tempMaestro = tempMaestro;
    }
    
    public PlantillaController getPlantillaController() {
        return plantillaController;
    }
    
    public void setPlantillaController(PlantillaController plantillaController) {
        this.plantillaController = plantillaController;
    }
    
    public List<EBSolicitudes> getListFindMaestro() {
        return listFindMaestro;
    }
    
    public void setListFindMaestro(List<EBSolicitudes> listFindMaestro) {
        this.listFindMaestro = listFindMaestro;
    }
    
    public List<EBFestivos> getListaFestivos() {
        return listaFestivos;
    }
    
    public void setListaFestivos(List<EBFestivos> listaFestivos) {
        this.listaFestivos = listaFestivos;
    }
    
    public List<EBTipoMotivoSolicitud> getListaMenuSolicitud() {
        return listaMenuSolicitud;
    }
    
    public void setListaMenuSolicitud(List<EBTipoMotivoSolicitud> listaMenuSolicitud) {
        this.listaMenuSolicitud = listaMenuSolicitud;
    }
    
    public List<EBReglasSolicitud> getListaReglas() {
        return listaReglas;
    }
    
    public void setListaReglas(List<EBReglasSolicitud> listaReglas) {
        this.listaReglas = listaReglas;
    }
    
    public int getUser() {
        return user;
    }
    
    public void setUser(int user) {
        this.user = user;
    }
    
    public EBSolicitudes getObjfindSolcitud() {
        return objfindSolcitud;
    }
    
    public void setObjfindSolcitud(EBSolicitudes objfindSolcitud) {
        this.objfindSolcitud = objfindSolcitud;
    }
    
    public List<EBSolicitudes> getListReportMaestro() {
        return listReportMaestro;
    }
    
    public void setListReportMaestro(List<EBSolicitudes> listReportMaestro) {
        this.listReportMaestro = listReportMaestro;
    }
    
    public List<EBPatios> getListPatios() {
        return listPatios;
    }
    
    public void setListPatios(List<EBPatios> listPatios) {
        this.listPatios = listPatios;
    }
    
    public List<Date> getListFechasSolicutud() {
        return listFechasSolicutud;
    }
    
    public void setListFechasSolicutud(List<Date> listFechasSolicutud) {
        this.listFechasSolicutud = listFechasSolicutud;
    }
    
    public List<EBAdjunto> getListAdjuntos() {
        return listAdjuntos;
    }
    
    public void setListAdjuntos(List<EBAdjunto> listAdjuntos) {
        this.listAdjuntos = listAdjuntos;
    }
    
    public List<EBSolicitudes> getSelectedMaestros() {
        return selectedMaestros;
    }
    
    public void setSelectedMaestros(List<EBSolicitudes> selectedMaestros) {
        this.selectedMaestros = selectedMaestros;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
