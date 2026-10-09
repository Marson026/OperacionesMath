package com.example.operacionesmath;

import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import com.example.operacionesmath.Models.MathOperations;
import com.example.operacionesmath.VIew.ResultActivity;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText numero1, numero2;

    // Limpia el formulario solo cuando se pulsa "Volver a calcular".
    private final ActivityResultLauncher<Intent> resultadoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    numero1.setText("");
                    numero2.setText("");
                    numero1.setError(null);
                    numero2.setError(null);
                    numero1.requestFocus();
                }
            });
//Se crea la actividad principal.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //Se crea la vista.
        setContentView(R.layout.activity_main);
        //Se crea los campos de texto.
        numero1 = findViewById(R.id.numero1);
        numero2 = findViewById(R.id.numero2);
        //Se crea los botones.
        findViewById(R.id.btnSumar).setOnClickListener(v -> calcular('+'));
        findViewById(R.id.btnRestar).setOnClickListener(v -> calcular('-'));
        findViewById(R.id.btnDividir).setOnClickListener(v -> calcular('/'));
        findViewById(R.id.btnMultiplicar).setOnClickListener(v -> calcular('*'));
        //Esta parte hace que el teclado se adapte a la pantalla.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // También se valida el texto pegado, no solamente lo escrito con el teclado.
    private Double leerNumero(EditText campo) {
        String texto = campo.getText().toString().trim().replace(',', '.');
        if (texto.isEmpty()) {
            campo.setError(getString(R.string.error_vacio));
            return null;
        }
        try {
            double valor = Double.parseDouble(texto);
            if (!Double.isFinite(valor)) {
                throw new NumberFormatException();
            }
            return valor;
        } catch (NumberFormatException e) {
            campo.setError(getString(R.string.error_numero));
            return null;
        }
    }

    private void calcular(char operacion) {
        numero1.setError(null);
        numero2.setError(null);
        Double primero = leerNumero(numero1);
        Double segundo = leerNumero(numero2);
        if (primero == null || segundo == null) {
            (primero == null ? numero1 : numero2).requestFocus();
            return;
        }

        MathOperations matematicas = new MathOperations(primero, segundo);
        double resultado;
        String simbolo;
        try {
            switch (operacion) {
                case '+': resultado = matematicas.sumar(); simbolo = "+"; break;
                case '-': resultado = matematicas.restar(); simbolo = "−"; break;
                case '*': resultado = matematicas.multiplicar(); simbolo = "×"; break;
                case '/': resultado = matematicas.dividir(); simbolo = "÷"; break;
                default: return;
            }
        } catch (ArithmeticException e) {
            numero2.setError(e.getMessage());
            numero2.requestFocus();
            return;
        }
        if (!Double.isFinite(resultado)) {
            numero1.setError(getString(R.string.error_resultado));
            numero1.requestFocus();
            return;
        }

        // Android crea la segunda actividad; los datos viajan mediante el Intent.
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_NUMERO1, primero.doubleValue());
        intent.putExtra(ResultActivity.EXTRA_NUMERO2, segundo.doubleValue());
        intent.putExtra(ResultActivity.EXTRA_OPERACION, simbolo);
        intent.putExtra(ResultActivity.EXTRA_RESULTADO, resultado);
        resultadoLauncher.launch(intent);
    }
}
