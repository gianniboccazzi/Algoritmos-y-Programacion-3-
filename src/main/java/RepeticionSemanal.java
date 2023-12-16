import java.time.DayOfWeek;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepeticionSemanal extends Repeticion {
    private ArrayList<DayOfWeek> diasDeRepeticion;
    public static Integer infinito = 700;


    public RepeticionSemanal(Evento evento, int repeticionesLimite, ArrayList<DayOfWeek> diasDeRepeticion){
        super(evento, repeticionesLimite);
        this.diasDeRepeticion = diasDeRepeticion;
        super.fechaLimite = LocalDateTime.now().plusWeeks(infinito);
    }


    @Override
    public ArrayList<Evento> repetir() {
        var repeticionesDeEvento = new ArrayList<Evento>();
        int repeticiones = 0;
        repeticionesDeEvento.add(super.eventoARepetir);
        var nuevoInicio = super.eventoARepetir.getFechaInicio();
        var nuevoFin = super.eventoARepetir.getFechaFin();
        while ((repeticiones < super.repeticionesLimite || repeticionesLimite == 0) && nuevoInicio.isBefore(fechaLimite)) {
            nuevoInicio = nuevoInicio.plusDays(1);
            nuevoFin = nuevoFin.plusDays(1);
            if (diasDeRepeticion.contains(nuevoInicio.getDayOfWeek())) {
                var nuevoEvento = super.eventoARepetir.clonar();
                nuevoEvento.modificarFechaSingular(nuevoInicio, nuevoFin);
                repeticionesDeEvento.add(nuevoEvento);
                repeticiones++;
            }
        }
        return repeticionesDeEvento;

    }

    @Override
    public void modificarAInfinito() {
        super.fechaLimite = LocalDateTime.now().plusWeeks(infinito);
    }
}


