package com.smove.smartmove;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;
import com.google.android.gms.location.FusedLocationProviderClient;
/**
 * Created by clem on 02/02/18.
 */

public class MainActivity extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    // Use this to call global-like variables set up in GlobalApplication
    private GlobalApplication mApp;

    private Boolean permissionGranted = false;
    private Boolean bleItemClicked = false;

    private View mLayout;

    // Used for Location Permission (required top enable bluetooth-related features)
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This variable is global and static-like
        // (i.e. all data inside is never reinitialized and accessible from everywhere)
        mApp = ((GlobalApplication) getApplicationContext());

        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.main_layout);

        // Used to request a location information
        mFusedLocationClient = new FusedLocationProviderClient(this);

        // Assume thisActivity is the current activity
        final int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        // Request Location Permission if application don't already have it
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestLocationPermission();
        } else {
            permissionGranted = true;
        }


        /*if(!localfound){
            liste deroulante
        }
        else{
            connect_local
        } */

        Button ble_init = (Button) findViewById(R.id.ble_init);
        ble_init.setOnClickListener(new Button.OnClickListener() { //Detect touch on button
            @Override
            public void onClick(View v) {
                bleItemClicked = true;
                if (permissionGranted) {
                    Intent playI = new Intent(MainActivity.this, com.smove.smartmove.ble.DeviceScanActivity.class); //Start next activity
                    startActivity(playI);
                } else {
                    requestLocationPermission();
                }
            }
        });
    }


    // Make a basic Android Alert to request for Location Permission
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
    }

    // Callback
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted
                permissionGranted = true;
                Toast.makeText(MainActivity.this, "Location permission has been granted",
                        Toast.LENGTH_SHORT).show();
                if(bleItemClicked){
                    Intent playI = new Intent(MainActivity.this, com.smove.smartmove.ble.DeviceScanActivity.class); //Start next activity
                    startActivity(playI);
                }
            } else {
                // Permission request was denied.
                permissionGranted = false;
                Toast.makeText(MainActivity.this, "Location permission is required to enable bluetooth features",
                        Toast.LENGTH_SHORT).show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)

    }
}
