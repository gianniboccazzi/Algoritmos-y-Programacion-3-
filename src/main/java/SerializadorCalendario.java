import java.io.*;

public class SerializadorCalendario {
    public static void guardarCalendario(Calendario calendario, String rutaCalendario) throws ValidacionCalendarioException {
        try {
            var archivoSalida = new FileOutputStream(rutaCalendario);
            var salida = new ObjectOutputStream(archivoSalida);
            salida.writeObject(calendario);
            salida.close();
            archivoSalida.close();
        } catch(IOException ex){
            throw new ValidacionCalendarioException();
        }
    }

    public static Calendario cargarCalendario(String rutaCalendario) throws ValidacionCalendarioException, ClassNotFoundException {
        Calendario calendarioGuardado = null;
        try{
            var archivoEntrada = new FileInputStream(rutaCalendario);
            var entrada = new ObjectInputStream(archivoEntrada);
            calendarioGuardado = (Calendario) entrada.readObject();
            entrada.close();
            archivoEntrada.close();
            return calendarioGuardado;
        } catch(IOException excepcion){
            throw new ValidacionCalendarioException();
        }
}
}
