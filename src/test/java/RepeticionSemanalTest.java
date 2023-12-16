import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RepeticionSemanalTest {

    @Test
    public void TestCrearRepeticion(){
        var limite = 5;
        var e = new Evento(1);
        var diasRep = new ArrayList<DayOfWeek>();
        var inicio = LocalDateTime.of(2023, 5, 1, 10,0,0,0);
        var fin = LocalDateTime.of(2023, 5, 1, 10,30,0,0);
        diasRep.add(inicio.getDayOfWeek());
        e.modificarFechaSingular(inicio, fin);

        var rep = new RepeticionSemanal(e, limite, diasRep);
        var lista = rep.repetir();
        assertEquals(limite+1, lista.size());


        for (int i=0; i<lista.size(); i++){
            var evento = lista.get(i);
            assertEquals(inicio, evento.getFechaInicio());
            assertEquals(fin, evento.getFechaFin());
            assertEquals(diasRep.get(0), inicio.getDayOfWeek());
            inicio = inicio.plusWeeks(1);
            fin = fin.plusWeeks(1);

        }
    }

}