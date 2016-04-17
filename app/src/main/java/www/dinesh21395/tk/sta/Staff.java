package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by dineshbabu on 05-03-2016.
 */
public class Staff extends Activity {

    String coordinator, busno, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff);
        Log.e("STAFF CLASS", "IN IT");
        busno = getIntent().getExtras().getString("busno");
        coordinator = getIntent().getExtras().getString("coordinator");
        if (coordinator.equals("yes")) {
            Toast.makeText(this, "You are a coordinator", Toast.LENGTH_SHORT).show();
        }
    }

    public void database(View view) {
        final String busid = busno;
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/json_get.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String busno = params[0];
                Log.e("in","database background task");
                String response = null;
                try {
                    String data = URLEncoder.encode("bus", "UTF-8") + "=" + URLEncoder.encode(busno, "UTF-8");

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
                Intent intent = new Intent(context, DisplayListview.class);
                intent.putExtra("jsondata", data);
                intent.putExtra("busno",busid);
                startActivity(intent);
            }

        }
        new BackgroundTask(this).execute(busid);

    }

    public void route(View view) {
        Log.e("AT Route", "e");
        class Busroute1 extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            Busroute1(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/route.php";
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

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
                Intent intent = new Intent(getBaseContext(), DisplayRoute.class);
                intent.putExtra("data", result);
                startActivity(intent);
            }

        }
        new Busroute1(this).execute();
    }

    public void alternate(View view) {
        Intent intent = new Intent(this, AlternateRoute.class);
        startActivity(intent);
    }

    public void news(View view)
    {
        Intent intent = new Intent(this,News.class);
        intent.putExtra("busno",busno);
        startActivity(intent);
    }


}
