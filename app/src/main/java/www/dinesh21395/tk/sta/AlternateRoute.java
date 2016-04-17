package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * Created by dineshbabu on 14-03-2016.
 */
public class AlternateRoute extends Activity {
    EditText editText1;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternateroute);
        editText1 = (EditText) findViewById(R.id.editText3);
    }
    public void search(View view)
    {
        name = editText1.getText().toString();
        class BackgroundTask extends AsyncTask<String,Void,String>
        {
            Context context;
            String json_url,area,Jsonresponse,response,data;
            BackgroundTask(Context context)
            {
                this.context = context;
            }
            @Override
            protected void onPreExecute()
            {
                json_url = "http://www.dinesh21395.tk/alternateroute.php";
            }

            @Override
            protected String doInBackground(String... params) {
                area = params[0];
                try {
                    String data = URLEncoder.encode("area", "UTF-8")+"="+URLEncoder.encode(area, "UTF-8");

                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());

                    wr.write(data);
                    wr.flush();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    while((Jsonresponse = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(Jsonresponse+"\n");
                    }
                    response = stringBuilder.toString().trim();
                    Log.e("JSONdata",response);
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                return response;
            }
            @Override
            protected void onPostExecute(String s) {
                Intent intent = new Intent(getBaseContext(),DisplayRoute.class);
                intent.putExtra("data",s);
                startActivity(intent);
            }
        }
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(name);

    }

}
