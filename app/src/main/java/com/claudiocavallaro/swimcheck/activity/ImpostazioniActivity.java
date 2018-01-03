package com.claudiocavallaro.swimcheck.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.com.claudiocavallaro.swimcheck.persistenza.RestCall;

/**
 * Created by claudiocavallaro on 03/01/18.
 */

public class ImpostazioniActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_impostazioni);
        final EditText editText = (EditText) findViewById(R.id.editImpo);


        Button conferma = (Button) findViewById(R.id.buttonImpo);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = editText.getText().toString();
                int num = Integer.parseInt(numero);
                if ((num <= 0) || (num >= 1000)){
                    Toast.makeText(getApplicationContext(), "Intervallo numerico non consentito", Toast.LENGTH_LONG).show();
                } else {
                    RestCall.setLimite(Integer.parseInt(numero));
                    finish();
                }
            }
        });

    }
}
