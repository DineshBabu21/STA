package www.dinesh21395.tk.sta;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UpdateStudent extends AppCompatActivity {

    EditText etrollno, etbusno;
    String name, rollno, dept, busno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        etrollno = (EditText) findViewById(R.id.editText15);
        etbusno = (EditText) findViewById(R.id.editText16);
    }
    public void update(View view)
    {
        rollno = etrollno.getText().toString();
        busno = etbusno.getText().toString();
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/updatestudent.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String rollno = params[0];
                String busno = params[1];
                Log.e("value",rollno+busno);
                try {
                    String data = URLEncoder.encode("busno", "UTF-8") + "=" + URLEncoder.encode(busno, "UTF-8")
                            + "&" + URLEncoder.encode("rollno", "UTF-8") + "=" + URLEncoder.encode(rollno, "UTF-8");

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
                TextView response = (TextView) findViewById(R.id.textView23);
                if (result.contains("succesfully")) {
                    response.setText("Updated Succesfully");
                } else if (result.contains("failure")) {

                    response.setText("Failed to Update in database");
                }
            }

        }
        new BackgroundTask(this).execute(rollno,busno);
    }
}
