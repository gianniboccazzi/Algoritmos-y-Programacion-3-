import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

public class VistaCalendarioMensual {


    private LocalDate fechaMostrada;
    private Scene escena;
    private Stage stagePrincipal;
    private HashMap<LocalDate, ArrayList<Actividad>> actividadesAMostrar;
    @FXML
    private ChoiceBox choiceAgregar;

    public ChoiceBox getChoiceAgregar() {
        return choiceAgregar;
    }



    @FXML
    private Button botonSumarMes;
    @FXML
    private Button botonRestarMes;
    private Calendario calendario;
    @FXML
    private Text textoMesActual;
    @FXML
    private Text textoDiaSeleccionado;

    // PANELES
    @FXML
    private GridPane gridpane;
    @FXML
    private Pane panelGeneral;
    @FXML
    private ListView<Actividad> listadoActividades;

    private VistaVentanas vistaVentanas;
    @FXML
    private Button cambiarSemanal;
    @FXML
    private Button cambiarDiario;

    public Button getCambiarDiario() {
        return cambiarDiario;
    }

    public Button getCambiarSemanal() {
        return cambiarSemanal;
    }


    public ListView<Actividad> getListadoActividades() {
        return this.listadoActividades;
    }

    public LocalDate getFechaMostrada() {
        return this.fechaMostrada;
    }

    public void setFechaMostrada(LocalDate fechaNueva) {
        this.fechaMostrada = fechaNueva;
    }

    public Button getBotonSumarMes() {
        return botonSumarMes;
    }

    public Button getBotonRestarMes() {
        return botonRestarMes;
    }


    public VistaCalendarioMensual(Stage stage, Calendario calendario) {
        this.stagePrincipal = stage;
        this.escena = Modelo.cargarFXML("vistaCalendarioMensual.fxml", this);
        this.calendario = calendario;

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

    public ArrayList<LocalDate> obtenerFechasDelMes(LocalDate fechaAObtener) {
        var fechas = new ArrayList<LocalDate>();
        var mes = fechaAObtener.getMonth();
        var anio = fechaAObtener.getYear();
        var totalDias = mes.length(Year.isLeap(anio));
        for (int dia = 1; dia <= totalDias; dia++) {
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            fechas.add(fecha);
        }
        return fechas;
    }

    public GridPane getGridpane() {
        return this.gridpane;
    }


    public void colocarNumerosDiasEnGridPane(GridPane gridPane, ArrayList<LocalDate> fechasMes) {
        LocalDate fechaInicioMes = fechasMes.get(0);
        int diaSemanaInicioMes = fechaInicioMes.getDayOfWeek().getValue();
        int fila = 1;
        int columna = diaSemanaInicioMes == 7 ? 0 : diaSemanaInicioMes;
        LocalDate fechaFinMes = fechaInicioMes.with(TemporalAdjusters.lastDayOfMonth());
        int ultimoDiaMes = fechaFinMes.getDayOfMonth();
        for (int dia = 1; dia <= ultimoDiaMes; dia++) {
            Label labelDia = new Label(String.valueOf(dia));
            labelDia.setStyle("-fx-border-width: 1px; -fx-font-family: Arial; -fx-font-size: 18px; -fx-font-weight: bold; -fx-alignment: center-left;");
            gridPane.add(labelDia, columna, fila);
            GridPane.setHalignment(labelDia, HPos.CENTER);
            GridPane.setValignment(labelDia, VPos.CENTER);
            labelDia.setPrefHeight(Double.POSITIVE_INFINITY);
            labelDia.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            LocalDate fecha = fechaInicioMes.withDayOfMonth(dia);
            ArrayList<Actividad> actividades = calendario.obtenerActividadesEnFecha(fecha);
            labelDia.setUserData(actividades);
            columna++;
            if (columna > 6) {
                columna = 0;
                fila++;
            }
        }
    }




    public void mostrarActividadesMensual() {
        var anio = this.fechaMostrada.getYear();
        var mes = this.fechaMostrada.getMonth();
        var mesEspaniol = obtenerMesEnEspaniol(mes);
        this.textoMesActual.setText(mesEspaniol + " de " + String.valueOf(anio));
        var fechasDelMes = obtenerFechasDelMes(this.fechaMostrada);
        colocarNumerosDiasEnGridPane(this.gridpane, fechasDelMes);
        this.stagePrincipal.setScene(this.escena);
        this.stagePrincipal.show();
    }

    public Text getTextoDiaSeleccionado() {
        return textoDiaSeleccionado;
    }

    public void mostrarActividadesPorDiaDeMes(ArrayList<Actividad> actividadesDelDia) {
        var dia = fechaMostrada.getDayOfWeek();
        var textoDia = obtenerDiaEnEspaniol(dia) + " " + String.valueOf(fechaMostrada.getDayOfMonth());
        this.textoDiaSeleccionado.setText(textoDia);
        if (!actividadesDelDia.isEmpty()) {
            ObservableList<Actividad> listado = FXCollections.observableArrayList(actividadesDelDia);
            this.listadoActividades.setItems(listado);
            listadoActividades.setCellFactory(param -> new ListCell<Actividad>() {
                @Override
                protected void updateItem(Actividad actividad, boolean empty) {
                    super.updateItem(actividad, empty);
                    if (empty || actividad == null) {
                        setText(null);
                    } else {
                        if (actividad instanceof Evento) {
                            Evento evento = (Evento) actividad;
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                            var horaInicio = evento.getFechaInicio().format(formatter);
                            var horaFin = evento.getFechaFin().format(formatter);
                            setText(evento.getTitulo() + "   Inicio: " + horaInicio + "  Fin: " + horaFin);
                        } else {
                            Tarea tarea = (Tarea) actividad;
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                            var vencimiento = tarea.getFechayHoraClave().format(formatter);
                            if (tarea.estaCompleta()) {
                                setText(tarea.getTitulo() + "   Vencimiento: " + vencimiento + "    COMPLETADA");
                            } else {
                                setText(tarea.getTitulo() + "   Vencimiento: " + vencimiento + "    NO COMPLETADA");
                            }

                        }
                    }
                }
            });


        } else {
            listadoActividades.getItems().clear();
        }
    }

}