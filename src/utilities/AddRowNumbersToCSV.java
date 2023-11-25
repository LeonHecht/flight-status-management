package utilities;
import java.io.*;


public class AddRowNumbersToCSV {
    public static void main(String[] args) {
        String inputFilePath = "Data/airlines_project_short.csv";  // Replace with the actual input file path
        String outputFilePath = "Data/modified_file.csv";  // Replace with the desired output file path

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            int rowNumber = 1;

            // Read the input CSV file line by line and add row numbers
            while ((line = reader.readLine()) != null) {
                line = rowNumber + "," + line; // Prepend row number to the line
                writer.write(line + "\n"); // Write the line to the output file
                rowNumber++;
            }

            // Close resources
            reader.close();
            writer.close();

            System.out.println("Row numbers added successfully to the CSV file.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
