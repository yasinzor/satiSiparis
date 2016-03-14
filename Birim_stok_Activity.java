package com.ulku.ulkubilgisayar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

public class Birim_stok_Activity extends Activity implements OnKeyListener {
	EditText stokadi, miktar, birim, birim_fiyat, vergiOran, vergiTtr, toplam, iskonto, tutar, barkod, stokKodu;
	String stokid, adi, birimi, birimfiyati, vergi_oran, iskonto_oran, miktari, toplami, kodu;
	Kullanici kullanici;
	Button Biskonto, bekle, biptal;
	Double iskont1, iskont2, iskont3, iskont4, iskont5, iskont6, istoplam;
	double tutari, vergittr, tplm;
	
	Stok stok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_birim_stok_);
		
		stok = new Stok(this);
		stok.open();
		
		stokadi = (EditText) findViewById(R.id.stokadi);
		miktar = (EditText) findViewById(R.id.Miktar);
		birim = (EditText) findViewById(R.id.Birim);
		birim_fiyat = (EditText) findViewById(R.id.BirimFiyat);
		vergiOran = (EditText) findViewById(R.id.Vergi_oran);
		vergiTtr = (EditText) findViewById(R.id.VergiTutar);
		toplam = (EditText) findViewById(R.id.Toplam);
		barkod = (EditText) findViewById(R.id.barkod);
		tutar = (EditText) findViewById(R.id.Ttr);
		Biskonto = (Button) findViewById(R.id.kapat);
		bekle = (Button) findViewById(R.id.bekle);
		biptal = (Button) findViewById(R.id.biptal);
		stokKodu = (EditText) findViewById(R.id.stokKodu);

		stokadi.setOnKeyListener(this);
		miktar.setOnKeyListener(this);
		birim_fiyat.setOnKeyListener(this);
		vergiOran.setOnKeyListener(this);
		tutar.setOnKeyListener(this);
		vergiTtr.setOnKeyListener(this);
		toplam.setOnKeyListener(this);
		barkod.setOnKeyListener(this);

		Bundle recieveBundle = this.getIntent().getExtras();
		stokid = recieveBundle.getString("stokid");
		miktari = recieveBundle.getString("miktar");
		kodu = recieveBundle.getString("kodu");
		kullanici = new Kullanici(this);
		kullanici.open();

		main(stokid, miktari, kodu);

		Biskonto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle sendBundle = new Bundle();
				sendBundle.putDouble("toplam", tutari);
				Intent intent = new Intent(getApplicationContext(), IskontoPopUp.class);
				intent.putExtras(sendBundle);
				startActivityForResult(intent, 100);
			}
		});
		bekle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ekle();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == RESULT_OK) {
			tutari = data.getDoubleExtra("isToplam", 0);
			iskont1 = data.getDoubleExtra("iskont1", 0);
			iskont2 = data.getDoubleExtra("iskont2", 0);
			iskont3 = data.getDoubleExtra("iskont3", 0);
			iskont4 = data.getDoubleExtra("iskont4", 0);
			iskont5 = data.getDoubleExtra("iskont5", 0);
			iskont6 = data.getDoubleExtra("iskont6", 0);

			int miktara = Integer.parseInt(miktari);
			double bfiyat = Double.parseDouble(birimfiyati);
			bfiyat = tutari / miktara;
			
			String bfiat = duble(bfiyat);
			String tutara = duble(tutari);

			birim_fiyat.setText(bfiat);
			tutar.setText(tutara);
			vergittr = tutari * Double.parseDouble(vergi_oran) / 100;
			String verg = duble(vergittr);
			vergiTtr.setText(verg);
			tplm = tutari + vergittr;
			String total = duble(tplm);
			toplam.setText(total);
		}
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.barkod) {
					stokadi.requestFocus();
				}
				
				else if (id == R.id.stokadi) {
					adi = stokadi.getText().toString();
					createExampleDialog(adi);
					barkod.clearFocus();
					miktar.setFocusableInTouchMode(true);
					miktar.requestFocus();
					miktar.setSelectAllOnFocus(true);
					miktari = miktar.getText().toString();
					birimfiyati = birim_fiyat.getText().toString();
					
				}

				else if (id == R.id.Miktar) {
					
					miktari = miktar.getText().toString();
					if(!miktari.equals("")){
						miktar.clearFocus();
						birim_fiyat.setFocusableInTouchMode(true);
						birim_fiyat.requestFocus();
						birimfiyati = birim_fiyat.getText().toString();
						birim_fiyat.setSelectAllOnFocus(true);
						hesap();
					}
					else{
						Toast.makeText(getApplication(), "Miktar Girmediniz.", Toast.LENGTH_LONG).show();
					}
					
				} else if (id == R.id.BirimFiyat) {
					birim_fiyat.clearFocus();
					vergiOran.setFocusableInTouchMode(true);
					vergiOran.requestFocus();
					vergiOran.setSelectAllOnFocus(true);
					birimfiyati = birim_fiyat.getText().toString();
					miktari = miktar.getText().toString();
					hesap();
				} else if (id == R.id.Vergi_oran) {
					vergiOran.clearFocus();
					tutar.setFocusableInTouchMode(true);
					tutar.requestFocus();
					tutar.setSelectAllOnFocus(true);
					vergi_oran = vergiOran.getText().toString();
					hesap();
				} else if (id == R.id.Ttr) {
					tutar.clearFocus();
					vergiTtr.setFocusableInTouchMode(true);
					vergiTtr.requestFocus();
					vergiTtr.setSelectAllOnFocus(true);
					miktari = miktar.getText().toString();

					String ttari = tutar.getText().toString();
					
					float ttr = Float.parseFloat(ttari);
					int miktara = Integer.parseInt(miktari);
					float bfiyat;
					bfiyat = ttr / miktara;
					float vergioran = Float.parseFloat(vergi_oran);

					String dx = duble(bfiyat);

					birim_fiyat.setText(dx);
					String dx1 = duble(ttr);
					tutar.setText(dx1);
					vergittr = ttr * vergioran / 100;
					String dx2 = duble(vergittr);
					vergiTtr.setText(dx2);
					tplm = ttr + vergittr;
					String dx3 = duble(tplm);
					toplam.setText(dx3);
				} else if (id == R.id.VergiTutar) {
					vergiTtr.clearFocus();
					toplam.setFocusableInTouchMode(true);
					toplam.requestFocus();
					toplam.setSelectAllOnFocus(true);
				} else if (id == R.id.Toplam) {
					toplam.clearFocus();
					String tplmi = toplam.getText().toString();

					tersHesap();
				}
			}
			return true;

		}
		return false;
	}
	
	private ArrayList<String> createExampleDialog(String urun) {
		final Dialog dialog = new Dialog(Birim_stok_Activity.this);
		dialog.setContentView(R.layout.cariliste);
		dialog.setTitle("Ürün Seç");
		dialog.setCancelable(true);
		ArrayList<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		final ListView list = (ListView) dialog.findViewById(R.id.liste);
		liste = (ArrayList<HashMap<String, String>>) stok.stok(urun);
		SpecialAdapter adapter = new SpecialAdapter(this,liste,R.layout.stokliste,new String[] { "id", "adi","b_fiyat","stokSayisi" },new int[] { R.id.id, R.id.adi,R.id.StkSayisi,R.id.Bfiyat });
		
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> itemValue = (HashMap<String, String>) list.getItemAtPosition(position);
				// cariID = itemValue.get("id");
				adi = itemValue.get("adi");
				stokid = itemValue.get("id");
				String stokSayisi = itemValue.get("stokSayisi");
				
				stokadi.setText(adi);
				barkod.setText(stokid);
				main(stokid, miktari, kodu);

				dialog.dismiss();
			}
		});

		dialog.show();
		return null;

	}

	public void tersHesap() {
		toplami = toplam.getText().toString();
		miktari = miktar.getText().toString();
		vergi_oran = vergiOran.getText().toString();
		int miktara = Integer.parseInt(miktari);
		double total = Double.parseDouble(toplami);
		double vergioran = Double.parseDouble(vergi_oran);
		tutari = (total * 100) / (vergioran + 100);
		double bfiyat = Double.parseDouble(birimfiyati);
		bfiyat = tutari / miktara;
		Toast.makeText(getApplicationContext(), Double.toString(bfiyat), Toast.LENGTH_LONG).show();
		String dx = duble(tutari);
		String dx1 = duble(bfiyat);
		birim_fiyat.setText(dx1);// String.format("%.2f",bfiyat));
		tutar.setText(dx);// String.format("%.2f",tutari));
		vergittr = tutari * vergioran / 100;
		String dx2 = duble(vergittr);
		vergiTtr.setText(dx2);// String.format("%.2f",vergittr));
		tplm = tutari + vergittr;
		String dx3 = duble(tplm);
		toplam.setText(dx3);// String.format("%.2f",tplm));
	}

	public void hesap() {

		int miktara = Integer.parseInt(miktari);

		tutari = miktara * Double.parseDouble(birimfiyati);
		Toast.makeText(getApplicationContext(), Double.toString(tutari), Toast.LENGTH_LONG).show();

		String ttr = duble(tutari);

		tutar.setText(ttr);// Double.toString(tutari));//String.format("%.2f",tutari));
		vergittr = tutari * Double.parseDouble(vergi_oran) / 100;
		String dx1 = duble(vergittr);

		vergiTtr.setText(dx1);// Double.toString(vergittr));//(String.format("%.2f",vergittr));
		tplm = tutari + vergittr;
		String dx2 = duble(tplm);
		toplam.setText(dx2);// Double.toString(tplm));//(String.format("%.2f",tplm));

	}

	public Double iskonto(Double toplam, Double iskonto) {
		double toplami = toplam;
		Double temp = toplami * iskonto / 100;
		toplami = toplami - temp;
		return toplami;
	}

	public void ekle() {
		Intent intent = new Intent();
		intent.putExtra("id", stokid);
		intent.putExtra("kodu", kodu);
		intent.putExtra("adi", adi);
		intent.putExtra("miktar", miktari);
		intent.putExtra("toplam", Double.parseDouble(duble(tplm)));
		intent.putExtra("birim_fiyat", duble(Double.parseDouble(birimfiyati)));
		intent.putExtra("birim", birimi);
		intent.putExtra("vergi_tutar", Double.parseDouble(duble(vergittr)));
		intent.putExtra("vergi_oran", vergi_oran);
		intent.putExtra("tutar", tutari);
		intent.putExtra("iskont1", iskont1);
		intent.putExtra("iskont2", iskont2);
		intent.putExtra("iskont3", iskont3);
		intent.putExtra("iskont4", iskont4);
		intent.putExtra("iskont5", iskont5);
		intent.putExtra("iskont6", iskont6);
		// intent.putExtra("iskont6", iskont6);
		setResult(RESULT_OK, intent);
		finish();
	}

	public void main(String stokid, String miktari, String kod) {
		List<String> cevaplist = new ArrayList<String>();
		
		if(!stokid.equals(" ")){
		cevaplist = stok.stokBilgi(stokid);
			}
		else if (stokid.equals(" ")) {
			cevaplist = stok.stokBilgiKodu(kodu);
		}
		
		if (cevaplist.size() != 0) {
			stokid=cevaplist.get(0);
			kodu = cevaplist.get(1);
			adi = cevaplist.get(2);
			birimi = cevaplist.get(3);
			birimfiyati = cevaplist.get(4);
			String vergi_kod = cevaplist.get(5);
			if (vergi_kod.equals("4")) {
				vergi_oran = "18";
			} else if (vergi_kod.equals("3")) {
				vergi_oran = "8";
			} else if (vergi_kod.equals("5")) {
				vergi_oran = "26";
			}
			else if (vergi_kod.equals("2")) {
				vergi_oran = "1";
			}
			else if (vergi_kod.equals("1")) {
				vergi_oran = "0";
			}
			iskonto_oran = cevaplist.get(6);
			double bfiyat = Double.parseDouble(birimfiyati);
			String bfiat = duble(bfiyat);
			barkod.setText(stokid);
			stokadi.setText(adi);
			stokKodu.setText(kodu);
			birim.setText(birimi);
			birim_fiyat.setText(bfiat);
			vergiOran.setText(vergi_oran);
			miktar.setText(miktari);
			
		}
	}

	public String duble(double duble) {
		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(symbols);
		String cvp = df.format(duble);
		return cvp;
	}

}
