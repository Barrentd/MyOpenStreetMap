package com.example.myopenstreetmap;

import android.location.*;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyLocationListener implements LocationListener {

    MainActivity main = new MainActivity();

    public void onLocationChanged(Location localisation)
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(localisation.getTime());
        String formatted = format.format(date);
        List<Address> addresses = getUserGeoInfo(localisation.getLatitude(),localisation.getLongitude());
        main.setStatelat(localisation.getLatitude());
        main.setStatelong(localisation.getLongitude());
        main.setStateUpdate(formatted);
        main.setStateCity(addresses.get(0).getLocality());
        main.setStateFournisseur(localisation.getProvider());
        Toast.makeText(main.getContext(), "Localisation mis a jour" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String fournisseur, int status, Bundle extras)
    {
        Toast.makeText(main.getContext(),fournisseur + " état : " + status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String fournisseur)
    {
        Toast.makeText(main.getContext(), fournisseur + " activé !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String fournisseur)
    {
        Toast.makeText(main.getContext(), fournisseur + " désactivé !", Toast.LENGTH_SHORT).show();
    }

    private List getUserGeoInfo(double lat, double lon){
        Geocoder geoCoder = new Geocoder(main.getContext(), Locale.getDefault());
        List<Address> addresses = null;
        if (Geocoder.isPresent()) {
            try {
                addresses = geoCoder.getFromLocation(lat, lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addresses;
    }

    public double getLat(Location localisation){
        return localisation.getLatitude();
    }

    public double getLong(Location localisation) {
        return localisation.getLongitude();
    }
}
