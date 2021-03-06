package com.ulku.ulkubilgisayar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

//import com.google.android.gms.maps.;

import android.widget.Toast;


public class MapActivity extends Activity  {
    private GoogleMap googleMap;

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_map);

	        try {
	            // Loading map
	            initilizeMap();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }

	 /**
	     * function to load map. If map is not created it will create it for you
	     * */
	    private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	                    R.id.map)).getMap();
	 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	 
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }
 
}