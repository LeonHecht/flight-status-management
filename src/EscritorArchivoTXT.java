import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EscritorArchivoTXT {
    public void escribirEnArchivo(String nombreArchivo, String contenido) {
        try {
            File archivo = new File(nombreArchivo);

            // Verificar si el archivo ya existe
            if (archivo.exists()) {
                 // Sobreescribir el archivo existente con el nuevo contenido
                 BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, false));
                 writer.write(contenido);
                 writer.close();
                 System.out.println("Archivo sobrescrito exitosamente.");
            } else {
                // Intentar crear el archivo si no existe
                BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo));
                writer.write(contenido);
                writer.close();
                System.out.println("Archivo creado exitosamente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        EscritorArchivoTXT escritor = new EscritorArchivoTXT();

        // Datos de prueba
        ArrayList<Double> testData = new ArrayList<>(Arrays.asList(20.0, 3.0, 5.0, 7.0, 5.0, 10.0));
        MetricasEstadisticas metricas = new MetricasEstadisticas(testData);

        String texto = ContenidoTXT.Contenido(metricas);
        
        String titulo_0 = "Columna";
        String titulo = String.format("Mérticas de %s.txt", titulo_0);

        // Generación del txt
        escritor.escribirEnArchivo(titulo, texto);
    }
}
