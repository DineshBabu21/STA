package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Created by dineshbabu on 24-03-2016.
 */
public class AdminRoute extends Activity {
    JSONArray jsonArray;
    JSONObject jsonObject;
    String jsondata;
    RouteAdapter routeAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("At","Admin Route");
        Log.e("at Display Route", "Admin Display");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_display_route);
        routeAdapter = new RouteAdapter(this,R.layout.route_layout);
        listView = (ListView) findViewById(R.id.listview3);
        listView.setAdapter(routeAdapter);
        jsondata=getIntent().getExtras().getString("data");
        String route = null,busno = null;
        try {
            jsonObject = new JSONObject(jsondata);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                busno = jo.getString("busno");
                route=jo.getString("route");
                BusRoute busRoute = new BusRoute(busno,route);
                routeAdapter.add(busRoute);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void insertroute(View view)
    {
        Intent intent = new Intent(this,Insert.class);
        startActivity(intent);
    }
    public void update(View view)
    {
        Intent intent = new Intent(this,Update.class);
        startActivity(intent);
    }
    public void refresh(View view)
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
    public void deleteroute(View view)
    {
        Intent intent = new Intent(this,Delete.class);
        startActivity(intent);

    }
}
