package com.huahcoding.metrojam.model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Point {

	private String name;
	private double longitude;
	private double latitude;

	public Point(String name, double lt, double lg) {
		this.name = name;
		this.latitude = lt;
		this.longitude = lg;
	}

	@Override
	public String toString() {
		return name + " \n[lt=" + latitude + " lg=" + longitude + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}
