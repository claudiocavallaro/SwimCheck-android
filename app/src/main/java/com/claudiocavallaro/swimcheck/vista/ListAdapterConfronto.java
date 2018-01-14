package com.claudiocavallaro.swimcheck.vista;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;

import java.util.ArrayList;

/**
 * Created by claudiocavallaro on 14/01/18.
 */

public class ListAdapterConfronto extends ArrayAdapter<ModelloConfronto> {

    private Context context;
    private ArrayList<ModelloConfronto> modelloConfrontos;

    public ListAdapterConfronto(Context context, ArrayList<ModelloConfronto> modelloConfrontos){
        super(context, R.layout.row, modelloConfrontos);
        this.context = context;
        this.modelloConfrontos = modelloConfrontos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;

        if (!modelloConfrontos.get(position).isGruppo()){
            rowView = inflater.inflate(R.layout.row, parent, false);

            TextView tvNome = (TextView) rowView.findViewById(R.id.textViewNome);
            TextView tvGara = (TextView) rowView.findViewById(R.id.textViewTempo);

            tvNome.setText(modelloConfrontos.get(position).getNome());
            tvGara.setText(modelloConfrontos.get(position).getTempo());
        } else {
            rowView = inflater.inflate(R.layout.group, parent, false);

            TextView title = (TextView) rowView.findViewById(R.id.header);
            title.setText(modelloConfrontos.get(position).getTitle());
        }

        return rowView;
    }
}
