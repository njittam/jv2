package com.rsdt.jotiv2;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.rsdt.jotial.JotiApp;
import com.rsdt.jotial.communication.area348.util.SyncUtil;


public class LocationService extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private long last_location_send = 0l;
    private boolean pref_send_loc_old = false;
    private boolean recieving_locations = false;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    /**
     *
     */
    @Override
    public void onCreate() {
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        int interval = 1;
        if (interval < 1) {
            interval = 1;
        }
        if (interval > 5) {
            interval = 5;
        }

        createLocationRequest(interval * 60 * 1000, 60000);
    }

    /**
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    /**
     * @param interval
     * @param fastest
     */
    public void createLocationRequest(int interval, int fastest) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(fastest);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     *
     */
    protected void startLocationUpdates() {
        startLocationUpdates(this.mLocationRequest);
    }

    /**
     * @param mLocationRequest
     */
    protected void startLocationUpdates(LocationRequest mLocationRequest) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    /**
     * @param location
     */
    protected void TryToSendLocation(Location location) {
        long time = System.currentTimeMillis();
        if (time - last_location_send > 60 * 1000) {
            last_location_send = time;
            sendlocation(location);
        }
    }

    /**
     * @param location
     */
    public void sendlocation(Location location) {
        SyncUtil.sendHunterLocation(location);
    }

    /**
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        JotiApp.setLastLocation(location);
        TryToSendLocation(location);
    }

    /**
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates(mLocationRequest);
    }


    /**
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    /**
     *
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     *
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        this.recieving_locations = false;
    }
}
