import utilities.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelCSVProcessing {
    private final File file;

    public ParallelCSVProcessing(File file) {
        this.file = file;
    }

    public String processPart(long start, long end, String columnToFilter, String filter) throws Exception {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            System.out.println("Computing the part from " + start + " to " + end);
            randomAccessFile.seek(start);

            /*
            // Adjust the start position to the beginning of a line
            while (start > 0 && randomAccessFile.read() != '\n') {
                start--;
                randomAccessFile.seek(start);
            }
             */

            int bufferSize = (int) (end - start);
            byte[] buffer = new byte[bufferSize];
            randomAccessFile.read(buffer);

            String chunkData = new String(buffer);

            List<Object[]> datos = CSVProcessor.convertStringToList(chunkData);

            // apply filter
            datos = Filtrador.filtrar(datos, columnToFilter, filter);
            System.out.println("Finished the part from " + start + " to " + end);
            return CSVProcessor.convertToString(datos);
        }
    }

    public Callable<String> processPartTask(final long start, final long end, String columnToFilter, String filter) {
        return () -> processPart(start, end, columnToFilter, filter);
    }

    public void processAll(int noOfThreads, String columnToFilter, String filter) throws Exception {
        long fileSize = file.length();
        List<Callable<String>> tasks = new ArrayList<>(noOfThreads);
        long start = 0;
        long end;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            for (int i = 0; i < noOfThreads; i++) {
                end = start + (fileSize / noOfThreads);
                if (i == noOfThreads - 1) {
                    end = fileSize; // Last thread takes remaining bytes
                } else {
                    // Adjust the start position to the beginning of a line
                    randomAccessFile.seek(end);
                    while (end < fileSize && randomAccessFile.read() != '\n') {
                        end++;
                    }
                    end++;
                }

                tasks.add(processPartTask(start, end, columnToFilter, filter));
                start = end;
            }
        }

        ExecutorService es = Executors.newFixedThreadPool(noOfThreads);
        List<Future<String>> results = es.invokeAll(tasks);
        es.shutdown();

        // Collect processed CSV data from the results
        List<String> processedData = new ArrayList<>();
        for (Future<String> result : results) {
            processedData.add(result.get());
        }

        // Write all the collected CSV data to a single file
        CSVWriter.writeCSVToFile(processedData);
    }

}
