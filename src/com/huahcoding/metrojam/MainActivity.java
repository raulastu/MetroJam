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
import com.huahcoding.metrojam.model.Point;
import com.huahcoding.metrojam.model.RoutePoint;
import com.huahcoding.metrojam.test.RouteTestData;

import android.app.Activity;
import android.app.ListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * The main activity of the API library demo gallery.
 * <p>
 * The main layout lists the demonstrated features, with buttons to launch them.
 */
public final class MainActivity extends ListActivity {

	private static final StationListDetails[] demos = new StationListDetails[2];
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        demos[0] = new StationListDetails("Home-Work", "My route", StationListActivity.class,
                RouteTestData.getWorkData());
        demos[1] = new StationListDetails("Metropolitano Troncal A", "Metro Jam", StationListActivity.class,
        		RouteTestData.getMetroPolitanoStationsData());
        
        ListAdapter adapter = new CustomArrayAdapter(this, demos);

        setListAdapter(adapter);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_action_menu, menu);
	    return true;
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_create_route:
	      Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT)
	          .show();
	      Intent intent = new Intent(this, RouteCreationActivity.class);
	      startActivity(intent);
	      break;
	    case R.id.action_refresh:
	      Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    default:
	      break;
	    }

	    return true;
	  } 
	
	
    private static class StationListDetails {
        private final String title;
        private final String description;
        private final Class<? extends Activity> activityClass;
        private RoutePoint[] points;

        public StationListDetails(
        		String titleId, 
        		String descriptionId, 
        		Class<? extends Activity> activityClass,
        		RoutePoint[] points) {
            super();
            this.title = titleId;
            this.description = descriptionId;
            this.activityClass = activityClass;
            this.points=points;
        }
        private RoutePoint[] getPoints() {
			return points;
		}
        private String getTitle() {
			return title;
		}
    }

    /**
     * A custom array adapter that shows a {@link FeatureView} containing details about the demo.
     */
    private static class CustomArrayAdapter extends ArrayAdapter<StationListDetails> {

        /**
         * @param demos An array containing the details of the demos to be displayed.
         */
        public CustomArrayAdapter(Context context, StationListDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }

            StationListDetails demo = getItem(position);

            featureView.setTitle(demo.title);
            featureView.setDescription(demo.description);

            return featureView;
        }
    }

    

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        StationListDetails routeList = (StationListDetails) getListAdapter().getItem(position);
        Intent i = new Intent(this, routeList.activityClass);
        i.putExtra("com.huahcoding.metrojam.SelectedRoute", routeList.getPoints());
        i.putExtra("com.huahcoding.metrojam.name", routeList.getTitle());
        startActivity(i);
    }
}
