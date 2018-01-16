package com.claudiocavallaro.swimcheck.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.model.Gara;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by claudiocavallaro on 15/01/18.
 */

public class GraficoActivity extends AppCompatActivity {

    private ArrayList<Gara> lista;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.grafico);


        Intent intent = this.getIntent();
        lista = (ArrayList<Gara>) intent.getSerializableExtra("lista");

        //Collections.reverse(lista);

        String tipo ="";
        for (Gara gara: lista){
            System.out.println(gara.getTipo());
            tipo = gara.getTipo();
        }

        this.setTitle("Progressione " + tipo);

        LineChart chart = (LineChart) findViewById(R.id.lineChart);

        LineData data = new LineData(getDataSet());

        /*XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });*/


        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(false);


        Description description = new Description();
        description.setText(tipo);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Gara g = (Gara)e.getData();
                Toast.makeText(GraficoActivity.this, g.getTempo() + "\n" + g.getData() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.setDescription(description);
        chart.setData(data);
        //chart.animateXY(2000, 2000);
        //chart.invalidate();

    }

    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets;

        ArrayList<Entry> valueSet1 = new ArrayList<>();



        for (int k = 0 ; k < lista.size() ; k++){
            Gara gara = lista.get(k);
            System.out.println("Numero " + k + " gara " + gara.getTempo());
            Entry v1e1 = new Entry(gara.getTime(),  k, gara);
            valueSet1.add(v1e1);
        }


        LineDataSet barDataSet1 = new LineDataSet(valueSet1, "Tempo");

        barDataSet1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                Gara g = (Gara) entry.getData();
                return g.getTempo();
            }
        });


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }





}
