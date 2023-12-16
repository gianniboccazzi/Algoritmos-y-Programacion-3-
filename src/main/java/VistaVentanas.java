import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VistaVentanas {


    @FXML
    private Text tituloEvento;

    @FXML
    private Text FechaInicioFechaFin;




    @FXML
    private Button editarEvento;

    @FXML
    private Button borrarEvento;

    public Button getBotonNotiAlarma() {
        return botonNotiAlarma;
    }

    public Text getTextoNotiAlarma() {
        return textoNotiAlarma;
    }

    @FXML
    private Text textoNotiAlarma;
    @FXML
    private Button botonNotiAlarma;

    public Button getEditarEvento() {
        return editarEvento;
    }

    public Button getBorrarEvento() {
        return borrarEvento;
    }

    public Button getEditarAlarmaEvento() {
        return editarAlarmaEvento;
    }

    public Button getEditarAlarmaTarea() {
        return editarAlarmaTarea;
    }

    public Button getBorrartarea() {
        return borrartarea;
    }

    public Button getEditartarea() {
        return editartarea;
    }

    public CheckBox getCompletartarea() {
        return completartarea;
    }
    public javafx.scene.shape.Rectangle getCuadradito(){
        return this.cuadraditoVerde;
    }

    @FXML
    private Button editarAlarmaEvento;
    @FXML
    private Button botonCrearAlarma;

    public Button getBotonCrearAlarma() {
        return botonCrearAlarma;
    }

    @FXML
    private Button editarAlarmaTarea;
    @FXML
    private Button borrartarea;
    @FXML
    private Button editartarea;

    @FXML
    private CheckBox completartarea;
    @FXML
    private Text titulotarea;

    public Button getBotonEliminar() {
        return botonEliminar;
    }

    @FXML
    private Button botonEliminar;

    @FXML
    private Text fechatarea;
    @FXML
    private javafx.scene.shape.Rectangle cuadraditoVerde;

    @FXML
    private CheckBox checkpersonalizado;
    @FXML
    private CheckBox checkagregaralarma;
    @FXML
    private Spinner<Integer> spinnerHoraAlarma;

    public Text getTituloEvento() {
        return tituloEvento;
    }

    public Text getFechaInicioFechaFin() {
        return FechaInicioFechaFin;
    }






    public Text getTitulotarea() {
        return titulotarea;
    }



    public Text getFechatarea() {
        return fechatarea;
    }

    public Rectangle getCuadraditoVerde() {
        return cuadraditoVerde;
    }

    public CheckBox getCheckpersonalizado() {
        return checkpersonalizado;
    }

    public CheckBox getCheckagregaralarma() {
        return checkagregaralarma;
    }

    public DatePicker getFechainicioTarea() {
        return fechainicioTarea;
    }

    public DatePicker getFechaalarma() {
        return fechaalarma;
    }

    public DatePicker getAlarmapersonalizable() {
        return alarmapersonalizable;
    }

    public TextField getMailAlarma() {
        return mailAlarma;
    }

    @FXML
    private Spinner<Integer> spinnerMinutoAlarma;

    public Button getBotonAceptar() {
        return botonAceptar;
    }

    @FXML
    private Button botonAceptar;

    @FXML
    private Spinner<Integer> spinnerIntervaloAlarma;
    @FXML
    private DatePicker fechainicioTarea;
    @FXML
    private DatePicker fechaalarma;
    @FXML
    private DatePicker alarmapersonalizable;
    @FXML
    private TextField mailAlarma;
    @FXML
    private ListView<Alarma> listaAlarmas;

    public ListView<Alarma> getListaAlarmas() {
        return listaAlarmas;
    }

    public void mostrarVentanaDetalladaEvento(Evento evento, Stage stagePrincipal) {
        var nuevaVentana = new Stage();
        nuevaVentana.initModality(Modality.WINDOW_MODAL);
        var scene = Modelo.cargarFXML("mostrar evento con horario.fxml", this);
        tituloEvento.setText(evento.getTitulo());
        FechaInicioFechaFin.setText(evento.getFechaClave().toString() + " , " + evento.pasarAHoraMinuto(evento.getFechaInicio()) + " - " + evento.pasarAHoraMinuto(evento.getFechaFin()));
        nuevaVentana.initOwner(stagePrincipal);
        nuevaVentana.setScene(scene);
        nuevaVentana.show();

    }

    public void mostrarVentanaDetalladaTarea(Tarea tarea, Stage stagePrincipal) {
        var nuevaVentana = new Stage();
        nuevaVentana.initModality(Modality.WINDOW_MODAL);
        var scene = Modelo.cargarFXML("mostrartarea.fxml", this);
        titulotarea.setText(tarea.getTitulo());
        if (tarea.estaCompleta()){
            completartarea.setSelected(true);
        }
        fechatarea.setText(tarea.getFechaClave().toString() + ",  " + tarea.pasarAHoraMinuto(tarea.getFechayHoraClave()));
        nuevaVentana.initOwner(stagePrincipal);
        nuevaVentana.setScene(scene);
        nuevaVentana.show();
    }

    public void mostrarVentanaAlarmas(ArrayList<Alarma> alarmas, Stage stageActividad) {
        var nuevaVentana = new Stage();
        nuevaVentana.initModality(Modality.WINDOW_MODAL);
        var scene = Modelo.cargarFXML("editar alarmas.fxml", this);
        ObservableList<Alarma> items = listaAlarmas.getItems();

        for (Alarma alarma : alarmas) {
            items.add(alarma);
        }
        listaAlarmas.setCellFactory(param -> new ListCell<Alarma>() {
            @Override
            protected void updateItem(Alarma alarma, boolean empty) {
                super.updateItem(alarma, empty);
                if (empty || alarma == null) {
                    setText(null);
                } else {
                    if (alarma.EsAbsoluta()) {
                        setText("Alarma configurada para las " + alarma.pasarAHoraMinuto(alarma.getHoraASonar()));
                    } else {
                        setText("Alarma configurada para " + alarma.getIntervalo().toString() + " minutos antes de la actividad");
                    }
                }
            }
        });
        nuevaVentana.initOwner(stageActividad);
        nuevaVentana.setScene(scene);
        nuevaVentana.show();
    }


    public Spinner<Integer> getSpinnerHoraAlarma() {
        return spinnerHoraAlarma;
    }

    public Spinner<Integer> getSpinnerMinutoAlarma() {
        return spinnerMinutoAlarma;
    }

    public Spinner<Integer> getSpinnerIntervaloAlarma() {
        return spinnerIntervaloAlarma;
    }

    public void mostrarVentanaEditarAlarma(Alarma alarma, Stage stageAlarmas){
        var nuevaVentana = new Stage();
        nuevaVentana.initModality(Modality.WINDOW_MODAL);
        var scene = Modelo.cargarFXML("crearAlarma.fxml", this);
        mailAlarma.setText(alarma.getCorreoElectronico());
        if (alarma.EsAbsoluta()){
            fechaalarma.setDisable(false);
            spinnerHoraAlarma.setDisable(false);
            spinnerMinutoAlarma.setDisable(false);
            spinnerIntervaloAlarma.setDisable(true);
            checkpersonalizado.setSelected(true);
            fechaalarma.setValue(alarma.getHoraASonar().toLocalDate());
            }else{
            fechaalarma.setValue(LocalDate.now());
        }
        nuevaVentana.initOwner(stageAlarmas);
        nuevaVentana.setScene(scene);
        nuevaVentana.show();
    }

    public void mostrarNotificacionAlarma(Actividad actividad){
        var nuevaVentana = new Stage();
        var scene = Modelo.cargarFXML("notificacion alarma.fxml", this);
        textoNotiAlarma.setText("Actividad ´" + actividad.getTitulo() + "´ para las " + actividad.pasarAHoraMinuto(actividad.getFechayHoraClave()));
        nuevaVentana.setScene(scene);
        nuevaVentana.show();
    }

}
