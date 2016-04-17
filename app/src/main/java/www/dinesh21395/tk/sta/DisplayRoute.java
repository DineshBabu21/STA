package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayRoute extends Activity {
    JSONArray jsonArray;
    JSONObject jsonObject;
    String jsondata;
    RouteAdapter routeAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("at Display Route","Display");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_route);
        routeAdapter = new RouteAdapter(this,R.layout.route_layout);
        listView = (ListView) findViewById(R.id.listview2);
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
}
