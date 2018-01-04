package com.claudiocavallaro.swimcheck.vista;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.claudiocavallaro.swimcheck.R;

import java.util.ArrayList;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class ListAdapterRicerca extends ArrayAdapter<ModelloRicerca> {

    private Context context;
    private ArrayList<ModelloRicerca> models;

    public ListAdapterRicerca(Context context, ArrayList<ModelloRicerca> models){
        super(context, R.layout.riga_ricerca, models);
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.riga_ricerca, parent, false);

        TextView nome = (TextView) row.findViewById(R.id.textNomeRiga);
        nome.setText(models.get(position).getNome() + " - " + models.get(position).getAnno());

        TextView soc = (TextView) row.findViewById(R.id.textSocRiga);
        soc.setText(models.get(position).getSocieta());

        return row;
    }
}
