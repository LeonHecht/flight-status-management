package utilities;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVWriter {

    // Function to write collected CSV data to a file
    public static void writeCSVToFile(List<String> data) {
        try (FileOutputStream outputStream = new FileOutputStream("Data/out_file.csv");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            for (String csvString : data) {
                writer.write(csvString);
                // writer.newLine(); // Add a new line between chunks if needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully created CSV file");
    }
}
