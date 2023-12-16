import org.junit.Test;

import static org.junit.Assert.*;

public class SerializadorCalendarioTest {
    @Test
    public void PruebaGuardadoTest() throws ValidacionCalendarioException, ClassNotFoundException {
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        calendario.AgregarActividad(evento);
        SerializadorCalendario.guardarCalendario(calendario, "calendario.ser");
        var calendarioDevuelto = SerializadorCalendario.cargarCalendario("calendario.ser");
        var idEvento = evento.getID();
        var fechaEvento = evento.getFechaClave();
        var eventoDevuelto = calendarioDevuelto.buscarActividad(fechaEvento,idEvento);
        assertEquals(idEvento, eventoDevuelto.getID());
        assertEquals(fechaEvento, eventoDevuelto.getFechaClave());
    }

    @Test
    public void PruebaGuardadoTestConRepeticiones() throws ValidacionCalendarioException, ClassNotFoundException {
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        var repeticion = new RepeticionDiaria(evento,0,  1);
        evento.ModificarRepeticion(repeticion);
        calendario.AgregarActividad(evento);
        SerializadorCalendario.guardarCalendario(calendario, "calendario.ser");
        var calendarioSerializado = SerializadorCalendario.cargarCalendario("calendario.ser");
        var idEvento = evento.getID();
        var fechaEvento = evento.getFechaClave();
        var repeticiones = evento.getRepeticiones();
        Evento eventoSerializado = (Evento) calendarioSerializado.buscarActividad(fechaEvento,idEvento);
        var repeticionesSerializadas = eventoSerializado.getRepeticiones();
        for (int i = 0 ; i<repeticiones.size(); i++){
            Evento evento1 = repeticiones.get(i);
            Evento evento2 = repeticionesSerializadas.get(i);
            assertEquals(evento1.getFechaClave(), evento2.getFechaClave());
        }
    }

    @Test
    public void PruebaGuardadoTestConAlarma() throws ValidacionCalendarioException, ClassNotFoundException {
        var calendario = new Calendario();
        var evento = new Evento(calendario.generarID());
        evento.AgregarAlarma("hola123@gmail.com");
        calendario.AgregarActividad(evento);
        SerializadorCalendario.guardarCalendario(calendario, "calendario.ser");
        var calendarioDevuelto = SerializadorCalendario.cargarCalendario("calendario.ser");
        var idEvento = evento.getID();
        var fechaEvento = evento.getFechaClave();
        var alarmas = evento.getAlarmasConfiguradas();
        Evento eventoSerializado = (Evento) calendarioDevuelto.buscarActividad(fechaEvento,idEvento);
        var alarmasSerializadas = eventoSerializado.getAlarmasConfiguradas();
        assertEquals(idEvento, eventoSerializado.getID());
        assertEquals(fechaEvento, eventoSerializado.getFechaClave());
        for (int i = 0 ; i<evento.getAlarmasConfiguradas().size(); i++){
            Alarma alarma1 = alarmas.get(i);
            Alarma alarma2 = alarmasSerializadas.get(i);
            assertEquals(alarma1.getHoraASonar(), alarma2.getHoraASonar());
        }
    }
}