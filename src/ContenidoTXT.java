import java.util.ArrayList;

/**
 * Clase que genera el contenido de un archivo de texto con métricas estadísticas a partir de una lista de datos.
 */
public class ContenidoTXT {
    
    /**
     * Genera el contenido de un archivo de texto con métricas estadísticas a partir de una lista de datos.
     *
     * @param datos Lista de datos de tipo Double para calcular las métricas.
     * @return El contenido del archivo de texto con las métricas estadísticas.
     */
    public static String Contenido(ArrayList<Double> datos){

        // Cálculos de las métricas...
        String maximo = String.valueOf(MetricasEstadisticas.Maximo(datos));
        String minimo = String.valueOf(MetricasEstadisticas.Minimo(datos));
        String rango = String.valueOf(MetricasEstadisticas.Rango(datos));
        String recuento = String.valueOf(MetricasEstadisticas.Recuento(datos));
        String desviacion = String.valueOf(MetricasEstadisticas.DesviacionEstandar(datos));
        String media = String.valueOf(MetricasEstadisticas.Media(datos));
        String mediana = String.valueOf(MetricasEstadisticas.Mediana(datos));
        String[] modas = new String[MetricasEstadisticas.Moda(datos).size()];
        for (int i = 0; i < MetricasEstadisticas.Moda(datos).size(); i++) {
            modas[i] = String.valueOf(MetricasEstadisticas.Cuartiles(datos)[i]);
        }
        String[] cuartiles = new String[MetricasEstadisticas.Cuartiles(datos).length];
        for (int i = 0; i < MetricasEstadisticas.Cuartiles(datos).length; i++) {
            cuartiles[i] = String.valueOf(MetricasEstadisticas.Cuartiles(datos)[i]);
        }
        String cuartil_1 = cuartiles[0];
        String cuartil_2 = cuartiles[1];
        String cuartil_3 = cuartiles[2];
        String moda_1 = modas[0];
        String unicos = String.valueOf(MetricasEstadisticas.ValoresUnicos(datos));

       
        // Texto generado con las métricas estadísticas
        String texto = String.format("\tMétricas Estadísticas\n*************************************\n"
                                     +"Máximo: %s\n"
                                     +"Mínimo: %s\n"
                                     +"Rango: %s\n"
                                     +"Recuento: %s\n"
                                     +"Desviación: %s\n"
                                     +"Media: %s\n"
                                     +"Mediana: %s\n"
                                     +"Moda: %s\n"
                                     +"Primer Cuartil: %s\n"
                                     +"Segundo Cuartil: %s\n"
                                     +"Tercer Cuartil: %s\n"
                                     +"Valores Únicos: %s\n", maximo, minimo, rango, recuento, desviacion, media, 
                                                    mediana, moda_1, cuartil_1, cuartil_2, cuartil_3, unicos);


        return texto;
    }
}

