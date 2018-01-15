package com.claudiocavallaro.swimcheck.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.model.Atleta;
import com.claudiocavallaro.swimcheck.model.Gara;
import com.claudiocavallaro.swimcheck.vista.ListAdapterGara;
import com.claudiocavallaro.swimcheck.vista.ModelloGara;
import com.claudiocavallaro.swimcheck.vista.ModelloRicerca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by claudiocavallaro on 07/01/18.
 */

public class GaraActivity extends AppCompatActivity {

    private ArrayList<ModelloGara> models = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListAdapterGara listAdapterGara;

    private ArrayList<Gara> listaAppoggio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gara);

        String garaS = getIntent().getStringExtra("tipo");
        System.out.println(garaS);

        Atleta a = AtletaActivity.getA();

        System.out.println(a.getNome());

        listaAppoggio = a.cercaGare(garaS);

        System.out.println(listaAppoggio.size());

        this.setTitle(garaS);

        Collections.sort(listaAppoggio, new Comparator<Gara>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Gara g0, Gara g1) {
                return Long.compare(g0.getTime(), g1.getTime());
            }
        });


        Gara gara = new Gara();
        for (int i = 0; i < listaAppoggio.size(); i++){

            gara = listaAppoggio.get(i);
            System.out.println("Time " + gara.getTime() + " tempo " + gara.getTempo());
            if (i == 0 && !(gara.getTempo().equals("Squalificato"))){
                ModelloGara modelloGara = new ModelloGara(gara.getTipo() + "\nRECORD PERSONALE", gara.getCitta() + " - " + gara.getData() + " - " + gara.getVasca(), gara.getTempo());
                if (gara.getFederazione().equals("FIN")){
                    modelloGara.setImage(R.drawable.fin2);
                }
                if (gara.getFederazione().equals("UISP")){
                    modelloGara.setImage(R.drawable.uisp);
                }
                models.add(modelloGara);
            } else {
                ModelloGara modelloGara = new ModelloGara(gara.getTipo(), gara.getCitta() + " - " + gara.getData() + " - " + gara.getVasca(), gara.getTempo());
                if (gara.getFederazione().equals("FIN")){
                    modelloGara.setImage(R.drawable.fin2);
                }
                if (gara.getFederazione().equals("UISP")){
                    modelloGara.setImage(R.drawable.uisp);
                }
                models.add(modelloGara);
            }


        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewG);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listAdapterGara = new ListAdapterGara(this, models);
        listAdapterGara.setPartenza(0);


        recyclerView.setAdapter(listAdapterGara);
    }

/*    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gara, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_grafico){
            Intent i = new Intent(this, GraficoActivity.class);
            i.putExtra("lista", listaAppoggio);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}
