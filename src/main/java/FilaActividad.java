import java.time.DayOfWeek;
import java.util.ArrayList;

public class FilaActividad {
    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    private ArrayList<Actividad> actividades;
    private String hora;
    private String nombre;
    private Actividad tipo;

    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;

    public FilaActividad(String hora, String actividad, Actividad tipo) {
        this.hora = hora;
        this.nombre = actividad;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLunes() {
        return lunes;
    }

    public String getMartes() {
        return martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public FilaActividad(String hora, String domingo, String lunes, String martes, String miercoles, String jueves, String viernes, String sabado, ArrayList<Actividad> actividades){
        this.hora = hora;
        this.actividades = actividades;
        this.domingo = domingo;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
    }

    public String getHora() {
        return hora;
    }

    public String getActividad() {
        return nombre;
    }
    public Actividad getTipo(){ return tipo;}

    public Actividad obtenerPorIndex(int index){
        DayOfWeek dayOfWeek = null;
        switch (index) {
            case 1:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 7:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
        }
        return obtenerActividadEnDia(dayOfWeek);
    }

    public Actividad obtenerActividadEnDia(DayOfWeek diaSemana) {
        for (Actividad actividad : actividades) {
            DayOfWeek diaActividad = actividad.getFechaClave().getDayOfWeek();
            if (diaActividad == diaSemana) {
                return actividad;
            }
        }
        return null;
    }
}


