package com.abhijeetonline.titi.titi;

import android.location.Location;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected PowerManager.WakeLock mWakeLock;
    Handler mHandler;
    Runnable refresh;

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Toast.makeText(MapsActivity.this,"Refreshing",Toast.LENGTH_SHORT).show();
            MapsActivity.this.mHandler.postDelayed(m_Runnable,20000);
            //ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.mHandler = new Handler();
        m_Runnable.run();

        //make the screen awake
        final PowerManager pm = (PowerManager) getSystemService(getApplicationContext().POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

        setUpMapIfNeeded();
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng home   = new LatLng(12.946698, 77.717486);
        LatLng work   = new LatLng(12.985856, 77.732994);
        mMap.addMarker(new MarkerOptions().position(home).title("Home"));
        mMap.addMarker(new MarkerOptions().position(work).title("Work"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 13));
        //   map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        mHandler.removeCallbacks(m_Runnable);
        super.onDestroy();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

}
