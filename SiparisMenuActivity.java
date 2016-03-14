package com.ulku.ulkubilgisayar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SiparisMenuActivity extends Activity {
	Button BsipOlus,BsipGos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_siparis_menu);
		BsipOlus=(Button)findViewById(R.id.SiparisOlustur);
		BsipGos=(Button)findViewById(R.id.SiparisGoster);
		
		BsipOlus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SiparisMenuActivity.this,SiparisActivity.class);
				startActivity(intent);
			}
		});
		
		BsipGos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SiparisMenuActivity.this,SiparisGosterActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
