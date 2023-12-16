import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void ModificarTareaTest(){
        var id = 1;
        var t1 = new Tarea(1);
        var fechaNueva = LocalDateTime.of(2023, 12, 12, 12, 0,0,0 );
        t1.ModificarFecha(fechaNueva);
        t1.editarTitulo("hola");
        t1.editarDescripcion("nueva des");
        assertEquals(false, t1.estaCompleta());
        assertEquals("hola", t1.getTitulo());
        assertEquals("nueva des", t1.getDescripcion());
        t1.completarTarea();
        assertEquals(true, t1.estaCompleta());


    }
    @Test
    public void ModificarHoraTest(){
        var id = 1;
        var t1 =new Tarea(id);
        var nuevaFecha = LocalDateTime.of(2023, 12, 11, 10, 0,0,0);
        var fecha = LocalDate.of(2023, 12, 11);
        t1.ModificarFecha(nuevaFecha);
        assertEquals(nuevaFecha, t1.getFechayHoraClave());
        assertEquals(t1.getFechaClave(), fecha);
    }


}