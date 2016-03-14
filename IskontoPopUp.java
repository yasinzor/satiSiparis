package com.ulku.ulkubilgisayar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IskontoPopUp extends Activity {
EditText iskonto1,iskonto2,iskonto3,iskonto4,iskonto5,iskonto6;
Double iskont1,iskont2,iskont3,iskont4,iskont5,iskont6;
Double toplam,i_toplam;
Button biskonto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iskonto_pop_up);
		
		Bundle recieveBundle = this.getIntent().getExtras();
		toplam = recieveBundle.getDouble("toplam");
		
		biskonto=(Button)findViewById(R.id.biskonto);
		
		iskonto1=(EditText)findViewById(R.id.iskont1);
		iskonto2=(EditText)findViewById(R.id.iskont2);
		iskonto3=(EditText)findViewById(R.id.iskont);
		iskonto4=(EditText)findViewById(R.id.iskont4);
		iskonto5=(EditText)findViewById(R.id.iskont5);
		iskonto6=(EditText)findViewById(R.id.iskont6);
	 
		biskonto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iskont1=Double.parseDouble(iskonto1.getText().toString());
				iskont2=Double.parseDouble(iskonto2.getText().toString());
				iskont3=Double.parseDouble(iskonto3.getText().toString());
				iskont4=Double.parseDouble(iskonto4.getText().toString());
				iskont5=Double.parseDouble(iskonto5.getText().toString());
				iskont6=Double.parseDouble(iskonto6.getText().toString());
				///*
				i_toplam=iskonto(toplam, iskont1);
				i_toplam=iskonto(i_toplam, iskont2);
				i_toplam=iskonto(i_toplam, iskont3);
				i_toplam=iskonto(i_toplam, iskont4);
				i_toplam=iskonto(i_toplam, iskont5);
				i_toplam=iskonto(i_toplam, iskont6);
			
				Intent intent = new Intent();
			    intent.putExtra("isToplam", i_toplam);
			    intent.putExtra("Toplam", toplam);
			    intent.putExtra("iskont1", iskont1);
				intent.putExtra("iskont2", iskont2);
				intent.putExtra("iskont3", iskont3);
				intent.putExtra("iskont4", iskont4);
				intent.putExtra("iskont5", iskont5);
				intent.putExtra("iskont6", iskont6);
			    setResult(RESULT_OK, intent);
			    finish();
			  
			}
		});
	}
	
	public Double iskonto(Double toplam,Double iskonto){
		Double temp = toplam*iskonto/100;
		toplam=toplam-temp;
		return toplam;
	}
}
