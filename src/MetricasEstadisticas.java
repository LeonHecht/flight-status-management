import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MetricasEstadisticas {
    private ArrayList<Double> datos;

    public MetricasEstadisticas(ArrayList<Double> datos) {
        this.datos = datos;
    }

    public double Media() {
        double suma = 0;
        for (double valor : datos) {
            suma += valor;
        }
        return suma / datos.size();
    }

    public double Mediana() {
        Collections.sort(datos);
        int n = datos.size();
        if (n % 2 == 0) {
            return (datos.get(n / 2 - 1) + datos.get(n / 2)) / 2.0;
        } else {
            return datos.get(n / 2);
        }
    }

    public ArrayList<Double> Moda() {

        // Creamos un ArrayList para almacenar los conteos de cada valor
        ArrayList<Integer> conteos = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            conteos.add(Collections.frequency(datos, datos.get(i)));
        }
    
        // Encontramos el máximo conteo
        int maxConteo = Collections.max(conteos);
    
        // Creamos un ArrayList para almacenar las modas
        ArrayList<Double> modas = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            if (conteos.get(i) == maxConteo && !modas.contains(datos.get(i))) {
                modas.add(datos.get(i));
            }
        }
    
        // Si hay múltiples modas con el mismo conteo, devolvemos el ArrayList de modas
        // Si solo hay una moda, devolvemos un ArrayList con un solo elemento
        if (modas.size() >= 1) {
            return modas;
        }
        else{
            // Si no hay modas, devolvemos un ArrayList vacío
            return new ArrayList<>();
        }
    }


    public double DesviacionEstandar() {
        // Calcular la media
        double media = Media();
    
        // Calcular la suma de los cuadrados de las diferencias entre cada valor y la media
        double sumaCuadradosDiferencias = 0;
        for (double valor : datos) {
            sumaCuadradosDiferencias += Math.pow(valor - media, 2);
        }
    
        // Calcular la varianza
        double varianza = sumaCuadradosDiferencias / datos.size();
    
        // La desviación estándar es la raíz cuadrada de la varianza
        return Math.sqrt(varianza);
    }
    
    // Para obtener el Mínimo
    public double Minimo() {
        return Collections.min(datos);
    }
    
    // Para obtener el Máximo
    public double Maximo() {
        return Collections.max(datos);
    }
    
    // Para obtener el número de datos en la columna
    public int Recuento() {
        return datos.size();
    }
    
    // Para obtener el rango
    public double Rango() {
        return Maximo() - Minimo();
    }
    

    // Para calcular los cuartiles
    public double[] Cuartiles() {
        Collections.sort(datos);

        double[] cuartiles = new double[3]; // Para almacenar los cuartiles Q1, Q2, y Q3
        int n = datos.size();

        // Cálculo de Q1
        int indexQ1 = (int) Math.ceil(0.25 * n) - 1;
        cuartiles[0] = n % 4 == 0 ? (datos.get(indexQ1) + datos.get(indexQ1 + 1)) / 2.0 : datos.get(indexQ1);

        // Cálculo de Q2 (Mediana)
        int indexQ2 = (int) Math.ceil(0.50 * n) - 1;
        cuartiles[1] = n % 2 == 0 ? (datos.get(indexQ2) + datos.get(indexQ2 + 1)) / 2.0 : datos.get(indexQ2);

        // Cálculo de Q3
        int indexQ3 = (int) Math.ceil(0.75 * n) - 1;
        cuartiles[2] = n % 4 == 0 ? (datos.get(indexQ3) + datos.get(indexQ3 + 1)) / 2.0 : datos.get(indexQ3);

        return cuartiles;
    }
    
    
    // Para obtener los valores únicos
    public Set<Double> ValoresUnicos() {
        return new HashSet<>(datos);
    }
}
