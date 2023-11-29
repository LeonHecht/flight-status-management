import utilities.CSVWriter;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ParallelCSVProcessing1 {
    private File file;

    public ParallelCSVProcessing1(File file) {
        this.file = file;
    }

    // Processes the given portion of the file.
    // Called simultaneously from several threads.
    // Use your custom return type as needed, I used String just to give an example.
    public String processPart(long start, long end, String columnToFilter, String filter) throws Exception {
        InputStream is = new FileInputStream(file);
        is.skip(start);
        // do a computation using the input stream,
        // checking that we don't read more than (end-start) bytes
        System.out.println("Computing the part from " + start + " to " + end);
        // Thread.sleep(1000);

        long chunkSize = end - start;
        byte[] buffer = new byte[(int) chunkSize];
        int bytesRead = is.read(buffer);

        if (bytesRead != -1) {
            /*
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(buffer, 0, bytesRead);
            outputStream.close();
            String csvString = outputStream.toString(StandardCharsets.UTF_8);
             */
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
            List<Object[]> datos = CSVProcessor.convertToList(inputStream);

            // apply filter
            datos = Filtrador.filtrar(datos, columnToFilter, filter);

            is.close();
            System.out.println("Finished the part from " + start + " to " + end);
            return CSVProcessor.convertToString(datos);
            // return csvString;
        }

        // return "Error occurred";
        return null;
    }

    // Creates a task that will process the given portion of the file,
    // when executed.
    public Callable<String> processPartTask(final long start, final long end, String columnToFilter, String filter) {
        return new Callable<String>() {
            public String call() throws Exception {
                return processPart(start, end, columnToFilter, filter);
            }
        };
    }

    // Splits the computation into chunks of the given size,
    // creates appropriate tasks and runs them using a
    // given number of threads.
    public void processAll(int noOfThreads, int chunkSize, String columnToFilter, String filter) throws Exception {
        //int count = (int)((file.length() + chunkSize - 1) / chunkSize);
        java.util.List<Callable<String>> tasks = new ArrayList<Callable<String>>(noOfThreads);
        for(int i = 0; i < noOfThreads; i++)
            tasks.add(processPartTask(i * chunkSize, Math.min(file.length(), (i+1) * chunkSize), columnToFilter, filter));
        ExecutorService es = Executors.newFixedThreadPool(noOfThreads);

        java.util.List<Future<String>> results = es.invokeAll(tasks);
        es.shutdown();

        /*
        // use the results for something
        for(Future<String> result : results)
            System.out.println(result.get());
         */
        // Collect processed CSV data from the results
        List<String> processedData = new ArrayList<>();
        for (Future<String> result : results) {
            processedData.add(result.get());
        }

        // Write all the collected CSV data to a single file
        CSVWriter.writeCSVToFile(processedData);
    }


    public static void main(String argv[]) throws Exception {
        int availableCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Num of cores: " + availableCores);
        File file = new File("Data/modified_file.csv");
        long fileSizeInBytes = file.length();
        ParallelCSVProcessing1 p = new ParallelCSVProcessing1(file);
        int chunks = (int) (fileSizeInBytes/availableCores);
        System.out.println("Num of chunks: " + chunks);
        // p.processAll(availableCores, chunks);
    }
}