package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dineshbabu on 16-03-2016.
 */
public class News extends Activity {
    EditText editText;
    String info,busno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        busno = getIntent().getStringExtra("busno");
        editText = (EditText) findViewById(R.id.editText4);
    }
    public void newspost(View view) {
        info = editText.getText().toString();
        class BackNews extends AsyncTask<String, Void, String> {
            Context context;
            String busno,info,url1,response;
            BackNews(Context context)
            {
                this.context=context;
            }
            @Override
            protected void onPreExecute() {
               url1 ="http://www.dinesh21395.tk/postnews.php";
            }

            @Override
            protected String doInBackground(String... params) {
                busno = params[0];
                info = params[1];
                try {
                    String data = URLEncoder.encode("busno", "UTF-8")+"="+URLEncoder.encode(busno, "UTF-8")
                            +"&"+ URLEncoder.encode("info", "UTF-8")+"="+URLEncoder.encode(info, "UTF-8");

                    URL url = new URL(url1);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());

                    wr.write(data);
                    wr.flush();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    while((response = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(response+"\n");
                    }
                    response = stringBuilder.toString().trim();
                    Log.e("JSONdata", response);
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
            }
        }
        new BackNews(this).execute(busno,info);
    }
}
