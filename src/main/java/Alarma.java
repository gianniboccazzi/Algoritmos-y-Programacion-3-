import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Alarma implements Serializable {

    private LocalDateTime horaASonar;
    private String correoElectronico;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    private boolean esAbsoluta;


    private Integer intervalo;

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Alarma(String correoElectronico, LocalDateTime horaASonarDeLaActividad){
        this.horaASonar = horaASonarDeLaActividad;
        this.correoElectronico = correoElectronico;
        this.esAbsoluta = false;
        this.intervalo = 0;
    }



    public String SonarAlarma(){
        return "Alarma Sonando..";
    }

    public String EnviarEmail(){
        return "Enviar Email a " + this.correoElectronico;
    }

    public String Notificar(){
        return "Enviando Notificacion";
    }

    public LocalDateTime getHoraASonar(){
        return this.horaASonar;
    }

    public void modificarHoraASonarPorIntervalo(int intervaloEnMinutos, LocalDateTime horaASonarDeLaActividad){
        this.horaASonar = horaASonarDeLaActividad.minusMinutes(intervaloEnMinutos);
        this.esAbsoluta = false;
        this.intervalo = intervaloEnMinutos;
    }

    public void modificarHoraASonarAbsoluta(LocalDateTime horaASonar){
        this.esAbsoluta = true;
        this.horaASonar = horaASonar;
        this.intervalo = 0;
    }


    public boolean EsAbsoluta() {
        return esAbsoluta;
    }

    public Integer getIntervalo() {
        return intervalo;
    }
    public  String pasarAHoraMinuto(LocalDateTime fecha){
        LocalTime hora = fecha.toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaMinutos = hora.format(formatter);
        return horaMinutos;
    }
}