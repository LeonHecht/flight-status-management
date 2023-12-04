import utilities.*;

import java.io.File;
import java.util.*;

public class App {

    String[] columns = {
            "Quarter", "Month", "DayofMonth", "DayOfWeek", "FlightDate", "DOT_ID_Marketing_Airline",
            "OriginAirportID", "OriginState", "OriginCityName", "DestAirportID", "DestState",
            "DestCityName", "DepTime", "DepDelayMinutes", "DepDelay", "ArrDelay", "Cancelled",
            "AirTime", "Distance", "ArrDel15", "DepDel15"
    };

    String archivo;


    public void run() throws Exception {
        // print welcome message
        this.printWelcome();
        this.archivo = this.getCSVPath();
        String seqOrConcurrent = this.getSeqOrConcurrent();
        String columnToFilter = this.getColumnToFilter();
        String filter = this.getFiltro(columnToFilter);
        int availableCores = Runtime.getRuntime().availableProcessors();
        File file = new File(this.archivo);
        ParallelCSVProcessing p = new ParallelCSVProcessing(file);
        String filename = file.getName();
        // Remove the file extension if present
        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf('.'));
        }
        MedicionTiempo medicion = new MedicionTiempo();
        medicion.iniciar();
        if (seqOrConcurrent.equals("n")) {
            p.processAll(1, columnToFilter, filter, filename);
        } else {
            p.processAll(availableCores, columnToFilter, filter, filename);
        }
        medicion.detener();
        double tiempoSegundos = medicion.obtenerTiempoSegundos();
        System.out.println("Tiempo de ejecución en segundos: " + tiempoSegundos);
    }

    private String getCSVPath() {
        String toWrite = "Ingresa la ruta del archivo CSV";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    private String getSeqOrConcurrent() {
        String toWrite = "Quieres procesar los datos de manera concurrente? Sí (s), No (n)";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        if (!userInput.equals("s") && !userInput.equals("n")) {
            throw new InputMismatchException("Input inválido");
        } else {
            return userInput;
        }
    }


    private void printWelcome(){
        String toWrite= "++++++++++++++++++++++++++++++++\n" +
                        "++++Flight Status Management++++\n" +
                        "++++++++++++++++++++++++++++++++\n";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);
    }

    private String getColumnToFilter() {
        String toWrite = "Escribe la columna que quieres filtrar:";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        List<String> columnNameList = Arrays.asList(this.columns); // Convert array to List
        // Check if user input is in the list of column names
        if (columnNameList.contains(userInput)) {
            return userInput;
        } else {
            throw new InputMismatchException("Input is not in the list of column names with double values.");
        }
    }


    private String getFiltro(String columna) {
        String toWrite = "Específica el filtro que quieres aplicar:";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
