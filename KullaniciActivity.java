package com.ulku.ulkubilgisayar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class KullaniciActivity extends Activity {
	Button kaydet, iptal;
	EditText kullaniciAdi, Sifre1, Sifre2, kullaniciKodu,mail,ip;
	String KullaniciAdi, Sifresi1, Sifresi2, KullaniciKodu,email,IP;
	Kullanici kullanici;
	Context context;
	String TAG="Jaseen";
	String cvp,md5adi,md5sifre1,md5sifre2;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kullanici);
		kaydet = (Button) findViewById(R.id.kaydet);
		iptal = (Button) findViewById(R.id.iptal);
		kullaniciAdi = (EditText) findViewById(R.id.username);
		Sifre1 = (EditText) findViewById(R.id.password);
		Sifre2 = (EditText) findViewById(R.id.password1);
		kullaniciKodu = (EditText) findViewById(R.id.Userkodu);
		mail=(EditText) findViewById(R.id.mail);
		ip=(EditText) findViewById(R.id.ip);
		tv=(TextView)findViewById(R.id.tv);

		kullanici = new Kullanici(this);
		kullanici.open();
		
		kaydet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				KullaniciAdi = kullaniciAdi.getText().toString();
				Sifresi1 = Sifre1.getText().toString();
				Sifresi2 = Sifre2.getText().toString();
				KullaniciKodu = kullaniciKodu.getText().toString();
				IP=ip.getText().toString();
				email=mail.getText().toString(); 
						
				md5adi =md5(KullaniciAdi);
				md5sifre1 =md5(Sifresi1);
				md5sifre2 =md5(Sifresi2);
				if(internetBaglantisiVarMi()==true)
				{
				if (md5sifre1.equals(md5sifre2)) {
					AsyncKayit task = new AsyncKayit();
					task.execute();			
				} else {
					Toast.makeText(getApplicationContext(), "Þifreler eþleþmedi.", Toast.LENGTH_LONG).show();
					Sifre1.requestFocus();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
			}
				
				
				/*
				boolean kayitKontrol = dbAdapter.kayitKontrol(KullaniciKodu);
				if (kayitKontrol == false) {
					
				} else {
					Toast.makeText(getApplication(), "Kullanýcý Zaten Kayýtlý", Toast.LENGTH_SHORT).show();
				}
			*/
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
	private class AsyncKayit extends AsyncTask<Integer, Void, Void> {
		protected Void doInBackground(Integer... params) {
			Log.i(TAG, "doInBackground");
			//cvp=dbAdapter.StokEkleme();
			cvp=cari(md5adi,md5sifre1,email);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp +" ");
			Toast.makeText(getApplicationContext(), cvp, Toast.LENGTH_LONG).show();
			if(cvp.equals("1")){finish();}
			else{Toast.makeText(getApplication(), "Servise", Toast.LENGTH_LONG).show();	}
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			tv.setText("Calculating...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}
	public String cari(String adi,String sifre,String mail){
		
		IP=ip.getText().toString();
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://"+IP+"/android.asmx?WSDL";
		final String SOAP_ACTION = "http://tempuri.org/userDbCheck";
		final String METHOD_NAME = "userDbCheck";
		//String cvp="";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		//String userID= dbAdapter.kullanici(kullanici);
	
		PropertyInfo adiPI = new PropertyInfo();
		adiPI.setName("username");
		adiPI.setValue(adi);
		adiPI.setType(String.class);
		request.addProperty(adiPI);

		PropertyInfo webPI = new PropertyInfo();
		webPI.setName("password");
		webPI.setValue(sifre);
		webPI.setType(String.class);
		request.addProperty(webPI);
		
		PropertyInfo mailPI = new PropertyInfo();
		mailPI.setName("mail");
		mailPI.setValue(mail);
		mailPI.setType(String.class);
		request.addProperty(mailPI);
	
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		//Set output SOAP object
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			//Invole web service
			androidHttpTransport.call(SOAP_ACTION, envelope);
			//Get the response
			//SoapObject response = (SoapObject) envelope.getResponse();
			
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); 
			
			cvp= response.toString();
			
			if(cvp.equals("1")){
				kullanici.register(md5adi, md5sifre1, KullaniciKodu,IP);
				
			}
			else{
				cvp="Tanýmlý kullanýcý yok.Program Yöneticinize Baþvurun." ;
			}
			
			/*SoapObject list; 
			list= (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
			int sayý = list.getPropertyCount();
			String id="";
			for(int i=0;i<sayý;i++){ 
				SoapObject list2=null;
				list2=(SoapObject) list.getProperty(i);
				id =list2.getProperty("adaycr_kod").toString();
				}
			*/
			
			
			//dbAdapter.crmKoduGüncelle(unvani,kod);
			//Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
			//tv.setText(e.toString());
		}

		
		return cvp;	
	}


	
	
	private String md5(String in) {
	    MessageDigest digest;
	    try {
	        digest = MessageDigest.getInstance("MD5");
	        digest.reset();
	        digest.update(in.getBytes());
	        byte[] a = digest.digest();
	        int len = a.length;
	        StringBuilder sb = new StringBuilder(len << 1);
	        for (int i = 0; i < len; i++) {
	            sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
	            sb.append(Character.forDigit(a[i] & 0x0f, 16));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
	    return null;
	}
	
	
	boolean internetBaglantisiVarMi() {

		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (conMgr.getActiveNetworkInfo() != null

				&& conMgr.getActiveNetworkInfo().isAvailable()

				&& conMgr.getActiveNetworkInfo().isConnected()) {

			return true;

		} else {

			return false;

		}

	}
	
}
