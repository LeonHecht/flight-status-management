import java.util.*;

public class Filtrador {

    static String[] columns = {
            "Quarter", "Month", "DayofMonth", "DayOfWeek", "FlightDate", "DOT_ID_Marketing_Airline",
            "OriginAirportID", "OriginState", "OriginCityName", "DestAirportID", "DestState",
            "DestCityName", "DepTime", "DepDelayMinutes", "DepDelay", "ArrDelay", "Cancelled",
            "AirTime", "Distance", "ArrDel15", "DepDel15"
    };

    private static String getDataTypeOfColumn(String columnName) {
        // Creating a HashMap to store columns as keys (dictionary)
        HashMap<String, Object> dataTypesDict = new HashMap<>();

        dataTypesDict.put(columns[0], "Integer");
        dataTypesDict.put(columns[1], "Integer");
        dataTypesDict.put(columns[2], "Integer");
        dataTypesDict.put(columns[3], "Integer");
        dataTypesDict.put(columns[4], "Not filterable");
        dataTypesDict.put(columns[5], "Integer");
        dataTypesDict.put(columns[6], "Integer");
        dataTypesDict.put(columns[7], "String");
        dataTypesDict.put(columns[8], "String");
        dataTypesDict.put(columns[9], "Integer");
        dataTypesDict.put(columns[10], "String");
        dataTypesDict.put(columns[11], "String");
        dataTypesDict.put(columns[12], "Double");
        dataTypesDict.put(columns[13], "Double");
        dataTypesDict.put(columns[14], "Double");
        dataTypesDict.put(columns[15], "Double");
        dataTypesDict.put(columns[16], "Boolean");
        dataTypesDict.put(columns[17], "Double");
        dataTypesDict.put(columns[18], "Double");
        dataTypesDict.put(columns[19], "Double");
        dataTypesDict.put(columns[20], "Double");

        return (String) dataTypesDict.get(columnName);
    }

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

    /**
     * Hay que checar si la primera fila del pedazo que está analizando el filtro es la fila
     * con las columnas o no.
     * Si no es, podemos filtrar a partir de la primera fila.
     * Si sí es, tenemos que ignorar la primera fila y filtrar a partir de la segunda fila.
     */
    public static boolean esPrimeraFila(Object[] fila) {
        return fila[0] == "Quarter";
    }

    /**
     * Filtra los datos de una columna booleana basado en un valor booleano especificado.
     *
     * @param datos   La lista de datos.
     * @param columna El nombre de la columna booleana.
     * @param bool    El valor booleano para filtrar.
     * @return Los datos filtrados.
     * @throws InputMismatchException Si la columna no contiene valores booleanos.
     */
    public static List<Object[]> applicaFiltroBooleano(List<Object[]> datos, String columna, Boolean bool) {

        int columnIndex = getIndiceDeColumna(columna);

        // Filter the data based on the specified boolean value in the column
        List<Object[]> filteredData = new ArrayList<>();

        for (Object[] row : datos) {
            System.out.println("row from boolean filter: " + Arrays.toString(row));
            // check if first row contains the columns
            if (row[0] == "Quarter") {
                filteredData.add(row);
            } else {
                String cell = (String) row[columnIndex];
                try {
                    boolean cellBool = Boolean.parseBoolean(cell.toLowerCase());
                    if (cellBool == bool) {
                        filteredData.add(row);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new InputMismatchException("Not castable to boolean");
                }
            }
        }
        return filteredData;
    }

    // Métodos applicaFiltroInt, applicaFiltroDouble, y applicaFiltroString tienen comentarios de uso similar y estructura similar.
    // No se ha incluido comentarios adicionales para evitar redundancia en el texto.
    // El contenido de estos métodos ya está documentado en los comentarios proporcionados anteriormente.
    public static List<Object[]> applicaFiltroInt(List<Object[]> datos, String columna, int min, int max) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(columna);

        // Filtrar los datos basados en el rango numérico especificado en la columna
        List<Object[]> filteredData = new ArrayList<>();

        for (Object[] row : datos) {
            // check if first row contains the columns
            if (Objects.equals(row[0], "Quarter")) {
                filteredData.add(row);
            } else {
                try {
                    int value = Integer.parseInt((String) row[columnIndex]);
                    if (value >= min && value <= max) {
                        filteredData.add(row);
                    }
                } catch (Exception e) {
                    try {
                        int value = (int) row[columnIndex];
                        if (value >= min && value <= max) {
                            filteredData.add(row);
                        }
                    } catch (Exception e2) {
                        System.out.println(e2.getMessage());
                        throw new InputMismatchException(row[columnIndex] + " Not castable to integer");
                    }
                }
            }
        }
        return filteredData;
    }

    // Método para filtrar valores double en una columna específica dentro de un rango
    public static List<Object[]> applicaFiltroDouble(List<Object[]> datos, String columna, double min, double max) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(columna);

        // Filtrar los datos basados en el rango de valores double especificado en la columna
        List<Object[]> filteredData = new ArrayList<>();

        for (Object[] row : datos) {
            // check if first row contains the columns
            if (row[0] == "Quarter") {
                filteredData.add(row);
            } else {
                try {
                    double value = Double.parseDouble((String) row[columnIndex]);
                    if (value >= min && value <= max) {
                        filteredData.add(row);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new InputMismatchException("Not castable to double");
                }
            }
        }
        return filteredData;
    }

    public static List<Object[]> applicaFiltroString(List<Object[]> datos, String columna, String[] filter) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(columna);

        // Filtrar los datos basados en el conjunto de valores String proporcionados como filtro
        List<Object[]> filteredData = new ArrayList<>();

        for (Object[] row : datos) {
            // check if first row contains the columns
            if (row[0] == "Quarter") {
                filteredData.add(row);
            } else {
                try {
                    String value = (String) row[columnIndex];
                    for (String filterValue : filter) {
                        if (value.equals(filterValue)) {
                            filteredData.add(row);
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new InputMismatchException("Not castable to String");
                }
            }
        }
        return filteredData;
    }

    public static List<Object[]> filtrar(List<Object[]> datos, String columna, String filtro) throws Exception {

        String[] min_max = filtro.split(" ");

        String dataTypeOfColumn = getDataTypeOfColumn(columna);

        switch (dataTypeOfColumn) {
            case "Boolean":
                datos = applicaFiltroBooleano(datos, columna, Boolean.valueOf(filtro));
                break;
            case "Double":
                datos = applicaFiltroDouble(datos, columna,
                        Double.parseDouble(min_max[0]), Double.parseDouble(min_max[1]));
                break;
            case "Integer":
                datos = applicaFiltroInt(datos, columna,
                        Integer.parseInt(min_max[0]), Integer.parseInt(min_max[1]));
                break;
            case "String":
                String[] filter = filtro.split(" ");
                datos = applicaFiltroString(datos, columna, filter);
                break;
            case "Not filterable":
                throw new Exception("This column is not filterable");
        }

        return datos;

    }

}
