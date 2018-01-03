package com.claudiocavallaro.swimcheck.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.com.claudiocavallaro.swimcheck.persistenza.RestCall;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edit = (EditText) findViewById(R.id.editText);

        Button conferma = (Button) findViewById(R.id.buttonConferma);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edit.getText().toString();
                char[] nomeC = nome.toCharArray();
                for (int i = 0 ; i < nomeC.length; i++){
                    if (nomeC[i] == ' '){
                        nomeC[i] = '+';
                    }
                }
                nome = String.valueOf(nomeC) + "&Azione=1";

                //System.out.println(nome);

                //----- HIDE KEYBOARD AFTER CLICK-----------
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                //------------------------------------------

                RestCall restCall = new RestCall();
                restCall.setUrl(nome);
                restCall.execute();

            }
        });
    }
}
