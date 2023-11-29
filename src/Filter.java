import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

public class Filter {

    static String[] columns = {
            "Quarter", "Month", "DayofMonth", "DayOfWeek", "FlightDate", "DOT_ID_Marketing_Airline",
            "OriginAirportID", "OriginState", "OriginCityName", "DestAirportID", "DestState",
            "DestCityName", "DepTime", "DepDelayMinutes", "DepDelay", "ArrDelay", "Cancelled",
            "AirTime", "Distance", "ArrDel15", "DepDel15"
    };


    /**
     * Obtiene el índice de la columna especificada en los datos.
     *
     * @param columna El nombre de la columna.
     * @return El índice de la columna.
     * @throws InputMismatchException Si la columna no está presente en los datos.
     */
    private static int getIndiceDeColumna(String columna) throws InputMismatchException {
        // Find the index of the specified column in the data

        // Creating a HashMap to store columns as keys (dictionary)
        HashMap<String, Object> columnsDictionary = new HashMap<>();

        // Assigning the column int values to each column name
        for (int i = 0; i < columns.length; i++) {
            columnsDictionary.put(columns[i], i);
        }

        if (columnsDictionary.containsKey(columna)) {
            return (int) columnsDictionary.get(columna);
        } else {
            System.out.println("Columna no está en el CSV");
            throw new InputMismatchException("Columna no está en el CSV");
        }
    }

    public static String applicaFiltroString(String csvData, String columna, String filter) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(columna);

        String[] lines = csvData.split("\\r?\\n"); // Split by newline

        List<String[]> filteredData = new ArrayList<>();

        for (String line : lines) {
            String[] row = line.split(",");
            // check if first row contains the columns
            if (row[0] == "Quarter") {
                filteredData.add(row);
            } else {
                try {
                    String value = (String) row[columnIndex];
                    if (value.equals(filter)) {
                        filteredData.add(row);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new InputMismatchException("Not castable to String");
                }
            }
        }
        return convertToString(filteredData);
    }

    private static String convertToString(List<String[]> filteredData) {
        StringBuilder result = new StringBuilder();

        for (String[] row : filteredData) {
            result.append(String.join(",", row)).append("\r\n");
        }

        return result.toString();
    }

    public static String filtrar(String datos, String columna, String filter) throws Exception {
        datos = applicaFiltroString(datos, columna, filter);

        return datos;

    }

}