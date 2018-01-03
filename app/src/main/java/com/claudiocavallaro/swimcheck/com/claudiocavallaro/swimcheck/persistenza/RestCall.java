package com.claudiocavallaro.swimcheck.com.claudiocavallaro.swimcheck.persistenza;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.claudiocavallaro.swimcheck.R;
import com.claudiocavallaro.swimcheck.activity.MainActivity;
import com.claudiocavallaro.swimcheck.model.Atleta;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Created by claudiocavallaro on 03/01/18.
 */

public class RestCall extends AsyncTask<Object, Void, Object> {

    private String url = "http://aquatime.it/tempim.php?AtletaSRC=";

    private static int limite = 5;

    public static int getLimite() {
        return limite;
    }

    public static void setLimite(int limite) {
        RestCall.limite = limite;
    }

    private ProgressDialog progressDialog;
    private ProgressBar spinner;
    private static Context context;

    private MainActivity mActivity;

    public RestCall(MainActivity mActivity){
        this.mActivity = mActivity;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        RestCall.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        spinner = (ProgressBar) mActivity.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        Toast.makeText(context, "Se non trovi quello che cerchi vai nelle impostazioni ed aumenta il limite di risultati", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlMod) {
        this.url = url + urlMod;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        System.out.println("Eseguo restcall");
        try {
            /*HttpClient request = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            HttpResponse response = request.execute(get);
            int responseCode = response.getStatusLine().getStatusCode();*/
            String content = null;
            /*if (responseCode == 200) {
                InputStream istream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
                String s = null;
                StringBuffer sb = new StringBuffer();
                while ((s = reader.readLine()) != null) {
                    sb.append(s + "\n");
                }*/


            //content = sb.toString();
            //System.out.println(content);
            String cookie = "_ga=GA1.2.1244528332.1514993144; _gid=GA1.2.1050162139.1514993144; " +
                    "HstCfa3275667=1514993512927; HstCla3275667=1515000602654; HstCmu3275667=1514993512927; HstPn3275667=12; " +
                    "HstPt3275667=12; HstCnv3275667=1; HstCns3275667=5; __gads=ID=f60a26d1ca8ea6d9:T=1514993513:S=ALNI_MZmfTol7_LoRa7DnQNRFXA7KzGRxA; " +
                    "__dtsu=2DE7B66B69F74C5AB71DFF2902BF798A; " +
                    "comitato=2; regione=999; _gat=1";

            content = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0").cookie("cookie", cookie).get().toString();
            //System.out.println(content);
            TagNode tag = new org.htmlcleaner.HtmlCleaner().clean(content);
            Document doc = null;
            try {
                doc = new DomSerializer(new CleanerProperties()).createDOM(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String nome = " ";
            for(int i = 1; i <= limite; i++){
                //------NOME----------------------------
                XPath xpath = XPathFactory.newInstance().newXPath();
                String expressionNome = "(//div[1]/center[4]/table[@class='datatable']/tbody/tr["+ i +"]/td[1])[1]";
                XPathExpression exprNome = xpath.compile(expressionNome);
                nome = exprNome.evaluate(doc);
                if (!(nome.equals(""))){
                    Atleta a = new Atleta();
                    a.setNome(nome);

                    //---------ANNO----------------------------
                    String expressionAnno = "(//div[1]/center[4]/table[@class='datatable']/tbody/tr["+ i +"]/td[2])[1]";
                    XPathExpression exprAnno = xpath.compile(expressionAnno);
                    String anno = exprAnno.evaluate(doc);
                    a.setAnno(anno);

                    //----------SESSO---------------
                    String expressionSesso = "(//div[1]/center[4]/table[@class='datatable']/tbody/tr["+ i +"]/td[4])[1]";
                    XPathExpression exprSesso = xpath.compile(expressionSesso);
                    String sesso = exprSesso.evaluate(doc);
                    a.setSesso(sesso);

                    //--------SOCIETA--------------------
                    String expressionSoc = "(//div[1]/center[4]/table[@class='datatable']/tbody/tr["+ i +"]/td[3])[1]";
                    XPathExpression exprSoc = xpath.compile(expressionSoc);
                    String soc = exprSoc.evaluate(doc);
                    a.setSoc(soc);

                    System.out.println(a.toString());


                    String expressionLink = "(//div[1]/center[4]/table[@class='datatable']/tbody/tr["+ i +"]/td[1]/a/@href)[1]";
                    XPathExpression exprLink = xpath.compile(expressionLink);
                    String link = exprLink.evaluate(doc);
                    a.setUrl(link);
                    //System.out.println(link);
                }

            //}

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("fine");
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        spinner.setVisibility(View.INVISIBLE);
    }
}
