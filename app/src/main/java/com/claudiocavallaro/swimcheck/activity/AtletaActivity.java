package com.claudiocavallaro.swimcheck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.model.Atleta;
import com.claudiocavallaro.swimcheck.model.Gara;
import com.claudiocavallaro.swimcheck.persistenza.RestCall;
import com.claudiocavallaro.swimcheck.persistenza.RestCallAtleta;
import com.claudiocavallaro.swimcheck.vista.ListAdapterGara;
import com.claudiocavallaro.swimcheck.vista.ModelloGara;
import com.claudiocavallaro.swimcheck.vista.ModelloRicerca;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

import static com.claudiocavallaro.swimcheck.R.drawable.fin;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class AtletaActivity extends AppCompatActivity {

    private ArrayList<ModelloGara> models = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListAdapterGara listAdapterGara;

    private static Atleta a;

    public static Atleta getA() {
        return a;
    }

    public static void setA(Atleta a) {
        AtletaActivity.a = a;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atleta);

        ModelloRicerca item = (ModelloRicerca) getIntent().getSerializableExtra("atleta");
        this.setTitle(item.getNome());

        ArrayList<Atleta> listaAtleti = RestCall.getListaAtleti();
        a = null;
        for (int i = 0 ; i < listaAtleti.size(); i++){
            if (item.getNome().equals(listaAtleti.get(i).getNome()) && item.getAnno().equals(listaAtleti.get(i).getAnno()) && item.getSocieta().equals(listaAtleti.get(i).getSoc())){
                a = listaAtleti.get(i);
            }
        }



        System.out.println(a.getUrl());

        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBarAtleta);
        spinner.setVisibility(View.INVISIBLE);

        RestCallAtleta call = new RestCallAtleta(this,a);
        RestCallAtleta.setContext(getApplicationContext());
        call.setUrl(a.getUrl());
        call.execute();
    }



    //----da provare a lungo termine-----

   /* @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_atleta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.menu_fav){
            aggiungi(a);
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void aggiungi(Atleta a) {

    }


    public void setIterface(Atleta atleta, final ArrayList<Gara> listaGare) {
        for (Gara gara : listaGare){
            //ModelloGara modelloGara = new ModelloGara(gara.getTipo(), gara.getCitta() + "\n" + gara.getData() + "\n" + gara.getVasca(), gara.getTempo());
            ModelloGara modelloGara = new ModelloGara(gara.getTipo(), gara.getCitta(), gara.getTempo());
            if (gara.getFederazione().equals("FIN")){
                modelloGara.setImage(R.drawable.fin2);
            }
            if (gara.getFederazione().equals("UISP")){
                modelloGara.setImage(R.drawable.uisp);
            }
            models.add(modelloGara);
        }

        a.setListaGare(listaGare);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listAdapterGara = new ListAdapterGara(this, models);
        listAdapterGara.setPartenza(0);
        listAdapterGara.setClickListener(new ListAdapterGara.ClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                //Toast.makeText(getApplicationContext(), "Ho cliccato " + models.get(position).getGara(), Toast.LENGTH_LONG).show();
                String gara = models.get(position).getGara();

                Intent i = new Intent(AtletaActivity.this, GaraActivity.class);
                i.putExtra("tipo", gara);
                startActivity(i);
            }
        });

        a.best();

        //System.out.println(a.getListBest());

        recyclerView.setAdapter(listAdapterGara);
    }

    //---------------------------------
}
