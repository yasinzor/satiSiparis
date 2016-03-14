package com.ulku.ulkubilgisayar;


import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
@SuppressWarnings("deprecation")
public class TaabActivity extends TabActivity {

String iskont1,iskont2,iskont3,iskont4,iskont5,iskont6;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost();
		
		//Stok Tab
		Intent intentStok = new Intent().setClass(this, Tab1.class);
		TabSpec tabSpecStok = tabHost
		  .newTabSpec("Android")
		  .setIndicator("Fatura Bilgi")
		  .setContent(intentStok);
		
		//Satis Tab
		Intent intentSatis = new Intent().setClass(this, Tab2.class);
		TabSpec tabSpecSatis = tabHost
		  .newTabSpec("Android")
		  .setIndicator("Ürün Giriþ")
		  .setContent(intentSatis);
		
		Intent intentTable = new Intent().setClass(this, Tab3.class);
		TabSpec tabSpecTable = tabHost
		  .newTabSpec("Table")
		  .setIndicator("Fatura Tutar")
		  .setContent(intentTable);
		
		//add tabs
		tabHost.addTab(tabSpecStok);
		tabHost.addTab(tabSpecSatis);
		tabHost.addTab(tabSpecTable);
		
		//default Tab
		tabHost.setCurrentTab(0);
		
		
	}
	
	
}
