package com.huahcoding.metrojam;

import java.util.Arrays;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;

import com.example.metrojam.R;
import com.huahcoding.metrojam.model.Point;
import com.huahcoding.metrojam.model.Route;
import com.huahcoding.metrojam.model.RoutePoint;
import com.huahcoding.metrojam.test.RouteTestData;
import com.nineoldandroids.animation.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class StationListActivity extends ListActivity implements Delegate {

	// ListView list;
	LazyAdapter adapter;
	RoutePoint[] points;
	Route route;
	GeoLocation geo;
	Vibrator v;
	public String routeName; 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_station_list);
		if(getIntent().getExtras()!=null){
			routeName=getIntent().getExtras().getString("com.huahcoding.metrojam.name");
			Object[] ob = getIntent().getExtras().getParcelableArray("com.huahcoding.metrojam.SelectedRoute");
			this.points= new RoutePoint[ob.length];
			System.arraycopy(ob, 0, points, 0, ob.length);
		}else{
			routeName="Home-Work";
			points=RouteTestData.getWorkData();
		}
		route = new Route(Arrays.asList(points));
		
		// list = (ListView) findViewById(R.id.list);

		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

//		points = RouteTestData.getWorkData();
		

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapter(this, R.id.list, Arrays.asList(points));
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		setListAdapter(adapter);


		getListView().post(new Runnable() {

			@Override
			public void run() {
				geo = new GeoLocation(StationListActivity.this, StationListActivity.this);
				adapter.notifyDataSetChanged();
			}
			
		});

	}
	String ss(Object... ob){
		return Arrays.deepToString(ob);
	}
	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {

		// adapter.notifyDataSetChanged();
	}

	@Override
	public void callback(Location location) {

		RoutePoint myPosition = new RoutePoint("me", location.getLatitude(),
				location.getLongitude(),null,null);

		// Vibrate for 500 milliseconds
		v.vibrate(new long[] { 0, 200, 100, 200, 100, 200 }, -1);
		
		Utils.log(myPosition.toString());
		

		route.getClosestPoints(myPosition);
		adapter.notifyDataSetChanged(); 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_action_menu, menu);
		return true;
	}
	
	@Override
	public String getName() {
		return routeName;
	}
}
