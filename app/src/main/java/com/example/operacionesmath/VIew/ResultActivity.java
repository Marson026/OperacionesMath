package com.example.operacionesmath.VIew;

import android.os.Bundle;
import android.widget.TextView;
import java.math.BigDecimal;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.operacionesmath.R;

public class ResultActivity extends AppCompatActivity {
    public static final String EXTRA_NUMERO1 = "numero1";
    public static final String EXTRA_NUMERO2 = "numero2";
    public static final String EXTRA_OPERACION = "operacion";
    public static final String EXTRA_RESULTADO = "resultado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        TextView resultado = findViewById(R.id.txtResultado);
        TextView operacion = findViewById(R.id.txtOperacion);
        if (getIntent().hasExtra(EXTRA_RESULTADO)) {
            operacion.setText(getString(R.string.formato_operacion,
                    formatear(getIntent().getDoubleExtra(EXTRA_NUMERO1, 0)),
                    getIntent().getStringExtra(EXTRA_OPERACION),
                    formatear(getIntent().getDoubleExtra(EXTRA_NUMERO2, 0))));
            resultado.setText(formatear(getIntent().getDoubleExtra(EXTRA_RESULTADO, 0)));
        } else {
            resultado.setText(R.string.sin_resultado);
        }
        findViewById(R.id.btnVolver).setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String formatear(double valor) {
        return BigDecimal.valueOf(valor).stripTrailingZeros().toPlainString();
    }
}
