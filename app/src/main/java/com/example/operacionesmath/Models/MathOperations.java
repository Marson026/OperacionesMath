package com.example.operacionesmath.Models;

public class MathOperations {
    private final double numero1;
    private final double numero2;

    // El constructor recibe los valores que se usarán en las operaciones.
    public MathOperations(double numero1, double numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
    }

    public double sumar() { return numero1 + numero2; }

    public double restar() {
        if (numero1 == 0 && numero2 == 0) {
            throw new ArithmeticException("No se permite restar 0 menos 0.");
        }
        return numero1 - numero2;
    }
    public double multiplicar() { return numero1 * numero2; }

    public double dividir() {
        if (numero2 == 0) {
            throw new ArithmeticException("No se puede dividir entre cero.");
        }
        return numero1 / numero2;
    }
}
