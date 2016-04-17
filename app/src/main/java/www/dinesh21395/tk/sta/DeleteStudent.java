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

public class DeleteStudent extends AppCompatActivity {

    EditText etrollno;
    String rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);
        etrollno = (EditText) findViewById(R.id.editText17);
    }
    public void deleteS(View view)
    {
        rollno = etrollno.getText().toString();
        Log.e("Bus no", rollno);
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/deletestudent.php";
            }

            @Override
            protected String doInBackground(String... params) {
                String rollno = params[0];
                Log.e("Value", rollno);
                try {
                    String data = URLEncoder.encode("rollno", "UTF-8") + "=" + URLEncoder.encode(rollno, "UTF-8");

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
                Log.e("Location","IN POST EXECUTE");
                TextView response = (TextView) findViewById(R.id.textView25);
                if (result.contains("succesfully"))
                {
                    response.setText("Deleted Succesfully From database");
                    Log.e("sucess", "success");
                }
                else if(result.contains("failure"))
                {
                    Log.e("Failure","Failed");
                    response.setText("Failed to Delete from database");
                }
            }

        }
        new BackgroundTask(this).execute(rollno);
    }
    public void deleteall(View view)
    {
        class BackgroundTask extends AsyncTask<String, Void, String> {
            Context context;
            String json_url, Jsonresponse, response;

            BackgroundTask(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                json_url = "http://www.dinesh21395.tk/deleteall.php";
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setDoOutput(true);

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
                Log.e("Location","IN POST EXECUTE");
                TextView response = (TextView) findViewById(R.id.textView25);
                if (result.contains("succesfully"))
                {
                    response.setText("Deleted Succesfully From database");
                    Log.e("sucess", "success");
                }
                else if(result.contains("failure"))
                {
                    Log.e("Failure","Failed");
                    response.setText("Failed to Delete from database");
                }
            }

        }
        new BackgroundTask(this).execute();
    }
}
