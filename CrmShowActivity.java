package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CrmShowActivity extends Activity {
	
	CariHesap cariHesap;
	CrmModule crmModule;
	
	Spinner spinnerTip;
	AutoCompleteTextView cariText;
	ListView crmList;
	List<HashMap<String, String>> cariEski;
	ArrayList<String> cariAday = new ArrayList<String>();
	ArrayList<String> cariIsimListe = new ArrayList<String>();
	Button listele;
	
	private ArrayAdapter<String> dataAdapterForTip;	
	private ArrayAdapter<String> dataAdapterForCari;

	private String[] tipListe = { "Müsteri Profili Seçiniz", "Eski Müsteri(MÝF)", "Aday Müþteri" };

	String cari_tip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crm_show);
		
		
		cariHesap= new CariHesap(this);
		cariHesap.open();
		
		crmModule=new CrmModule(this);
		crmModule.open();
		
		listele = (Button) findViewById(R.id.listele);
		
		cariText = (AutoCompleteTextView) findViewById(R.id.cariAutoAra);
		
		spinnerTip = (Spinner) findViewById(R.id.tip);
		dataAdapterForTip = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipListe);
		dataAdapterForTip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTip.setAdapter(dataAdapterForTip);
		
		crmList = (ListView) findViewById(R.id.crmList);
		
		cariEski = cariHesap.CariAdiSorgu("");
		for (int i = 0; cariEski.size() > i; i++) {

			String Adi = cariEski.get(i).get("adi");
			cariIsimListe.add(Adi);
		}
		
		List<HashMap<String, String>> cariaday = cariHesap.AdayCariSorgu("");
		for (int i = 0; cariaday.size() > i; i++) {

			String Adi = cariaday.get(i).get("adi");
			cariAday.add(Adi);
			//Toast.makeText(getBaseContext(), cariAday.toString(), Toast.LENGTH_LONG).show();
		}
		final ArrayAdapter EskiCariAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,cariIsimListe);
		final ArrayAdapter AdayCariAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,cariAday);

		spinnerTip.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (parent.getSelectedItem().toString().equals(tipListe[1])) {
					cariText.setAdapter(EskiCariAdapter);
					cariText.requestFocus();
					cari_tip = "0";
				}  
				else if (parent.getSelectedItem().toString().equals(tipListe[2])) {
					cariText.setAdapter(AdayCariAdapter);
					cari_tip = "1";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		listele.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				String cari = cariText.getText().toString();
				cari=cari.trim();
				list = crmModule.CrmBilgiCari(cari);
				for(int i =0;list.size()>i;i++){
				SimpleAdapter simpleAdapter = new SimpleAdapter(CrmShowActivity.this, list, R.layout.cliste, new String[] { "id","cari_id", "tarih" },new int[] { R.id.idi,R.id.id, R.id.adi });
				crmList.setAdapter(simpleAdapter);
				}//list = dbAdapter.CrmBilgi();	
			}
		});
		
		crmList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> itemValue = (HashMap<String, String>) crmList.getItemAtPosition(position);
				String cariID = itemValue.get("id");
				String carikodu = itemValue.get("kodu");
				String adi = itemValue.get("cari_id");
				String yetkili = itemValue.get("yetkili");
				String noot = itemValue.get("not");
				String sonuc = itemValue.get("sonuc");
				String tarih = itemValue.get("tarih");
				String iliski_tipi = itemValue.get("iliski_tipi");
				String cari_tipi = itemValue.get("cari_tipi");
				String enlem = itemValue.get("enlem");
				String boylam = itemValue.get("boylam");
				String markaAdi = itemValue.get("markaadi");
				String markaFiyat = itemValue.get("markafiyat");
				 Toast.makeText(getApplicationContext(),
				 "Position :"+position+" ListItem : " +itemValue ,
				 Toast.LENGTH_LONG)
				 .show();
				 Bundle sendBundle =new Bundle();
				 sendBundle.putString("id", cariID);
				 sendBundle.putString("kodu", carikodu);
				 sendBundle.putString("adi", adi);
				 sendBundle.putString("yetkili", yetkili);
				 sendBundle.putString("noot", noot);
				 sendBundle.putString("sonuc", sonuc);
				 sendBundle.putString("tarih", tarih);
				 sendBundle.putString("iliski_tipi", iliski_tipi);
				 sendBundle.putString("cari_tipi", cari_tipi);
				 sendBundle.putString("enlem", enlem);
				 sendBundle.putString("boylam", boylam);
				 sendBundle.putString("markaAdi", markaAdi);
				 sendBundle.putString("markaFiyat", markaFiyat);
				 Intent intent = new Intent(CrmShowActivity.this,CrmDetayMainActivity.class);
				 intent.putExtras(sendBundle);
				 startActivity(intent);
				// cariId.setText(cariID);
				// cariAdi.setText(CariAdi);

			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crm_show, menu);
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
