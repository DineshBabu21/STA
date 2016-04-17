package www.dinesh21395.tk.sta;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Update extends AppCompatActivity {

    EditText text_bus_no, text_route;
    String busno, route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        text_bus_no = (EditText) findViewById(R.id.editText8);
        text_route = (EditText) findViewById(R.id.editText9);
    }
    public void update_route(View view)
    {
        busno = text_bus_no.getText().toString();
        route = text_route.getText().toString();
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/update.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String bus = params[0];
                String rout = params[1];
                Log.e("Value", bus + rout);
                try {
                    String data = URLEncoder.encode("busno", "UTF-8") + "=" + URLEncoder.encode(bus , "UTF-8")
                            + "&" + URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode(rout, "UTF-8");

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

                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.e("Location","IN POST EXECUTE");
                TextView response = (TextView) findViewById(R.id.textView13);
                if (result.contains("succesfully"))
                {
                    response.setText("Updated Succesfully");
                    Log.e("sucess", "success");
                }
                else if(result.contains("failure"))
                {
                    Log.e("sucess","success");
                    response.setText("Failed to Update in database");
                }
            }

        }
        new BackgroundTask(this).execute(busno,route);
    }
}
