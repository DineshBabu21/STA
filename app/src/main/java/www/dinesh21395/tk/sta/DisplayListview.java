package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListview extends Activity {
    String json,busno;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("in", "DisplayListview");
        setContentView(R.layout.activity_display_listview);
        contactAdapter=new ContactAdapter(this,R.layout.rowlayout);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(contactAdapter);
        json =getIntent().getExtras().getString("jsondata");
        busno = getIntent().getExtras().getString("busno");
        Log.e("JSONDATA",json);
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name,dept,roll;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                name = jo.getString("name");
                roll=jo.getString("rollno");
                dept =jo.getString("dept");
                Contacts contacts = new Contacts(name,roll,dept);
                contactAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void searchdatabase(View view)
    {
        Intent intent = new Intent(this,SearchDatabase.class);
        intent.putExtra("busno",busno);
        startActivity(intent);
    }
}
