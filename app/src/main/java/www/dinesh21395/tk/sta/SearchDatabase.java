package www.dinesh21395.tk.sta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * Created by dineshbabu on 23-03-2016.
 */
public class SearchDatabase extends Activity {
    EditText et_rollno;
    String bus_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdatabase);
        bus_no = getIntent().getExtras().getString("busno");
        et_rollno = (EditText) findViewById(R.id.editText5);
    }
    public void searchrollno(View view)
    {
        String rollno;
        rollno = et_rollno.getText().toString();
        Log.e("rollno in database",rollno+bus_no);

        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/searchdatabase.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String busno = params[0];
                String rollno = params[1];
                String response = null;
                try {
                    String data = URLEncoder.encode("bus", "UTF-8") + "=" + URLEncoder.encode(busno, "UTF-8")
                            +"&"+URLEncoder.encode("rollno", "UTF-8") + "=" + URLEncoder.encode(rollno, "UTF-8");

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
                intent.putExtra("busno",bus_no);
                startActivity(intent);
            }

        }
        new BackgroundTask(this).execute(bus_no,rollno);

    }
}
