
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


public class Evento extends Actividad{
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private ArrayList<Evento> repeticiones;

    private Repeticion Tiporepeticion;

    public Evento(Integer ID){
        super(ID);
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = LocalDateTime.now().plusHours(1);
        this.Tiporepeticion = null;
        this.repeticiones = new ArrayList<>();
        this.repeticiones.add(this);
    }

    public void ModificarRepeticion(Repeticion repeticionNueva){
        this.Tiporepeticion = repeticionNueva;
        super.seRepite = true;
        this.repetir();
    }

    public void quitarRepeticion(){
        this.Tiporepeticion = null;
        super.seRepite = false;
    }

    @Override
    public void editarTitulo(String nuevoTitulo){
        for (Evento e : this.repeticiones) {
            e.setTitulo(nuevoTitulo);
        }
    }
    @Override
    public void editarDescripcion(String nuevaDescripcion){
        for (Evento e : this.repeticiones) {
            e.setDescripcion(nuevaDescripcion);
        }
    }

    public Repeticion getTiporepeticion() {
        return Tiporepeticion;
    }

    public void ModificarFecha(LocalDateTime nuevoInicio, LocalDateTime nuevoFin) {
        for (Evento e : this.repeticiones) {
            e.modificarFechaSingular(nuevoInicio, nuevoFin);
            e.ModificarFechaAlarma(nuevoInicio);
        }

    }

    public void modificarFechaSingular(LocalDateTime nuevoInicio, LocalDateTime nuevoFin){
        this.fechaInicio = nuevoInicio;
        this.fechaFin = nuevoFin;
        this.ModificarFechaAlarma(nuevoInicio);
    }

    public void repetir(){
        this.repeticiones = Tiporepeticion.repetir();
        for (Evento e : this.repeticiones){
            e.repeticiones = this.repeticiones;
        }


    }

    public ArrayList<Evento> getRepeticiones(){
        return this.repeticiones;
    }

    public void establecerdeTodoElDia(){
        this.todoElDia = true;
        for (Evento e : this.repeticiones) {
            var NuevaFechaInicio = LocalDateTime.of(e.getFechaInicio().toLocalDate(), LocalTime.MIN);
            var NuevaFechaFin = LocalDateTime.of(e.getFechaInicio().toLocalDate(), LocalTime.MAX);
            e.modificarFechaSingular(NuevaFechaInicio, NuevaFechaFin);
            e.ModificarFechaAlarma(NuevaFechaInicio);
        }
    }

    @Override
    public LocalDate getFechaClave() {
        return this.fechaInicio.toLocalDate();
    }

    @Override
    public LocalDateTime getFechayHoraClave() {
        return this.fechaInicio;
    }

    @Override
    public LocalTime getHoraClave() {
        return this.fechaInicio.toLocalTime();
    }


    public LocalDateTime getFechaInicio() {
        return this.fechaInicio;
    }
    public LocalDateTime getFechaFin() {
        return this.fechaFin;
    }

    public Evento clonar(){
        var nuevo = new Evento(this.getID());
        nuevo.editarDescripcion(this.getDescripcion());
        nuevo.editarTitulo(this.getTitulo());
        var alarmas = super.getAlarmasConfiguradas();
        nuevo.setAlarmas(alarmas);
        return nuevo;
    }



    @Override
    public void AgregarAlarma(String correoElectronico) {
        for(Evento e : this.repeticiones) {
            var nuevaAlarma = new Alarma(correoElectronico, e.fechaInicio);
            super.alarmasConfiguradas.add(nuevaAlarma);
        }
    }

    @Override
    public void AgregarAlarma(Alarma alarma) {
        for(Evento e : this.repeticiones) {
            super.alarmasConfiguradas.add(alarma);
        }
    }

    @Override
    public void ModificarFechaAlarma(LocalDateTime fechaNueva){
        for (Alarma alarma : this.alarmasConfiguradas){
            if (alarma.EsAbsoluta()){
                continue;
            }
            alarma.modificarHoraASonarPorIntervalo(alarma.getIntervalo(), fechaNueva);
        }
    }
    @Override
    public void ModificarIntervaloAlarma(int intervalo){
        for(Evento e : this.repeticiones){
            for (Alarma a: e.alarmasConfiguradas){
                if (a.EsAbsoluta()){
                    continue;
                }
                a.modificarHoraASonarPorIntervalo(intervalo, this.fechaInicio);
            }
        }
    }

}