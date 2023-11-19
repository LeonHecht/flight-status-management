import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lector {

    static public List<Object[]> leerArchivoCSV(String archivo) {

        List<Object[]> datos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(","); // Puedes ajustar el delimitador según tu archivo
                Object[] fila = new Object[valores.length];

                for (int i = 0; i < valores.length; i++) {
                    // Intenta convertir el valor a diferentes tipos
                    try {
                        fila[i] = Integer.parseInt(valores[i]);
                    } catch (NumberFormatException e1) {
                        try {
                            fila[i] = Double.parseDouble(valores[i]);
                        } catch (NumberFormatException e2) {
                            if ("true".equalsIgnoreCase(valores[i]) || "false".equalsIgnoreCase(valores[i])) {
                                fila[i] = Boolean.parseBoolean(valores[i]);
                            } else {
                                fila[i] = valores[i]; // Si no es un número ni un booleano, lo consideramos como String
                            }
                        }
                    }
                }

                datos.add(fila);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }
}
