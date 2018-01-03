package com.claudiocavallaro.swimcheck.com.claudiocavallaro.swimcheck.persistenza;

import android.os.AsyncTask;

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

            Atleta a = new Atleta();
            //------NOME----------------------------
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expressionNome = "(//div[1]/center/table[@class='datatable']/tbody/tr[1]/td[1])[1]";
            XPathExpression exprNome = xpath.compile(expressionNome);
            String nome = exprNome.evaluate(doc);
            a.setNome(nome);

            //---------ANNO----------------------------
            String expressionAnno = "(//div[1]/center/table[@class='datatable']/tbody/tr[1]/td[2])[1]";
            XPathExpression exprAnno = xpath.compile(expressionAnno);
            String anno = exprAnno.evaluate(doc);
            a.setAnno(anno);

            //----------SESSO---------------
            String expressionSesso = "(//div[1]/center/table[@class='datatable']/tbody/tr[1]/td[4])[1]";
            XPathExpression exprSesso = xpath.compile(expressionSesso);
            String sesso = exprSesso.evaluate(doc);
            a.setSesso(sesso);

            //--------SOCIETA--------------------
            String expressionSoc = "(//div[1]/center/table[@class='datatable']/tbody/tr[1]/td[3])[1]";
            XPathExpression exprSoc = xpath.compile(expressionSoc);
            String soc = exprSoc.evaluate(doc);
            a.setSoc(soc);

            System.out.println(a.toString());
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
