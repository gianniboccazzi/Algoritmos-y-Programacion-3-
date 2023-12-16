import com.sun.source.tree.Tree;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import javafx.util.converter.IntegerStringConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Controlador {
    private VistaVentanas vistaVentanas;

    private Stage stagePadreActual;
    private VistaCalendarioDiario vistaCalendarioDiario;
    private VistaCalendarioMensual vistaCalendarioMensual;
    private VistaVentanaEvento vistaVentanaEvento;
    private Calendario calendario;
    private VistaVentanaTarea vistaCrearTarea;
    private VistaCalendarioSemanal vistaCalendarioSemanal;

    public Controlador(VistaCalendarioDiario vistaCalendarioDiario, VistaCalendarioMensual vistaCalendarioMensual, VistaCalendarioSemanal vistaCalendarioSemanal, VistaVentanaTarea vistaCrearTarea, VistaVentanas vistaVentanas, Calendario calendario, VistaVentanaEvento vistaVentanaEvento, Stage stagePadreActual) {
        this.vistaVentanaEvento = vistaVentanaEvento;
        this.vistaVentanas = vistaVentanas;
        this.vistaCrearTarea = vistaCrearTarea;
        this.vistaCalendarioDiario = vistaCalendarioDiario;
        this.calendario = calendario;
        this.vistaCalendarioMensual = vistaCalendarioMensual;
        this.stagePadreActual = stagePadreActual;
        this.vistaCalendarioSemanal = vistaCalendarioSemanal;
    }


    public void iniciarCalendarioDiario() {
        var listaActividades = this.vistaCalendarioDiario.getTreeTableView();
        var sumarDia = vistaCalendarioDiario.getBotonSumarDia();
        var restarDia = vistaCalendarioDiario.getBotonRestarDia();
        var cambiarAMensual = vistaCalendarioDiario.getCambiarMensual();
        var cambiarASemanal = vistaCalendarioDiario.getCambiarSemanal();
        listaActividades.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<FilaActividad> selectedItem = listaActividades.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    FilaActividad item = selectedItem.getValue();
                    if (item.getTipo() instanceof Evento) {
                        vistaVentanas.mostrarVentanaDetalladaEvento((Evento) item.getTipo(), this.stagePadreActual);

                        iniciarBotonesVentanaEvento((Evento) item.getTipo(), (Stage) listaActividades.getScene().getWindow(), selectedItem, "diario");
                    } else if (item.getTipo() instanceof Tarea) {
                        vistaVentanas.mostrarVentanaDetalladaTarea((Tarea) item.getTipo(), this.stagePadreActual);
                        iniciarBotonesVentanaTarea((Tarea) item.getTipo(), (Stage) listaActividades.getScene().getWindow(), selectedItem, "diario");
                    }
                }
            }
        });
        sumarDia.setOnAction(actionEvent -> {
            LocalDate nuevaFecha = vistaCalendarioDiario.getFechaAMostrar().plusDays(1);
            vistaCalendarioDiario.setFechaAMostrar(nuevaFecha);
            var actividades = this.calendario.obtenerActividadesEnFecha(nuevaFecha);
            vistaCalendarioDiario.mostrarActividadesDiarias(actividades);
        });
        restarDia.setOnAction(actionEvent -> {
            LocalDate nuevaFecha = vistaCalendarioDiario.getFechaAMostrar().minusDays(1);
            vistaCalendarioDiario.setFechaAMostrar(nuevaFecha);
            var actividades = this.calendario.obtenerActividadesEnFecha(nuevaFecha);
            vistaCalendarioDiario.mostrarActividadesDiarias(actividades);
        });
        cambiarAMensual.setOnAction(actionEvent -> {
            vistaCalendarioMensual.setFechaMostrada(LocalDate.now());
            vistaCalendarioMensual.mostrarActividadesMensual();
            iniciarCalendarioMensual();
        });
        cambiarASemanal.setOnAction(actionEvent -> {
            var nuevaFecha = vistaCalendarioSemanal.obtenerFechadeDomingo(LocalDate.now());
            vistaCalendarioSemanal.setFechaMostrada(nuevaFecha);
            vistaCalendarioSemanal.mostrarCalendarioSemanal(calendario.obtenerActividadesPorRango(nuevaFecha, nuevaFecha.plusDays(6)));
            iniciarCalendarioSemanal();
        });
        iniciarChoiceboxAgregar("diario");
    }


    public void iniciarCalendarioMensual() {
        var grilla = this.vistaCalendarioMensual.getGridpane();
        var sumarMes = vistaCalendarioMensual.getBotonSumarMes();
        var restarMes = vistaCalendarioMensual.getBotonRestarMes();
        var listadoActividades = vistaCalendarioMensual.getListadoActividades();
        var cambiarSemanal = this.vistaCalendarioMensual.getCambiarSemanal();
        var cambiarDiario = this.vistaCalendarioMensual.getCambiarDiario();
        for (Node node : grilla.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                label.setOnMouseClicked(event -> {
                    ArrayList<Actividad> actividades = ((ArrayList<Actividad>) label.getUserData());
                    vistaCalendarioMensual.setFechaMostrada(vistaCalendarioMensual.getFechaMostrada().withDayOfMonth(Integer.parseInt(label.getText())));
                    vistaCalendarioMensual.mostrarActividadesPorDiaDeMes(actividades);
                });
            }
        }
        cambiarDiario.setOnAction(actionEvent -> {
            iniciarCalendarioDiario();
            vistaCalendarioDiario.mostrarActividadesDiarias(calendario.obtenerActividadesEnFecha(LocalDate.now()));
        });
        cambiarSemanal.setOnAction(actionEvent -> {
            var nuevaFecha = vistaCalendarioSemanal.obtenerFechadeDomingo(LocalDate.now());
            vistaCalendarioSemanal.setFechaMostrada(nuevaFecha);
            vistaCalendarioSemanal.mostrarCalendarioSemanal(calendario.obtenerActividadesPorRango(nuevaFecha, nuevaFecha.plusDays(6)));
            iniciarCalendarioSemanal();
        });
        listadoActividades.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Actividad actividadSeleccionada = listadoActividades.getSelectionModel().getSelectedItem();
                if (actividadSeleccionada instanceof Evento) {
                    vistaVentanas.mostrarVentanaDetalladaEvento((Evento) actividadSeleccionada, this.stagePadreActual);
                    iniciarBotonesVentanaEvento((Evento) actividadSeleccionada, (Stage) listadoActividades.getScene().getWindow(), null, "mensual");
                } else {
                    vistaVentanas.mostrarVentanaDetalladaTarea((Tarea) actividadSeleccionada, this.stagePadreActual);
                    iniciarBotonesVentanaTarea((Tarea) actividadSeleccionada, (Stage) listadoActividades.getScene().getWindow(), null, "mensual");
                }
            }
        });
        sumarMes.setOnAction(actionEvent -> {
            LocalDate nuevaFecha = vistaCalendarioMensual.getFechaMostrada().plusMonths(1);
            borrarLabelsEnGridPane(vistaCalendarioMensual.getGridpane());
            vistaCalendarioMensual.setFechaMostrada(nuevaFecha);
            vistaCalendarioMensual.mostrarActividadesMensual();
            iniciarCalendarioMensual();
        });
        restarMes.setOnAction(actionEvent -> {
            LocalDate nuevaFecha = vistaCalendarioMensual.getFechaMostrada().minusMonths(1);
            borrarLabelsEnGridPane(vistaCalendarioMensual.getGridpane());
            vistaCalendarioMensual.setFechaMostrada(nuevaFecha);
            vistaCalendarioMensual.mostrarActividadesMensual();
            iniciarCalendarioMensual();
        });
        iniciarChoiceboxAgregar("mensual");
    }


    public void iniciarCalendarioSemanal() {
        var sumarSemana = vistaCalendarioSemanal.getBotonSumarSemana();
        var restarSeamana = vistaCalendarioSemanal.getBotonRestarSemana();
        var botonDiario = vistaCalendarioSemanal.getCambiarDiario();
        var botonMensual = vistaCalendarioSemanal.getCambiarMensual();
        var tablaActividades = vistaCalendarioSemanal.getListaActividades();
        sumarSemana.setOnAction(actionEvent -> {
            LocalDate nuevaFecha = vistaCalendarioSemanal.getFechaMostrada().plusWeeks(1);
            vistaCalendarioSemanal.setFechaMostrada(nuevaFecha);
            vistaCalendarioSemanal.mostrarCalendarioSemanal(calendario.obtenerActividadesPorRango(nuevaFecha, nuevaFecha.plusDays(6)));
        });
        restarSeamana.setOnAction(actionEvent -> {
            LocalDate nuevaFecha = vistaCalendarioSemanal.getFechaMostrada().minusWeeks(1);
            vistaCalendarioSemanal.setFechaMostrada(nuevaFecha);
            vistaCalendarioSemanal.mostrarCalendarioSemanal(calendario.obtenerActividadesPorRango(nuevaFecha, nuevaFecha.plusDays(6)));
        });
        botonMensual.setOnAction(actionEvent -> {
            vistaCalendarioMensual.setFechaMostrada(LocalDate.now());
            vistaCalendarioMensual.mostrarActividadesMensual();
            iniciarCalendarioMensual();
        });
        botonDiario.setOnAction(actionEvent -> {
            iniciarCalendarioDiario();
            vistaCalendarioDiario.mostrarActividadesDiarias(calendario.obtenerActividadesEnFecha(LocalDate.now()));
        });
        tablaActividades.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<FilaActividad> selectedItem = tablaActividades.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    TreeTableColumn<FilaActividad, ?> selectedColumn = tablaActividades.getFocusModel().getFocusedCell().getTableColumn();
                    int columnIndex = tablaActividades.getColumns().indexOf(selectedColumn);
                    FilaActividad item = selectedItem.getValue();
                    if (item != null && item.getActividades() != null && columnIndex != -1) {
                        Actividad actividad = item.obtenerPorIndex(columnIndex);
                        if (actividad instanceof Evento) {
                            vistaVentanas.mostrarVentanaDetalladaEvento((Evento) actividad, this.stagePadreActual);
                            iniciarBotonesVentanaEvento((Evento) actividad, (Stage) tablaActividades.getScene().getWindow(), selectedItem, "semanal");
                        } else if (actividad instanceof Tarea) {
                            vistaVentanas.mostrarVentanaDetalladaTarea((Tarea) actividad, this.stagePadreActual);
                            iniciarBotonesVentanaTarea((Tarea) actividad, (Stage) tablaActividades.getScene().getWindow(), selectedItem, "semanal");
                        }
                    }
                }
            }
        });


        iniciarChoiceboxAgregar("semanal");
    }

    public void marcarCompletada(Tarea tarea) {
        var checkbox = vistaVentanas.getCompletartarea();
        checkbox.setOnAction(actionEvent -> {
            if (!checkbox.isSelected()) {
                tarea.destildarTarea();
                vistaVentanas.getCuadradito().setFill(Color.RED);
            } else {
                tarea.completarTarea();
                vistaVentanas.getCuadradito().setFill(Color.RED);
            }
        });
    }


    public void iniciarChoiceboxAgregar(String tipo) {
        ChoiceBox choiceBox = null;
        switch (tipo) {
            case "semanal":
                choiceBox = vistaCalendarioSemanal.getChoiceAgregar();
                break;
            case "diario":
                choiceBox = vistaCalendarioDiario.getChoiceAgregar();
                break;
            case "mensual":
                choiceBox = vistaCalendarioMensual.getChoiceAgregar();
                break;
        }
        choiceBox.setValue("Agregar");
        choiceBox.setItems(FXCollections.observableArrayList(
                "Tarea",
                "Evento"
        ));
        ChoiceBox finalChoiceBox = choiceBox;
        choiceBox.setOnAction(event -> {
            var selectedOption = finalChoiceBox.getValue();
            if (selectedOption.equals("Tarea")) {
                var tareanueva = new Tarea(calendario.generarID());
                calendario.AgregarActividad(tareanueva);
                iniciarEditarTarea(tareanueva, tipo);
                vistaCrearTarea.mostrarVentanaEditarTarea(tareanueva, stagePadreActual);
            } else if (selectedOption.equals("Evento")) {
                var eventonuevo = new Evento(calendario.generarID());
                calendario.AgregarActividad(eventonuevo);
                iniciarEditarEvento(eventonuevo, tipo);
                vistaVentanaEvento.mostrarVentanaEditarEvento(eventonuevo, this.stagePadreActual);
            }
            finalChoiceBox.setValue("Agregar");
        });
    }

    public void iniciarEditarEvento(Evento evento, String tipo) {
        var tituloEvento = vistaVentanaEvento.getTituloevento();
        var descripcionevento = vistaVentanaEvento.getDescripcionevento();
        var fechaInicio = vistaVentanaEvento.getFechainicioevento();
        var fechaFin = vistaVentanaEvento.getFechafinevento();
        var spinnerHoraInicio = vistaVentanaEvento.getSpinnerHoraInicio();
        var spinnerMinutoInicio = vistaVentanaEvento.getSpinnerMinutoInicio();
        var spinnerHoraFin = vistaVentanaEvento.getSpinnerHoraFin();
        var spinnerMinutoFin = vistaVentanaEvento.getSpinnerMinutoFin();
        var spinnerIntervalo = vistaVentanaEvento.getSpinnerIntervalo();
        var spinnerRepeticion = vistaVentanaEvento.getSpinnerLimiteRepeticiones();
        setlimiteSpinner(spinnerHoraInicio, 0, 23, evento.getFechaInicio().getHour());
        setlimiteSpinner(spinnerMinutoInicio, 0, 59, evento.getFechaInicio().getMinute());
        setlimiteSpinner(spinnerHoraFin, 0, 23, evento.getFechaFin().getHour());
        setlimiteSpinner(spinnerMinutoFin, 0, 59, evento.getFechaFin().getMinute());
        setlimiteSpinner(spinnerIntervalo, 1, 365, 1);
        setlimiteSpinner(spinnerRepeticion, 1, 1000, 1);
        if (evento.getTiporepeticion() != null) {
            vistaVentanaEvento.getCheckeventorepetible().setSelected(true);
            var repeticion = (RepeticionDiaria) evento.getTiporepeticion();
            setlimiteSpinner(spinnerIntervalo, 1, 365, repeticion.getIntervalo());
            setlimiteSpinner(spinnerRepeticion, 1, 1000, repeticion.getRepeticionesLimite());
        }
        var checktodoeldia = vistaVentanaEvento.getChecktodoeldia();
        setearCheckbox(checktodoeldia, spinnerHoraInicio, true);
        setearCheckbox(checktodoeldia, spinnerMinutoInicio, true);
        setearCheckbox(checktodoeldia, spinnerHoraFin, true);
        setearCheckbox(checktodoeldia, spinnerMinutoFin, true);
        var botonOK = vistaVentanaEvento.getBotoncrear();
        var checkrepeticion = vistaVentanaEvento.getCheckeventorepetible();
        setearCheckbox(checkrepeticion, spinnerIntervalo, false);
        setearCheckbox(checkrepeticion, spinnerRepeticion, false);
        var botonAlarmas = vistaVentanaEvento.getbotonAlarmas();
        botonAlarmas.setOnAction(event -> {
            vistaVentanas.mostrarVentanaAlarmas(evento.getAlarmasConfiguradas(), vistaVentanaEvento.getStageVentana());
            iniciarAlarmas(evento);
        });
        botonOK.setOnAction(event -> {
            String titulo = tituloEvento.getText();
            String descrip = descripcionevento.getText();
            evento.setTitulo(titulo);
            evento.setDescripcion(descrip);
            LocalDate inicioFecha = fechaInicio.getValue();
            if (checktodoeldia.isSelected()) {
                evento.ModificarFecha(inicioFecha.atTime(0, 0), LocalDateTime.now());
                evento.establecerdeTodoElDia();
            } else {
                LocalDate finFecha = fechaFin.getValue();
                LocalDateTime inicio = inicioFecha.atTime(spinnerHoraInicio.getValue(), spinnerMinutoInicio.getValue());
                LocalDateTime fin = finFecha.atTime(spinnerHoraFin.getValue(), spinnerMinutoFin.getValue());
                calendario.ModificarFechaEvento(evento, inicio, fin);
            }
            if (checkrepeticion.isSelected()) {
                calendario.BorrarActividad(evento);
                var repeticion = new RepeticionDiaria(evento, vistaVentanaEvento.getSpinnerLimiteRepeticiones().getValue(), vistaVentanaEvento.getSpinnerIntervalo().getValue());
                evento.ModificarRepeticion(repeticion);
                calendario.AgregarActividad(evento);
            } else {
                calendario.BorrarActividad(evento);
                evento.quitarRepeticion();
                calendario.AgregarActividad(evento);
            }
            actualizarCalendario(tipo, evento.getFechaClave());
            Stage estaVentana = (Stage) botonOK.getScene().getWindow();
            estaVentana.close();

        });
    }

    public void iniciarEditarTarea(Tarea tarea, String tipo) {
        var tituloTarea = vistaCrearTarea.getTituloTarea();
        var descripcionTarea = vistaCrearTarea.getDescripcionTarea();
        var fechaInicio = vistaCrearTarea.getFechainicioTarea();
        var horainiciotarea = vistaCrearTarea.getSpinnerHora();
        var minutoiniciotarea = vistaCrearTarea.getSpinnerMinuto();
        setlimiteSpinner(horainiciotarea, 0, 23, tarea.getFechayHoraClave().getHour());
        setlimiteSpinner(minutoiniciotarea, 0, 59, tarea.getFechayHoraClave().getMinute());
        var botonAlarmas = vistaCrearTarea.getBotonAlarmas();
        botonAlarmas.setOnAction(event -> {
            vistaVentanas.mostrarVentanaAlarmas(tarea.getAlarmasConfiguradas(), vistaCrearTarea.getStageVentana());
            iniciarAlarmas(tarea);
        });
        var checktodoeldia = vistaCrearTarea.getChecktodoeldia();
        setearCheckbox(checktodoeldia, horainiciotarea, true);
        setearCheckbox(checktodoeldia, minutoiniciotarea, true);
        var button = vistaCrearTarea.getBotonOK();
        button.setOnAction(event -> {
            String titulo = tituloTarea.getText();
            String descrip = descripcionTarea.getText();
            tarea.setTitulo(titulo);
            tarea.setDescripcion(descrip);
            LocalDate inicioFecha = fechaInicio.getValue();
            if (checktodoeldia.isSelected()) {
                calendario.ModificarFechaTarea(tarea, inicioFecha.atTime(0, 0));
                tarea.establecerdeTodoElDia();
            } else {
                LocalDateTime inicio = inicioFecha.atTime(horainiciotarea.getValue(), minutoiniciotarea.getValue());
                calendario.ModificarFechaTarea(tarea, inicio);
            }
            actualizarCalendario(tipo, tarea.getFechaClave());
            Stage estaVentana = (Stage) button.getScene().getWindow();
            estaVentana.close();
        });

    }

    public void iniciarAlarmas(Actividad actividad) {
        var botonCrearAlarmas = vistaVentanas.getBotonCrearAlarma();
        botonCrearAlarmas.setOnAction(event -> {
            var alarmaNueva = new Alarma("", actividad.getFechayHoraClave());
            actividad.AgregarAlarma(alarmaNueva);
            vistaVentanas.mostrarVentanaEditarAlarma(alarmaNueva, (Stage) botonCrearAlarmas.getScene().getWindow());
            iniciarEditarAlarmas(alarmaNueva, actividad);
        });
        var alarmas = vistaVentanas.getListaAlarmas();
        alarmas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var selectedItem = alarmas.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    vistaVentanas.mostrarVentanaEditarAlarma(selectedItem, (Stage) botonCrearAlarmas.getScene().getWindow());
                    iniciarEditarAlarmas(selectedItem, actividad);
                }

            }
        });
    }

    public void iniciarEditarAlarmas(Alarma alarma, Actividad actividad) {
        var mailAlarma = vistaVentanas.getMailAlarma();
        var spinnerHoraAlarma = vistaVentanas.getSpinnerHoraAlarma();
        var spinnerMinutoAlarma = vistaVentanas.getSpinnerMinutoAlarma();
        var spinnerIntervalo = vistaVentanas.getSpinnerIntervaloAlarma();
        var checkpersonalizado = vistaVentanas.getCheckpersonalizado();
        var fechaAlarma = vistaVentanas.getFechaalarma();
        setearCheckbox(checkpersonalizado, spinnerIntervalo, true);
        setearCheckbox(checkpersonalizado, spinnerHoraAlarma, false);
        setearCheckbox(checkpersonalizado, spinnerMinutoAlarma, false);
        setlimiteSpinner(spinnerHoraAlarma, 0, 23, alarma.getHoraASonar().getHour());
        setlimiteSpinner(spinnerMinutoAlarma, 0, 59, alarma.getHoraASonar().getMinute());
        setlimiteSpinner(spinnerIntervalo, 0, 365, alarma.getIntervalo());
        checkpersonalizado.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fechaAlarma.setDisable(false);
            } else {
                fechaAlarma.setDisable(true);
            }
        });
        var botonAceptar = vistaVentanas.getBotonAceptar();
        botonAceptar.setOnAction(actionEvent -> {
            alarma.setCorreoElectronico(mailAlarma.getText());
            if (checkpersonalizado.isSelected()) {
                var hora = LocalTime.of(spinnerHoraAlarma.getValue(), spinnerMinutoAlarma.getValue());
                alarma.modificarHoraASonarAbsoluta(LocalDateTime.of(fechaAlarma.getValue(), hora));
            } else {
                alarma.modificarHoraASonarPorIntervalo(spinnerIntervalo.getValue(), actividad.getFechayHoraClave());
            }
            Stage estaVentana = (Stage) botonAceptar.getScene().getWindow();
            Stage stageAnterior = (Stage) estaVentana.getOwner();
            vistaVentanas.mostrarVentanaAlarmas(actividad.getAlarmasConfiguradas(), (Stage) stageAnterior.getOwner());
            iniciarAlarmas(actividad);
            stageAnterior.close();
            estaVentana.close();
        });
    }

    public void actualizarCalendario(String tipo, LocalDate fecha) {
        if (tipo == "diario") {
            this.vistaCalendarioDiario.mostrarActividadesDiarias(calendario.obtenerActividadesEnFecha(fecha));
        } else if (tipo == "mensual") {
            vistaCalendarioMensual.mostrarActividadesPorDiaDeMes(calendario.obtenerActividadesEnFecha(fecha));
        } else if (tipo == "semanal"){
            var fechanueva = vistaCalendarioSemanal.obtenerFechadeDomingo(fecha);
            vistaCalendarioSemanal.mostrarCalendarioSemanal(calendario.obtenerActividadesPorRango(fechanueva, fechanueva.plusDays(6)));
        }
    }

    public void setlimiteSpinner(Spinner spinner, int valorInicial, int valorFinal, int comienzo) {
        SpinnerValueFactory<Integer> valueFactoryHora = new SpinnerValueFactory.IntegerSpinnerValueFactory(valorInicial, valorFinal, comienzo);
        spinner.setValueFactory(valueFactoryHora);
    }

    public void setearCheckbox(CheckBox checkBox, Spinner spinner, boolean tipo) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                spinner.setDisable(tipo);
            } else {
                spinner.setDisable(!tipo);
            }
        });
    }

    public void iniciarBotonesVentanaTarea(Tarea tarea, Stage stagePrincipal, TreeItem<FilaActividad> item, String tipo) {
        var borrarTarea = vistaVentanas.getBorrartarea();
        borrarTarea.setOnAction(actionEvent -> {
            calendario.borrarActividadSingular(tarea);
            Stage stage = (Stage) borrarTarea.getScene().getWindow();
            stage.close();
            actualizarCalendario(tipo, tarea.getFechaClave());
        });
        var editarTarea = vistaVentanas.getEditartarea();
        var alarmasTarea = vistaVentanas.getEditarAlarmaTarea();
        var checkcompletar = vistaVentanas.getCompletartarea();
        checkcompletar.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                tarea.completarTarea();
            } else {
                tarea.destildarTarea();
            }
        });
        editarTarea.setOnAction(actionEvent -> {
            vistaCrearTarea.mostrarVentanaEditarTarea(tarea, stagePrincipal);
            iniciarEditarTarea(tarea, "diario");
            Stage stage = (Stage) editarTarea.getScene().getWindow();
            stage.close();

        });
        alarmasTarea.setOnAction(actionEvent -> {
            vistaVentanas.mostrarVentanaAlarmas(tarea.getAlarmasConfiguradas(), stagePrincipal);
            iniciarAlarmas(tarea);
            Stage stage = (Stage) editarTarea.getScene().getWindow();
            stage.close();
        });
        var fecha = tarea.getFechaClave();
        alarmasTarea.getScene().getWindow().setOnCloseRequest(event -> {
            actualizarCalendario(tipo, fecha);
        });
    }

    public void iniciarBotonesVentanaEvento(Evento evento, Stage stagePrincipal, TreeItem<FilaActividad> item, String tipo) {
        var borrarEvento = vistaVentanas.getBorrarEvento();
        var editarEvento = vistaVentanas.getEditarEvento();
        var alarmasEvento = vistaVentanas.getEditarAlarmaEvento();
        borrarEvento.setOnAction(actionEvent -> {
            calendario.borrarActividadSingular(evento);
            Stage stage = (Stage) editarEvento.getScene().getWindow();
            stage.close();
            actualizarCalendario(tipo, evento.getFechaClave());
        });
        editarEvento.setOnAction(actionEvent -> {
            vistaVentanaEvento.mostrarVentanaEditarEvento(evento, stagePrincipal);
            iniciarEditarEvento(evento, tipo);
            Stage stage = (Stage) editarEvento.getScene().getWindow();
            stage.close();

        });
        alarmasEvento.setOnAction(actionEvent -> {
            vistaVentanas.mostrarVentanaAlarmas(evento.getAlarmasConfiguradas(), stagePrincipal);
            iniciarAlarmas(evento);
            Stage stage = (Stage) editarEvento.getScene().getWindow();
            stage.close();
        });


    }

    public void borrarLabelsEnGridPane(GridPane gridPane) {
        List<Node> nodosEliminar = new ArrayList<>();

        for (Node nodo : gridPane.getChildren()) {
            if (nodo instanceof Label) {
                nodosEliminar.add(nodo);
            }
        }

        gridPane.getChildren().removeAll(nodosEliminar);
    }

    public void iniciarNotificaciones() {
        var timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Actividad actividad = calendario.obtenerAlarmaASonar();
                if (actividad != null) {
                    Platform.runLater(() -> {
                        vistaVentanas.mostrarNotificacionAlarma(actividad);
                        var botonEntendido = vistaVentanas.getBotonNotiAlarma();
                        botonEntendido.setOnAction(actionEvent -> {
                            var ventana = (Stage) botonEntendido.getScene().getWindow();
                            ventana.close();
                        });
                    });
                }
            }
        }, 0, 1000);




    }
}








