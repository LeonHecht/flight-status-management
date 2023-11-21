package utilities;

import java.util.ArrayList;
import java.util.List;

public class ColumnNumberFinder {

    public static int getColumnNumber(List<Object[]> data, String columnName) {
        if (!data.isEmpty()) {
            Object[] columnNames = data.get(0);

            for (int i = 0; i < columnNames.length; i++) {
                if (columnNames[i] instanceof String && columnNames[i].equals(columnName)) {
                    return i; // Return the column number if column name matches
                }
            }
        }

        return -1; // Column name not found
    }

    public static void main(String[] args) {
        // Assuming data is a List<Object[]> where the first row contains column names as Strings
        List<Object[]> data = new ArrayList<>();

        // Add example data (replace this with your actual data)
        data.add(new Object[]{"Column1", "Column2", "Column3"}); // First row with column names

        // Assuming you have populated the rest of the data

        // Example: Get the column number for a specific column name
        String columnNameToFind = "Column2";
        int columnNumber = getColumnNumber(data, columnNameToFind);
        if (columnNumber != -1) {
            System.out.println("Column '" + columnNameToFind + "' found at column number: " + columnNumber);
        } else {
            System.out.println("Column '" + columnNameToFind + "' not found.");
        }
    }
}
