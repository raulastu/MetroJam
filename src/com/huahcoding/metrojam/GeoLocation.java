package com.huahcoding.metrojam;

import com.huahcoding.metrojam.test.RouteTestData;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;

public class GeoLocation implements LocationListener{
	
	private Context context;
	private Delegate delegate;
	
	private LocationManager locationManager;
	private String provider;

	public GeoLocation(Context context, Delegate delegate) {
		this.context=context;
		this.delegate=delegate;
		init();
	}
	
	private void init(){

		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// LocationListener ll = new mylocationlistener();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		

		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			System.out.println("Location not available");
		}
	}
	
	int testArrayIndex=0;
	
	boolean test=true;
	
	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
//			double lat = (location.getLatitude());
//			double lng = (location.getLongitude());
			// double lng = (location.getAccuracy()());
//			System.out.println("lat" + lat);
//			System.out.println("long" + lng);
//			System.out.println("long" + location);
			
			if(test && delegate.getName().equals("Home-Work")){
				Location a = new Location("RC-TEST");
				double [] data = RouteTestData.getWorkPoints();
				Utils.log(testArrayIndex+""); 
				if(testArrayIndex<data.length-1){
					a.setLatitude(data[testArrayIndex++]);
					a.setLongitude(data[testArrayIndex++]);
//					testArrayIndex++;
					delegate.callback(a);
				}
			}else{
				delegate.callback(location);
			}
//			delegate.callback(location);
			
		}
	}

	private static final int MY_NOTIFICATION_ID = 1;
	private NotificationManager notificationManager;
	private Notification myNotification;
	private int idx = 0;


	@Override
	public void onProviderDisabled(String provider) {
		System.out.println("Disabled provider  " + provider
				+ " has been selected.");
	}

	@Override
	public void onProviderEnabled(String provider) {
		System.out.println("Enabled new provider  " + provider
				+ " has been selected.");

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
