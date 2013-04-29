package com.huahcoding.metrojam.model;

import java.util.ArrayList;
import java.util.List;

import com.huahcoding.metrojam.Utils;

public class Route {
	List<RoutePoint> points;
	public static double METERS_ERROR_MARGIN= 0.0020; 
	
	public Route(List<RoutePoint> points) {
		this.points = points;
		for (int i = 0; i < points.size(); i++) {
			if(i>0){
				double distance = points.get(i).distanceFrom(points.get(i-1));
				points.get(i).setDistanceToBack(distance);
				points.get(i).setBack(points.get(i-1));
			}
			if(i<points.size()-1){
				double distance = points.get(i).distanceFrom(points.get(i+1));
				points.get(i).setDistanceToNext(distance);
				points.get(i).setNext(points.get(i+1));
			}
			points.get(i).setIndex(i);
		}
	}

	public void updateCurrentLocation(RoutePoint current) {

	}

	public RoutePoint[] getClosestPoints(RoutePoint deviceLocation) {
		RoutePoint closest=null;
		double closestDistance=Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			points.get(i).setClosestToCurrent(false);
			double distance=points.get(i).distanceFrom(deviceLocation);
//			Utils.log(distance+"");
			if (distance<closestDistance) {
				closestDistance=distance;
				closest=points.get(i);
			}
			points.get(i).setCurrentPoint(deviceLocation);
		}
		closest.setClosestToCurrent(true);
		
		Utils.log("distance from 0",points.get(0).distanceFrom(deviceLocation));
		Utils.log("distance from 1",points.get(1).distanceFrom(deviceLocation));
		RoutePoint[] res = new RoutePoint[3];
		res[1]=closest;
		if(closest==points.get(0)){
			
		}else if(closest==points.get(points.size()-1)){
			
		}else{
			
		}
		return res;
	}
}
