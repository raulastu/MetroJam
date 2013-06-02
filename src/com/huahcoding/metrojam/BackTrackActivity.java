/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huahcoding.metrojam;

import com.example.metrojam.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.huahcoding.metrojam.test.RouteTestData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 * <p>
 * Notice how we deal with the possibility that the Google Play services APK is not
 * installed/enabled/updated on a user's device.
 */
public class BackTrackActivity extends FragmentActivity implements OnMapLongClickListener,SensorListener{
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    static final LatLng limaLatLng= new LatLng(-12.085989,-77.034416);
    static final LatLng chileLatLng= new LatLng(-33.39189277577807, -70.5027437210083);
//    BlueArrow rose;
    SensorManager sensorManager;
    static final int sensor = SensorManager.SENSOR_ORIENTATION;
    ImageView imageView;
    
    
    static final CameraPosition LIMA =
            new CameraPosition.Builder().target(limaLatLng)
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();

//    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        savedInstanceState.putParce(key, value)
        setContentView(R.layout.create_route_map);
        setUpMapIfNeeded();
//        startSensor();
        imageView = (ImageView)findViewById(R.id.imageView1);
//        rose = new BlueArrow(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
 
    }
//    protected void startSensor(){
//    	// First, get an instance of the SensorManager
//        SensorManager sMan = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//
//        // Second, get the sensor you're interested in
//        Sensor magnetField = sMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//
//        // Third, implement a SensorEventListener class
//        SensorEventListener magnetListener = new SensorEventListener(){
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//                // do things if you're interested in accuracy changes
//            }
//            
//            public void onSensorChanged(SensorEvent event) {
//            	if (event.sensor. != Compass.){
//            		return;
//            	}
//                int orientation = (int) event.values[0];
//            	rose.setDirection(orientation);
//            	System.err.println(event.accuracy);
//            }
//        };
//        
        // Finally, register your listener
//        sMan.registerListener(magnetListener, magnetField, SensorManager.SENSOR_DELAY_NORMAL);
//
//    }
    
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        sensorManager.registerListener(this, sensor);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
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
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	 mMap.getUiSettings().setZoomControlsEnabled(false);

         // Show Lima
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chileLatLng, 15));
         
         mMap.setOnMapLongClickListener(this);
         mMap.setMyLocationEnabled(true);
         mMap.setTrafficEnabled(true);
         
      // Instantiates a new Polyline object and adds points to define a rectangle
         PolylineOptions rectOptions = new PolylineOptions();
         double[] route = RouteTestData.getChilePoints3();
         for (int i = 0; i < route.length-1; i+=2) {
        	 LatLng ll = new LatLng(route[i],route[i+1]);
        	 if(i>1){
        		 double lat=(route[i]+route[i-2])/2.0;
        		 double lng=(route[i+1]+route[i+1-2])/2.0;
        		 double dist = distance(route[i],route[i+1],route[i-2],route[i+1-2]);
        		 String pretty = getPrettyDistance(dist);
        		 
        		 LatLng ll2 = new LatLng(lat,lng);
        		 String strText= pretty ;
        		 Rect boundsText = new Rect();
        		 Paint textPaint = new Paint();
        		 textPaint .setTextSize(18);
        		 textPaint.getTextBounds(strText, 0, strText.length(),
        		     boundsText);
        		 
        		 textPaint.setColor(Color.RED);
        		 
        		 Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        		 Bitmap bmpText = Bitmap.createBitmap(boundsText.width()*3,
        		     boundsText.height(), conf);
        		 
        		 Canvas canvasText = new Canvas(bmpText);
        		 canvasText.drawText(strText, canvasText.getWidth() / 2,
        		             canvasText.getHeight(), textPaint);
        		 
        		 MarkerOptions markerOptions = new MarkerOptions()
        		    .position(ll2)
        		    .icon(BitmapDescriptorFactory.fromBitmap(bmpText))
        		    .anchor(0.5f, 1);
            	 mMap.addMarker(markerOptions);
        	 }
        	 rectOptions.add(ll);
		}
//         rectOptions.
         // Get back the mutable Polyline
         mMap.addPolyline(rectOptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
 // Ignore for now
    public void onAccuracyChanged(int sensor, int accuracy) {
    }
    
    // Listen to sensor and provide output
    public void onSensorChanged(int sensor, float[] values) {
      if (sensor != this.sensor)
        return;
      
      Matrix matrix=new Matrix();
      imageView.setScaleType(ScaleType.MATRIX);   //required
      matrix.postRotate(-values[0],imageView.getDrawable().getBounds().width()/2, imageView.getDrawable().getBounds().height()/2);

      imageView.setImageMatrix(matrix);
      
      
//      int orientation = (int) values[0];
//      System.err.println(orientation);
//      rose.setDirection(orientation);
    }
    
	public String getPrettyDistance(double distance) {
		if (distance > 1) {
			return String.format("%.2f km", distance);
		} else {
			return String.format("%.1f m", distance * 1000);
		}
	}
	
	public double distance(double lat1, double lon1, double lat2, double lon2) {
		double R = 6371; // km
		double dLat = toRad(lat2 - lat1);
		double dLon = toRad(lon2 - lon1);
		lat1 = toRad(lat1);
		lat2 = toRad(lat2);

		double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
				+ Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0) * Math.cos(lat1)
				* Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
		double d = R * c;
		return d;
	}

	private double toRad(double val) {
		return val * Math.PI / 180;
	}

    
    
    @Override
    public void onMapLongClick(LatLng arg0) {
    	Utils.log("LatLng", mMap.getCameraPosition().target);
    }
    
}
