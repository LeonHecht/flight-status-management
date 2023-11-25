import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Filtrador {

    /**
     * Obtiene el índice de la columna especificada en los datos.
     *
     * @param datos   La lista de datos.
     * @param columna El nombre de la columna.
     * @return El índice de la columna.
     * @throws InputMismatchException Si la columna no está presente en los datos.
     */
    private static int getIndiceDeColumna(List<Object[]> datos, String columna) throws InputMismatchException {
        // Find the index of the specified column in the data
        int columnIndex = -1;
        Object[] firstRow = datos.get(0); // Assuming the first row contains column headers

        for (int i = 0; i < firstRow.length; i++) {
            if (firstRow[i].equals(columna)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex == -1) {
            throw new InputMismatchException("Columna '" + columna + "' no está en los datos.");
        }

        return columnIndex;
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

        int columnIndex = getIndiceDeColumna(datos, columna);

        if (!(datos.get(1)[columnIndex] instanceof Boolean)) {
            throw new InputMismatchException("Columna " + columna + " no contiene booleanos.");
        }

        // Filter the data based on the specified boolean value in the column
        List<Object[]> filteredData = new ArrayList<>();

        // Add first row, containing column names
        filteredData.add(datos.get(0));

        for (Object[] row : datos) {
            if (row.length > columnIndex && row[columnIndex] instanceof Boolean && row[columnIndex].equals(bool)) {
                filteredData.add(row);
            }
        }

        return filteredData;
    }

    // Métodos applicaFiltroInt, applicaFiltroDouble, y applicaFiltroString tienen comentarios de uso similar y estructura similar.
    // No se ha incluido comentarios adicionales para evitar redundancia en el texto.
    // El contenido de estos métodos ya está documentado en los comentarios proporcionados anteriormente.
    public static List<Object[]> applicaFiltroInt(List<Object[]> datos, String columna, int min, int max) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(datos, columna);

        if (!(datos.get(1)[columnIndex] instanceof Integer)) {
            throw new InputMismatchException("Columna " + columna + " no contiene enteros.");
        }

        // Filtrar los datos basados en el rango numérico especificado en la columna
        List<Object[]> filteredData = new ArrayList<>();

        // Add first row, containing column names
        filteredData.add(datos.get(0));

        for (Object[] row : datos) {
            if (row.length > columnIndex && row[columnIndex] instanceof Integer) {
                int value = (int) row[columnIndex];
                if (value >= min && value <= max) {
                    filteredData.add(row);
                }
            }
        }

        return filteredData;
    }

    // Método para filtrar valores double en una columna específica dentro de un rango
    public static List<Object[]> applicaFiltroDouble(List<Object[]> datos, String columna, double min, double max) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(datos, columna);

        if (!(datos.get(1)[columnIndex] instanceof Double)) {
            throw new InputMismatchException("Columna " + columna + " no contiene doubles.");
        }

        // Filtrar los datos basados en el rango de valores double especificado en la columna
        List<Object[]> filteredData = new ArrayList<>();

        // Add first row, containing column names
        filteredData.add(datos.get(0));

        for (Object[] row : datos) {
            if (row.length > columnIndex && row[columnIndex] instanceof Double) {
                double value = (double) row[columnIndex];
                if (value >= min && value <= max) {
                    filteredData.add(row);
                }
            }
        }
        return filteredData;
    }

    public static List<Object[]> applicaFiltroString(List<Object[]> datos, String columna, String[] filter) {
        // Encontrar el índice de la columna especificada en los datos
        int columnIndex = getIndiceDeColumna(datos, columna);

        if (!(datos.get(1)[columnIndex] instanceof String)) {
            throw new InputMismatchException("Columna " + columna + " no contiene strings.");
        }

        // Filtrar los datos basados en el conjunto de valores String proporcionados como filtro
        List<Object[]> filteredData = new ArrayList<>();

        // Add first row, containing column names
        filteredData.add(datos.get(0));

        for (Object[] row : datos) {
            System.out.println("row: " + Arrays.toString(row));
            if (row.length > columnIndex && row[columnIndex] instanceof String) {
                String value = (String) row[columnIndex];
                for (String filterValue : filter) {
                    if (value.equals(filterValue)) {
                        filteredData.add(row);
                        break;
                    }
                }
            }
        }
        return filteredData;
    }

    public static List<Object[]> filtrar(List<Object[]> datos, String columna, String filtro) {
        try {
            datos = applicaFiltroBooleano(datos, columna, Boolean.valueOf(filtro));
        } catch (InputMismatchException e) {
            try {
                String[] min_max = filtro.split(" ");
                datos = Filtrador.applicaFiltroDouble(datos, columna,
                        Double.parseDouble(min_max[0]), Double.parseDouble(min_max[1]));
            } catch (InputMismatchException e2) {
                try {
                    String[] min_max = filtro.split(" ");
                    datos = Filtrador.applicaFiltroInt(datos, columna,
                            Integer.parseInt(min_max[0]), Integer.parseInt(min_max[1]));
                } catch (InputMismatchException e3) {
                    try {
                        String[] filter = filtro.split(" ");
                        datos = Filtrador.applicaFiltroString(datos, columna, filter);
                    } catch (InputMismatchException e4) {
                        return datos;
                    }
                }
            }
        }
        return datos;

    }

}
