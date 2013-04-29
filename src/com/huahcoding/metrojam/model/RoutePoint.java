package com.huahcoding.metrojam.model;

import com.huahcoding.metrojam.Utils;

import android.annotation.SuppressLint;

public class RoutePoint extends Point {

	private double distanceFromCurrent;
	private RoutePoint back;
	private RoutePoint next;
	private RoutePoint deviceLocation;
	private double distanceToNext=-1;
	private double distanceToBack=-1;
	private double timeToGetTo;
	private double timeSinceBeenThere;
	private int index;
	private boolean selected;
	private boolean isClosestToCurrent;
	private double positionInCell;
	

	@Override
	public String toString() {
		return getName() + " \n[lt=" + getLatitude() + "lg=" + getLongitude()+"] "+index ;
	}

	public RoutePoint(String name, double lt, double lg) {
		super(name, lt, lg);
	}
	
	public RoutePoint(String name, double lt, double lg, RoutePoint back, RoutePoint next) {
		super(name, lt, lg);
		this.back=back;
		this.next=next;
	}

	public boolean isClosestToCurrent() {
		return isClosestToCurrent;
	}

	public void setClosestToCurrent(boolean isClosestToCurrent) {
		this.isClosestToCurrent = isClosestToCurrent;
		updatePerspectiveFromDeviceLocation();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPrettyDistance() {
		if (distanceFromCurrent > 1) {
			return String.format("%.2f km", distanceFromCurrent);
		} else {
			return String.format("%.1f m", distanceFromCurrent * 1000);
		}
	}
	public String getPrettyDistance(double distance) {
		if (distance > 1) {
			return String.format("%.2f km", distance);
		} else {
			return String.format("%.1f m", distance * 1000);
		}
	}

	public double getDistanceFromCurrent() {
		return distanceFromCurrent;
	}

	public double getTimeToGetTo() {
		return 10;
	}

	public void setCurrentPoint(RoutePoint current) {
//		setDistanceFromCurrent(current);
		this.deviceLocation=current;
		this.distanceFromCurrent = distanceFrom(current);
	}

	public void updatePerspectiveFromDeviceLocation() {
		if (deviceLocation == null) {
			this.distanceFromCurrent = -1;
		} else
			this.distanceFromCurrent = distanceFrom(deviceLocation);
		
		if(isClosestToCurrent){
			if(distanceFromCurrent>Route.METERS_ERROR_MARGIN){
				if(back==null){
					positionInCell = calcPositionInCell(next);
					positionInCell=positionInCell!=-1?positionInCell*50+50:-1;
				}else if(next==null){
					positionInCell = calcPositionInCell(back);
					positionInCell=positionInCell!=-1?50-positionInCell*50:-1;
				}else{
					boolean isBetweenHereAndNext=distanceToNext>next.distanceFromCurrent;
					if(isBetweenHereAndNext){
						positionInCell = calcPositionInCell(next);
						positionInCell=positionInCell!=-1?positionInCell*50+50:-1;
					}else{
						positionInCell = calcPositionInCell(back);
						positionInCell=positionInCell!=-1?50-positionInCell*50:-1;
					}
				}
			}else{
				positionInCell=50;
			}
		}else{
			positionInCell=0;
		}
		Utils.log("damn",index,positionInCell);
	}
	public double getPositionInCell() {
		return positionInCell;
	}
	
	private double calcPositionInCell(RoutePoint closestPoint){
//		double middleDistance=(closestPoint.dista)/2.0;
		double middleDistance=(distanceFromCurrent+closestPoint.distanceFromCurrent)/2.0;
		if(distanceFromCurrent<middleDistance){
			return distanceFromCurrent/middleDistance;
		}else{
			return -1;
		}
	}

	public double distanceFrom(Point b) {
		return distance(getLatitude(), getLongitude(), b.getLatitude(),
				b.getLongitude());
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

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setDistanceToBack(double distanceToBack) {
		this.distanceToBack = distanceToBack;
	}
	
	public void setDistanceToNext(double distanceToNext) {
		this.distanceToNext = distanceToNext;
	}
	
	public double getDistanceToBack() {
		return distanceToBack;
	}
	public double getDistanceToNext() {
		return distanceToNext;
	}
	public void setNext(RoutePoint next) {
		this.next = next;
	}
	public void setBack(RoutePoint back) {
		this.back = back;
	}
}
