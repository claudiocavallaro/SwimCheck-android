package com.claudiocavallaro.swimcheck.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_result);

        this.setTitle("Confronto atleti");

        ArrayList<Atleta> lista = ConfrontaActivity.getListaAppoggioConfronto();

        System.out.println(lista.size());



        for (Atleta a : lista){
            System.out.println(a.getUrl());
        }

        RestCallResult restCallResult = new RestCallResult(this, lista);
        RestCallResult.setContext(this);
        restCallResult.execute();

    }


    public void setList(ArrayList<Atleta> listaAtleta) {
        ArrayList<Gara> best = new ArrayList<>();
        ArrayList<String> tipiGara = new ArrayList<>();

        for (int i = 0 ; i < listaAtleta.size(); i++){
            Atleta atleta = listaAtleta.get(i);
            atleta.best();
            for (Gara gara : atleta.getListBest()){
                gara.setAtleta(atleta);
                best.add(gara);
                if (tipiGara.contains(gara.getTipo())){
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

        for (String string : tipiGara){
            System.out.println("-----" + string + "------");
            modelloConfrontos.add(new ModelloConfronto(string));
            for (Gara gara : best){
                if (gara.getTipo().equals(string)){
                    modelloConfrontos.add(new ModelloConfronto(gara.getTipo(), gara.getAtleta().getNome(), gara.getTempo() + " - " + gara.getData()));
                    System.out.println( gara.getAtleta().getNome() + " " + gara.getTempo());
                }
            }
        }

        ListAdapterConfronto listAdapterConfronto = new ListAdapterConfronto(this, modelloConfrontos);
        ListView lv = (ListView) findViewById(R.id.listViewResult);
        lv.setAdapter(listAdapterConfronto);

    }
}
