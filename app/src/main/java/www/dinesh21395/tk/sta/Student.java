package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
public class Student extends Activity {
    String coordinator, busno, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DINESH", "STUDENT ACTIVITY");
        busno = getIntent().getExtras().getString("busno");
        coordinator = getIntent().getExtras().getString("coordinator");
        if (!(coordinator.equals("yes"))) {
            setContentView(R.layout.student);
        } else if (coordinator.equals("yes")) {
            setContentView(R.layout.studentcoordinator);
        }
        Toast.makeText(this, coordinator + "" + busno, Toast.LENGTH_SHORT).show();
    }

    public void status(View view) {
        new Busstatus(this).execute(busno);
    }

    public void inbus(View view) {
        Intent service = new Intent(this, Services.class);
        service.putExtra("busno", busno);
        startService(service);

    }

    public void offbus(View view) {
        Intent service = new Intent(getBaseContext(), Services.class);
        stopService(service);

    }

    public void logout(View view) {
        Intent intent = new Intent(this, Services.class);
        stopService(intent);
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

    public void news(View view) {
        class GetNews extends AsyncTask<String, Void, String> {
            Context context;
            String url1, Jsonresponse;

            GetNews(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                url1 = "http://www.dinesh21395.tk/getnews.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String busno = params[0];
                String response = null;
                try {
                    String data = URLEncoder.encode("busno", "UTF-8") + "=" + URLEncoder.encode(busno, "UTF-8");

                    URL url = new URL(url1);
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
                    Log.e("Response getNews", response);
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                JSONObject jsonObject;
                JSONArray jsonArray;
                String info = null, busno = null, date = null;
                try {
                    jsonObject = new JSONObject(s);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        info = jo.getString("info");
                        busno = jo.getString("busno");
                        date = jo.getString("date");
                        Toast.makeText(context, date + ":" + busno + ":" + info, Toast.LENGTH_LONG).show();
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        new GetNews(this).execute(busno);
    }

    public class Busstatus extends AsyncTask<String, Void, String> {
        Context context;
        String json_url, Jsonresponse, response;

        Busstatus(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://www.dinesh21395.tk/json_status.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String busno = params[0];

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

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            String busno = null, area = null;

            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    busno = jo.getString("busno");
                    area = jo.getString("area");
                    count++;
                }
                Toast.makeText(context, "Your Bus No:" + busno + "is at the Place:" + area, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

