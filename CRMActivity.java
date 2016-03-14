package com.ulku.ulkubilgisayar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

public class CRMActivity extends Activity implements OnKeyListener {
	Spinner spinnerTip, spinnerCari, spinnerYetkili, spinnerSonuc, spinnerGorusme;
	
	CariHesap cariHesap;
	CrmModule crmModule;
	
	AutoCompleteTextView cariText, yetkiliText;
	String Cari, yetkiliAdi, sonuc, gorusme, cari_tip, iliski_tipi, kodu_Aday_cari, enlem, boylam,marka_adi,marka_fiyat;
	List<HashMap<String, String>> cariEski;
	// ArrayList<String> cariAday;
	EditText not,markaAdi,markaFiyat;
	Button kayit, iptal;

	// ArrayList<String> tipListe,cariListe,YetkiliListe,SonucListe;
	private String[] tipListe = { "Müsteri Profili Seçiniz", "Eski Müsteri(MÝF)", "Yeni Müþteri", "Aday Müþteri" };
	private String[] sonucListe = { "Sonuç Seçiniz", "Olumlu", "Olumsuz", "Ertelendi" };
	private String[] gorusmeListe = { "Görüsme Þeklini Seçiniz", "Telefon", "Ziyaret", "E-posta", "Sms", "Karþýlaþma" };

