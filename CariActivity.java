package com.ulku.ulkubilgisayar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CariActivity extends Activity implements OnKeyListener{
	EditText unvan,telefon,fax,vergiDairesi,VergiNo,adres,il,ilce,web,email,yetkili1;
	CheckBox aday;
	Button kayit,iptal;
	Kullanici kullanici;
	CariHesap cariHesap;
	String unvani,telefonu,faxi,vdairesi,vnosu,adresi,ili,ilcesi,webi,emaili,yetkili1i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cari);
		
		kullanici = new Kullanici(this);
		kullanici.open();
		
		cariHesap =new CariHesap(this);
		cariHesap.open();
		
		unvan=(EditText)findViewById(R.id.Unvan);
		telefon=(EditText)findViewById(R.id.telefon);
		fax=(EditText)findViewById(R.id.fax);
		vergiDairesi=(EditText)findViewById(R.id.Vdairesi);
		VergiNo=(EditText)findViewById(R.id.VNo);
		adres=(EditText)findViewById(R.id.adres);
		il=(EditText)findViewById(R.id.adres_il);
		ilce=(EditText)findViewById(R.id.adres_ilce);
		web=(EditText)findViewById(R.id.site);
		email=(EditText)findViewById(R.id.email);
		yetkili1=(EditText)findViewById(R.id.yetkili1);
		//yetkili2=(EditText)findViewById(R.id.yetkili2);
		aday=(CheckBox)findViewById(R.id.adayCari);
		kayit=(Button)findViewById(R.id.bkayit);
		iptal=(Button)findViewById(R.id.bIptal);
	
		unvan.setOnKeyListener(this);
		telefon.setOnKeyListener(this);
		fax.setOnKeyListener(this);
		vergiDairesi.setOnKeyListener(this);
		VergiNo.setOnKeyListener(this);
		adres.setOnKeyListener(this);
		il.setOnKeyListener(this);
		ilce.setOnKeyListener(this);
		web.setOnKeyListener(this);
		email.setOnKeyListener(this);
		yetkili1.setOnKeyListener(this);
		//yetkili2.setOnKeyListener(this);
		kayit.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbKayit();
				Intent intent = new Intent(CariActivity.this,CRMActivity.class);
				startActivity(intent);
			}
		});
	
		iptal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
	
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.Unvan) {
					unvani=unvan.getText().toString();
					telefon.requestFocus();
				} else if (id == R.id.telefon) {
					telefon.clearFocus();
					fax.requestFocus();
					telefonu=telefon.getText().toString();
				} else if (id == R.id.fax) {
					fax.clearFocus();
					vergiDairesi.requestFocus();
					faxi=fax.getText().toString();
				} else if (id == R.id.Vdairesi) {
					vergiDairesi.clearFocus();
					VergiNo.requestFocus();
					vdairesi=vergiDairesi.getText().toString();
				} else if (id == R.id.VNo) {
					VergiNo.clearFocus();
					adres.requestFocus();
					vnosu=VergiNo.getText().toString();
				} else if (id == R.id.adres) {
					adres.clearFocus();
					il.requestFocus();
					adresi=adres.getText().toString();
				} else if (id == R.id.adres_il) {
					il.clearFocus();
					ilce.requestFocus();
					ili=il.getText().toString();
				} else if (id == R.id.adres_ilce) {
					ilce.clearFocus();
					web.requestFocus();
					ilcesi=ilce.getText().toString();
				} else if (id == R.id.site) {
					web.clearFocus();
					email.requestFocus();
					webi=web.getText().toString();
				} else if (id == R.id.email) {
					email.clearFocus();
					yetkili1.requestFocus();
					emaili=email.getText().toString();
				} else if (id == R.id.yetkili1) {
					yetkili1.clearFocus();
					//yetkili2.requestFocus();
					yetkili1i=yetkili1.getText().toString();
				}
			}
			return true;
		}
		return false;
	}

	public String dbKayit(){
		webi=web.getText().toString();
		ilcesi=ilce.getText().toString();
		ili=il.getText().toString();
		vnosu=VergiNo.getText().toString();
		telefonu=telefon.getText().toString();
		unvani=unvan.getText().toString();
		vdairesi=vergiDairesi.getText().toString();
		faxi=fax.getText().toString();

		emaili=email.getText().toString();
		yetkili1i=yetkili1.getText().toString();
		String id="";
		cariHesap.AdayCariEkleme(id,unvani, telefonu, faxi, emaili, webi, adresi, ili, ilcesi, yetkili1i, "","0");
		finish();
		return "Kayýt Baþarýlý";
	}
}
