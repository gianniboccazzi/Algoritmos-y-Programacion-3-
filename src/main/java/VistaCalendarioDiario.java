import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.shape.Rectangle;

public class VistaCalendarioDiario {
    private Scene escena;
    private  Stage stagePrincipal;


    public ChoiceBox getChoiceAgregar(){return choiceAgregar;}

    public ChoiceBox getChoiceVisualizacion() {
        return choiceVisualizacion;
    }

    @FXML
    private Button cambiarSemanal;
    @FXML
    private Button cambiarMensual;

    public Button getCambiarMensual() {
        return cambiarMensual;
    }

    public Button getCambiarSemanal() {
        return cambiarSemanal;
    }

    @FXML
    private ChoiceBox choiceVisualizacion;

    @FXML
    private ChoiceBox choiceAgregar;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaActividades;

    @FXML
    private TreeTableView<FilaActividad> listaActividades;

    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaHora;
    @FXML
    private Button botonSumarDia;
    @FXML
    private Button botonRestarDia;
    @FXML
    private Text diaDeHoy;


    private VistaVentanas vistaVentanas;


    private Calendario calendario;

    public LocalDate getFechaAMostrar() {
        return fechaAMostrar;
    }

    public void setFechaAMostrar(LocalDate fechaAMostrar) {
        this.fechaAMostrar = fechaAMostrar;
    }

    private LocalDate fechaAMostrar;

    public Button getBotonRestarDia() {
        return botonRestarDia;
    }

    public Button getBotonSumarDia() {
        return botonSumarDia;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaActividades() {
        return tablaColumnaActividades;
    }

    public TreeTableView<FilaActividad> getListaActividades() {
        return listaActividades;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaHora() {
        return tablaColumnaHora;
    }

    public VistaCalendarioDiario(Stage stage) throws IOException {
        this.stagePrincipal = stage;
        this.escena = Modelo.cargarFXML("ListaDiariaCalendario.fxml", this);
        this.fechaAMostrar = LocalDate.now();

    }





    public void mostrarActividadesDiarias(ArrayList<Actividad> actividades) {
        var horas = Modelo.obtenerListaHoras();
        var dia = this.fechaAMostrar.getDayOfWeek();
        var numero = this.fechaAMostrar.getDayOfMonth();
        var mes = this.fechaAMostrar.getMonth();
        diaDeHoy.setText(obtenerDiaEnEspaniol(dia) + " " + String.valueOf(numero) + " de " + obtenerMesEnEspaniol(mes));
        tablaColumnaActividades.setCellValueFactory(new TreeItemPropertyValueFactory<>("actividad"));
        tablaColumnaHora.setCellValueFactory(new TreeItemPropertyValueFactory<>("hora"));
        TreeItem<FilaActividad> rootNode = new TreeItem<>(new FilaActividad("", "", null));
        for (String hora : horas) {
            TreeItem<FilaActividad> horario = new TreeItem<>(new FilaActividad(hora, "...", null));
            if (actividades != null){
                var actsEnHora = Modelo.obtenerActividadesPorHora(actividades, hora);
                for (Actividad act : actsEnHora) {
                    String columna1;
                    String columna2;
                    if (act instanceof Evento) {
                        columna1 = act.pasarAHoraMinuto(((Evento) act).getFechaInicio()) + " - " + act.pasarAHoraMinuto(((Evento) act).getFechaFin());
                        columna2 = "Evento                " + act.getTitulo();
                    } else{
                        columna1 = act.pasarAHoraMinuto(act.getFechayHoraClave());
                        columna2 = "Tarea                 " + act.getTitulo();
                        if(((Tarea)act).estaCompleta()){
                            columna2 += "   COMPLETADA";
                        } else {
                            columna2 += "   NO COMPLETADA";
                        }
                    }
                    if(act.esTodoElDia()){
                        columna2 += "  Actividad de Todo el Día";
                    }
                    TreeItem<FilaActividad> nuevaAct = new TreeItem<>(new FilaActividad(columna1, columna2, act));
                    horario.getChildren().add(nuevaAct);
                }
            }
            rootNode.getChildren().add(horario);
        }
        listaActividades.setRoot(rootNode);
        listaActividades.setShowRoot(false);
        this.stagePrincipal.setScene(this.escena);
        this.stagePrincipal.show();
    }
    public TreeTableView<FilaActividad> getTreeTableView(){
        return this.listaActividades;
    }

    public String obtenerMesEnEspaniol(Month mes) {
        switch (mes) {
            case JANUARY:
                return "Enero";
            case FEBRUARY:
                return "Febrero";
            case MARCH:
                return "Marzo";
            case APRIL:
                return "Abril";
            case MAY:
                return "Mayo";
            case JUNE:
                return "Junio";
            case JULY:
                return "Julio";
            case AUGUST:
                return "Agosto";
            case SEPTEMBER:
                return "Septiembre";
            case OCTOBER:
                return "Octubre";
            case NOVEMBER:
                return "Noviembre";
            case DECEMBER:
                return "Diciembre";
            default:
                return "";
        }

    }

    public String obtenerDiaEnEspaniol(DayOfWeek dia) {
        switch (dia) {
            case MONDAY:
                return "Lunes";
            case TUESDAY:
                return "Martes";
            case WEDNESDAY:
                return "Miercoles";
            case THURSDAY:
                return "Jueves";
            case FRIDAY:
                return "Viernes";
            case SATURDAY:
                return "Sabado";
            case SUNDAY:
                return "Domingo";
            default:
                return "";
        }
    }
}
