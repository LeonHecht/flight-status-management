public class MedicionTiempo {
    private long inicio;
    private long fin;

    public void iniciar() {
        inicio = System.currentTimeMillis();
    }

    public void detener() {
        fin = System.currentTimeMillis();
    }

    public long obtenerTiempoMillis() {
        return fin - inicio;
    }

    public double obtenerTiempoMinutos() {
        long tiempoMillis = obtenerTiempoMillis();
        return tiempoMillis / 60000.0;
    }

    /*
        //Para la medición de tiempo en la ejecución
        MedicionTiempo medicion = new MedicionTiempo();

        // Iniciar la medición
        medicion.iniciar();


        // Aquí va el código que se desea medir


        // Detener la medición
        medicion.detener();

        // Obtener el tiempo en minutos
        double tiempoMinutos = medicion.obtenerTiempoMinutos();
        System.out.println("Tiempo de ejecución en minutos: " + tiempoMinutos);
    */
}
