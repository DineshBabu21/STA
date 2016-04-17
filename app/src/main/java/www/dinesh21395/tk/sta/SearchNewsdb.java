package www.dinesh21395.tk.sta;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SearchNewsdb extends AppCompatActivity {
    String busno;
    EditText et_busno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_newsdb);
        et_busno = (EditText) findViewById(R.id.editText18);
    }
    public void searchnewsdb(View view)
    {
        busno = et_busno.getText().toString();
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/searchnewsdb.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String busno = params[0];
                Log.e("in", "database background task");
                String response = null;
                try {
                    String data = URLEncoder.encode("busno", "UTF-8") + "=" + URLEncoder.encode(busno, "UTF-8");

                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());

                    wr.write(data);
                    wr.flush();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((Jsonresponse = bufferedReader.readLine()) != null) {
                        stringBuilder.append(Jsonresponse + "\n");
                    }
                    response = stringBuilder.toString().trim();
                    Log.e("DATABASE",response);
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                String data = result;
                Intent intent = new Intent(context, AdminNews.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }

        }
        new BackgroundTask(this).execute(busno);

    }
}
