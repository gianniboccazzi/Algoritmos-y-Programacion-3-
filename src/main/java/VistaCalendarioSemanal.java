import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class VistaCalendarioSemanal {
    private LocalDate fechaMostrada;
    private Scene escena;
    private Stage stagePrincipal;

    public Text getTextMesAno() {
        return textMesAno;
    }

    @FXML
    private Text textMesAno;

    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaHora;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaDomingo;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaLunes;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaMartes;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaMiercoles;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaJueves;
    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaViernes;

    public TreeTableColumn<FilaActividad, String> getTablaColumnaHora() {
        return tablaColumnaHora;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaDomingo() {
        return tablaColumnaDomingo;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaLunes() {
        return tablaColumnaLunes;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaMartes() {
        return tablaColumnaMartes;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaMiercoles() {
        return tablaColumnaMiercoles;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaJueves() {
        return tablaColumnaJueves;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaViernes() {
        return tablaColumnaViernes;
    }

    public TreeTableColumn<FilaActividad, String> getTablaColumnaSabado() {
        return tablaColumnaSabado;
    }

    public TreeTableView<FilaActividad> getListaActividades() {
        return listaActividades;
    }

    public Button getCambiarMensual() {
        return cambiarMensual;
    }

    public Button getCambiarDiario() {
        return cambiarDiario;
    }

    @FXML
    private TreeTableColumn<FilaActividad, String> tablaColumnaSabado;
    @FXML
    private TreeTableView<FilaActividad> listaActividades;
    @FXML
    private ChoiceBox choiceAgregar;
    @FXML
    private Button sumarSemana;
    @FXML
    private Button restarSemana;
    @FXML
    private Button cambiarMensual;
    @FXML
    private Button cambiarDiario;

    public VistaCalendarioSemanal(Stage stage) {
        this.stagePrincipal = stage;
        this.fechaMostrada = obtenerFechadeDomingo(LocalDate.now());
        this.escena = Modelo.cargarFXML("vistaCalendarioSemanal.fxml", this);
    }

    public Button getBotonRestarSemana() {
        return restarSemana;
    }

    public LocalDate getFechaMostrada() {
        return fechaMostrada;
    }

    public Button getBotonSumarSemana() {
        return sumarSemana;
    }

    public ChoiceBox getChoiceAgregar() {
        return choiceAgregar;
    }

    public Scene getEscena() {
        return escena;
    }

    public Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public void setFechaMostrada(LocalDate fechaNueva) {
        this.fechaMostrada = fechaNueva;
    }

    public void mostrarCalendarioSemanal(ArrayList<Actividad> actividades) {
        var anio = this.fechaMostrada.getYear();
        var mes = this.fechaMostrada.getMonth();
        var mesEspaniol = obtenerMesEnEspaniol(mes);
        textMesAno.setText(mesEspaniol + " de " + String.valueOf(anio));
        mostrarFechasEnColumnas();
        setearlistaActividades();
        var horas = Modelo.obtenerListaHoras();
        TreeItem<FilaActividad> rootNode = new TreeItem<>(new FilaActividad("", "", "", "", "", "", "", "", null));
        for (String hora : horas) {
            TreeItem<FilaActividad> horario = new TreeItem<>(new FilaActividad(hora,  "", "", "", "", "", "", "", null));
            if (actividades != null) {
                var acts = Modelo.obtenerActividadesPorHora(actividades, hora);
                while (!acts.isEmpty()) {
                    String lunes = "";
                    String martes = "";
                    String miercoles = "";
                    String jueves = "";
                    String viernes = "";
                    String sabado = "";
                    String domingo = "";
                    var actividadesItem = new ArrayList<Actividad>();
                    Iterator<Actividad> iterator = acts.iterator();
                    while (iterator.hasNext()) {
                        Actividad act = iterator.next();
                        DayOfWeek dayOfWeek = act.getFechaClave().getDayOfWeek();
                        String titulo = act.getTitulo();
                        switch (dayOfWeek) {
                            case MONDAY:
                                if (lunes.equals("")) {
                                    lunes = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                            case TUESDAY:
                                if (martes.equals("")) {
                                    martes = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                            case WEDNESDAY:
                                if (miercoles.equals("")) {
                                    miercoles = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                            case THURSDAY:
                                if (jueves.equals("")) {
                                    jueves = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                            case FRIDAY:
                                if (viernes.equals("")) {
                                    viernes = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                            case SATURDAY:
                                if (sabado.equals("")) {
                                    sabado = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                            case SUNDAY:
                                if (domingo.equals("")) {
                                    domingo = titulo;
                                    actividadesItem.add(act);
                                    iterator.remove();
                                }
                                break;
                        }
                    }
                    actividadesItem.sort(Comparator.comparing(Actividad::getFechayHoraClave));
                    TreeItem<FilaActividad> nuevaAct = new TreeItem<>(new FilaActividad("", domingo, lunes, martes, miercoles, jueves, viernes, sabado, actividadesItem));
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



    public LocalDate obtenerFechadeDomingo(LocalDate fecha) {
        while (fecha.getDayOfWeek() != DayOfWeek.SUNDAY) {
            fecha = fecha.minusDays(1);
        }
        return fecha;
    }

    public void setearlistaActividades(){
        tablaColumnaHora.setCellValueFactory(new TreeItemPropertyValueFactory<>("hora"));
        tablaColumnaLunes.setCellValueFactory(new TreeItemPropertyValueFactory<>("lunes"));
        tablaColumnaMartes.setCellValueFactory(new TreeItemPropertyValueFactory<>("martes"));
        tablaColumnaMiercoles.setCellValueFactory(new TreeItemPropertyValueFactory<>("miercoles"));
        tablaColumnaJueves.setCellValueFactory(new TreeItemPropertyValueFactory<>("jueves"));
        tablaColumnaViernes.setCellValueFactory(new TreeItemPropertyValueFactory<>("viernes"));
        tablaColumnaSabado.setCellValueFactory(new TreeItemPropertyValueFactory<>("sabado"));
        tablaColumnaDomingo.setCellValueFactory(new TreeItemPropertyValueFactory<>("domingo"));
    }
    public void mostrarFechasEnColumnas() {
        tablaColumnaDomingo.setText("Domingo " + fechaMostrada.getDayOfMonth());
        LocalDate fechaSiguiente = fechaMostrada.plusDays(1);
        tablaColumnaLunes.setText("Lunes " + fechaSiguiente.getDayOfMonth());
        LocalDate fechaMartes = fechaSiguiente.plusDays(1);
        tablaColumnaMartes.setText("Martes " + fechaMartes.getDayOfMonth());
        LocalDate fechaMiercoles = fechaMartes.plusDays(1);
        tablaColumnaMiercoles.setText("Miércoles " + fechaMiercoles.getDayOfMonth());
        LocalDate fechaJueves = fechaMiercoles.plusDays(1);
        tablaColumnaJueves.setText("Jueves " + fechaJueves.getDayOfMonth());
        LocalDate fechaViernes = fechaJueves.plusDays(1);
        tablaColumnaViernes.setText("Viernes " + fechaViernes.getDayOfMonth());
        LocalDate fechaSabado = fechaViernes.plusDays(1);
        tablaColumnaSabado.setText("Sábado " + fechaSabado.getDayOfMonth());
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

}

