package www.dinesh21395.tk.sta;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

public class InsertStudent extends AppCompatActivity {

    EditText etname, etrollno, etbusno, etdept;
    String name, rollno, dept, busno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student);
        etname = (EditText) findViewById(R.id.editText11);
        etrollno = (EditText) findViewById(R.id.editText12);
        etdept = (EditText) findViewById(R.id.editText13);
        etbusno = (EditText) findViewById(R.id.editText14);
    }

    public void insert(View view) {
        name = etname.getText().toString();
        rollno = etrollno.getText().toString();
        dept = etdept.getText().toString();
        busno = etbusno.getText().toString();
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/insertstudent.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
                String rollno = params[1];
                String dept = params[2];
                String busno = params[3];
                try {
                    String data = URLEncoder.encode("busno", "UTF-8") + "=" + URLEncoder.encode(busno, "UTF-8")
                            + "&" + URLEncoder.encode("rollno", "UTF-8") + "=" + URLEncoder.encode(rollno, "UTF-8")
                            + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                            + "&" + URLEncoder.encode("dept", "UTF-8") + "=" + URLEncoder.encode(dept, "UTF-8");

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
                Log.e("Location", "IN POST EXECUTE");
                TextView response = (TextView) findViewById(R.id.textView20);
                if (result.contains("succesfully")) {
                    response.setText("Inserted Succesfully");
                } else if (result.contains("failure")) {

                    response.setText("Failed to Insert in database");
                }
            }

        }
        new BackgroundTask(this).execute(name,rollno,dept,busno);
    }
}
