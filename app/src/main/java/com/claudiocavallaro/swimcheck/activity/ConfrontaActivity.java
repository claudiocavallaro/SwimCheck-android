package com.claudiocavallaro.swimcheck.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.model.Atleta;
import com.claudiocavallaro.swimcheck.persistenza.RestCall;
import com.claudiocavallaro.swimcheck.persistenza.RestCallConfronto;
import com.claudiocavallaro.swimcheck.vista.ListAdapterRicerca;
import com.claudiocavallaro.swimcheck.vista.ModelloRicerca;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by claudiocavallaro on 13/01/18.
 */

public class ConfrontaActivity extends AppCompatActivity {


    private ModelloRicerca item;
    private String itemSelected;
    private Button conferma;

    private boolean isInThere = false;

    private static ArrayList<Atleta> listaAppoggioConfronto ;

    public static ArrayList<Atleta> getListaAppoggioConfronto() {
        return listaAppoggioConfronto;
    }

    public static void setListaAppoggioConfronto(ArrayList<Atleta> listaAppoggioConfronto) {
        ConfrontaActivity.listaAppoggioConfronto = listaAppoggioConfronto;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_confronto);
        setTitle("Confronto atleti");

        listaAppoggioConfronto = new ArrayList<>();

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
                    a.setNome("Luigi Pagano");
                    a.setUrl("?Atleta=72737&Azione=2");
                    a.setSesso("M");
                    a.setSoc("Gym Sport Mania SSD - Terzigno");
                    a.setAnno("1999");
                    list.add(a);
                    RestCallConfronto.setListaAtleti(list);
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
                    RestCallConfronto.setListaAtleti(list);
                    //----- HIDE KEYBOARD AFTER CLICK-----------
                    edit.setText("");
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                    //------------------------------------------


                    showList(list);
                } else if (StringUtils.containsIgnoreCase(nome, "Vincenzo") && StringUtils.containsIgnoreCase(nome, "De") && StringUtils.containsIgnoreCase(nome, "Iulio")) {
                    ArrayList<Atleta> list = new ArrayList<>();
                    Atleta a = new Atleta();
                    a.setNome("Vincenzo De Iulio");
                    a.setUrl("?Atleta=72739&Azione=2");
                    a.setSesso("M");
                    a.setSoc("Gym Sport Mania SSD - Terzigno");
                    a.setAnno("1995");
                    list.add(a);
                    RestCallConfronto.setListaAtleti(list);
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

                        //----- HIDE KEYBOARD AFTER CLICK-----------
                        edit.setText("");
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                        //------------------------------------------

                        RestCallConfronto restCall = new RestCallConfronto(ConfrontaActivity.this);
                        RestCallConfronto.setContext(getApplicationContext());
                        restCall.setUrl(nome);
                        restCall.execute();
                    }
                }


            }
        });


        Button confronto = (Button) findViewById(R.id.buttonConfronto);
        confronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaAppoggioConfronto.size() < 2) {
                    Toast.makeText(ConfrontaActivity.this, "Devi inserire almeno due atleti per poter effettuare il confronto", Toast.LENGTH_LONG).show();
                } else {
                    for (Atleta atleta : listaAppoggioConfronto) {
                        //System.out.println(atleta.toString());
                    }
                    Intent i = new Intent(ConfrontaActivity.this, ResultActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

    }


    public void showList(final ArrayList<Atleta> listaAtleti) {
        if (listaAtleti.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nessun risultato");
            builder.setMessage("Controlla:\nChe il nome sia specificato correttamente.\nChe sei connesso ad internet.\nSe il problema persiste fai una segnalazione.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Impostazioni", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(ConfrontaActivity.this, ImpostazioniActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        ArrayList<ModelloRicerca> models = new ArrayList<ModelloRicerca>();
        for (Atleta a : listaAtleti) {
            models.add(new ModelloRicerca(a.getNome(), a.getSoc(), a.getAnno()));
        }

        ListAdapterRicerca adapterRicerca = new ListAdapterRicerca(this, models);
        final ListView lv = (ListView) findViewById(R.id.list_item);
        lv.setAdapter(adapterRicerca);

        System.out.println("Size lista before " + listaAppoggioConfronto.size());

        final TextView tv = (TextView) findViewById(R.id.textView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = (ModelloRicerca) lv.getItemAtPosition(i);
                for (int p = 0; p < listaAtleti.size(); p++) {
                    final Atleta app = listaAtleti.get(p);
                    if (item.getNome().equals(app.getNome()) && item.getSocieta().equals(app.getSoc()) && item.getAnno().equals(app.getAnno())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ConfrontaActivity.this, R.style.myDialog));
                        builder.setTitle("Confronto");
                        builder.setMessage("Stai aggiungendo l'atleta " + app.getNome() + ". Sei sicuro di proseguire?");
                        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isInThere = cercaNellaLista(app, listaAppoggioConfronto);
                                if (isInThere == false) {
                                    ConfrontaActivity.listaAppoggioConfronto.add(app);
                                    tv.setText("Numero atleti selezionati : " + listaAppoggioConfronto.size());
                                } else {
                                    Toast.makeText(ConfrontaActivity.this, "Atleta già presente nella lista dei selezionati", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                }
                System.out.println("Size lista after " + listaAppoggioConfronto.size());
            }
        });
    }

    private boolean cercaNellaLista(Atleta app, ArrayList<Atleta> listaAppoggioConfronto) {
        for (int i = 0; i < listaAppoggioConfronto.size(); i++) {
            Atleta a = listaAppoggioConfronto.get(i);
            if (a.getNome().equals(app.getNome()) && a.getSoc().equals(app.getSoc()) && a.getAnno().equals(app.getAnno())) {
                return true;
            }
        }
        return false;
    }


    public void setButtonFalse() {
        conferma.setClickable(false);
    }

    public void setButtonTrue() {
        conferma.setClickable(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
