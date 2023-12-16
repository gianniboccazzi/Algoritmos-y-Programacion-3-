import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventoTest {

    @Test
    public void CrearEventoSinRepeticion(){
        var id = 1;
        var e1 = new Evento(id);
        var inicio = LocalDateTime.of(2023, 11, 12, 14, 0, 0, 0 );
        var fin = LocalDateTime.of(2023, 11, 12, 16, 0, 0, 0 );
        e1.modificarFechaSingular(inicio,  fin);
        var repeticiones = e1.getRepeticiones();
        assertEquals(1, repeticiones.size());
        assertEquals("Sin Título", e1.getTitulo());
        assertEquals("", e1.getDescripcion());

        e1.editarTitulo("Titulo nuevo");
        e1.editarDescripcion("probando los cambios");

        assertEquals("Titulo nuevo", e1.getTitulo());
        assertEquals("probando los cambios", e1.getDescripcion());

    }
    @Test
    public void TestClonarEvento(){
        var id = 1;
        var e1 = new Evento(id);
        e1.editarTitulo("test");
        e1.editarDescripcion("probando clonar");
        var e2 = e1.clonar();

        assertEquals(e1.getDescripcion(), e2.getDescripcion());
        assertEquals(e1.getTitulo(), e2.getTitulo());
        assertEquals(e1.getID(), e2.getID());

    }

    @Test

    public void TestRepeticionDiariaInfinita(){
        var id = 1;
        var e1 = new Evento(id);
        var fechaInicio = LocalDateTime.of(2023, 10, 1, 19, 0, 0, 0);
        var fechaFin = LocalDateTime.of(2023, 10, 1, 20, 0, 0, 0);
        e1.modificarFechaSingular(fechaInicio, fechaFin);
        var repeticion = new RepeticionDiaria(e1, 1, 3);
        repeticion.modificarAInfinito();
        e1.ModificarRepeticion(repeticion);
        e1.repetir();

        for (Evento e : e1.getRepeticiones()){
            assertEquals(e1.getRepeticiones(),e.getRepeticiones());

        }

    }





}