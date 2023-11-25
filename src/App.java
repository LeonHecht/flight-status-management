import utilities.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class App {

    int typeDelay = 20;     // milliseconds
    String[] columns = {
            "Quarter", "Month", "DayofMonth", "DayOfWeek", "FlightDate", "DOT_ID_Marketing_Airline",
            "OriginAirportID", "OriginState", "OriginCityName", "DestAirportID", "DestState",
            "DestCityName", "DepTime", "DepDelayMinutes", "DepDelay", "ArrDelay", "Cancelled",
            "AirTime", "Distance", "ArrDel15", "DepDel15"
    };

    String[] doubleColumns = {
            "DepTime", "DepDelayMinutes", "DepDelay", "ArrDelay", "AirTime", "Distance", "ArrDel15", "DepDel15"
    };

    List<Object[]> datos;

    String archivo = "Data/airlines_project_short.csv";
    // String archivo = "Data/modified_file.csv";

    public App() {
        // this.setDatos();
    }

    private void readDatosSecuencial() {
        // The next line is only for sequential
        this.datos = Lector.leerArchivoCSV(this.archivo);

    }

    public void run() throws Exception {
        // print welcome message
        this.printWelcome();

        String seqOrConcurrent = this.getSeqOrConcurrent();
        if (seqOrConcurrent.equals("n")) {
            this.readDatosSecuencial();
            // Get user choice whether to enter comparison-mode or query mode
            String choice;
            while (true) {
                try {
                    choice = this.getChoice();
                    this.printChoiceConfirm(choice);
                    if (choice.equals("f")) {
                        // User chose to filter data
                        this.handleFilter();
                    } else if (choice.equals("m")) {
                        // user chose to get metrics
                        this.handleMetrics();
                    } else if (choice.equals("s")) {
                        // user chose to quit app
                        // stop method run -> quit the app
                        return;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input inválido. Intenta de nuevo.");
                }
            }
        } else {
            // User wants concurrency
            String columnToFilter = this.getColumnToFilter();
            String filter = this.getFiltro(columnToFilter);

            int availableCores = Runtime.getRuntime().availableProcessors();
            System.out.println("Num of cores: " + availableCores);
            File file = new File(this.archivo);
            long fileSizeInBytes = file.length();
            ParallelCSVProcessing p = new ParallelCSVProcessing(file);
            int chunks = (int) (fileSizeInBytes/availableCores);
            p.processAll(availableCores, chunks, columnToFilter, filter);
            return;
        }
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

    private void handleFilter() {
        try {
            // get column
            String column = this.getColumnToFilter();
            // apply filter to column
            String filtro = this.getFiltro(column);
            this.datos = Filtrador.filtrar(this.datos, column, filtro);
            String datosString = CSVProcessor.convertToString(this.datos);
            List<String> datosStringList = new ArrayList<>();
            datosStringList.add(datosString);
            CSVWriter.writeCSVToFile(datosStringList);
            /*
            if (!datos) {
                System.out.println("No fue posible aplicar este filtro a los datos. Intenta de nuevo");
            }
             */
        } catch (InputMismatchException e) {
            System.out.println("La entrada no está en la lista de nombres de columnas." +
                    "Intenta de nuevo.");
        }
    }

    private void handleMetrics() {
        System.out.println("Aquí están las métricas solicitadas:");
        String columna = this.getColumnForMetrics();
        int columnNumber = ColumnNumberFinder.getColumnNumber(this.datos, columna);
        // System.out.println("column: " + columnNumber);
        // System.out.println("Datos: " + this.datos);
        List<List<Object>> transposed_datos = DataConverter.transpose(this.datos);
        // System.out.println("Transposed datos: " + transposed_datos);
        List<Object> datos = transposed_datos.get(columnNumber);
        // System.out.println(datos);
        ArrayList<Double> datosDouble = ObjectToListConversion.convertListToDoubleArrayList(datos);
        // System.out.println(datosDouble);
        String texto = ContenidoTXT.Contenido(datosDouble);
        String titulo = "Mérticas de " + columna + ".txt";
        EscritorArchivoTXT escritor = new EscritorArchivoTXT();
        escritor.escribirEnArchivo(titulo, texto);
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

    private String getColumnForMetrics() {
        String toWrite = "Escribe la columna de que quieres obtener las métricas:";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        List<String> columnNameList = Arrays.asList(this.doubleColumns); // Convert array to List
        // Check if user input is in the list of column names
        if (columnNameList.contains(userInput)) {
            return userInput;
        } else {
            throw new InputMismatchException("Input is not in the list of column names.");
        }
    }

    private String getFiltro(String columna) {
        String toWrite = "Específica el filtro que quieres aplicar:";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String getChoice() throws InputMismatchException {
        String toWrite = "Si quieres filtrar los datos, pulse 'f'\n" +
                         "Si quieres obtener métricas, pulse 'm'\n" +
                         "Si deseas salir, pulse 's'.";
        // TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        // check for potential input mismatch
        if (!choice.equals("f") && !choice.equals("m") && !choice.equals("s")) {
            throw new InputMismatchException("Input inválido");
        } else {
            return choice;
        }
    }

    private void printChoiceConfirm(String choice) {
        switch (choice) {
            case "f":
                //TextTyper.typeText("Eligiste filtrar los datos", this.typeDelay);
                System.out.println("Eligiste filtrar los datos");
                break;
            case "m":
                //TextTyper.typeText("Eligiste obtener métricas", this.typeDelay);
                System.out.println("Eligiste obtener métricas");
                break;
            case "s":
                // TextTyper.typeText("Saliendo de la App", this.typeDelay);
                System.out.println("Saliendo de la App");
                break;
        }
    }
}
