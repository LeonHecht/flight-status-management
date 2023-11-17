import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MetricasEstadisticasTest {

    private MetricasEstadisticas metricas;
    private ArrayList<Double> testData;

    @Before
    public void setUp() {
        // Datos de prueba
        testData = new ArrayList<>(Arrays.asList(2.0, 3.0, 5.0, 7.0, 5.0, 10.0));
        metricas = new MetricasEstadisticas(testData);
    }

    @Test
    public void testMedia() {
        assertEquals(5.333333333333333, metricas.Media(), 0.00001);
    }

    @Test
    public void testMediana() {
        assertEquals(5.0, metricas.Mediana(), 0.00001);
    }

    @Test
    public void testModa() {
        ArrayList<Double> expectedModa = new ArrayList<>(Arrays.asList(5.0));
        assertEquals(expectedModa, metricas.Moda());
    }

    @Test
    public void testDesviacionEstandar() {
        assertEquals(2.6246692913372702, metricas.DesviacionEstandar(), 0.00001);
    }

    @Test
    public void testMinimo() {
        assertEquals(2.0, metricas.Minimo(), 0.00001);
    }

    @Test
    public void testMaximo() {
        assertEquals(10.0, metricas.Maximo(), 0.00001);
    }

    @Test
    public void testRecuento() {
        assertEquals(6, metricas.Recuento());
    }

    @Test
    public void testRango() {
        assertEquals(8.0, metricas.Rango(), 0.00001);
    }

    @Test
    public void testCuartiles() {
        double[] expectedCuartiles = {3.0, 5.0, 7.0};
        assertArrayEquals(expectedCuartiles, metricas.Cuartiles(), 0.00001);
    }

    @Test
    public void testValoresUnicos() {
        Set<Double> expectedValoresUnicos = new HashSet<>(Arrays.asList(2.0, 3.0, 5.0, 7.0, 10.0));
        assertEquals(expectedValoresUnicos, metricas.ValoresUnicos());
    }
}
