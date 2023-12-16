import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepeticionAnual extends Repeticion{
    public static Integer infinito = 30;
    public RepeticionAnual(Evento evento, int repeticionesLimite){
        super(evento, repeticionesLimite);
        super.fechaLimite = LocalDateTime.now().plusYears(infinito);
    }

    @Override
    public ArrayList<Evento> repetir() {
        var repeticionesDeEvento = new ArrayList<Evento>();
        int repeticiones = 0;
        repeticionesDeEvento.add(super.eventoARepetir);
        var nuevoInicio = super.eventoARepetir.getFechaInicio();
        var nuevoFin = super.eventoARepetir.getFechaFin();
        while ((repeticiones < super.repeticionesLimite || repeticionesLimite == 0) && nuevoInicio.compareTo(fechaLimite) < 0) {
            var nuevoEvento = super.eventoARepetir.clonar();
            nuevoInicio = nuevoInicio.plusYears(1);
            nuevoFin = nuevoFin.plusYears(1);
            nuevoEvento.modificarFechaSingular(nuevoInicio, nuevoFin);
            repeticionesDeEvento.add(nuevoEvento);
            repeticiones++;
        }
        return repeticionesDeEvento;
    }

    @Override
    public void modificarAInfinito() {
        super.fechaLimite = LocalDateTime.now().plusYears(infinito);
    }

}