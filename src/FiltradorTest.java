import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltradorTest {

    @Test
    public void testApplicaFiltroBooleano() {
        List<Object[]> datos = new ArrayList<>();
        datos.add(new Object[]{"Columna1", "Columna2", "Columna3",});
        datos.add(new Object[]{true, false, true});
        datos.add(new Object[]{false, false, true});

        List<Object[]> resultado = Filtrador.applicaFiltroBooleano(datos, "Columna1", true);
        assertEquals(2, resultado.size());      // check row size
        assertEquals(true, resultado.get(1)[0]);
    }

    @Test
    public void testApplicaFiltroInt() {
        List<Object[]> datos = new ArrayList<>();
        datos.add(new Object[]{"Columna1", "Columna2", "Columna3",});
        datos.add(new Object[]{10, 20, 30});
        datos.add(new Object[]{90, 20, 30});

        List<Object[]> resultado = Filtrador.applicaFiltroInt(datos, "Columna1", 10, 25);

        assertEquals(2, resultado.size());
        assertEquals(10, resultado.get(1)[0]);
    }

    @Test
    public void testApplicaFiltroDouble() {
        List<Object[]> datos = new ArrayList<>();
        datos.add(new Object[]{"Columna1", "Columna2", "Columna3",});
        datos.add(new Object[]{10.0, 20.0, 30.0});

        List<Object[]> resultado = Filtrador.applicaFiltroDouble(datos, "Columna1", 15.0, 25.0);

        assertEquals(1, resultado.size());
    }

    @Test
    public void testApplicaFiltroString() {
        List<Object[]> datos = new ArrayList<>();
        datos.add(new Object[]{"Columna1", "Columna2", "Columna3",});
        datos.add(new Object[]{"Valor1", "Valor2", "Valor3",});
        datos.add(new Object[]{"Valor4", "Valor5", "Valor6",});

        String[] filtro = {"Valor2"};
        List<Object[]> resultado = Filtrador.applicaFiltroString(datos, "Columna2", filtro);

        assertEquals(2, resultado.size());
        assertEquals("Valor2", resultado.get(1)[1]);
    }
}
