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

public class Adminstudentdb extends AppCompatActivity {
    String jsondata, busno, name, dept, rollnumber;
    JSONObject jsonObject;
    JSONArray jsonArray;
    StudentdbAdapter studentdbAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminstudentdb_display_listview);
        studentdbAdapter = new StudentdbAdapter(this, R.layout.studentdblayout);
        listView = (ListView) findViewById(R.id.listview4);
        listView.setAdapter(studentdbAdapter);
        jsondata = getIntent().getExtras().getString("data");
        try {
            jsonObject = new JSONObject(jsondata);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                busno = jo.getString("busno");
                name = jo.getString("name");
                dept = jo.getString("dept");
                rollnumber = jo.getString("rollno");
                Studentdb studentdb = new Studentdb(name, rollnumber, dept, busno);
                studentdbAdapter.add(studentdb);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshstudent(View view) {
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
                Intent intent = new Intent(getBaseContext(), Adminstudentdb.class);
                intent.putExtra("data", result);
                startActivity(intent);
            }

        }
        new student(this).execute();


    }

    public void insertstudent(View view)
    {
        Intent intent = new Intent(this,InsertStudent.class);
        startActivity(intent);
    }
    public void updatestudent(View view)
    {
        Intent intent = new Intent(this,UpdateStudent.class);
        startActivity(intent);
    }
    public void deletestudent(View view)
    {
        Intent intent = new Intent(this,DeleteStudent.class);
        startActivity(intent);
    }
}