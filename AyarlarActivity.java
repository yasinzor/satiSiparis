package com.ulku.ulkubilgisayar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AyarlarActivity extends Activity {
	Button DbGuncel, dbCariGuncel, bAdayCari, bAdayCariGet, crmGonder, Kullanici, genel, stokHareketButton,siparisButton;
	Kullanici kullaniciDb;
	Stok stok;
	StokHareket stokHareket;
	CariHesap cariHesap;
	CrmModule crmModule;
	Siparis siparis;
	
	String TAG = "jaseen";
	String IP, mikrokodu, cvp, kod, userID, unvani, tel, fax, mail, site, kullanici, adres, adres_il, adres_ilce,
			yetkili1, yetkili2;
	Integer db = 0;
	TextView tv;
	ArrayList<HashMap<String, String>> adayCariListe, crm, StokHareketListe,siparisListesi;
	EditText ip;
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		kullaniciDb = new Kullanici(this);
		kullaniciDb.open();
		
		stok = new Stok(this);
		stok.open();
		
		cariHesap=new CariHesap(this);
		cariHesap.open();
		
		crmModule=new CrmModule(this);
		crmModule.open();
		
		stokHareket=new StokHareket(this);
		stokHareket.open();
		
		siparis=new Siparis(this);
		siparis.open();
		
		setContentView(R.layout.activity_ayarlar);
		DbGuncel = (Button) findViewById(R.id.bDb);
		dbCariGuncel = (Button) findViewById(R.id.bCariGuncelle);
		bAdayCari = (Button) findViewById(R.id.bAdayCari);
		bAdayCariGet = (Button) findViewById(R.id.bAdayCariGet);
		crmGonder = (Button) findViewById(R.id.crmGonder);
		genel = (Button) findViewById(R.id.genel);
		stokHareketButton = (Button) findViewById(R.id.stokHareket);
		siparisButton=(Button) findViewById(R.id.siparis);
		tv = (TextView) findViewById(R.id.tv);
		ip = (EditText) findViewById(R.id.ip);
		

		adayCariListe = new ArrayList<HashMap<String, String>>();
		crm = new ArrayList<HashMap<String, String>>();
		StokHareketListe = new ArrayList<HashMap<String, String>>();

		Bundle recieveBundle = this.getIntent().getExtras();
		kullanici = recieveBundle.getString("isim");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		///
		kullanici = md5(kullanici);
		list = kullaniciDb.kullanici(kullanici);

		userID = list.get(0).get("id");
		IP = list.get(0).get("ip");
		ip.setText(IP);
		main();
	}

	public void main() {
		
		siparisButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
					siparisListesi=siparis.SiparisGetir();
					AsyncCallSiparisYolla task = new AsyncCallSiparisYolla();
					task.execute(siparisListesi);
				}
				else
				{
				 Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
				
				
			}
		});
		 
		genel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
				AsyncCallStokWS task = new AsyncCallStokWS();
				task.execute();
				}
				else
				{
				 Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

		DbGuncel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(internetBaglantisiVarMi()==true)
				{
				AsyncCallStokWS task = new AsyncCallStokWS();
				task.execute();
				}
				else
				{
				 Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

		dbCariGuncel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
					AsyncCallcariGetir task = new AsyncCallcariGetir();
					task.execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

		bAdayCari.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
				adayCariListe = cariHesap.AdayCariBilgi();
				AsyncCallAdayCariYolla task = new AsyncCallAdayCariYolla();
				task.execute(adayCariListe);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

		bAdayCariGet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
					AsyncCallAdayCari task = new AsyncCallAdayCari();
					task.execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

		crmGonder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
				crm = crmModule.CrmBilgi();
				AsyncCallCRMYolla task1 = new AsyncCallCRMYolla();
				task1.execute(crm);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

		stokHareketButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(internetBaglantisiVarMi()==true)
				{
				StokHareketListe = stokHareket.StokHareketBilgi();
				AsyncCallStokHareketYolla task = new AsyncCallStokHareketYolla();
				task.execute(StokHareketListe);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý kontrol ediniz", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private class AsyncCallStokWS extends AsyncTask<Integer, Void, Void> {
		protected Void doInBackground(Integer... params) {
			Log.i(TAG, "doInBackground");
			// cvp=dbAdapter.StokEkleme();
			cvp = stok();
			stokMarkaCrm();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			AsyncCallcariGetir task = new AsyncCallcariGetir();
			task.execute();
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

	public String stok() {
		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim/* 192.168.100.103:8081 */ + "/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/StokGetir";
			final String METHOD_NAME = "StokGetir";
			// String cvp="";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject response = (SoapObject) envelope.getResponse();
			
				SoapObject list;
				list = (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
				int sayý = list.getPropertyCount();

				for (int i = 0; i <= sayý; i++) { // kod calýstýrýlacak :)
					SoapObject list2 = null;
					list2 = (SoapObject) list.getProperty(i);
					String id = "";
					String isim = "";
					String birim = "";
					String vergi = "";
					String birim_fiyat = "";
					String stokSayisi="";
					id = list2.getProperty("sto_kod").toString();
					isim = list2.getProperty("sto_isim").toString();
					birim = list2.getProperty("sto_birim1_ad").toString();
					vergi = list2.getProperty("sto_perakende_vergi").toString();
					birim_fiyat = list2.getProperty("sfiyat_fiyati").toString();
					stokSayisi=list2.getProperty("sth_eldeki_miktar").toString();
					
					cvp = stokDB(id, isim, birim, birim_fiyat, vergi, "0", "TL",stokSayisi);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cvp;
	}

	public String stokDB(String id, String isim, String birim, String birim_fiyat, String vergi, String iskonto,
			String doviz,String StokSayisi) {
		String cvp = "";
		String sonuc = stok.StokDbKontrol(id, isim);
		if (sonuc.equals("0")) {
			cvp = stok.stokEkler(id, isim, birim, birim_fiyat, vergi, "0", "TL",StokSayisi);
		}
		else{
			cvp=stok.stokGuncel(isim, birim_fiyat, StokSayisi);
		}
		
		return cvp;
	}

	public String stokMarkaCrm() {
		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim/* 192.168.100.103:8081 */ + "/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/stok_markalari";
			final String METHOD_NAME = "stok_markalari";
			// String cvp="";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();
				SoapObject list;
				list = (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
				int sayý = list.getPropertyCount();

				for (int i = 0; i <= sayý; i++) { // kod calýstýrýlacak :)
					SoapObject list2 = null;
					list2 = (SoapObject) list.getProperty(i);
					String id = "";
					String isim = "";
					String birim = "";
					String vergi = "";
					id = list2.getProperty("mrk_kod").toString();
					isim = list2.getProperty("mrk_ismi").toString();
					// cvp = id + isim+ birim+vergi;
					cvp = stokMarkaCrmDB(id, isim);
					// cvp=dbAdapter.StokEkleme(id,isim,birim,"0.00",vergi,"0","TL");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// tv.setText(e.toString());
			}
		}
		return cvp;
	}

	public String stokMarkaCrmDB(String id, String isim) {
		String cvp = "";
		String sonuc = crmModule.stokMarkaDbKontrol(id, isim);
		if (sonuc.equals("0")) {
			cvp = crmModule.stokMarkaEkle(id, isim);
		}
		return cvp;
	}

	private class AsyncCallcariGetir extends AsyncTask<Integer, Void, Void> {
		protected Void doInBackground(Integer... params) {
			Log.i(TAG, "doInBackground");
			// cvp=dbAdapter.StokEkleme();
			cvp = cari();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			adayCariListe = cariHesap.AdayCariBilgi();
			AsyncCallAdayCariYolla task = new AsyncCallAdayCariYolla();
			task.execute(adayCariListe);
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

	public String cari() {
		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim/* 192.168.100.103:8081 */ + "/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/CariGetir";
			final String METHOD_NAME = "CariGetir";
			// String cvp="";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();
				SoapObject list;
				list = (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
				int sayý = list.getPropertyCount();

				for (int i = 0; i <= sayý; i++) {
					SoapObject list2 = null;
					list2 = (SoapObject) list.getProperty(i);
					String id = "";
					String unvan = "";
					String unvan2 = "";
					String vergi = "";
					id = list2.getProperty("cari_kod").toString();
					unvan = list2.getProperty("cari_unvan1").toString();
					unvan2 = list2.getProperty("cari_unvan2").toString();
					// cvp = id + isim+ birim+vergi;
					cvp = CariDB(id, unvan, unvan2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cvp;
	}

	public String CariDB(String id, String unvan, String unvan2) {
		String cvp = "";
		String sonuc = cariHesap.CariDbKontrol(id, unvan);
		if (sonuc.equals("0")) {
			cvp = cariHesap.cariEkleme(id, unvan, unvan2);
		} else {
			cvp = "Eklenecek yeni cari kaydý yok.";
		}
		return cvp;
	}

	private class AsyncCallAdayCariYolla extends AsyncTask<List, Void, Void> {
		protected Void doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			for (int i = 0; i < adayCariListe.size(); i++) {
				kod = adayCariListe.get(i).get("id");
				unvani = adayCariListe.get(i).get("unvani");
				tel = adayCariListe.get(i).get("tel");
				mail = adayCariListe.get(i).get("mail");
				site = adayCariListe.get(i).get("site");
				adres = adayCariListe.get(i).get("adres");
				adres_il = adayCariListe.get(i).get("il");
				adres_ilce = adayCariListe.get(i).get("ilce");
				yetkili1 = adayCariListe.get(i).get("yetkili1");
				yetkili2 = adayCariListe.get(i).get("yetkili2");
				String dbKayit = adayCariListe.get(i).get("dbKayit");
				if (dbKayit.equals("0")) {
					cvp = adayCariYolla(kod, unvani, tel, fax, mail, site, adres, adres_il, adres_ilce, yetkili1,yetkili2);
				} else {
					cvp = "Gönderilecek yeni kayýt yok.";
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			AsyncCallAdayCari task=new AsyncCallAdayCari();
			task.execute();
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

	public String adayCariYolla(String kod, String unvani, String tel, String fax, String mail, String site,
			String adres, String adres_il, String adres_ilce, String yetkili1, String yetkili2) {
		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim + /* 192.168.100.103:8081 */"/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/AdayCariEkle";
			final String METHOD_NAME = "AdayCariEkle";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			PropertyInfo kullaniciPI = new PropertyInfo();
			kullaniciPI.setName("Kullanici");
			kullaniciPI.setValue(userID);
			kullaniciPI.setType(String.class);
			request.addProperty(kullaniciPI);

			PropertyInfo idPI = new PropertyInfo();
			idPI.setName("id");
			idPI.setValue(kod);
			idPI.setType(String.class);
			request.addProperty(idPI);

			PropertyInfo adiPI = new PropertyInfo();
			adiPI.setName("unvan");
			adiPI.setValue(unvani);
			adiPI.setType(String.class);
			request.addProperty(adiPI);

			PropertyInfo webPI = new PropertyInfo();
			webPI.setName("web");
			webPI.setValue(site);
			webPI.setType(String.class);
			request.addProperty(webPI);

			PropertyInfo mailPI = new PropertyInfo();
			mailPI.setName("mail");
			mailPI.setValue(mail);
			mailPI.setType(String.class);
			request.addProperty(mailPI);

			PropertyInfo adresPI = new PropertyInfo();
			adresPI.setName("adres");
			adresPI.setValue(adres);
			adresPI.setType(String.class);
			request.addProperty(adresPI);

			PropertyInfo ilPI = new PropertyInfo();
			ilPI.setName("il");
			ilPI.setValue(adres_il);
			ilPI.setType(String.class);
			request.addProperty(ilPI);

			PropertyInfo ilcePI = new PropertyInfo();
			ilcePI.setName("ilce");
			ilcePI.setValue(adres_ilce);
			ilcePI.setType(String.class);
			request.addProperty(ilcePI);

			PropertyInfo telPI = new PropertyInfo();
			telPI.setName("tel");
			telPI.setValue(tel);
			telPI.setType(String.class);
			request.addProperty(telPI);

			PropertyInfo yetkili1PI = new PropertyInfo();
			yetkili1PI.setName("yetkili");
			yetkili1PI.setValue(yetkili1);
			yetkili1PI.setType(String.class);
			request.addProperty(yetkili1PI);

			PropertyInfo yetkili2PI = new PropertyInfo();
			yetkili2PI.setName("yetkili");
			yetkili2PI.setValue("  ");
			yetkili2PI.setType(String.class);
			request.addProperty(yetkili2PI);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject response = (SoapObject) envelope.getResponse();
				SoapObject list;
				list = (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
				int sayý = list.getPropertyCount();
				String id = "";
				for (int i = 0; i < sayý; i++) {
					SoapObject list2 = null;
					list2 = (SoapObject) list.getProperty(i);
					id = list2.getProperty("adaycr_kod").toString();
				}
				cariHesap.adayCariKoduGuncele(kod, id);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "kayýt basarýlý";
	}

	private class AsyncCallAdayCari extends AsyncTask<Integer, Void, Void> {
		protected Void doInBackground(Integer... params) {
			Log.i(TAG, "doInBackground");
			cvp = Adaycari();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			crm = crmModule.CrmBilgi();
			AsyncCallCRMYolla task1 = new AsyncCallCRMYolla();
			task1.execute(crm);

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

	public String Adaycari() {
		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim/* 192.168.100.103:8081 */ + "/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/AdayCariGetir";
			final String METHOD_NAME = "AdayCariGetir";
			// String cvp="";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject response = (SoapObject) envelope.getResponse();
				SoapObject list;
				list = (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
				int sayý = list.getPropertyCount();

				for (int i = 0; i <= sayý; i++) {
					SoapObject list2 = null;
					list2 = (SoapObject) list.getProperty(i);
					String id = "";
					String unvan = "";
					String unvan2 = "";
					String vergi = "";
					String web, mail, adres, il, ilce, tel, yetkili1, yetkili2;
					id = list2.getProperty("adaycr_kod").toString();
					unvan = list2.getProperty("adaycr_unvan1").toString();
					web = list2.getProperty("adaycr_wwwadresi").toString();
					mail = list2.getProperty("adaycr_EMail").toString();
					adres = list2.getProperty("adaycr_adr1_mahalle").toString();
					il = list2.getProperty("adaycr_adr1_il").toString();
					ilce = list2.getProperty("adaycr_adr1_ilce").toString();
					tel = list2.getProperty("adaycr_adr1_tel_no1").toString();
					yetkili1 = list2.getProperty("adaycr_yetkili1_isim").toString();
					yetkili2 = "";

					id = chck(id);
					unvan = chck(unvan);
					web = chck(web);
					mail = chck(mail);
					adres = chck(adres);
					il = chck(il);
					ilce = chck(ilce);
					tel = chck(tel);
					yetkili1 = chck(yetkili1);
					// yetkili2=list2.getProperty("adaycr_yetkili2_isim").toString();
					cvp = AdayCariDB(id, unvan, web, mail, adres, il, ilce, tel, yetkili1, yetkili2);
					// cvp=dbAdapter.StokEkleme(id,isim,birim,"0.00",vergi,"0","TL");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// tv.setText(e.toString());
			}
		}
		return cvp;
	}

	public String AdayCariDB(String id, String unvan, String web, String mail, String adres, String il, String ilce,
			String tel, String yetkili1, String yetkili2) {
		String cvp = "";
		String fax = "";
		String sonuc = cariHesap.AdayCariDbKontrol(id, unvan);
		if (sonuc.equals("0")) {
			String dbKayit = "0";
			cvp = cariHesap.AdayCariEkleme(id, unvan, tel, fax, mail, web, adres, il, ilce, yetkili1, yetkili2,
					dbKayit);
		} else {
			cvp = "Zaten Kayitli";
		}
		return cvp;
	}

	private class AsyncCallCRMYolla extends AsyncTask<List, Void, Void> {
		protected Void doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			// cvp=dbAdapter.StokEkleme();
			for (int i = 0; i < crm.size(); i++) {
				String id = crm.get(i).get("id");
				String kodu = crm.get(i).get("kodu");
				String cari_id = crm.get(i).get("cari_id");
				String yetkili = crm.get(i).get("yetkili");
				// fax= list.get(i).get("fax");
				String not = crm.get(i).get("not");
				String sonuc = crm.get(i).get("sonuc");
				String tarih = crm.get(i).get("tarih");
				String iliski_tipi = crm.get(i).get("iliski_tipi");
				String cari_tipi = crm.get(i).get("cari_tipi");
				String enlem = crm.get(i).get("enlem");
				String boylam = crm.get(i).get("boylam");
				String dbkayit = crm.get(i).get("dbKayit");
				String markaAdi = crm.get(i).get("markaadi");
				String markaFiyat = crm.get(i).get("markafiyat");
				// Toast.makeText(getApplicationContext(),
				// task.getStatus().toString(), Toast.LENGTH_LONG).show();
				if (dbkayit.equals("0")) {
					cvp = CrmYolla(id, kodu, cari_id, yetkili, not, sonuc, tarih, iliski_tipi, cari_tipi, enlem, boylam,
							markaAdi, markaFiyat);
					crmModule.CRMdbKayitGuncelle(id);
				} else {
					cvp = "Kayýtlarýn tamamý önceden gönderilmiþ.";
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			StokHareketListe = stokHareket.StokHareketBilgi();
			AsyncCallStokHareketYolla task=new AsyncCallStokHareketYolla();
			task.execute(StokHareketListe);
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

	public String CrmYolla(String id, String kodu, String cari_adi, String yetkili, String not, String sonuc,
			String tarih, String iliski_tipi, String cari_tipi, String enlem, String boylam, String markaAdi,
			String markaFiyat) {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		if (cari_tipi.equals("0")) {
			list = (ArrayList<HashMap<String, String>>) cariHesap.CariAdiSorgu(cari_adi);
			mikrokodu = list.get(0).get("id").toString();
			crmModule.crmKoduGüncelle(cari_adi, mikrokodu);
		} else {
			list = (ArrayList<HashMap<String, String>>) cariHesap.AdayCariSorgu(cari_adi);
			mikrokodu = list.get(0).get("id").toString();
			//
			// String[] unvani = new String[] ;
			crmModule.crmKoduGüncelle(cari_adi, mikrokodu);
		}

		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim/* 192.168.100.103:8081 */ + "/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/CRM";
			final String METHOD_NAME = "CRM";
			// String cvp="";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			// userID= dbAdapter.kullanici("kullanici");

			PropertyInfo kullaniciPI = new PropertyInfo();
			kullaniciPI.setName("Kullanici");
			kullaniciPI.setValue(userID);
			kullaniciPI.setType(String.class);
			request.addProperty(kullaniciPI);

			PropertyInfo cariIdPI = new PropertyInfo();
			cariIdPI.setName("Cari_id");
			cariIdPI.setValue(mikrokodu);
			cariIdPI.setType(String.class);
			request.addProperty(cariIdPI);

			PropertyInfo yetkiliPI = new PropertyInfo();
			yetkiliPI.setName("yetkili");
			yetkiliPI.setValue(yetkili);
			yetkiliPI.setType(String.class);
			request.addProperty(yetkiliPI);

			PropertyInfo notPI = new PropertyInfo();
			notPI.setName("noot");
			notPI.setValue(not);
			notPI.setType(String.class);
			request.addProperty(notPI);

			PropertyInfo sonucPI = new PropertyInfo();
			sonucPI.setName("sonuc");
			sonucPI.setValue(sonuc);
			sonucPI.setType(String.class);
			request.addProperty(sonucPI);

			PropertyInfo tarihPI = new PropertyInfo();
			tarihPI.setName("tarih");
			tarihPI.setValue(tarih);// tarih degeri duzeltilcek
			tarihPI.setType(String.class);
			request.addProperty(tarihPI);

			PropertyInfo iliski_tipiPI = new PropertyInfo();
			iliski_tipiPI.setName("iliski_tipi");
			iliski_tipiPI.setValue(iliski_tipi);
			iliski_tipiPI.setType(String.class);
			request.addProperty(iliski_tipiPI);

			PropertyInfo cari_tipiPI = new PropertyInfo();
			cari_tipiPI.setName("cari_tipi");
			cari_tipiPI.setValue(cari_tipi);
			cari_tipiPI.setType(String.class);
			request.addProperty(cari_tipiPI);

			PropertyInfo enlemPI = new PropertyInfo();
			enlemPI.setName("enlem");
			enlemPI.setValue(enlem);
			enlemPI.setType(String.class);
			request.addProperty(enlemPI);

			PropertyInfo boylamPI = new PropertyInfo();
			boylamPI.setName("boylam");
			boylamPI.setValue(boylam);
			boylamPI.setType(String.class);
			request.addProperty(boylamPI);

			PropertyInfo markaAdiPI = new PropertyInfo();
			markaAdiPI.setName("Makina_Model");
			markaAdiPI.setValue(markaAdi);
			markaAdiPI.setType(String.class);
			request.addProperty(markaAdiPI);

			PropertyInfo markaFiyatPI = new PropertyInfo();
			markaFiyatPI.setName("Makine_fiyat");
			markaFiyatPI.setValue(markaFiyat);
			markaFiyatPI.setType(String.class);
			request.addProperty(markaFiyatPI);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();

			} catch (Exception e) {
				e.printStackTrace();
				// tv.setText(e.toString());
			}
			cvp = "kayýt basarýlý";
		}
		return cvp;

	}
	String a ="";
	private class AsyncCallStokHareketYolla extends AsyncTask<List, Void, Void> {
		protected Void doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			// cvp=dbAdapter.StokEkleme();
			a = "0";
			for (int i = 0; i < StokHareketListe.size(); i++) {

				String faturaSeriNo = StokHareketListe.get(i).get("faturaSeriNo");
				String faturaNo = StokHareketListe.get(i).get("faturaNo");
				String cari_id = StokHareketListe.get(i).get("cari_id");
				String fat_toplam = StokHareketListe.get(i).get("toplam");
				String fat_toplamVergi = StokHareketListe.get(i).get("toplamVergi");
				String ozel = StokHareketListe.get(i).get("ozel");
				String tarih = StokHareketListe.get(i).get("tarih");
				String plasiyer = StokHareketListe.get(i).get("plasiyer");
				String stok_id = StokHareketListe.get(i).get("stok_id");
				String miktar = StokHareketListe.get(i).get("miktar");
				String birimFiyat = StokHareketListe.get(i).get("birimFiyat");
				String sh_dbkayit = StokHareketListe.get(i).get("sh_dbKayit");
				String fat_dbkayit = StokHareketListe.get(i).get("fat_dbKayit");
				String iskonto1 = StokHareketListe.get(i).get("iskonto1");
				String iskonto2 = StokHareketListe.get(i).get("iskonto2");
				String iskonto3 = StokHareketListe.get(i).get("iskonto3");
				String iskonto4 = StokHareketListe.get(i).get("iskonto4");
				String iskonto5 = StokHareketListe.get(i).get("iskonto5");
				String iskonto6 = StokHareketListe.get(i).get("iskonto6");
				String net_tutar = StokHareketListe.get(i).get("net_tutar");
				String tutar = StokHareketListe.get(i).get("tutar");
				String vergi_oran = StokHareketListe.get(i).get("vergi_oran");
				String vergi_tutar = StokHareketListe.get(i).get("vergi_tutar");
				String satirNo = StokHareketListe.get(i).get("satirNo");
				String fat_id = StokHareketListe.get(i).get("fat_id");
				String sh_id = StokHareketListe.get(i).get("sh_id");
				String fat_gtoplam = StokHareketListe.get(i).get("fat_toplam");
				// Toast.makeText(getApplicationContext(),
				// task.getStatus().toString(), Toast.LENGTH_LONG).show();
				if (sh_dbkayit.equals("0")) {
					cvp = StokHareketYolla(faturaSeriNo, faturaNo, satirNo, tarih, stok_id, cari_id, miktar, tutar,
							iskonto1, iskonto2, iskonto3, iskonto4, iskonto5, iskonto6, vergi_oran, vergi_tutar,
							plasiyer, net_tutar, a, fat_toplam, fat_toplamVergi, fat_gtoplam);
					stokHareket.StokHareketdbKayitGuncelle(fat_id, sh_id);
					a = "1";
				} else {
					cvp = "Kayýtlarýn tamamý önceden gönderilmiþ.";
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			siparisListesi=siparis.SiparisGetir();
			AsyncCallSiparisYolla task=new AsyncCallSiparisYolla();
			task.execute(siparisListesi);
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

	public String StokHareketYolla(String faturaSeri, String faturaNo, String satirNo, String tarih, String stok_id,
			String cari_id, String miktar, String tutar, String iskonto1, String iskonto2, String iskonto3,
			String iskonto4, String iskonto5, String iskonto6, String vergi_oran, String vergi_tutar, String plasiyer,
			String net_tutar, String fat_dbkayit, String fat_toplam, String fat_toplamVergi, String fat_gtoplam) {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim/* 192.168.100.103:8081 */ + "/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/stok_hareket";
			final String METHOD_NAME = "stok_hareket";
			// String cvp="";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			// userID= dbAdapter.kullanici("kullanici");

			PropertyInfo kullaniciPI = new PropertyInfo();
			kullaniciPI.setName("Kullanici");
			kullaniciPI.setValue(userID);
			kullaniciPI.setType(String.class);
			request.addProperty(kullaniciPI);

			PropertyInfo faturaSeriPI = new PropertyInfo();
			faturaSeriPI.setName("faturaSeri");
			faturaSeriPI.setValue(faturaSeri);
			faturaSeriPI.setType(String.class);
			request.addProperty(faturaSeriPI);

			PropertyInfo faturaNoPI = new PropertyInfo();
			faturaNoPI.setName("faturaNo");
			faturaNoPI.setValue(faturaNo);
			faturaNoPI.setType(String.class);
			request.addProperty(faturaNoPI);

			PropertyInfo satirNoPI = new PropertyInfo();
			satirNoPI.setName("satirNo");
			satirNoPI.setValue(satirNo);
			satirNoPI.setType(String.class);
			request.addProperty(satirNoPI);

			PropertyInfo tarihPI = new PropertyInfo();
			tarihPI.setName("tarih");
			tarihPI.setValue(tarih);// tarih degeri duzeltilcek
			tarihPI.setType(Date.class);
			request.addProperty(tarihPI);

			PropertyInfo stokPI = new PropertyInfo();
			stokPI.setName("stokKod");
			stokPI.setValue(stok_id);
			stokPI.setType(String.class);
			request.addProperty(stokPI);

			PropertyInfo cariIdPI = new PropertyInfo();
			cariIdPI.setName("cariID");
			cariIdPI.setValue(cari_id);
			cariIdPI.setType(String.class);
			request.addProperty(cariIdPI);

			PropertyInfo miktarPI = new PropertyInfo();
			miktarPI.setName("miktar");
			miktarPI.setValue(miktar);
			miktarPI.setType(String.class);
			request.addProperty(miktarPI);

			PropertyInfo tutarPI = new PropertyInfo();
			tutarPI.setName("sh_aratoplam");
			tutarPI.setValue(tutar);
			tutarPI.setType(String.class);
			request.addProperty(tutarPI);

			PropertyInfo iskonto1PI = new PropertyInfo();
			iskonto1PI.setName("isk1");
			iskonto1PI.setValue(iskonto1);
			iskonto1PI.setType(String.class);
			request.addProperty(iskonto1PI);

			PropertyInfo iskonto2PI = new PropertyInfo();
			iskonto2PI.setName("isk2");
			iskonto2PI.setValue(iskonto1);
			iskonto2PI.setType(String.class);
			request.addProperty(iskonto2PI);

			PropertyInfo iskonto3PI = new PropertyInfo();
			iskonto3PI.setName("isk3");
			iskonto3PI.setValue(iskonto3);
			iskonto3PI.setType(String.class);
			request.addProperty(iskonto3PI);

			PropertyInfo iskonto4PI = new PropertyInfo();
			iskonto4PI.setName("isk4");
			iskonto4PI.setValue(iskonto4);
			iskonto4PI.setType(String.class);
			request.addProperty(iskonto4PI);

			PropertyInfo iskonto5PI = new PropertyInfo();
			iskonto5PI.setName("isk5");
			iskonto5PI.setValue(iskonto5);
			iskonto5PI.setType(String.class);
			request.addProperty(iskonto5PI);

			PropertyInfo iskonto6PI = new PropertyInfo();
			iskonto6PI.setName("isk6");
			iskonto6PI.setValue(iskonto6);
			iskonto6PI.setType(String.class);
			request.addProperty(iskonto6PI);

			if (vergi_oran.equals("18")) {
				vergi_oran = "4";
			}
			else if (vergi_oran.equals("8")) {
				vergi_oran = "3";
			}
			else if (vergi_oran.equals("26")) {
				vergi_oran = "5";
			}
			else if (vergi_oran.equals("1")) {
				vergi_oran = "2";
			}
			else if (vergi_oran.equals("")) {
				vergi_oran = "1";
			}
			PropertyInfo vergiKoduPI = new PropertyInfo();
			vergiKoduPI.setName("vergiKodu");
			vergiKoduPI.setValue(vergi_oran);
			vergiKoduPI.setType(String.class);
			request.addProperty(vergiKoduPI);

			PropertyInfo vergiTutarPI = new PropertyInfo();
			vergiTutarPI.setName("sh_vergiTutar");
			vergiTutarPI.setValue(vergi_tutar);
			vergiTutarPI.setType(String.class);
			request.addProperty(vergiTutarPI);

			PropertyInfo plasiyerPI = new PropertyInfo();
			plasiyerPI.setName("plasiyer");
			plasiyerPI.setValue(plasiyer);
			plasiyerPI.setType(String.class);
			request.addProperty(plasiyerPI);

			PropertyInfo toplamPI = new PropertyInfo();
			toplamPI.setName("sh_toplam");
			toplamPI.setValue(net_tutar);
			toplamPI.setType(String.class);
			request.addProperty(toplamPI);

			PropertyInfo fat_dbKayitPI = new PropertyInfo();
			fat_dbKayitPI.setName("fat_dbkayit");
			fat_dbKayitPI.setValue(fat_dbkayit);
			fat_dbKayitPI.setType(String.class);
			request.addProperty(fat_dbKayitPI);

			PropertyInfo fat_aratoplamPI = new PropertyInfo();
			fat_aratoplamPI.setName("fat_aratoplam");
			fat_aratoplamPI.setValue(fat_toplam);
			fat_aratoplamPI.setType(String.class);
			request.addProperty(fat_aratoplamPI);

			PropertyInfo fat_vergiTutarPI = new PropertyInfo();
			fat_vergiTutarPI.setName("fat_vergiTutar");
			fat_vergiTutarPI.setValue(fat_toplamVergi);
			fat_vergiTutarPI.setType(String.class);
			request.addProperty(fat_vergiTutarPI);

			PropertyInfo fat_gtoplamPI = new PropertyInfo();
			fat_gtoplamPI.setName("fat_toplam");
			fat_gtoplamPI.setValue(fat_gtoplam);
			fat_gtoplamPI.setType(String.class);
			request.addProperty(fat_gtoplamPI);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();
				cvp = response.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				cvp = e.toString();
			}
		}
		return cvp;

	}

	
	
	public String chck(String param) {
		if (param.equals("anyType{}")) {
			param = "   ";
		}
		return param;
	}

	public void startTimer() {
		// set a new Timer
		timer = new Timer();

		// initialize the TimerTask's job
		initializeTimerTask();

		// schedule the timer, after the first 5000ms the TimerTask will run
		// every 10000ms
		timer.schedule(timerTask, 30000, 900000); //
	}

	public void stoptimertask(View v) {
		// stop the timer, if it's not already null
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void initializeTimerTask() {

		timerTask = new TimerTask() {
			public void run() {

				// use a handler to run a toast that shows the current timestamp
				handler.post(new Runnable() {
					public void run() {
						// String ipim=ip.getText().toString();
						AsyncCallStokWS task = new AsyncCallStokWS();
						task.execute();

						AsyncCallcariGetir task1 = new AsyncCallcariGetir();
						task1.execute();

						adayCariListe = cariHesap.AdayCariBilgi();
						AsyncCallAdayCariYolla adayCariYolla = new AsyncCallAdayCariYolla();
						adayCariYolla.execute(adayCariListe);

						AsyncCallAdayCari AdayCari = new AsyncCallAdayCari();
						AdayCari.execute();

						crm = crmModule.CrmBilgi();
						AsyncCallCRMYolla crmYolla = new AsyncCallCRMYolla();
						crmYolla.execute(crm);
					}
				});
			}
		};
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
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
	
	private class AsyncCallSiparisYolla extends AsyncTask<List, Void, Void> {
		protected Void doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			// cvp=dbAdapter.StokEkleme();
			
			for (int i = 0; i < siparisListesi.size(); i++) {
				String kod = siparisListesi.get(i).get("id");
				String belgeSeriNo = siparisListesi.get(i).get("belgeSeriNo");
				String belgeNo = siparisListesi.get(i).get("belgeNo");
				String siparisTarih = siparisListesi.get(i).get("siparisTarih");
				String teslimTarih = siparisListesi.get(i).get("teslimTarih");
				String stokKodu = siparisListesi.get(i).get("stokKodu");
				String bFiyat = siparisListesi.get(i).get("bFiyat");
				String miktar = siparisListesi.get(i).get("miktar");
				String aratoplam = siparisListesi.get(i).get("aratoplam");
				String vergiTutar = siparisListesi.get(i).get("vergiTutar");
				String vergikodu =siparisListesi.get(i).get("vergikodu");
				String iskonto1 =siparisListesi.get(i).get("iskonto1");
				String iskonto2 =siparisListesi.get(i).get("iskonto2");
				String iskonto3 =siparisListesi.get(i).get("iskonto3");
				String iskonto4 =siparisListesi.get(i).get("iskonto4");
				String iskonto5 =siparisListesi.get(i).get("iskonto5");
				String iskonto6 =siparisListesi.get(i).get("iskonto6");
				String CariID =siparisListesi.get(i).get("cariID");
				String satirNo =siparisListesi.get(i).get("satirNo");
				String dbKayit = siparisListesi.get(i).get("dbKayit");
				if (dbKayit.equals("0")) {
					cvp = siparisYolla(kod, belgeSeriNo, belgeNo, siparisTarih, teslimTarih, stokKodu, bFiyat, miktar, aratoplam, vergiTutar,vergikodu,iskonto1,iskonto2,iskonto3,iskonto4,iskonto5,iskonto6,CariID,satirNo);
				} else {
					cvp = "Gönderilecek yeni kayýt yok.";
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(cvp + " ");
			// AsyncCallAdayCari task = new AsyncCallAdayCari();
			// task.execute();
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

	public String siparisYolla(String kod, String belgeSeriNo, String belgeNo, String siparisTarih, String teslimTarih, String stokKodu,String bFiyat,String miktar, String aratoplam, String vergiTutar,String vergikodu,String iskonto1,String iskonto2,String iskonto3,String iskonto4,String iskonto5,String iskonto6,String CariID,String satirNo) {
		String ipim = ip.getText().toString();
		if (ipim.equals("")) {
			cvp = "Ip adresini kontrol ediniz.";
		} else {
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = "http://" + ipim + /* 192.168.100.103:8081 */"/android.asmx?WSDL";
			final String SOAP_ACTION = "http://tempuri.org/siparis";
			final String METHOD_NAME = "siparis";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			PropertyInfo kullaniciPI = new PropertyInfo();
			kullaniciPI.setName("Kullanici");
			kullaniciPI.setValue(userID);
			kullaniciPI.setType(String.class);
			request.addProperty(kullaniciPI);

			PropertyInfo siparisSeriPI = new PropertyInfo();
			siparisSeriPI.setName("siparisSeri");
			siparisSeriPI.setValue(belgeSeriNo);
			siparisSeriPI.setType(String.class);
			request.addProperty(siparisSeriPI);

			PropertyInfo belgeNoPI = new PropertyInfo();
			belgeNoPI.setName("siparisSiraNo");
			belgeNoPI.setValue(belgeNo);
			belgeNoPI.setType(String.class);
			request.addProperty(belgeNoPI);

			PropertyInfo satirNoPI = new PropertyInfo();
			satirNoPI.setName("satirNo");
			satirNoPI.setValue(satirNo);
			satirNoPI.setType(String.class);
			request.addProperty(satirNoPI);

			PropertyInfo siptarihPI = new PropertyInfo();
			siptarihPI.setName("siptarih");
			siptarihPI.setValue(siparisTarih);
			siptarihPI.setType(String.class);
			request.addProperty(siptarihPI);

			PropertyInfo tslmtrhPI = new PropertyInfo();
			tslmtrhPI.setName("tslmtrh");
			tslmtrhPI.setValue(teslimTarih);
			tslmtrhPI.setType(String.class);
			request.addProperty(tslmtrhPI);

			PropertyInfo stokKodPI = new PropertyInfo();
			stokKodPI.setName("stokKod");
			stokKodPI.setValue(stokKodu);
			stokKodPI.setType(String.class);
			request.addProperty(stokKodPI);

			PropertyInfo bFiyatPI = new PropertyInfo();
			bFiyatPI.setName("birimFiyat");
			bFiyatPI.setValue(bFiyat);
			bFiyatPI.setType(String.class);
			request.addProperty(bFiyatPI);

			PropertyInfo miktarPI = new PropertyInfo();
			miktarPI.setName("miktar");
			miktarPI.setValue(miktar);
			miktarPI.setType(String.class);
			request.addProperty(miktarPI);

			PropertyInfo aratoplamPI = new PropertyInfo();
			aratoplamPI.setName("aratoplam");
			aratoplamPI.setValue(aratoplam);
			aratoplamPI.setType(String.class);
			request.addProperty(aratoplamPI);

			PropertyInfo vergiTutarPI = new PropertyInfo();
			vergiTutarPI.setName("vergiTutar");
			vergiTutarPI.setValue(vergiTutar);
			vergiTutarPI.setType(String.class);
			request.addProperty(vergiTutarPI);
			
			PropertyInfo iskonto1PI = new PropertyInfo();
			iskonto1PI.setName("isk1");
			iskonto1PI.setValue(iskonto1);
			iskonto1PI.setType(String.class);
			request.addProperty(iskonto1PI);

			PropertyInfo iskonto2PI = new PropertyInfo();
			iskonto2PI.setName("isk2");
			iskonto2PI.setValue(iskonto1);
			iskonto2PI.setType(String.class);
			request.addProperty(iskonto2PI);

			PropertyInfo iskonto3PI = new PropertyInfo();
			iskonto3PI.setName("isk3");
			iskonto3PI.setValue(iskonto3);
			iskonto3PI.setType(String.class);
			request.addProperty(iskonto3PI);

			PropertyInfo iskonto4PI = new PropertyInfo();
			iskonto4PI.setName("isk4");
			iskonto4PI.setValue(iskonto4);
			iskonto4PI.setType(String.class);
			request.addProperty(iskonto4PI);

			PropertyInfo iskonto5PI = new PropertyInfo();
			iskonto5PI.setName("isk5");
			iskonto5PI.setValue(iskonto5);
			iskonto5PI.setType(String.class);
			request.addProperty(iskonto5PI);

			PropertyInfo iskonto6PI = new PropertyInfo();
			iskonto6PI.setName("isk6");
			iskonto6PI.setValue(iskonto6);
			iskonto6PI.setType(String.class);
			request.addProperty(iskonto6PI);

			if (vergikodu.equals("18")) {
				vergikodu = "4";
			}
			else if (vergikodu.equals("8")) {
				vergikodu = "3";
			}
			else if (vergikodu.equals("26")) {
				vergikodu = "5";
			}
			else if (vergikodu.equals("1")) {
				vergikodu = "2";
			}
			else if (vergikodu.equals("")) {
				vergikodu = "1";
			}
			PropertyInfo vergiKoduPI = new PropertyInfo();
			vergiKoduPI.setName("vergiKodu");
			vergiKoduPI.setValue(vergikodu);
			vergiKoduPI.setType(String.class);
			request.addProperty(vergiKoduPI);
			
			PropertyInfo cariIDPI = new PropertyInfo();
			cariIDPI.setName("cariID");
			cariIDPI.setValue(CariID);
			cariIDPI.setType(String.class);
			request.addProperty(cariIDPI);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject response = (SoapObject) envelope.getResponse();
				
				/*SoapObject list;
				list = (SoapObject) ((SoapObject) response.getProperty(1)).getProperty(0);
				int sayý = list.getPropertyCount();
				String id = "";
				for (int i = 0; i < sayý; i++) {
					SoapObject list2 = null;
					list2 = (SoapObject) list.getProperty(i);
					id = list2.getProperty("adaycr_kod").toString();
				}
				*/
				String dbKayit="1";
				siparis.siparisDbKayit(kod, dbKayit);//  (kod, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "kayýt basarýlý";
	}
}
