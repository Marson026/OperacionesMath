package com.example.operacionesmath;

import com.example.operacionesmath.Models.MathOperations;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MathOperationsTest {
    @Test public void operacionesConDecimalesYNegativos() {
        MathOperations operaciones = new MathOperations(-7.5, 2);
        assertEquals(-5.5, operaciones.sumar(), 0.000001);
        assertEquals(-9.5, operaciones.restar(), 0.000001);
        assertEquals(-15, operaciones.multiplicar(), 0.000001);
        assertEquals(-3.75, operaciones.dividir(), 0.000001);
    }

    @Test public void ceroComoNumeradorEsValido() {
        assertEquals(0, new MathOperations(0, 5).dividir(), 0);
    }

    @Test public void rechazaDivisionEntreCero() {
        assertThrows(ArithmeticException.class, () -> new MathOperations(5, 0).dividir());
        assertThrows(ArithmeticException.class, () -> new MathOperations(0, -0.0).dividir());
    }

    @Test public void rechazaValoresNoFinitos() {
        assertThrows(IllegalArgumentException.class,
                () -> new MathOperations(Double.NaN, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new MathOperations(1, Double.POSITIVE_INFINITY));
    }
}
