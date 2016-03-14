package com.ulku.ulkubilgisayar;

import android.support.v7.app.ActionBarActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
import org.kobjects.xml.XmlReader;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
*/
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	/*
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://192.168.100.103:8081/Servis.asmx?WSDL";
	private final String SOAP_ACTION = "http://tempuri.org/Kullanici";
	private final String METHOD_NAME = "Kullanici";
	
	private String TAG = "JASEEN";*/
	private static String isim, sifre;
	
	EditText pass;
	Button bgiris,bkayit,biptal;
	TextView tv;
	EditText adi;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		main();
	
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public void main(){
		adi = (EditText) findViewById(R.id.adi);
		pass = (EditText) findViewById(R.id.password);
		//Fahrenheit Text control
		tv = (TextView) findViewById(R.id.textView1);
		//Button to trigger web service invocation
		bgiris = (Button) findViewById(R.id.bgiris);
		bkayit = (Button) findViewById(R.id.bkayitmain);
		biptal=(Button)findViewById(R.id.biptal);
		final Kullanici kullanici;

		kullanici = new Kullanici(this);
		kullanici.open();
		
		bgiris.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (adi.getText().length() != 0 || pass.getText().length() != 0  ) {
					//Get the text control value
					isim = adi.getText().toString();
					sifre = pass.getText().toString();
					
					
					if(kullanici.Login(md5(isim), md5(sifre))){
						Bundle sendBundle =new Bundle();
						sendBundle.putString("isim", isim);
						Intent intent = new Intent(MainActivity.this, MenuActivity.class);
			            intent.putExtras(sendBundle);
						startActivity(intent);
			            finish();
					}
					else{
						Toast.makeText(MainActivity.this,
								"Yanlýþ Kullanýcý Adý veya Þifre",
								Toast.LENGTH_LONG).show();
						main();
					}
					//Create instance for AsyncCallWS
					///AsyncCallWS task = new AsyncCallWS();
					//Call execute 
					///task.execute();
				//If text control is empty
				} else {
					Toast.makeText(MainActivity.this,
							"Kullanýcý adýný ve þifreyi giriniz",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	
	bkayit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,KullaniciActivity.class);
			startActivity(intent);
		}
	});
	
	biptal.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	
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
}
