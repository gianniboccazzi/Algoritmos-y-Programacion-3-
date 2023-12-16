import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CalendarioTest {
    @Test
    public void TestAgregarEvento() {
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        calendario.AgregarActividad(evento);
        assertEquals(1, calendario.obtenerActividadesEnFecha(evento.getFechaClave()).size());
        assertEquals(evento, calendario.obtenerActividadesEnFecha(evento.getFechaClave()).get(0));
    }

    @Test
    public void TestBorrarEvento() {
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        calendario.AgregarActividad(evento);
        calendario.BorrarActividad(evento);
        assertEquals(0, calendario.obtenerActividadesEnFecha(evento.getFechaClave()).size());
    }

    @Test
    public void TestAgregarTarea() {
        var calendario = new Calendario();
        var tarea = new Tarea(calendario.generarID());
        calendario.AgregarActividad(tarea);
        assertEquals(1, calendario.obtenerActividadesEnFecha(tarea.getFechaClave()).size());
        assertEquals(tarea, calendario.obtenerActividadesEnFecha(tarea.getFechaClave()).get(0));
    }

    @Test
    public void TestBorrarTarea() {
        var calendario = new Calendario();
        var tarea = new Evento(calendario.generarID());
        calendario.AgregarActividad(tarea);
        calendario.BorrarActividad(tarea);
        assertEquals(0, calendario.obtenerActividadesEnFecha(tarea.getFechaClave()).size());
    }
    @Test
    public void TestAgregarEventoRepetibleAnualmenteInfinito(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionAnual(evento, 0);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        LocalDate diasTest = evento.getFechaClave();
        for(int i = 0; i < 30; i++){
            assertEquals(1, calendario.obtenerActividadesEnFecha(diasTest).size());
            diasTest = diasTest.plusYears(1);
        }
    }
    @Test
    public void TestAgregarEventoRepetibleDiariamenteInfinito(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionDiaria(evento,0,  1);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        LocalDate diasTest = evento.getFechaClave();
        for(int i = 0; i < 100; i++){
            assertEquals(1, calendario.obtenerActividadesEnFecha(diasTest).size());
            diasTest = diasTest.plusDays(1);
        }
    }

    @Test
    public void TestAgregarEventoRepetibleMensualInfinito(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionMensual(evento,0);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        LocalDate diasTest = evento.getFechaClave();
        for(int i = 0; i < 100; i++){
            assertEquals(1, calendario.obtenerActividadesEnFecha(diasTest).size());
            diasTest = diasTest.plusMonths(1);
        }
    }

    @Test
    public void TestAgregarEventoRepetibleSemanalmenteInfinito(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var diasDeSemana = new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        var repeticion = new RepeticionSemanal(evento,0, diasDeSemana);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        LocalDate diasTest = evento.getFechaClave();
        for(int i = 0; i < 100; i++){
            if (diasDeSemana.contains(diasTest.getDayOfWeek())) {
                assertEquals(1, calendario.obtenerActividadesEnFecha(diasTest).size());
            }
            diasTest = diasTest.plusDays(1);
        }
    }

    @Test
    public void TestEliminarEventoRepetible(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionDiaria(evento,0,  1);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        LocalDate diasTest = evento.getFechaClave();
        calendario.BorrarActividad(evento);
        for(int i = 0; i < 100; i++){
            assertEquals(0, calendario.obtenerActividadesEnFecha(diasTest).size());
            diasTest = diasTest.plusDays(1);
        }
    }

    @Test
    public void TestModificarTextoYDescripcion(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var tarea = new Tarea(calendario.generarID());
        calendario.AgregarActividad(evento);
        calendario.AgregarActividad(tarea);
        tarea.editarTitulo("Prueba1");
        evento.editarTitulo("Prueba2");
        tarea.editarDescripcion("Prueba3");
        evento.editarDescripcion("Prueba4");
        var tareaLocalizada = calendario.buscarActividad(tarea.getFechaClave(), tarea.getID());
        assertEquals(tarea.getTitulo(), tareaLocalizada.getTitulo());
        assertEquals(tarea.getDescripcion(), tareaLocalizada.getDescripcion());
        var eventoLocalizado = calendario.buscarActividad(evento.getFechaClave(), evento.getID());
        assertEquals(evento.getTitulo(), eventoLocalizado.getTitulo());
        assertEquals(evento.getDescripcion(), eventoLocalizado.getDescripcion());

    }
    @Test
    public void TestModificarEventoRepetible(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionDiaria(evento,0,  1);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        evento.editarTitulo("PruebaNueva");
        evento.editarDescripcion("DescripNueva");
        LocalDate diasTest = evento.getFechaClave();
        for(int i = 0; i < 100; i++){
            var eventoActual = calendario.buscarActividad(diasTest, evento.getID());
            assertEquals(evento.getTitulo(), eventoActual.getTitulo());
            assertEquals(evento.getDescripcion(), eventoActual.getDescripcion());
            diasTest = diasTest.plusDays(1);
        }


    }
    @Test
    public void TestModificarHoraEventoRepetible(){
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionDiaria(evento,0,  1);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        calendario.ModificarFechaEvento(evento, evento.getFechaInicio().plusHours(2), evento.getFechaFin().plusHours(2));
        LocalDate diasTest = evento.getFechaClave();
        for(int i = 0; i < 100; i++){
            var eventoActual = calendario.buscarActividad(diasTest, evento.getID());
            assertEquals(evento.getFechaInicio(), evento.getFechayHoraClave());
            diasTest = diasTest.plusDays(1);
        }
    }


}