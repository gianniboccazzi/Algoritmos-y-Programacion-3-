import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var modelo = new Modelo();
        var vistaCalendarioDiario = new VistaCalendarioDiario(stage);
        var vistaCalendarioMensual = new VistaCalendarioMensual(stage, modelo.getCalendario());
        var vistaCalendarioSemanal = new VistaCalendarioSemanal(stage);
        var vistaVentanaTarea = new VistaVentanaTarea();
        var vistaVentanaEvento = new VistaVentanaEvento();
        var vistaVentanas = new VistaVentanas();
        var controlador = new Controlador(vistaCalendarioDiario, vistaCalendarioMensual,vistaCalendarioSemanal, vistaVentanaTarea, vistaVentanas, modelo.getCalendario(), vistaVentanaEvento, stage);
        vistaCalendarioDiario.mostrarActividadesDiarias(modelo.getCalendario().obtenerActividadesEnFecha(LocalDate.now()));
        controlador.iniciarCalendarioDiario();
        controlador.iniciarNotificaciones();
        Modelo.cerrarCalendario(stage, modelo.getCalendario());
    }

    public static void main(String[] args){
        launch();
    }
}
