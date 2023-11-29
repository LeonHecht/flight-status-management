import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor {

    // Convert CSV chunk data (String) to List<Object[]>
    public static List<Object[]> convertStringToList(String csvData) {
        List<Object[]> rows = new ArrayList<>();
        String[] lines = csvData.split(System.lineSeparator());
        for (String line : lines) {
            String[] columns = line.split(",");

            Object[] rowData = new Object[columns.length];
            for (int i = 0; i < columns.length; i++) {
                rowData[i] = columns[i]; // Trim spaces if needed
            }

            rows.add(rowData);
        }

        return rows;
    }

    // Function to process a chunk of CSV data from an input stream
    public static List<Object[]> convertToList(InputStream inputStream) throws IOException {
        List<Object[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line based on the delimiter (e.g., comma)
                String[] columns = line.split(",");

                // Convert each column to Object and add to the row
                Object[] rowData = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    rowData[i] = columns[i]; // Convert to appropriate type if needed
                }

                rows.add(rowData); // Add the row to the list
            }
        }
        return rows;
    }

    // Convert List<Object[]> back to CSV string
    public static String convertToString(List<Object[]> rows) {
        StringBuilder csvString = new StringBuilder();

        int totalRows = rows.size();
        int rowIndex = 0;
        for (Object[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                csvString.append(row[i]); // Append the column data

                if (i < row.length - 1) {
                    csvString.append(","); // Add delimiter (comma in this case)
                }
            }
            rowIndex++;
            if (rowIndex < totalRows) {
                csvString.append(System.lineSeparator()); // Add new line after each row
            }
        }

        return csvString.toString();
    }

}
