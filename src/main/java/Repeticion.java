
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Repeticion implements Serializable {
    protected LocalDateTime fechaLimite;

    protected int repeticionesLimite;

    protected Evento eventoARepetir;



    public Repeticion(Evento evento, int repeticionesLimite){
        this.repeticionesLimite = repeticionesLimite;
        this.eventoARepetir = evento;

    }

    public abstract ArrayList<Evento> repetir();

    public LocalDateTime getFechaLimite(){
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime nuevaFecha){
        this.fechaLimite = nuevaFecha;
    }

    public abstract void modificarAInfinito();

}
