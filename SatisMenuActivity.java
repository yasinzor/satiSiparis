package com.ulku.ulkubilgisayar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SatisMenuActivity extends Activity {
	Button SatisYap,SatisGoster;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_satis_menu);
		main();
	}
	public void main(){
		SatisYap=(Button)findViewById(R.id.SatisOlustur);
		SatisGoster=(Button)findViewById(R.id.FaturaGoster);
		
		SatisYap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SatisMenuActivity.this,TaabActivity.class);
				startActivity(intent);
			}
		});
		
		SatisGoster.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SatisMenuActivity.this,FaturaShowActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
