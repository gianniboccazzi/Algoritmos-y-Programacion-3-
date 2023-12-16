import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Tarea extends Actividad{


    private boolean completada;

    private LocalDateTime vencimiento;

    public Tarea(int ID){
        super(ID);
        this.vencimiento = LocalDateTime.now().plusHours(1);
        this.completada = false;

    }


    public void completarTarea(){
        this.completada = true;
    }

    public void destildarTarea(){
        this.completada = false;
    }
    public boolean estaCompleta(){
        return this.completada;
    }

    @Override
    public void editarTitulo(String nuevoTitulo) {

        this.setTitulo(nuevoTitulo);
    }

    @Override
    public void editarDescripcion(String nuevaDescripcion) {
        this.setDescripcion( nuevaDescripcion);
    }

    @Override
    public void establecerdeTodoElDia(){
        this.todoElDia = true;
        this.vencimiento = LocalDateTime.of(this.vencimiento.toLocalDate(), LocalTime.MIN);
        this.ModificarFechaAlarma(this.vencimiento);
    }

    @Override
    public void AgregarAlarma(String correoElectronico) {
        var nuevaAlarma = new Alarma(correoElectronico, this.vencimiento);
        super.alarmasConfiguradas.add(nuevaAlarma);
    }

    @Override
    public void AgregarAlarma(Alarma alarma) {
        super.alarmasConfiguradas.add(alarma);
    }

    @Override
    public LocalDate getFechaClave() {
        return this.vencimiento.toLocalDate();
    }

    @Override
    public LocalDateTime getFechayHoraClave() {
        return this.vencimiento;
    }

    @Override
    public LocalTime getHoraClave() {
        return this.vencimiento.toLocalTime();
    }



    public void ModificarFecha(LocalDateTime fechaNueva){
        this.ModificarFechaAlarma(fechaNueva);
        this.vencimiento = fechaNueva;
    }

    public void ModificarFechaAlarma(LocalDateTime fechaNueva) {
        for (Alarma alarma : this.alarmasConfiguradas) {
            if (alarma.EsAbsoluta()) {
                continue;
            }
            alarma.modificarHoraASonarPorIntervalo(alarma.getIntervalo(), fechaNueva);
        }
    }

    @Override
    public void ModificarIntervaloAlarma(int intervalo) {
        for (Alarma alarma : this.alarmasConfiguradas) {
            if (alarma.EsAbsoluta()) {
                continue;
            }
            alarma.modificarHoraASonarPorIntervalo(intervalo, this.vencimiento);
        }
    }
}
