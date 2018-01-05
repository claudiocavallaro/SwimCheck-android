package com.claudiocavallaro.swimcheck.vista;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.claudiocavallaro.swimcheck.R;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class ListAdapterGara extends RecyclerView.Adapter<ListAdapterGara.ViewHolder>{

    private ArrayList<ModelloGara> models;
    private Context context;
    private LayoutInflater inflater;

    private ClickListener clickListener;
    private LongClickListener longClickListener;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    private int partenza = 0;

    public int getPartenza() {
        return partenza;
    }

    public void setPartenza(int partenza) {
        this.partenza = partenza;
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public LongClickListener getLongClickListener() {
        return longClickListener;
    }

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(SparseBooleanArray selectedItems) {
        this.selectedItems = selectedItems;
    }

    public ListAdapterGara(Context context, ArrayList<ModelloGara> models){
        super();
        this.context = context;
        this.models = models;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelloGara modelloGara = models.get(position);
        holder.gara.setText(modelloGara.getGara());
        holder.desc.setText(modelloGara.getDescrizione());
        holder.tempo.setText(modelloGara.getTempo());
        holder.img.setImageResource(modelloGara.getImage());
        holder.itemView.setSelected(getSelectedItems().get(position, false));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView gara;
        public TextView desc;
        public TextView tempo;
        public ImageView img;

        public ViewHolder(final View itemView){
            super(itemView);
            gara = (TextView)itemView.findViewById(R.id.textGara);
            desc = (TextView)itemView.findViewById(R.id.textDesc);
            tempo = (TextView)itemView.findViewById(R.id.textTempo);
            img = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null){
                        clickListener.itemClicked(view, getAdapterPosition());
                    }
                }
            });

            if (partenza == 1){
                System.out.println("arrivo qui dentro");
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                    /*if (getLongClickListener() != null){
                        getLongClickListener().itemClicked(v, getAdapterPosition());
                    }*/
                        if (getSelectedItems().get(getAdapterPosition(), false)){
                            getSelectedItems().delete(getAdapterPosition());
                            itemView.setSelected(false);
                        } else {
                            getSelectedItems().put(getAdapterPosition(), true);
                            itemView.setSelected(true);
                            System.out.println(getSelectedItems());
                        }
                        return true;  //QUESTO TRUE FA SI CHE AL LUNGO CLICK NON SI INTERPRETI ANCHE IL CLICK CORTO
                    }
                });
            }

        }
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }

    public interface LongClickListener{
        public void itemClicked(View view, int position);
    }
}
