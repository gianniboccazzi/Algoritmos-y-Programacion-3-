import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Actividad implements Serializable {

    public boolean SeRepite() {
        return seRepite;
    }

    protected boolean seRepite;

    private String titulo;

    private String descripcion;

    private final Integer ID;

    protected ArrayList<Alarma> alarmasConfiguradas;
    protected Boolean todoElDia;


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Actividad(Integer ID){
        this.todoElDia = false;
        this.titulo = "Sin Título";
        this.descripcion = "";
        this.ID = ID;
        this.alarmasConfiguradas = new ArrayList<>();
        this.seRepite = false;
    }

    public abstract void  editarTitulo(String nuevoTitulo);

    public abstract void editarDescripcion(String nuevaDescripcion);

    public abstract void establecerdeTodoElDia();


    public Boolean esTodoElDia(){
        return this.todoElDia;
    }

    public Integer getID(){
        return this.ID;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public String getDescripcion(){
        return this.descripcion;
    }


    public void destildarTodoElDia(){
        this.todoElDia = false;
    }
    public abstract void AgregarAlarma(String correoElectronico);

    public abstract void AgregarAlarma(Alarma alarma);
    public ArrayList<Alarma> getAlarmasConfiguradas(){
        return this.alarmasConfiguradas;
    }

    public abstract void ModificarFechaAlarma(LocalDateTime fechaNueva);

    public abstract void ModificarIntervaloAlarma(int intervalo);


    protected void setAlarmas(ArrayList<Alarma> alarmasNuevas) {
        this.alarmasConfiguradas = alarmasNuevas;
    }

    public abstract LocalDate getFechaClave();

    public abstract LocalDateTime getFechayHoraClave();

    public abstract LocalTime getHoraClave();
    public  String pasarAHoraMinuto(LocalDateTime fecha){
        LocalTime hora = fecha.toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaMinutos = hora.format(formatter);
        return horaMinutos;
    }
}






