import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Modelo {
    public Calendario calendario;

    public Calendario getCalendario() {
        return calendario;
    }
    public Modelo(){
        Calendario calendario = null;
        try{
            calendario = SerializadorCalendario.cargarCalendario("calendario.ser");
        } catch (ValidacionCalendarioException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar el calendario guardado");
            calendario = new Calendario();
        }
        this.calendario = calendario;
    }

    public static Scene cargarFXML(String nombrearchivo, Object clase){
        FXMLLoader loader = new FXMLLoader(clase.getClass().getResource(nombrearchivo));
        loader.setController(clase);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var scene = new Scene(root);
        return scene;
    }
    public static void cerrarCalendario(Stage stage, Calendario calendario){
        stage.setOnCloseRequest(event -> {
            try {
                SerializadorCalendario.guardarCalendario(calendario, "calendario.ser");
            } catch (ValidacionCalendarioException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static List<String> obtenerListaHoras() {
        List<String> listaHoras = new ArrayList<>();
        int horaInicial = 0;
        int horaFinal = 23;
        for (int hora = horaInicial; hora <= horaFinal; hora++) {
            String horaFormateada = String.format("%02d:00", hora);
            listaHoras.add(horaFormateada);
        }
        return listaHoras;
    }

    public static List<Actividad> obtenerActividadesPorHora(ArrayList<Actividad> actividades, String hora) {
        List<Actividad> actividadesEnHora = new ArrayList<>();
        for (Actividad actividad : actividades) {
            String horaInicio = String.format("%02d:00",actividad.getHoraClave().getHour());
            if (horaInicio.equals(hora)) {
                actividadesEnHora.add(actividad);
            }
        }
        return actividadesEnHora;
    }


}
