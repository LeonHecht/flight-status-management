import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FiltradorTest {


    @Test
    public void testEsPrimeraFila() {
        Object[] row = {"Quarter", "Month", "Day"};
        assertTrue(Filtrador.esPrimeraFila(row));
    }

    @Test
    public void testApplicaFiltroBooleanoOld() {
        List<Object[]> data = Arrays.asList(
                new Object[]{"Quarter", "Month", "Day"},
                new Object[]{"1", "1", "23"},
                new Object[]{"1", "1", "24"}
                // Add more rows as needed
        );
        List<Object[]> filteredData = Filtrador.applicaFiltroBooleanoOld(data, "Month", true);
        assertEquals(2, filteredData.size());
        assertEquals("1", filteredData.get(0)[0]);
        assertEquals("1", filteredData.get(1)[0]);
    }

    @Test
    public void testApplicaFiltroBooleano() {
        String csvData = "Quarter,Month,Day\n1,1,23\n1,1,24\n1,1,25";
        String filteredData = Filtrador.applicaFiltroBooleano(csvData, "Month", true);
        String[] lines = filteredData.split("\\r?\\n");
        assertEquals(3, lines.length);
        assertEquals("1,1,23", lines[0]);
        assertEquals("1,1,24", lines[1]);
        assertEquals("1,1,25", lines[2]);
    }

    @Test
    public void testApplicaFiltroInt() {
        String csvData = "Quarter,Month,Day\n1,1,23\n2,1,24\n3,1,25";
        String filteredData = Filtrador.applicaFiltroInt(csvData, "Quarter", 2, 2);
        String[] lines = filteredData.split("\\r?\\n");
        assertEquals(2, lines.length);
        assertEquals("2,1,24", lines[1]);
    }

    @Test
    public void testApplicaFiltroDouble() {
        String csvData = "Quarter,ArrDelay\n1,2.5\n2,3.5\n3,4.5";
        String filteredData = Filtrador.applicaFiltroDouble(csvData, "ArrDelay", 3.0, 4.0);
        String[] lines = filteredData.split("\\r?\\n");
        assertEquals(2, lines.length);
        assertEquals("2,3.5", lines[1]);
    }

    @Test
    public void testApplicaFiltroString() {
        String csvData = "Quarter,Name\n1,Alice\n2,Bob\n3,Charlie";
        String filteredData = Filtrador.applicaFiltroString(csvData, "Name", new String[]{"Bob", "Charlie"});
        String[] lines = filteredData.split("\\r?\\n");
        assertEquals(2, lines.length);
        assertEquals("2,Bob", lines[0]);
        assertEquals("3,Charlie", lines[1]);
    }

    // Add more tests for other methods as needed...
}
