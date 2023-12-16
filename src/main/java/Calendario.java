import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Calendario implements Serializable {
    private TreeMap<LocalDate, ArrayList<Actividad>> ActividadesCreadas;

    private Integer idActual;

    public Calendario() {
        this.idActual = 0;
        this.ActividadesCreadas = new TreeMap<>();
    }

    public void agregarActividadSingular(Actividad actividad){
        if (ActividadesCreadas.containsKey(actividad.getFechaClave())){
            var arregloContenido = ActividadesCreadas.get(actividad.getFechaClave());
            arregloContenido.add(actividad);
            ActividadesCreadas.put(actividad.getFechaClave(), arregloContenido);
        } else{
            var arregloNuevo = new ArrayList<Actividad>();
            arregloNuevo.add(actividad);
            ActividadesCreadas.put(actividad.getFechaClave(), arregloNuevo);
        }
    }

    public void agregarEventoRepetible(Evento evento){
        var eventosARepetir = evento.getRepeticiones();
        for (Evento e : eventosARepetir){
            this.agregarActividadSingular(e);
        }
    }

    public void AgregarActividad(Actividad actividad){
        if (actividad.SeRepite()){
            this.agregarEventoRepetible((Evento) actividad);
        } else{
            this.agregarActividadSingular(actividad);
        }
    }

    public Actividad buscarActividad(LocalDate fecha, Integer id){
        var actividades = ActividadesCreadas.get(fecha);
        for (Actividad actividad : actividades){
            if (Objects.equals(actividad.getID(), id)){
                return actividad;
            }
        }
        return null;
    }
    public Integer generarID(){
        this.idActual++;
        return this.idActual - 1;

    }

    public void BorrarActividad(Actividad actividad){
        if(actividad.SeRepite()){
            this.borrarEventosRepetibles((Evento) actividad);
        } else{
            this.borrarActividadSingular(actividad);
        }
    }

    public void borrarActividadSingular(Actividad actividad){
        var actividadesDeLaFecha = this.ActividadesCreadas.get(actividad.getFechaClave());
        actividadesDeLaFecha.remove(actividad);
        this.ActividadesCreadas.put(actividad.getFechaClave(), actividadesDeLaFecha);
    }

    public void borrarEventosRepetibles(Evento evento){
        var eventosABorrar = evento.getRepeticiones();
        for (Evento e : eventosABorrar){
            borrarActividadSingular(e);
        }
    }

    public void ModificarFechaTarea(Tarea actividad, LocalDateTime nuevaFecha){
        this.BorrarActividad(actividad);
        actividad.ModificarFecha(nuevaFecha);
        this.AgregarActividad(actividad);
        actividad.destildarTodoElDia();
    }

    public void ModificarFechaEvento(Evento evento, LocalDateTime nuevaFechaInicial, LocalDateTime nuevaFechaFin){
        this.BorrarActividad(evento);
        evento.ModificarFecha(nuevaFechaInicial, nuevaFechaFin);
        this.AgregarActividad(evento);
        evento.destildarTodoElDia();
    }

    public ArrayList<Actividad> obtenerActividadesEnFecha(LocalDate fecha){
        if (this.ActividadesCreadas.get(fecha) == null){
            return new ArrayList<Actividad>();
        }
        var acts = this.ActividadesCreadas.get(fecha);
        acts.sort(Comparator.comparing(Actividad::getFechayHoraClave));
        return acts;
    }
    public ArrayList<Actividad> obtenerActividadesPorRango(LocalDate fechainicio, LocalDate fechafin) {
        if (this.ActividadesCreadas.get(fechainicio) == null && this.ActividadesCreadas.get(fechafin) == null) {
            return new ArrayList<Actividad>();
        }
        var acts = new ArrayList<Actividad>();
        for (LocalDate fecha = fechainicio; !fecha.isAfter(fechafin); fecha = fecha.plusDays(1)) {
            if (this.ActividadesCreadas.containsKey(fecha)) {
                acts.addAll(this.ActividadesCreadas.get(fecha));
            }
        }
        acts.sort(Comparator.comparing(Actividad::getFechayHoraClave));
        return acts;
    }


    public Actividad obtenerAlarmaASonar() {
        var alarmasDeLaFecha = new ArrayList<Alarma>();
        var actividadesEnFecha = obtenerActividadesEnFecha(LocalDate.now());
        for (Actividad actividad : actividadesEnFecha) {
            for (Alarma alarma : actividad.getAlarmasConfiguradas()) {
                if (alarma.getHoraASonar().truncatedTo(ChronoUnit.SECONDS).isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))){
                    return actividad;
                }
            }
        }
        return null;
    }






}
