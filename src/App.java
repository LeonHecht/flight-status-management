import utilities.TextTyper;

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

    List<Object[]> datos;

    String archivo = "Data/datos.csv";

    public App() {
        this.setDatos();
    }

    private void setDatos() {
        this.datos = Lector.leerArchivoCSV(this.archivo);
    }

    public void run() {
        // print welcome message
        this.printWelcome();

        // Get user choice whether to enter comparison-mode or query mode
        String choice;
        while (true) {
            try {
                choice = this.getChoice();
                this.printChoiceConfirm(choice);
                switch (choice) {
                    // User chose to filter data
                    case "f":
                        try {
                            // get column
                            String column = this.getColumnToFilter();
                            // apply filter to column
                            boolean result = this.aplicarFiltro(column);
                            if (!result) {
                                System.out.println("No fue posible aplicar este filtro a los datos. Intenta de nuevo");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("La entrada no está en la lista de nombres de columnas." +
                                    "Intenta de nuevo.");
                        }
                        break;
                    // user chose to get metrics
                    case "m":
                        // TODO
                        System.out.println("Aquí están las métricas solicitadas:");
                        break;
                    // user chose to quit app
                    case "s":
                        // stop method run -> quit the app
                        return;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Input inválido. Intenta de nuevo.");
            }
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
            throw new InputMismatchException("Input is not in the list of column names.");
        }
    }

    private boolean aplicarFiltro(String columna) {
        String toWrite = "Específica el filtro que quieres aplicar:";
        //TextTyper.typeText(toWrite, this.typeDelay);
        System.out.println(toWrite);

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        try {
            this.datos = Filtrador.applicaFiltroBooleano(this.datos, columna, Boolean.valueOf(userInput));
        } catch (InputMismatchException e) {
            try {
                String[] min_max = userInput.split(" ");
                this.datos = Filtrador.applicaFiltroDouble(this.datos, columna,
                        Double.parseDouble(min_max[0]), Double.parseDouble(min_max[1]));
            } catch (InputMismatchException e2) {
                try {
                    String[] min_max = userInput.split(" ");
                    this.datos = Filtrador.applicaFiltroInt(this.datos, columna,
                            Integer.parseInt(min_max[0]), Integer.parseInt(min_max[1]));
                } catch (InputMismatchException e3) {
                    try {
                        String[] filter = userInput.split(" ");
                        this.datos = Filtrador.applicaFiltroString(this.datos, columna, filter);
                    } catch (InputMismatchException e4) {
                        return false;
                    }
                }
            }
        }
        return true;
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
