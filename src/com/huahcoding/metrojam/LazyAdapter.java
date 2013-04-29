package com.huahcoding.metrojam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.metrojam.R;
import com.huahcoding.metrojam.model.RoutePoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends ArrayAdapter<RoutePoint> {

	private Activity activity;
	private List<RoutePoint> data;
	private static LayoutInflater inflater = null;
	private GradientDrawable[] backgrounds=new GradientDrawable[10];
	private int backGroundIds[]={
			R.drawable.gradient_bg_tracker_1,
			R.drawable.gradient_bg_tracker_2,
			R.drawable.gradient_bg_tracker_3,
			R.drawable.gradient_bg_tracker_4,
			R.drawable.gradient_bg_tracker_5,
			R.drawable.gradient_bg_tracker_6,
			R.drawable.gradient_bg_tracker_7,
			R.drawable.gradient_bg_tracker_8,
			R.drawable.gradient_bg_tracker_9,
			R.drawable.gradient_bg_tracker_10
	};

	public LazyAdapter(Activity a, int viewId, List<RoutePoint> d) {
		super(a, viewId, d);
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for (int i = 0; i < backGroundIds.length; i++) {
			backgrounds[i]  = (GradientDrawable)activity.getResources().getDrawable(backGroundIds[i]);
		}
	}

	public int getCount() {
		return data.size();
	}

	public RoutePoint getItem(int position) {
		return data.get(position);
	}
	

	public long getItemId(int position) {
		return position;
	}

	
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null){
			vi = inflater.inflate(R.layout.list_row, null);
		}
			

		TextView title = (TextView) vi.findViewById(R.id.title); // title
		TextView artist = (TextView) vi.findViewById(R.id.artist); // artist
																	// name
		TextView distanceToNext = (TextView) vi.findViewById(R.id.duration); // duration

		RoutePoint point = data.get(position);
		
		
		title.setText(point.getName());
		artist.setText(point.getPrettyDistance());
		distanceToNext.setText(point.getPrettyDistance(point.getDistanceToNext()) + "");
		
		if(point.isClosestToCurrent()){
//			Utils.log(back);
			int idx = (int)point.getPositionInCell()/10;
			Utils.log("point.getPositionInCell()",point.getPositionInCell(),"idx",idx);
			vi.setBackground(backgrounds[idx]);
		}else{
			vi.setBackgroundResource(R.drawable.gradient_bg);
		}
		return vi;
	}
}