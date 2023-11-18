public class ContenidoTXT {
    
    public static String Contenido(MetricasEstadisticas objeto){

        // Métricas
        String maximo = String.valueOf(objeto.Maximo());
        String minimo = String.valueOf(objeto.Minimo());
        String rango = String.valueOf(objeto.Rango());
        String recuento = String.valueOf(objeto.Recuento());
        String desviacion = String.valueOf(objeto.DesviacionEstandar());
        String media = String.valueOf(objeto.Media());
        String mediana = String.valueOf(objeto.Mediana());
        String[] modas = new String[objeto.Moda().size()];
        for (int i = 0; i < objeto.Moda().size(); i++) {
            modas[i] = String.valueOf(objeto.Cuartiles()[i]);
        }
        String[] cuartiles = new String[objeto.Cuartiles().length];
        for (int i = 0; i < objeto.Cuartiles().length; i++) {
            cuartiles[i] = String.valueOf(objeto.Cuartiles()[i]);
        }
        String cuartil_1 = cuartiles[0];
        String cuartil_2 = cuartiles[1];
        String cuartil_3 = cuartiles[2];
        String moda_1 = modas[0];

       
        // Texto
        String texto = String.format("\tMétricas Estadísticas\n*************************************\n"
                                     +"Máximo: %s\n"
                                     +"Mínimo: %s\n"
                                     +"Rango: %s\n"
                                     +"Recuento: %s\n"
                                     +"Desviación: %s\n"
                                     +"Media: %s\n"
                                     +"Mediana: %s\n"
                                     +"Primer Cuartil: %s\n"
                                     +"Segundo Cuartil: %s\n"
                                     +"Tercer Cuartil: %s\n"
                                     +"Moda: %s\n", maximo, minimo, rango, recuento, desviacion, media, 
                                                    mediana, cuartil_1, cuartil_2, cuartil_3, moda_1);


        return texto;
    }
}
