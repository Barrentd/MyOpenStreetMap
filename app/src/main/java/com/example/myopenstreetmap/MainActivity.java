package com.example.myopenstreetmap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.StringValue;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static TextView statelat;
    private static TextView statelong;
    private static TextView stateUpdate;
    private static TextView stateFournisseur;
    private static TextView stateCity;

    private Button buttonStart;
    private Button buttonStop;
    private Button buttonNewActivity;

    private static Context mContext;

    private static LocationManager locationManager;
    private String fournisseur;
    private List<String> providers;
    private static Location localisation;

    private static final int REQUEST_LOCATION = 2;
    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 11;

    MyLocationListener myLocationListener;
    Message mes = Message.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        ConnectFirebase connect = new ConnectFirebase();
        Log.d("wesh","wesh");
        connect.initializeFirebase();
        Log.d("test", connect.getInformation());

        Log.d("id", String.valueOf(mes.getId()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        statelat = (TextView) findViewById(R.id.textView7);
        statelong = (TextView) findViewById(R.id.textView5);
        stateUpdate = (TextView) findViewById(R.id.textView6);
        stateCity = (TextView) findViewById(R.id.textView9);
        stateFournisseur = (TextView) findViewById(R.id.textView11);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonNewActivity = findViewById(R.id.buttonMap);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialiserLocalisation();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arreterLocalisation();
            }
        });

        buttonNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), SearchActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void initialiserLocalisation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.d("network_enabled_error:", ex.toString());
        }

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.d("GPS_enabled_error:", ex.toString());
        }

        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(this)
                    .setTitle("GPS settings")
                    .setMessage("GPS is not enabled.\nDo you want to go to settings menu ?")
                    .setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            if (locationManager != null) {
                //Log.d("Location before",locationManager.toString());

                providers = locationManager.getAllProviders();

                //Log.d("Location after",locationManager.toString());

                Log.d("provider", providers.toString());

                Criteria criteres = new Criteria();

                // la précision  : (ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision)
                criteres.setAccuracy(Criteria.ACCURACY_FINE);

                // l'altitude
                criteres.setAltitudeRequired(true);

                // la direction
                criteres.setBearingRequired(true);

                // la vitesse
                criteres.setSpeedRequired(true);

                // la consommation d'énergie demandée
                criteres.setCostAllowed(true);
                criteres.setPowerRequirement(Criteria.POWER_HIGH);

                try {
                    //fournisseur = locationManager.getBestProvider(criteres, true);
                    fournisseur = providers.get(2);
                } catch (Exception e) {
                    Log.d("erreur fournisseur", e.toString());
                }
                Log.d("fournisseur", fournisseur);
            } else {
                Toast.makeText(mContext, "Location manager is null", Toast.LENGTH_SHORT);
            }

            if (fournisseur != null) {
                Log.d("Enter fournisseur", "oui");

                localisation = null;
                myLocationListener = new MyLocationListener();
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                }
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION }, MY_PERMISSION_ACCESS_COURSE_LOCATION);
                }
                try {
                    localisation = locationManager.getLastKnownLocation(fournisseur);
                }catch (Exception e){
                    Log.d("erreur localisation", e.toString());
                }

                if (localisation != null) {
                    Log.d("Localisation not null", localisation.toString());
                    myLocationListener.onLocationChanged(localisation);
                    locationManager.requestLocationUpdates(fournisseur, 1, 1, myLocationListener); // on configure la mise à jour automatique : au moins 10 mètres et 15 secondes
                }
                Toast.makeText(getApplicationContext(), "Localisation initialiser", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Pas de fournisseur", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void arreterLocalisation(){
        if(locationManager != null)
        {
            try {
                locationManager.removeUpdates(myLocationListener);
                Toast.makeText(getApplicationContext(), "Localisation arreter", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Log.d("erreur",e.toString());
                Toast.makeText(getApplicationContext(), "Service deja arreter", Toast.LENGTH_SHORT).show();
            }
            myLocationListener = null;
        }
        else {
            Toast.makeText(getApplicationContext(), "Pas de fournisseur", Toast.LENGTH_SHORT).show();
        }
    }

    public static Context getContext(){
        return mContext;
    }

    public void setStatelat(double value){
        statelat.setText(String.valueOf(value));
    }

    public void setStatelong(double value) {
        statelong.setText(String.valueOf(value));
    }

    public void setStateUpdate(String value){
        stateUpdate.setText(value);
    }

    public void setStateFournisseur(String value){
        stateFournisseur.setText(value);
    }

    public void setStateCity(String value){
        stateCity.setText(value);
    }

    public Location getLocalisation(){
        return localisation;
    }
}
