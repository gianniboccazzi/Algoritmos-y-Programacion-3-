import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AlarmaTest {

    @Test
    public void ModificarHoraIntervalo(){
        var hora = LocalDateTime.of(2023, 4, 24, 12, 0, 0, 0);
        var horaNueva = LocalDateTime.of(2023, 4, 24, 20, 0, 0, 0);
        var intervaloMinutos = 30;
        var a1 = new Alarma("testAlarma@hotmail.com", hora);
        var horarioCambiado = LocalDateTime.of(2023, 4, 24, 19, 30, 0, 0);

        a1.modificarHoraASonarPorIntervalo(intervaloMinutos ,horaNueva);

        assertEquals(horarioCambiado, a1.getHoraASonar());
        assertEquals(false, a1.EsAbsoluta());

    }
    @Test
    public void ModificarHoraAbsoluta(){
        var hora = LocalDateTime.of(2023, 4, 24, 12, 0, 0, 0);
        var horaNueva = LocalDateTime.of(2023, 6, 19, 16, 30, 0, 0);
        var a1 = new Alarma("testAlarma@hotmail.com", hora);

        assertEquals(false, a1.EsAbsoluta());
        a1.modificarHoraASonarAbsoluta(horaNueva);
        assertEquals(true, a1.EsAbsoluta());
        assertEquals(horaNueva, a1.getHoraASonar());

    }

    @Test
    public void modificarAbsolutaAintervalo(){
        var hora = LocalDateTime.of(2023, 4, 24, 12, 0, 0, 0);
        var horaNuevaAbsoluta = LocalDateTime.of(2023, 6, 19, 16, 30, 0, 0);
        var horaNuevaIntervalo = LocalDateTime.of(2023, 7, 2, 10, 30, 0, 0);
        var horaASonarFinal = LocalDateTime.of(2023, 7, 2, 10, 0, 0, 0);
        var intervalo = 30;
        var a1 = new Alarma("testAlarma@hotmail.com", hora);

        a1.modificarHoraASonarAbsoluta(horaNuevaAbsoluta);

        assertEquals(true, a1.EsAbsoluta());
        a1.modificarHoraASonarPorIntervalo(intervalo, horaNuevaIntervalo);

        assertEquals(horaASonarFinal, a1.getHoraASonar());
        assertEquals(false, a1.EsAbsoluta());

    }


}