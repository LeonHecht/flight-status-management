package utilities;

import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static List<List<Object>> transpose(List<Object[]> data) {
        List<List<Object>> transposed = new ArrayList<>();

        if (!data.isEmpty()) {
            int cols = data.get(0).length;

            for (int i = 0; i < cols; i++) {
                List<Object> column = new ArrayList<>();
                for (Object[] row : data) {
                    column.add(row[i]);
                }
                transposed.add(column);
            }
        }

        return transposed;
    }

    // Example usage:
    public static void main(String[] args) {
        // Assuming this.datos is a List<Object[]> representing rows
        List<Object[]> rows = new ArrayList<>();

        // Add some example data (replace this with your actual data)
        rows.add(new Object[]{1, 2, 3}); // Row 1
        rows.add(new Object[]{4, 5, 6}); // Row 2
        rows.add(new Object[]{7, 8, 9}); // Row 3

        // Transpose the data
        List<List<Object>> columns = transpose(rows);

        System.out.println(columns);
        // Now 'columns' contains the data represented as columns
    }
}
