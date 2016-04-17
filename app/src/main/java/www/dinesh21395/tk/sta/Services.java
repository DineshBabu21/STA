package www.dinesh21395.tk.sta;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

/**
 * Created by dineshbabu on 01-03-2016.
 */
public class Services extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener {
    private static final int REQUEST_RESOLVE_ERROR=0;
    private static final int REQUEST_CODE_LOCATION = 2;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Location mLastLocation;
    String lat, lon,busno,result;
    private boolean mResolvingError;

    class Myclass extends AsyncTask<String, Void, String>
    {
        String busno,area,data,dataurl,response,sqlresponses;
        Context context;
        Myclass(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dataurl = "http://www.dinesh21395.tk/busloc.php";
        }

        @Override
        protected String doInBackground(String... params) {
            busno=params[0];
            area = params[1];
            String sqlresponse;
            try {
                URL url = new URL(dataurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTf-8"));
                data = URLEncoder.encode("busno", "UTF-8")+"="+URLEncoder.encode(busno, "UTF-8")
                        +"&"+ URLEncoder.encode("area", "UTF-8")+"="+URLEncoder.encode(area, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((sqlresponse = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(sqlresponse+"\n");
                }
                response = stringBuilder.toString().trim();


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    @Override
    public void onCreate() {
        Log.e("IN SERVICE", "SERVICE");
        buildGoogleApiClient();
    }
    @Override
    public void onDestroy() {
        Log.e("Location Update", "Cancelled");
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(100); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getBaseContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display UI and wait for user interaction
            } else {
                ActivityCompat.requestPermissions((Activity) getBaseContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
        }


    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }

            } else {
                // Permission was denied or request was cancelled
            }
        }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            Address address = addresses.get(0);
            // sending back first address line and locality
            result =address.getAddressLine(1)+","+address.getAddressLine(2)+","+ address.getLocality();
            //Toast.makeText(this,"the bus"+busno+" is at"+result,Toast.LENGTH_SHORT).show();
            Log.e("CHANGED LOCATION", "the bus" + busno + " is at" + result);
            new Myclass(this).execute(busno,result);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
            try {
                mResolvingError = true;
                connectionResult.startResolutionForResult((Activity) getBaseContext(), REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else
        {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            Toast.makeText(getBaseContext(),connectionResult.getErrorCode(),Toast.LENGTH_SHORT).show();
            mResolvingError = true;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(busno == null)
        {
            busno = intent.getStringExtra("busno");
            mGoogleApiClient.connect();
        }
        else
        {
            mGoogleApiClient.connect();
        }
            return START_STICKY;
    }

    synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }

}