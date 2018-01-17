package com.claudiocavallaro.swimcheck.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.model.Atleta;
import com.claudiocavallaro.swimcheck.persistenza.RestCall;
import com.claudiocavallaro.swimcheck.vista.ListAdapterRicerca;
import com.claudiocavallaro.swimcheck.vista.ModelloRicerca;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ModelloRicerca item;
    private String itemSelected;
    private Button conferma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);
        final EditText edit = (EditText) findViewById(R.id.editText);

        conferma = (Button) findViewById(R.id.buttonConferma);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edit.getText().toString();

                if (StringUtils.containsIgnoreCase(nome, "luigi") && StringUtils.containsIgnoreCase(nome, "pagano")) {
                    ArrayList<Atleta> list = new ArrayList<>();
                    Atleta a = new Atleta();
                    a.setNome("Pagano Luigi");
                    a.setUrl("?Atleta=72737&Azione=2");
                    a.setSesso("M");
                    a.setSoc("Gym Sport Mania SSD - Terzigno");
                    a.setAnno("1999");
                    list.add(a);
                    RestCall.setListaAtleti(list);
                    //----- HIDE KEYBOARD AFTER CLICK-----------
                    edit.setText("");
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                    //------------------------------------------


                    showList(list);
                } else if (StringUtils.containsIgnoreCase(nome, "arcangelo") && StringUtils.containsIgnoreCase(nome, "ambrosio")) {
                    ArrayList<Atleta> list = new ArrayList<>();
                    Atleta a = new Atleta();
                    a.setNome("Ambrosio Arcangelo");
                    a.setUrl("?Atleta=42881&Azione=2");
                    a.setSesso("M");
                    a.setSoc("Gym Sport Mania SSD - Terzigno");
                    a.setAnno("1994");
                    list.add(a);
                    RestCall.setListaAtleti(list);
                    //----- HIDE KEYBOARD AFTER CLICK-----------
                    edit.setText("");
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                    //------------------------------------------


                    showList(list);
                } else if (StringUtils.containsIgnoreCase(nome, "Vincenzo") && StringUtils.containsIgnoreCase(nome, "De") && StringUtils.containsIgnoreCase(nome, "Iulio")) {
                    ArrayList<Atleta> list = new ArrayList<>();
                    Atleta a = new Atleta();
                    a.setNome("De Iulio Vincenzo");
                    a.setUrl("?Atleta=72739&Azione=2");
                    a.setSesso("M");
                    a.setSoc("Gym Sport Mania SSD - Terzigno");
                    a.setAnno("1995");
                    list.add(a);
                    RestCall.setListaAtleti(list);
                    //----- HIDE KEYBOARD AFTER CLICK-----------
                    edit.setText("");
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                    //------------------------------------------

                    showList(list);
                } else {
                    char[] nomeC = nome.toCharArray();
                    if (nomeC.length < 3) {
                        Toast.makeText(getApplicationContext(), "Devi inserire almeno tre caratteri per la ricerca", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < nomeC.length; i++) {
                            if (nomeC[i] == ' ') {
                                nomeC[i] = '+';
                            }
                        }
                        nome = String.valueOf(nomeC) + "&Azione=1";

                        //System.out.println(nome);

                        //----- HIDE KEYBOARD AFTER CLICK----------
                        edit.setText("");
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                        //------------------------------------------

                        RestCall restCall = new RestCall(MainActivity.this);
                        RestCall.setContext(getApplicationContext());
                        restCall.setUrl(nome);
                        restCall.execute();
                    }
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_setup) {
            Intent i = new Intent(MainActivity.this, ImpostazioniActivity.class);
            startActivity(i);
        }

        if (id == R.id.menu_confronto) {
            Intent i = new Intent(MainActivity.this, ConfrontaActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showList(ArrayList<Atleta> listaAtleti) {
        if (listaAtleti.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nessun risultato");
            builder.setMessage("Fai Attenzione a scrivere la sintassi 'nome spazio cognome' oppure aumenta il numero di possibili risultati.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Impostazioni", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, ImpostazioniActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        ArrayList<ModelloRicerca> models = new ArrayList<ModelloRicerca>();
        for (Atleta a : listaAtleti) {
            System.out.println(a.getUrl());
            models.add(new ModelloRicerca(a.getNome(), a.getSoc(), a.getAnno()));
        }

        ListAdapterRicerca adapterRicerca = new ListAdapterRicerca(this, models);
        final ListView lv = (ListView) findViewById(R.id.list_item);
        lv.setAdapter(adapterRicerca);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = (ModelloRicerca) lv.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, AtletaActivity.class);
                intent.putExtra("atleta", item);
                startActivity(intent);
            }
        });
    }

    public void setButtonFalse() {
        conferma.setClickable(false);
    }

    public void setButtonTrue() {
        conferma.setClickable(true);
    }
}
