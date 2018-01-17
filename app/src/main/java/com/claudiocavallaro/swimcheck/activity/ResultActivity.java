package com.claudiocavallaro.swimcheck.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.model.Atleta;
import com.claudiocavallaro.swimcheck.model.Gara;
import com.claudiocavallaro.swimcheck.persistenza.RestCallResult;
import com.claudiocavallaro.swimcheck.vista.ListAdapterConfronto;
import com.claudiocavallaro.swimcheck.vista.ModelloConfronto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by claudiocavallaro on 13/01/18.
 */

public class ResultActivity extends AppCompatActivity {

    private ArrayList<String> tipiGara;
    private ArrayList<Gara> best;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_result);

        this.setTitle("Confronto atleti");

        ArrayList<Atleta> lista = ConfrontaActivity.getListaAppoggioConfronto();

        System.out.println(lista.size());


        for (Atleta a : lista) {
            System.out.println(a.getUrl());
        }

        RestCallResult restCallResult = new RestCallResult(this, lista);
        RestCallResult.setContext(this);
        restCallResult.execute();

    }


    public void setList(ArrayList<Atleta> listaAtleta) {
        best = new ArrayList<>();
        tipiGara = new ArrayList<>();

        for (int i = 0; i < listaAtleta.size(); i++) {
            Atleta atleta = listaAtleta.get(i);
            atleta.best();
            for (Gara gara : atleta.getListBest()) {
                gara.setAtleta(atleta);
                best.add(gara);
                if (tipiGara.contains(gara.getTipo())) {
                    //do nothing
                } else {
                    tipiGara.add(gara.getTipo());
                }
            }
        }

        Collections.sort(best, new Comparator<Gara>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Gara g0, Gara g1) {
                return Long.compare(g0.getTime(), g1.getTime());
            }
        });


        ArrayList<ModelloConfronto> modelloConfrontos = new ArrayList<>();

        for (String string : tipiGara) {
            System.out.println("-----" + string + "------");
            modelloConfrontos.add(new ModelloConfronto(string));
            for (Gara gara : best) {
                if (gara.getTipo().equals(string)) {
                    modelloConfrontos.add(new ModelloConfronto(gara.getTipo(), gara.getAtleta().getNome(), gara.getTempo() + " - " + gara.getData() + " - " + gara.getVasca()));
                    System.out.println(gara.getAtleta().getNome() + " " + gara.getTempo());
                }
            }
        }


        ListAdapterConfronto listAdapterConfronto = new ListAdapterConfronto(this, modelloConfrontos);
        ListView lv = (ListView) findViewById(R.id.listViewResult);
        lv.setAdapter(listAdapterConfronto);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confronto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_share) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Che disciplina vuoi condividere?");

            String[] array = new String[tipiGara.size()];

            for (int i = 0; i < tipiGara.size(); i++) {
                array[i] = tipiGara.get(i).toString();
            }
            builder.setItems(array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.out.println(tipiGara.get(i));
                    ArrayList<Gara> share = new ArrayList<>();
                    for (Gara gara : best){
                        if (gara.getTipo().equals(tipiGara.get(i))){
                            share.add(gara);
                        }
                    }
                    String message = "";

                    if (share.size() == 1){
                        message = "Lo sapevi che il record personale di " + share.get(0).getAtleta().getNome() + " su " + share.get(0).getTipo() + " è " + share.get(0).getTempo() + ".\nTempo cercato con l'applicazione SwimCheck, vai sul PlayStore per scaricarla";
                    } else {
                        message = "Scopri chi dei tuoi amici è più veloce al " + share.get(0).getTipo() + "\n";
                        for (Gara g : share){
                            message += g.getAtleta().getNome() + " - " + g.getTempo() + "\n";
                        }
                        message += "Tempi confrontati con l'applicazione SwimCheck, vai sul PlayStore per scaricarla";
                    }

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Confronto"));

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            /**/
        }

        return super.onOptionsItemSelected(item);
    }

}
