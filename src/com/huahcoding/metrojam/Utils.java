package com.huahcoding.metrojam;

import java.util.Arrays;

import android.util.Log;

public class Utils {
	public static void log(String msg){
		Log.d("mylog", msg);
	}
	
	public static void log(Object... ob){
		Log.d("mylog", Arrays.deepToString(ob));
	}
}
