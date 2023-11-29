package utilities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CSVWriter {

    // Function to write a single CSV string to a file
    public static void writeCSVToFile(String csvString, String fileName, boolean appendIfExists) {
        try {
            String currentDate = new SimpleDateFormat("yyyyMMdd.HHmm").format(new Date());
            String directoryPath = "Data/resultados";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Create resultados directory if it doesn't exist
            }

            String filePath = directoryPath + "/" + fileName + "_filtered(" + currentDate + ").csv";
            FileOutputStream outputStream = new FileOutputStream(filePath, appendIfExists);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            String[] lines = csvString.split("\\r?\\n"); // Split by newline

            for (String line : lines) {
                int commaCount = 0;
                int index = 0;

                // Count commas until the 20th occurrence
                while (commaCount < 20 && index < line.length()) {
                    if (line.charAt(index) == ',') {
                        commaCount++;
                    }
                    index++;
                }

                if (!line.isEmpty()) {
                    if (line.charAt(0) == 'Q') {
                        index += 7;
                    } else {
                        index += 2;
                    }
                }

                // If 20 commas found and the subsequent word is not followed by \n, insert \n
                if (commaCount == 20 && index < line.length() - 1 && line.charAt(index) != '\n') {
                    StringBuilder modifiedLine = new StringBuilder(line);
                    modifiedLine.insert(index + 1, "\n");
                    line = modifiedLine.toString();
                }

                writer.write(line + "\n");
            }
            writer.close();
            System.out.println("Successfully created/updated CSV file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static synchronized void writeCSVToFileSub(String csvString, String fileName, boolean appendIfExists) {
        try {
            String directoryPath = "Data";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Create resultados directory if it doesn't exist
            }

            String filePath = directoryPath + "/" + fileName + ".csv";
            FileOutputStream outputStream = new FileOutputStream(filePath, appendIfExists);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            String[] lines = csvString.split("\\r?\\n"); // Split by newline

            for (String line : lines) {
                int commaCount = 0;
                int index = 0;

                // Count commas until the 20th occurrence
                while (commaCount < 20 && index < line.length()) {
                    if (line.charAt(index) == ',') {
                        commaCount++;
                    }
                    index++;
                }

                if (!line.isEmpty()) {
                    if (line.charAt(0) == 'Q') {
                        index += 7;
                    } else {
                        index += 2;
                    }
                }

                // If 20 commas found and the subsequent word is not followed by \n, insert \n
                if (commaCount == 20 && index < line.length() - 1 && line.charAt(index) != '\n') {
                    StringBuilder modifiedLine = new StringBuilder(line);
                    modifiedLine.insert(index + 1, "\n");
                    line = modifiedLine.toString();
                }

                writer.write(line + "\n");
            }
            writer.close();
            System.out.println("Successfully created/updated CSV file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeCSVFiles(List<String> fileNames, String outputFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            boolean isFirstFile = true;

            for (String fileName : fileNames) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    boolean isColumnNamesWritten = !isFirstFile;

                    while ((line = reader.readLine()) != null) {
                        if (isFirstFile || !isColumnNamesWritten) {
                            writer.write(line);
                            writer.newLine();
                            if (line.startsWith("Quarter")) {
                                isColumnNamesWritten = true;
                            }
                        } else {
                            if (!line.startsWith("Quarter")) {
                                writer.write(line);
                                writer.newLine();
                            }
                        }
                    }

                    isFirstFile = false;
                } catch (IOException e) {
                    System.err.println("Error reading file: " + fileName);
                    e.printStackTrace();
                }
            }

            System.out.println("Merged files successfully into: " + outputFileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + outputFileName);
            e.printStackTrace();
        }
    }


}