	ArrayList<String> cariIsimListe = new ArrayList<String>();
	ArrayList<String> cariYetkiliListe = new ArrayList<String>();
	ArrayList<String> cariAday = new ArrayList<String>();
	private ArrayAdapter<String> dataAdapterForTip;
	private ArrayAdapter<String> dataAdapterForCari;
	private ArrayAdapter<String> dataAdapterForYetkili;
	private ArrayAdapter<String> dataAdapterForGorusme;
	private ArrayAdapter<String> dataAdapterForSonuc;
	// ArrayList<String > cariYetkiliListe;
	ArrayList<HashMap<String, String>> yetkiliListe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crm);
		not = (EditText) findViewById(R.id.Not);
		kayit = (Button) findViewById(R.id.kayit);
		iptal = (Button) findViewById(R.id.iptal);
		spinnerGorusme = (Spinner) findViewById(R.id.gorusme);

		cariHesap =new CariHesap(this);
		cariHesap.open();
		
		crmModule=new CrmModule(this);
		crmModule.open();
		
		cariEski = cariHesap.CariAdiSorgu("");
		List<HashMap<String, String>> cariaday = cariHesap.AdayCariSorgu("");
		for (int i = 0; cariaday.size() > i; i++) {

			String Adi = cariaday.get(i).get("adi");
			cariAday.add(Adi);
			//Toast.makeText(getBaseContext(), cariAday.toString(), Toast.LENGTH_LONG).show();
		}

		yetkiliListe = new ArrayList<HashMap<String, String>>();
		konum();

		for (int i = 0; cariEski.size() > i; i++) {

			String Adi = cariEski.get(i).get("adi");
			cariIsimListe.add(Adi);
		}
		final ArrayAdapter EskiCariAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,cariIsimListe);
		final ArrayAdapter AdayCariAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,cariAday);

		// Toast.makeText(getBaseContext(), Adi, Toast.LENGTH_LONG).show();
		spinnerTip = (Spinner) findViewById(R.id.tip);
		markaAdi = (EditText) findViewById(R.id.markaadi);
		markaFiyat=(EditText) findViewById(R.id.markaFiyat);
		// spinnerCari = (Spinner)findViewById(R.id.cari);
		yetkiliText = (AutoCompleteTextView) findViewById(R.id.yetkili);
		spinnerSonuc = (Spinner) findViewById(R.id.Sonuc);
		cariText = (AutoCompleteTextView) findViewById(R.id.cariAutoAra);
		cariText.setOnKeyListener(this);
		// Spinner'lar için adapterleri hazýrlýyoruz.
		dataAdapterForTip = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipListe);
		dataAdapterForSonuc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sonucListe);
		dataAdapterForGorusme = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gorusmeListe);

		// Listelenecek verilerin görünümünü belirliyoruz.
		dataAdapterForTip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapterForSonuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapterForGorusme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Hazýrladðýmýz Adapter'leri Spinner'lara ekliyoruz.
		spinnerTip.setAdapter(dataAdapterForTip);
		spinnerSonuc.setAdapter(dataAdapterForSonuc);
		spinnerGorusme.setAdapter(dataAdapterForGorusme);

		spinnerTip.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (parent.getSelectedItem().toString().equals(tipListe[1])) {
					cariText.setAdapter(EskiCariAdapter);
					cariText.requestFocus();
					cari_tip = "0";
				} else if (parent.getSelectedItem().toString().equals(tipListe[2])) {
					Intent intent = new Intent(CRMActivity.this, CariActivity.class);
					startActivity(intent);
					// Toast.makeText(getApplicationContext(), "2",
					// Toast.LENGTH_LONG).show();
				} // dataAdapterForIlceler = new
					// ArrayAdapter<String>(MainActivity.this,
					// android.R.layout.simple_spinner_item,ilceler1);
				else if (parent.getSelectedItem().toString().equals(tipListe[3])) {
					cariText.setAdapter(AdayCariAdapter);
					cari_tip = "1";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		spinnerSonuc.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (parent.getSelectedItem().toString().equals(sonucListe[0])) {
					Toast.makeText(getApplication(), "Sonuc Tipini Seçiniz", Toast.LENGTH_LONG).show();
				} else {
					sonuc = parent.getSelectedItem().toString();
					//Toast.makeText(getApplication(), sonuc, Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		spinnerGorusme.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (parent.getSelectedItem().toString().equals(gorusmeListe[0])) {
					Toast.makeText(getApplication(), "Görüþme Þeklini Seçiniz", Toast.LENGTH_LONG).show();
					iliski_tipi = null;
				} else if (parent.getSelectedItem().toString().equals(gorusmeListe[1])) {
					iliski_tipi = "0";
				} else if (parent.getSelectedItem().toString().equals(gorusmeListe[2])) {
					iliski_tipi = "4";
				} else if (parent.getSelectedItem().toString().equals(gorusmeListe[3])) {
					iliski_tipi = "1";
				} else if (parent.getSelectedItem().toString().equals(gorusmeListe[4])) {
					iliski_tipi = "10";
				} else if (parent.getSelectedItem().toString().equals(gorusmeListe[5])) {
					iliski_tipi = "7";
				} else {
					gorusme = parent.getSelectedItem().toString();
					Toast.makeText(getApplication(), gorusme, Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
		/*
		harita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CRMActivity.this, MapActivity.class);
				startActivity(intent);
			}
		});
		*/
		kayit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm ");

				String unvan = cariText.getText().toString();
				// String kodu=dbAdapter.KEY_PASSWORD;
				String yetkili = yetkiliText.getText().toString();
				String noot = not.getText().toString();
				marka_adi = markaAdi.getText().toString();
				marka_fiyat=markaFiyat.getText().toString();
				// String sonuc=spinnerSonuc.getSelectedItem().toString();
				// String gorusme=spinnerGorusme.getSelectedItem().toString();
				if (iliski_tipi == null || sonuc == null) {
					Toast.makeText(getApplication(), "Tam doldurunuz", Toast.LENGTH_LONG).show();
				} else {
					String tarih = df.format(c.getTime());
					if (cari_tip.equals("1")) {
						List<HashMap<String, String>> cariaday = cariHesap.AdayCariSorgu(unvan);
						for (int i = 0; cariaday.size() > i; i++) {
							kodu_Aday_cari = cariaday.get(i).get("id");
						}
					} else if (cari_tip.equals("0")) {
						List<HashMap<String, String>> cariaday = cariHesap.CariAdiSorgu(unvan);
						for (int i = 0; cariaday.size() > i; i++) {
							kodu_Aday_cari = cariaday.get(i).get("id");
						}
					}
					if (enlem == null || boylam == null) {
						enlem = " ";
						boylam = " ";
						unvan=unvan.trim();
						crmModule.crmEkleme(kodu_Aday_cari, unvan, yetkili, noot, sonuc, tarih, iliski_tipi, cari_tip,
								enlem, boylam, "0",marka_adi,marka_fiyat);
						finish();
					} else {
						crmModule.crmEkleme(kodu_Aday_cari, unvan, yetkili, noot, sonuc, tarih, iliski_tipi, cari_tip,
								enlem, boylam, "0",marka_adi,marka_fiyat);
						finish();
					}
				}
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

	private ArrayList<String> createExampleDialog(String CariAdim) {
		final Dialog dialog = new Dialog(CRMActivity.this);
		dialog.setContentView(R.layout.cariliste);
		dialog.setTitle("Cari Seç");
		dialog.setCancelable(true);
		ArrayList<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		final ListView list = (ListView) dialog.findViewById(R.id.liste);
		liste = (ArrayList<HashMap<String, String>>) cariHesap.CariAdiSorgu(CariAdim);
		// Toast.makeText(this, liste.toString(), Toast.LENGTH_LONG).show();
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, liste, R.layout.cliste, new String[] { "id", "adi" },
				new int[] { R.id.id, R.id.adi });
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, android.R.id.text1, liste);
		list.setAdapter(simpleAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> itemValue = (HashMap<String, String>) list.getItemAtPosition(position);
				String cariID = itemValue.get("id");
				String CariAdi = itemValue.get("adi");
				// Toast.makeText(getApplicationContext(),
				// "Position :"+itemPosition+" ListItem : " +itemValue ,
				// Toast.LENGTH_LONG)
				// .show();
				// cariId.setText(cariID);
				// cariAdi.setText(CariAdi);

				dialog.dismiss();
			}
		});

		dialog.show();
		return null;

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.cariAutoAra) {
					cariText.clearFocus();
					yetkiliText.requestFocus();
					Cari = cariText.getText().toString();
					yetkiliListe = cariHesap.cariYetkili(Cari);
					for (int i = 0; yetkiliListe.size() > i; i++) {

						String yetkili1 = yetkiliListe.get(i).get("yetkili1");
						String yetkili2 = yetkiliListe.get(i).get("yetkili2");
						cariYetkiliListe.add(yetkili1);
						cariYetkiliListe.add(yetkili2);
					}
					ArrayAdapter CariYetkiliAdapter = new ArrayAdapter(CRMActivity.this,
							android.R.layout.simple_dropdown_item_1line, cariYetkiliListe);
					yetkiliText.setAdapter(CariYetkiliAdapter);
				}

			}
			return true;
		}
		return false;
	}

	public void konum() {
		final String gpsAcildi = "GPS Acýldý";
		final String gpsKapatildi = "GPS Kapalý";

		final TextView konumText = (TextView) findViewById(R.id.konum);

		LocationManager konumYoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		LocationListener konumDinleyicisi = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {
				Toast.makeText(getApplicationContext(), gpsAcildi, Toast.LENGTH_SHORT).show();
				konumText.setText("GPS Veri bilgileri Alýnýyor...");
			}

			@Override
			public void onProviderDisabled(String provider) {
				Toast.makeText(getApplicationContext(), gpsKapatildi, Toast.LENGTH_SHORT).show();
				konumText.setText("GPS Baðlantý Bekleniyor...");
			}

			@Override
			public void onLocationChanged(Location loc) {
				enlem = Double.toString(loc.getLatitude());
				boylam = Double.toString(loc.getLongitude());
				String Text = "Bulunduðunuz konum bilgileri : \n" + "Latitud = " + loc.getLatitude() + "\nLongitud = "
						+ loc.getLongitude();

				konumText.setText(Text);
				// dbAdapter.konumDb(ID, enlem, boylam);
			}
		};

		konumYoneticisi.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, konumDinleyicisi);
	}
}
