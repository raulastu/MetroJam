package com.huahcoding.metrojam;

import android.location.Location;

public interface Delegate {

	public String getName();
	public void callback(Location location);
}
