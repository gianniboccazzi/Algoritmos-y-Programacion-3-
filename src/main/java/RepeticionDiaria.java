
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepeticionDiaria extends Repeticion {
    private int intervalo;

    public int getIntervalo() {
        return intervalo;
    }

    public static Integer infinito = 720;

    public RepeticionDiaria(Evento evento, int repeticionesLimite, int intervalo){
        super(evento, repeticionesLimite);
        this.repeticionesLimite = repeticionesLimite;
        this.intervalo = intervalo;
        super.fechaLimite = LocalDateTime.now().plusDays(infinito);

    }

    @Override
    public ArrayList<Evento> repetir() {
        var repeticionesDeEvento = new ArrayList<Evento>();
        int repeticiones = 0;
        repeticionesDeEvento.add(super.eventoARepetir);
        var nuevoInicio = super.eventoARepetir.getFechaInicio();
        var nuevoFin = super.eventoARepetir.getFechaFin();
        while ((repeticiones < super.repeticionesLimite || repeticionesLimite == 0) && nuevoInicio.isBefore(fechaLimite)) {
            var nuevoEvento = super.eventoARepetir.clonar();
            nuevoInicio = nuevoInicio.plusDays(intervalo);
            nuevoFin = nuevoFin.plusDays(intervalo);
            nuevoEvento.modificarFechaSingular(nuevoInicio, nuevoFin);
            repeticionesDeEvento.add(nuevoEvento);
            repeticiones++;
        }
        return repeticionesDeEvento;
    }
    @Override
    public void modificarAInfinito(){
        super.fechaLimite = LocalDateTime.now().plusDays(infinito);
    }

    public int getRepeticionesLimite(){
        return repeticionesLimite;
    }

}
