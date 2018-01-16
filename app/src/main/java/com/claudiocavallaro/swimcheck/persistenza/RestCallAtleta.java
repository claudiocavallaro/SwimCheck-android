package com.claudiocavallaro.swimcheck.persistenza;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.activity.AtletaActivity;
import com.claudiocavallaro.swimcheck.model.Atleta;
import com.claudiocavallaro.swimcheck.model.Gara;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class RestCallAtleta extends AsyncTask<Object, Void, Object> {


    private String url = "http://aquatime.it/tempim.php";
    private ArrayList<Gara> listaGare = new ArrayList<>();

    private ProgressDialog progressDialog;
    private ProgressBar spinner;
    private static Context context;

    private Atleta atleta;


    private AtletaActivity mActivity;

    public RestCallAtleta(AtletaActivity mActivity, Atleta atleta) {
        this.mActivity = mActivity;
        this.atleta = atleta;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        RestCallAtleta.context = context;
    }

    public AtletaActivity getmActivity() {
        return mActivity;
    }

    public void setmActivity(AtletaActivity mActivity) {
        this.mActivity = mActivity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlMod) {
        this.url = url + urlMod;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        spinner = (ProgressBar) mActivity.findViewById(R.id.progressBarAtleta);
        spinner.setVisibility(View.VISIBLE);
        Toast.makeText(context, "Ricorda che verranno considerata solo le ultime 20 gare disputate", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            String content = null;
            String cookie = "_ga=GA1.2.1244528332.1514993144; _gid=GA1.2.1050162139.1514993144; " +
                    "HstCfa3275667=1514993512927; HstCla3275667=1515000602654; HstCmu3275667=1514993512927; HstPn3275667=12; " +
                    "HstPt3275667=12; HstCnv3275667=1; HstCns3275667=5; __gads=ID=f60a26d1ca8ea6d9:T=1514993513:S=ALNI_MZmfTol7_LoRa7DnQNRFXA7KzGRxA; " +
                    "__dtsu=2DE7B66B69F74C5AB71DFF2902BF798A; " +
                    "comitato=2; regione=999; _gat=1";

            content = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0").cookie("cookie", cookie).get().toString();

            TagNode tag = new org.htmlcleaner.HtmlCleaner().clean(content);
            Document doc = null;
            try {
                doc = new DomSerializer(new CleanerProperties()).createDOM(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //------SCANSIONE ELENCO GARE----------------------------
            String garaString = " ";
            for (int i = 1; i <= 20; i++) {
                XPath xpath = XPathFactory.newInstance().newXPath();
                String expressionGara = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[1]";
                XPathExpression exprGara = xpath.compile(expressionGara);
                garaString = exprGara.evaluate(doc);
                int start = garaString.indexOf("(") + 1;
                int end = garaString.indexOf(")");
                String garaT = garaString.replaceAll("&deg;","°");
                String gara = garaT.replaceAll("&igrave;", "ì");
                //gara = gara.substring(0, gara.length() - 1);
                if (!(gara.equals(""))) {
                    Gara gara1 = new Gara();
                    gara1.setCitta(gara);

                    String expressionData = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[2]";
                    XPathExpression exprData = xpath.compile(expressionData);
                    String data = exprData.evaluate(doc);
                    gara1.setData(data);

                    String expressionTipo = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[3]";
                    XPathExpression exprTipo = xpath.compile(expressionTipo);
                    String tipo = exprTipo.evaluate(doc);
                    gara1.setTipo(tipo);

                    String expressionTempo = "//div[1]/center[7]/table/tbody/tr["+ i +"]/td[4]/a/text()";
                    XPathExpression exprTempo = xpath.compile(expressionTempo);
                    String tempo = exprTempo.evaluate(doc);


                    if (tempo.equals("")){
                        expressionTempo = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[4]";
                        exprTempo = xpath.compile(expressionTempo);
                        tempo = exprTempo.evaluate(doc);
                        gara1.setTempo(tempo);
                        if (tempo.equals("Squalificato")){
                            gara1.setTime(999999999);
                        }else{
                            gara1.setTime(gara1.toTime(tempo));
                        }
                    } else {
                        if (gara1.getTipo().contains("4x")) {
                            gara1.setTempo("Tempo totale staffetta : " + tempo.substring(tempo.indexOf("gt;") + 3 , tempo.length()));
                            gara1.setTime(gara1.toTime(tempo));
                        } else {
                            String tempoSt = tempo.substring(tempo.indexOf("gt;") + 3 , tempo.length());
                            gara1.setTempo(tempoSt);
                            System.out.println(tempo);
                            gara1.setTime(gara1.toTime(tempoSt));
                        }
                    }




                    String expressionVasca = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[5]";
                    XPathExpression exprVasca = xpath.compile(expressionVasca);
                    String vasca = exprVasca.evaluate(doc);
                    gara1.setVasca(vasca);

                    String expressionFederazione = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[7]";
                    XPathExpression exprFederazione = xpath.compile(expressionFederazione);
                    String federazione = exprFederazione.evaluate(doc);
                    gara1.setFederazione(federazione);

                    String expressionCategoria = "//div[1]/center[7]/table/tbody/tr[" + i + "]/td[8]";
                    XPathExpression exprCategoria = xpath.compile(expressionCategoria);
                    String categoria = exprCategoria.evaluate(doc);
                    gara1.setCategoria(categoria);


                    /*String expressionLink = "//div[1]/center[5]/table/tbody/tr["+ i +"]/td[1]/a/@href";
                    XPathExpression exprLink = xpath.compile(expressionLink);
                    String link = exprLink.evaluate(doc);
                    System.out.println("link " + link);*/

                    //System.out.println(gara1.toString());
                    listaGare.add(gara1);
                }
            }

            //--------------------------------------------------------------------------------------



            atleta.setListaGare(listaGare);




        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        spinner.setVisibility(View.INVISIBLE);
        mActivity.setIterface(atleta, listaGare);
    }
}
