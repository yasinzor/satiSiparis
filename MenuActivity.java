package com.ulku.ulkubilgisayar;

import android.support.v7.app.ActionBarActivity;
import android.R.string;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		TextView isim = (TextView) findViewById(R.id.textView1);
		//Button bsatis = (Button) findViewById(R.id.bsatis);
		Button btab = (Button) findViewById(R.id.btab);
		Button bcrm= (Button) findViewById(R.id.bcrm);
		Button bayar = (Button) findViewById(R.id.bayarlar);
		Button bcrmshow = (Button) findViewById(R.id.kapat);
		Button bsiparis = (Button) findViewById(R.id.bsiparis);
		
		Bundle recieveBundle = this.getIntent().getExtras();
		final String recieveValue = recieveBundle.getString("isim");
		isim.setText("Hoþgeldin " + recieveValue);
	    
		bcrmshow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// deneme button
				Intent intent = new Intent(MenuActivity.this,CariFSActivity.class);
				startActivity(intent);
				
			}
		});
		
		bsiparis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this,SiparisMenuActivity.class);
				startActivity(intent);
			}
		});
		bcrm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 Intent intent = new Intent(MenuActivity.this,CrmMenuActivity.class);
			 startActivity(intent);
			 
			}
		});
		
		btab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this,SatisMenuActivity.class);
				startActivity(intent);
			}
		});
		
		bayar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle sendBundle =new Bundle();
				sendBundle.putString("isim", recieveValue);
				Intent intent= new Intent(MenuActivity.this,AyarlarActivity.class);
				intent.putExtras(sendBundle);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
