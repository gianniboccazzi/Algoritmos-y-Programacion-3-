import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RepeticionAnualTest {
    @Test
    public void TestCrearRepeticion(){
        var limite = 5;
        var e = new Evento(1);
        var diasRep = new ArrayList<DayOfWeek>();
        var inicio = LocalDateTime.of(2023, 5, 1, 10,0,0,0);
        var fin = LocalDateTime.of(2023, 5, 1, 10,30,0,0);
        e.modificarFechaSingular(inicio, fin);

        var rep = new RepeticionAnual(e, limite);
        var lista = rep.repetir();
        assertEquals(limite+1, lista.size());


        for (int i=0; i<lista.size(); i++){
            var evento = lista.get(i);
            assertEquals(inicio, evento.getFechaInicio());
            assertEquals(fin, evento.getFechaFin());
            inicio = inicio.plusYears(1);
            fin = fin.plusYears(1);

        }
    }

}