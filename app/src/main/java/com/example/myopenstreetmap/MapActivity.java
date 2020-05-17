package com.example.myopenstreetmap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.OverlayManager;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class MapActivity  extends AppCompatActivity {

    private MapView mapView;
    MyLocationListener myLocationListener;
    MainActivity mainActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Button buttonExit = findViewById(R.id.buttonExit);

        mapView = (MapView) findViewById(R.id.mapview);
        //mapView.setUseSafeCanvas(false);
        mapView.setClickable(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(9.5);
        Double lat = myLocationListener.getLat(mainActivity.getLocalisation());
        Double Long =  myLocationListener.getLong(mainActivity.getLocalisation());
        Log.d("lat and long", lat.toString()+"-"+Long.toString());
        GeoPoint startPoint = new GeoPoint(lat,Long);
        mapController.setCenter(startPoint);

        //ResourceProxyImpl resProxyImp = new ResourceProxyImpl(this);

       /* GpsMyLocationProvider imlp = new GpsMyLocationProvider(this.getBaseContext());

        imlp.setLocationUpdateMinDistance(1000);

        imlp.setLocationUpdateMinTime(60000);

        MyLocationNewOverlay mMyLocationOverlay = new MyLocationNewOverlay(this.getBaseContext(), imlp, mapView, resProxyImp);
        //mMyLocationOverlay.setUseSafeCanvas(false);
        mMyLocationOverlay.setDrawAccuracyEnabled(true);

        mapView.getOverlays().add(mMyLocationOverlay);

        ItemizedIconOverlay markersOverlay = new ItemizedIconOverlay<OverlayItem>(new LinkedList<OverlayItem>(), myMarker, null, resProxyImp);
        mapView.getOverlays().add(markersOverlay);

        OverlayItem ovm = new OverlayItem("title", "description", new GeoPoint(s.LatitudeE6(), s.LongitudeE6()));
        ovm.setMarker(myMarker);
        markersOverlay.addItem(ovm);*/


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
