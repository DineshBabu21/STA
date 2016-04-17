package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dineshbabu on 24-03-2016.
 */
public class Admin extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
    }
    public void busroute(View view)
    {
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
                Intent intent = new Intent(getBaseContext(),AdminRoute.class);
                intent.putExtra("data",result);
                startActivity(intent);
            }

        }
        new Busroute1(this).execute();
    }

    public void studentdatabase(View view)
    {
        class student extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            student(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/studentdb.php";
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
                Intent intent = new Intent(getBaseContext(),Adminstudentdb.class);
                intent.putExtra("data",result);
                startActivity(intent);
            }

        }
        new student(this).execute();

    }
    public void newss(View view)
    {
        class Newss extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            Newss(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/newss.php";
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
                Intent intent = new Intent(getBaseContext(),AdminNews.class);
                intent.putExtra("data",result);
                startActivity(intent);
            }

        }
        new Newss(this).execute();

    }
}
