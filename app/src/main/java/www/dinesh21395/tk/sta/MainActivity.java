package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    EditText ET_NAME,ET_PASS;
    String name,password;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET_NAME = (EditText) findViewById(R.id.editText);
        ET_PASS= (EditText) findViewById(R.id.editText2);

    }
    public void userLogin(View view)
    {
        name=ET_NAME.getText().toString();
        password=ET_PASS.getText().toString();
        String method ="login";
        String result=null;

        class BackgroundTask extends AsyncTask<String,Void,String> {
            Context context;
            String json_url,Jsonresponse,response;

            BackgroundTask(Context context)
            {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url="http://www.dinesh21395.tk/json_get_data.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String method = params[0];
                String response = null;
                if (method.equals("login")) {
                    String username = params[1];
                    String password = params[2];
                    try {
                        String data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")
                                +"&"+ URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

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

                    } catch (Exception e) {
                        return new String("Exception: " + e.getMessage());
                    }
                }
                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                JSONObject jsonObject;
                JSONArray jsonArray;
                String desig = null,coordinator = null,busno = null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        desig = jo.getString("desig");
                        coordinator = jo.getString("coordinator");
                        busno = jo.getString("busno");
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                check(desig,coordinator,busno);
                Log.e("Main thread", String.valueOf(Thread.currentThread().getId()));
       }
            String desig,coordinator,busno;

            private void check(String desig, String coordinator, String busno) {
                this.desig=desig;
                this.coordinator=coordinator;
                this.busno=busno;
                if(desig.equals("student")) {
                        Intent intent = new Intent(getBaseContext(), Student.class);
                        intent.putExtra("busno", busno);
                        intent.putExtra("coordinator", coordinator);
                        startActivity(intent);
                    }
                else if(desig.equals("staff"))
                {
                    Intent intent = new Intent(getBaseContext(),Staff.class);
                    intent.putExtra("busno",busno);
                    intent.putExtra("coordinator",coordinator);
                    startActivity(intent);
                }
                else if(desig.equals("admin"))
                {
                    Intent intent = new Intent(getBaseContext(),Admin.class);
                    startActivity(intent);
                }
            }

        }

        new BackgroundTask(this).execute(method, name, password);

    }

    public void logout(View view)
    {
        Intent intent = new Intent(this,Services.class);
        stopService(intent);
    }
}

