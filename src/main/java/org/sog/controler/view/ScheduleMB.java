package org.sog.controler.view;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.sog.persistence.entities.EBRestricciones;
import org.sog.persistence.sesion.AccesDao;
import java.util.List;
import org.sog.controler.security.PlantillaController;

@ManagedBean(name = "scheduleMB")
@ViewScoped
public class ScheduleMB implements Serializable {

    private ScheduleModel eventModel;
    private final AccesDao objAccesDao;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private Date fechaActual;
    private PlantillaController plantillaController;
    private int user;

    public ScheduleMB() {
        objAccesDao = AccesDao.getSingletonInstance();
        plantillaController = new PlantillaController();
        user = plantillaController.getIdUser();
        eventModel = new DefaultScheduleModel();
        listarFechas();
    }

    public void listarFechas() {
        List<EBRestricciones> listaRestricciones = null;
        try {
            EBRestricciones objRestriccion = null;
            listaRestricciones = (List<EBRestricciones>) objAccesDao.find("selectRestricciones", objRestriccion, 1);
            for (EBRestricciones restriccion : listaRestricciones) {
                DefaultScheduleEvent event = new DefaultScheduleEvent(restriccion.getTitulo(), restriccion.getFecha_desde(), restriccion.getFecha_hasta());
                event.setDescription(restriccion.getDescripcion());
                event.setStyleClass(restriccion.getStyleClass());
                eventModel.addEvent(event);
            }
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
    }

    public void deleteRestriccion(ActionEvent actionEvent) {
        try {
            EBRestricciones restriccion = new EBRestricciones();
            restriccion.setTitulo(event.getTitle());
            restriccion.setFecha_creacion(fechaActual = new Date());
            restriccion.setUsuario_creacion(user);
            objAccesDao.edit("deleteRestriccion", restriccion, 1);
        } catch (SQLException | ParseException e) {
            System.out.println("Error" + e);
        } catch (Exception e) {
        }
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        EBRestricciones restriccion = new EBRestricciones();
        restriccion.setTitulo(event.getTitle());
        restriccion.setDescripcion(event.getDescription());
        restriccion.setFecha_desde(event.getStartDate());
        restriccion.setFecha_hasta(event.getEndDate());
        restriccion.setUrl(event.getUrl());
        restriccion.setEditable(Boolean.TRUE);
        restriccion.setStyleClass("calendarRojo");
        if (event.getId() == null) {
            try {
                if ((event.getStartDate().getTime() >= event.getEndDate().getTime())) {
                    generadorDeMensages("Error", "Verifique fecha y hora final de su solicitud. ");
                } else {
                    restriccion.setFecha_creacion(fechaActual = new Date());
                    restriccion.setUsuario_creacion(user);
                    objAccesDao.create("createRestricciones", restriccion, 1);
                    restriccion.setTitulo(restriccion.getId_restriccion() + "_" + restriccion.getTitulo());
                    objAccesDao.edit("editRestriccionesUrl", restriccion, 1);
                    eventModel.addEvent(event);
                }
            } catch (ParseException | SQLException ex) {
                System.out.println("Error en createRestricciones:  " + ex);
            }
        } else {
            try {
                restriccion.setUsuario_edicion(user);
                restriccion.setFecha_edicion(fechaActual = new Date());
                objAccesDao.edit("editRestricciones", restriccion, 1);
            } catch (ParseException | SQLException ex) {
                System.out.println("Error en editRestricciones:  " + ex);
            }
        }

        event = new DefaultScheduleEvent();
    }

    public void generadorDeMensages(String funcion, String texto) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(funcion, new FacesMessage(FacesMessage.SEVERITY_ERROR, funcion, texto));
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento movido", "Ok");

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento redimensionado", "Ok");

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
