package com.example.erdo.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.button);
         textView=(TextView)findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("sunucu cevabı");
                DownloadData downloadData=new DownloadData();

                try {
                    //String url="https://api.fixer.io/latest";
                    String url ="https://api.coinmarketcap.com/v1/ticker/?convert=EUR&limit=2";
                    downloadData.execute(url);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


    }
//çok fazla ram gerektiren downloadı beklemeden arka planda indirmeyi yapacak
    private class DownloadData extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
          //sonucu alıp işleme olucak
            //System.out.print("My result is "+s);
           // Log.d("Gelen Bilgi",s);
            //textView.setText(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                /*String base =jsonObject.getString("base");
                Log.d("Gelen bilgi",base);
                String date=jsonObject.getString("date");
                //String TRY=jsonObject.getString("TRY");
                String rates =jsonObject.getString("rates");
                System.out.println("bilgiler :"+base);
                JSONObject jsonObject1=new JSONObject(rates);
                String TRY=jsonObject1.getString("TRY");

                textView.setText(base+"\n"+date+"\n"+TRY);
                */
                String name =jsonObject.getString("name");
                textView.setText("BTC ismi :"+name);




            }catch (Exception e){
                e.printStackTrace();
            }



            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {//params birden fazla string alabilir tam string değil arrayde değil.
            String result="";
            String satir;
            URL url;
            //urlyi kullanmak için httpconnexti olusturcaz
            HttpURLConnection httpURLConnection;
            BufferedReader br=null;
            try{
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                //url datasını okumak için input stream olustururuz.
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                //int data=inputStreamReader.read(); //okunan data sayısı data -1 olmadığı sürece okuma olucak
                br=new BufferedReader(inputStreamReader);

               /* while(data>0){
                    //harfleri tek tek okuyor inputstreamreader
                    char character=(char)data;
                    result+=character;
                    data=inputStream.read();

                }*/
               while ((satir=br.readLine())!=null){
                   Log.d("satir",satir);
                   result+=satir;

               }

                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;//hata varsa null döncek
            }


        }
    }

}
