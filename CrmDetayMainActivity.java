package com.ulku.ulkubilgisayar;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CrmDetayMainActivity extends Activity {

	Spinner cariTip,gorusme,sonuc;
	AutoCompleteTextView cari,yetkili;
	EditText not,markaadi,markaFiyat,tarih;
	TextView konum;
	Button iptal;
	
	private String[] tipListe = { "Müsteri Profili Seçiniz", "Eski Müsteri(MÝF)", "Aday Müþteri" };
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crm_detay_main);
		
		cariTip=(Spinner) findViewById(R.id.tip);
		cari =(AutoCompleteTextView) findViewById(R.id.cariAutoAra);
		yetkili=(AutoCompleteTextView) findViewById(R.id.yetkili);
		gorusme=(Spinner) findViewById(R.id.gorusme);
		not =(EditText)findViewById(R.id.Not);
		markaadi = (EditText)findViewById(R.id.markaadi);
		markaFiyat =(EditText)findViewById(R.id.markaFiyat);
		sonuc = (Spinner)findViewById(R.id.Sonuc);
		konum =(TextView)findViewById(R.id.konum);
		iptal =(Button)findViewById(R.id.iptal);
		tarih =(EditText)findViewById(R.id.tarih);
		
		dataAdapterForTip = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipListe);
		dataAdapterForSonuc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sonucListe);
		dataAdapterForGorusme = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gorusmeListe);

		// Listelenecek verilerin görünümünü belirliyoruz.
		dataAdapterForTip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapterForSonuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapterForGorusme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Hazýrladðýmýz Adapter'leri Spinner'lara ekliyoruz.
		cariTip.setAdapter(dataAdapterForTip);
		sonuc.setAdapter(dataAdapterForSonuc);
		gorusme.setAdapter(dataAdapterForGorusme);
		
		Bundle recieveBundle = this.getIntent().getExtras();
		final String id = recieveBundle.getString("id");
		final String kodu = recieveBundle.getString("kodu");
		final String adi = recieveBundle.getString("adi");
		final String yetkiliS = recieveBundle.getString("yetkili");
		final String noot = recieveBundle.getString("noot");
		String sonuc1 = recieveBundle.getString("sonuc");
		final String tarihi = recieveBundle.getString("tarih");
		final String iliski_tipi = recieveBundle.getString("iliski_tipi");
		final String cari_tipi = recieveBundle.getString("cari_tipi");
		final String enlem = recieveBundle.getString("enlem");
		final String boylam = recieveBundle.getString("boylam");
		final String markaAdi = recieveBundle.getString("markaAdi");
		final String markaFiyat = recieveBundle.getString("markaFiyat");
		
		cari.setText(adi);
		yetkili.setText(yetkiliS);
		not.setText(noot);
		markaadi.setText(markaAdi);
		this.markaFiyat.setText(markaFiyat);
		tarih.setText(tarihi);
		konum.setText("Enlem : " + enlem + "\nBoylam : "+boylam );
		if(cari_tipi.equals("1")){
			cariTip.setSelection(2);
		}
		else if(cari_tipi.equals("0")){
			cariTip.setSelection(1);
		}
		
		
		if(iliski_tipi.equals("0")){
			gorusme.setSelection(1);		
			}
		else if(iliski_tipi.equals("4")){
			gorusme.setSelection(2);		
			}
		else if(iliski_tipi.equals("1")){
			gorusme.setSelection(3);		
			}
		else if(iliski_tipi.equals("10")){
			gorusme.setSelection(4);		
			}
		else if(iliski_tipi.equals("7")){
			gorusme.setSelection(5);		
			}
		
		
		sonuc1=sonuc1.trim();
		if(sonuc1.equals("Olumlu")){
			sonuc.setSelection(1);
		}
		else if(sonuc1.equals("Olumsuz")){
			sonuc.setSelection(2);
		}
		else if(sonuc1.equals("Ertelendi")){
			sonuc.setSelection(3);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crm_detay_main, menu);
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
