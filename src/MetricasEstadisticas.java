import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta clase proporciona métodos estáticos para calcular diversas métricas estadísticas
 * a partir de una lista de datos de tipo Double.
 */
public class MetricasEstadisticas {

    /**
     * Calcula la media aritmética de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return La media aritmética de los datos.
     */
    static public double Media(ArrayList<Double> datos) {
        double suma = 0;
        for (double valor : datos) {
            suma += valor;
        }
        return suma / datos.size();
    }


    /**
     * Calcula la mediana de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return La mediana de los datos.
     */
    static public double Mediana(ArrayList<Double> datos) {
        Collections.sort(datos);
        int n = datos.size();
        if (n % 2 == 0) {
            return (datos.get(n / 2 - 1) + datos.get(n / 2)) / 2.0;
        } else {
            return datos.get(n / 2);
        }
    }


    /**
     * Calcula la moda de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return La moda de los datos.
     */
    static public ArrayList<Double> Moda(ArrayList<Double> datos) {

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


    /**
     * Calcula la desviación estándar de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return La desviación estándar de los datos.
     */
    static public double DesviacionEstandar(ArrayList<Double> datos) {
        // Calcular la media
        double media = Media(datos);
    
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
    
    
    /**
     * Calcula el valor mínimo en una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return El valor mínimo en los datos.
     */
    static public double Minimo(ArrayList<Double> datos) {
        return Collections.min(datos);
    }
    
    
    /**
     * Calcula el valor máximo en una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return El valor máximo en los datos.
     */
    static public double Maximo(ArrayList<Double> datos) {
        return Collections.max(datos);
    }
    
    
    /**
     * Calcula el número de datos en una lista.
     *
     * @param datos Lista de datos de tipo Double.
     * @return El número de datos en la lista.
     */
    static public int Recuento(ArrayList<Double> datos) {
        return datos.size();
    }
    
    
    /**
     * Calcula el rango de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return El rango de los datos.
     */
    static public double Rango(ArrayList<Double> datos) {
        return Maximo(datos) - Minimo(datos);
    }
    

    /**
     * Calcula los cuartiles Q1, Q2 (mediana) y Q3 de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return Un array con los valores de los cuartiles Q1, Q2 (mediana) y Q3, respectivamente.
     */
    static public double[] Cuartiles(ArrayList<Double> datos) {
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
    
    
    /**
     * Obtiene los valores únicos en una lista de datos.
     *
     * @param datos Lista de datos de tipo Double.
     * @return Un conjunto que contiene los valores únicos en los datos.
     */
    static public Set<Double> ValoresUnicos(ArrayList<Double> datos) {
        return new HashSet<>(datos);
    }
}
