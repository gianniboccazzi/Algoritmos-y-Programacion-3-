import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalTime;

public class VistaVentanaEvento {
    private Scene escena;

    public Stage getStageVentana() {
        return stageVentana;
    }

    private Stage stageVentana;
    // PANELES
    @FXML
    private Pane panelgeneral;
    @FXML
    private AnchorPane agregaralarma;
    @FXML
    private AnchorPane alarmapersonalizable ;
    @FXML
    private AnchorPane fijarhorarios;
    @FXML
    private AnchorPane eventorepetible;

    public AnchorPane getFijarhorarios() {
        return fijarhorarios;
    }

    public AnchorPane getAgregaralarma() {
        return agregaralarma;
    }

    public AnchorPane getAlarmapersonalizable() {
        return alarmapersonalizable;
    }

    public AnchorPane getEventorepetible() {
        return eventorepetible;
    }

    public TextField getTituloevento() {
        return tituloevento;
    }

    //TEXT FIELD
    @FXML
    private TextField tituloevento;

    public TextField getDescripcionevento() {
        return descripcionevento;
    }



    public DatePicker getFechainicioevento() {
        return fechainicioevento;
    }

    public DatePicker getFechafinevento() {
        return fechafinevento;
    }

    public CheckBox getChecklunes() {
        return checklunes;
    }

    public CheckBox getCheckmartes() {
        return checkmartes;
    }

    public CheckBox getCheckmiercoles() {
        return checkmiercoles;
    }

    public CheckBox getCheckjueves() {
        return checkjueves;
    }

    public CheckBox getCheckviernes() {
        return checkviernes;
    }

    public CheckBox getChecksabado() {
        return checksabado;
    }

    public CheckBox getCheckdomingo() {
        return checkdomingo;
    }

    public CheckBox getChecktodoeldia() {
        return checktodoeldia;
    }

    public CheckBox getCheckagregaralarma() {
        return checkagregaralarma;
    }

    public CheckBox getCheckeventorepetible() {
        return checkeventorepetible;
    }



    public ChoiceBox getTipoderepeticion() {
        return tipoderepeticion;
    }

    public Button getBotoncrear() {
        return botoncrear;
    }

    @FXML
    private TextField descripcionevento;

    // DATEPICKERS
    @FXML
    private DatePicker fechainicioevento;

    @FXML
    private DatePicker fechafinevento;

    // CHECKBOX
    @FXML
    private CheckBox checklunes;
    @FXML
    private CheckBox checkmartes;
    @FXML
    private CheckBox checkmiercoles;
    @FXML
    private CheckBox checkjueves;
    @FXML
    private CheckBox checkviernes;
    @FXML
    private CheckBox checksabado;
    @FXML
    private CheckBox checkdomingo;
    @FXML
    private CheckBox checktodoeldia;
    @FXML
    private CheckBox checkagregaralarma;
    @FXML
    private CheckBox checkeventorepetible;

    // CHOICEBOX
    @FXML
    private ChoiceBox tipoderepeticion;
    // boton
    @FXML
    private Button botoncrear;
    @FXML
    private Button botonAlarmas;
    public Button getbotonAlarmas(){
        return botonAlarmas;
    }

    @FXML
    private Spinner<Integer> spinnerMinutoInicio;

    public Spinner<Integer> getSpinnerMinutoInicio() {
        return spinnerMinutoInicio;
    }

    public Spinner<Integer> getSpinnerHoraInicio() {
        return spinnerHoraInicio;
    }

    public Spinner<Integer> getSpinnerIntervalo() {
        return spinnerIntervalo;
    }

    public Spinner<Integer> getSpinnerLimiteRepeticiones() {
        return spinnerLimiteRepeticiones;
    }

    @FXML
    private Spinner<Integer> spinnerIntervalo;

    @FXML
    private Spinner<Integer> spinnerLimiteRepeticiones;
    @FXML
    private Spinner<Integer> spinnerHoraInicio;

    @FXML
    private Spinner<Integer> spinnerMinutoFin;

    public Spinner<Integer> getSpinnerMinutoFin() {
        return spinnerMinutoFin;
    }

    public Spinner<Integer> getSpinnerHoraFin() {
        return spinnerHoraFin;
    }

    @FXML
    private Spinner<Integer> spinnerHoraFin;

    public VistaVentanaEvento() throws IOException {
        this.escena = Modelo.cargarFXML("crearEventoDefault.fxml", this);
    }

    public void mostrarVentanaEditarEvento(Evento evento, Stage stagePadre){
        this.stageVentana = new Stage();
        stageVentana.initModality(Modality.WINDOW_MODAL);
        tituloevento.setText(evento.getTitulo());
        descripcionevento.setText(evento.getDescripcion());
        fechainicioevento.setValue(evento.getFechaInicio().toLocalDate());
        fechafinevento.setValue(evento.getFechaFin().toLocalDate());
        if(evento.getFechaInicio().toLocalTime() == LocalTime.MIN && evento.getFechaFin().toLocalTime() == LocalTime.MAX){
            checktodoeldia.setSelected(true);
        }
        stageVentana.initOwner(stagePadre);
        stageVentana.setScene(this.escena);
        stageVentana.show();
    }


}
