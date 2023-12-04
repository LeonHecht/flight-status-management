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

    public String processPart(String id, long start, long end, String columnToFilter, String filter) throws Exception {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            // System.out.println("Computing the part from " + start + " to " + end);
            randomAccessFile.seek(start);

            int bufferSize = (int) (end - start);
            byte[] buffer = new byte[bufferSize];
            randomAccessFile.read(buffer);

            String chunkData = new String(buffer);

            // apply filter
            chunkData = Filter.filtrar(chunkData, columnToFilter, filter);
            // System.out.println("Finished the part from " + start + " to " + end);
            CSVWriter.writeCSVToFileSub(chunkData, "subarchivo_" + id, false);
            return "";
        }
    }

    public Callable<String> processPartTask(String id, final long start, final long end, String columnToFilter, String filter) {
        return () -> processPart(id, start, end, columnToFilter, filter);
    }

    public void processAll(int noOfThreads, String columnToFilter, String filter, String fileName) throws Exception {
        long fileSize = file.length();
        List<Callable<String>> tasks = new ArrayList<>(noOfThreads);
        long start = 0;
        long end;
        List<String> csvFilenames = new ArrayList<>();

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
                String threadId = String.valueOf(i+1);
                tasks.add(processPartTask(threadId, start, end, columnToFilter, filter));
                start = end;
                csvFilenames.add("Data/subarchivo_" + threadId + ".csv");
            }
        }

        ExecutorService es = Executors.newFixedThreadPool(noOfThreads);
        es.invokeAll(tasks);
        es.shutdown();

        CSVWriter.mergeCSVFiles(csvFilenames, fileName);

        /*
        for (Future<String> result : results) {
            CSVWriter.writeCSVToFile(result.get(), fileName, true);
        }
         */
    }
}
