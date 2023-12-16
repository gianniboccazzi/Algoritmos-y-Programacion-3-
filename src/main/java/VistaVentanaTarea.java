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


public class VistaVentanaTarea{
    private Scene escena;
    // PANELES
    @FXML
    private Pane panelgeneral;
    @FXML
    private AnchorPane agregaralarma;
    @FXML
    private AnchorPane alarmapersonalizable ;
    private Stage stageVentana;

    public Pane getPanelgeneral() {
        return panelgeneral;
    }

    public AnchorPane getAgregaralarma() {
        return agregaralarma;
    }

    public AnchorPane getAlarmapersonalizable() {
        return alarmapersonalizable;
    }

    public AnchorPane getFijarhorarios() {
        return fijarhorarios;
    }

    public TextField getTituloTarea() {
        return tituloTarea;
    }

    public TextField getDescripcionTarea() {
        return descripcionTarea;
    }



    public Button getBotonOK() {
        return botonOK;
    }

    @FXML
    private Spinner<Integer> spinnerMinuto;

    public Spinner<Integer> getSpinnerMinuto() {
        return spinnerMinuto;
    }

    public Spinner<Integer> getSpinnerHora() {
        return spinnerHora;
    }

    @FXML
    private Spinner<Integer> spinnerHora;
    @FXML
    private AnchorPane fijarhorarios;

    //TEXT FIELD
    @FXML
    private TextField tituloTarea;
    @FXML
    private TextField descripcionTarea;


    public CheckBox getChecktodoeldia() {
        return checktodoeldia;
    }

    public DatePicker getFechainicioTarea() {
        return fechainicioTarea;
    }

    @FXML
    private DatePicker fechainicioTarea;
    //checkBox
    @FXML
    private CheckBox checktodoeldia;

    // boton
    @FXML
    private Button botonOK;
    @FXML
    private Button botonAlarmas;

    public Button getBotonAlarmas() {
        return botonAlarmas;
    }

    public VistaVentanaTarea() throws IOException {
        this.escena = Modelo.cargarFXML("tarea default.fxml", this);
    }


    public Stage getStageVentana() {
        return stageVentana;
    }

    public void mostrarVentanaEditarTarea(Tarea tarea, Stage stagePrincipal){
        this.stageVentana = new Stage();
        stageVentana.initModality(Modality.WINDOW_MODAL);
        stageVentana.setScene(this.escena);
        tituloTarea.setText(tarea.getTitulo());
        descripcionTarea.setText(tarea.getDescripcion());
        fechainicioTarea.setValue(tarea.getFechaClave());

        if(tarea.getFechayHoraClave().toLocalTime() == LocalTime.MIN){
            checktodoeldia.setSelected(true);
        }
        stageVentana.initOwner(stagePrincipal);
        stageVentana.show();
    }
}


