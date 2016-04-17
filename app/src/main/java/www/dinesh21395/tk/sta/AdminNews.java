package www.dinesh21395.tk.sta;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminNews extends AppCompatActivity {

    String jsonstring,busno,date,info;
    JSONObject jsonObject;
    JSONArray jsonArray;
    NewsdbAdapter newsdbAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news);
        newsdbAdapter = new NewsdbAdapter(this,R.layout.news_layout);
        listView= (ListView) findViewById(R.id.listview5);
        listView.setAdapter(newsdbAdapter);
        jsonstring = getIntent().getExtras().getString("data");
        try {
            jsonObject = new JSONObject(jsonstring);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                busno = jo.getString("busno");
                date = jo.getString("date");
                info = jo.getString("info");
                Newsdb newsdb = new Newsdb(busno,date,info);
                newsdbAdapter.add(newsdb);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void refreshnews(View view)
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
    public void searchnews(View view)
    {
        Intent intent = new Intent(this,SearchNewsdb.class);
        startActivity(intent);
    }
    public void deletenews(View view)
    {
        class Newss extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            Newss(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/deletenewsdb.php";
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
