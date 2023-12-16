import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class RepeticionDiariaTest {
    @Test
    public void TestCrearRepeticion(){
        var intervalo = 2;
        var limite = 100;
        var e = new Evento(1);
        var inicio = LocalDateTime.of(2023, 5, 1, 10,0,0,0);
        var fin = LocalDateTime.of(2023, 5, 1, 10,30,0,0);
        e.modificarFechaSingular(inicio, fin);
        var rep = new RepeticionDiaria(e, limite, intervalo);
        var lista = rep.repetir();
        assertEquals((limite +1), lista.size());

        for (int i=0; i<lista.size(); i++){
            var evento = lista.get(i);
            assertEquals(inicio, evento.getFechaInicio());
            assertEquals(fin, evento.getFechaFin());
            inicio = inicio.plusDays(rep.getIntervalo());
            fin = fin.plusDays(rep.getIntervalo());

        }




    }

}