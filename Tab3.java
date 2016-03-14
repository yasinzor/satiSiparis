package com.ulku.ulkubilgisayar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.ads.internal.request.StringParcel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3 extends Activity {
	TextView tutar, kdv, net_tutar;
	Button bkayit, bguncel;
	String cariid, dovizCins, tarih, iskont1, iskont2, iskont3, iskont4, iskont5, iskont6, iskonto_toplam, toplamVergi,
			faturaId;
	Stok stok;
	StokHareket stokHareket;
	Fatura fatura;
	
	Double net_tutr;
	ListView lvi;
	HashMap<String, String> map;
	ArrayList<HashMap<String, String>> feedList;
	ArrayList<String> cvp = new ArrayList<String>();

	double istop = 0; // Double.parseDouble(iskonto_toplam);
	double topVergi = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab3);
		main();
	}

	public void main() {
		final ListView lv = (ListView) findViewById(R.id.lv);
		lv.setClickable(true);
		tutar = (TextView) findViewById(R.id.tutr);
		kdv = (TextView) findViewById(R.id.kdv);
		net_tutar = (TextView) findViewById(R.id.net_ttr);
		bkayit = (Button) findViewById(R.id.bkayt);
		bguncel = (Button) findViewById(R.id.bguncel);
		
		stok = new Stok(this);
		stok.open();
		
		stokHareket=new StokHareket(this);
		stokHareket.open();
		
		fatura =new Fatura(this);
		fatura.open();
		
		feedList = (ArrayList<HashMap<String, String>>) getParent().getIntent().getSerializableExtra("list");

		if (feedList == null || feedList.equals("")) {
			Toast.makeText(getApplicationContext(), "Ürün Girmeden Fatura Tutar Görülemez.", Toast.LENGTH_LONG).show();
		} else {
			tutar();
			final SimpleAdapter simpleAdapter = new SimpleAdapter(this, feedList, R.layout.tab2,
					new String[] { "kodu", "adi", "miktar", "birimfi", "tutar", "vergiTutar", "total" },
					new int[] { R.id.stokKodu, R.id.stokAdi, R.id.miktar, R.id.birim_fiyat, R.id.Tutar, R.id.VergiTutar,
							R.id.Total });

			lv.setAdapter(simpleAdapter);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					//HashMap<String, String> o = (HashMap<String, String>) simpleAdapter.getItem(position);
					//int pos = lv.getSelectedItemPosition();
					createDialog(position);
				}
			});
		}

		bguncel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tutar();
				feedList = (ArrayList<HashMap<String, String>>) getParent().getIntent().getSerializableExtra("list");
				SimpleAdapter simpleAdapter = new SimpleAdapter(Tab3.this, feedList, R.layout.tab2,
						new String[] { "kodu", "adi", "miktar", "birimfi", "tutar", "vergiTutar", "total" },
						new int[] { R.id.stokKodu, R.id.stokAdi, R.id.miktar, R.id.birim_fiyat, R.id.Tutar,
								R.id.VergiTutar, R.id.Total });
				lv.setAdapter(simpleAdapter);
			}
		});
		tutar();

		bkayit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int lng = feedList.size() - 1;
				iskont1 = getParent().getIntent().getStringExtra("iskont1");
				iskont2 = getParent().getIntent().getStringExtra("iskont2");
				iskont3 = getParent().getIntent().getStringExtra("iskont3");
				iskont4 = getParent().getIntent().getStringExtra("iskont4");
				iskont5 = getParent().getIntent().getStringExtra("iskont5");
				iskont6 = getParent().getIntent().getStringExtra("iskont6");
				tarih = getParent().getIntent().getStringExtra("tarih");
				cariid = getParent().getIntent().getStringExtra("cariid");

				String fseri = getParent().getIntent().getStringExtra("faturaSeri");
				String fno = getParent().getIntent().getStringExtra("fno");
				String ozelkodu = getParent().getIntent().getStringExtra("ozelKodu");
				String plasiyer = getParent().getIntent().getStringExtra("plasiyeri");

				if (tarih == null || cariid == null || fseri.equals("") || fno.equals("")) {
					Toast.makeText(getApplicationContext(), "Fatura oluþturulmadý. Kontrol Ediniz.", Toast.LENGTH_LONG)
							.show();
				} else {

					dovizCins = getParent().getIntent().getStringExtra("dovizCins");

					String faturaid = fatura.faturaKayit(fseri, fno, tarih, ozelkodu, plasiyer, cariid);
					faturaId = faturaid;
					for (int i = 1; i <= lng; i++) {

						String kodu = feedList.get(i).get("kodu").toString();
						String adi = feedList.get(i).get("adi").toString();
						String miktar = feedList.get(i).get("miktar").toString();
						String birimfi = feedList.get(i).get("birimfi").toString();
						String VergiOran = feedList.get(i).get("vergiOran").toString();
						String vergi = feedList.get(i).get("vergiTutar").toString();
						String tutar = feedList.get(i).get("tutar").toString();
						String iskont1 = feedList.get(i).get("iskont1").toString();
						String iskont2 = feedList.get(i).get("iskont2").toString();
						String iskont3 = feedList.get(i).get("iskont3").toString();
						String iskont4 = feedList.get(i).get("iskont4").toString();
						String iskont5 = feedList.get(i).get("iskont5").toString();
						String iskont6 = feedList.get(i).get("iskont6").toString();

						double net_tutr = Double.parseDouble(tutar) + Double.parseDouble(vergi);
						String cvp = stokHareket.stokHareketKayit(kodu, miktar, birimfi, VergiOran, tarih, iskont1, iskont2,
								iskont3, iskont4, iskont5, iskont6, tutar, vergi, Double.toString(net_tutr), faturaId,
								cariid, i - 1);
						String stokSayisi = stok.getStokSayisi(adi);
						int no = Integer.parseInt(stokSayisi);
						int MiktarNo = Integer.parseInt(miktar);
						no = no - MiktarNo;
						cvp = stok.stokSayisiGuncel(adi, Integer.toString(no));
					}

					fatura.faturaTutar(Double.toString(istop), Double.toString(topVergi),
							Double.toString(net_tutr), faturaId);
					finish();
				}
			}
		});
	}

	private void tutar() {
		istop = 0;
		topVergi = 0;

		feedList = (ArrayList<HashMap<String, String>>) getParent().getIntent().getSerializableExtra("list");
		if (feedList == null || feedList.equals("")) {
			Toast.makeText(getApplicationContext(), "Ürün Girmeden Fatura Tutar Görülemez.", Toast.LENGTH_LONG).show();
		} else {
			for (int i = 1; i <= feedList.size() - 1; i++) {
				String istoplam = feedList.get(i).get("tutar").toString();
				double istoplami = Double.parseDouble(istoplam);
				String vergi = feedList.get(i).get("vergiTutar").toString();
				double vergii = Double.parseDouble(vergi);

				istop = istop + istoplami;
				topVergi = topVergi + vergii;
			}

			net_tutr = istop + topVergi;
			tutar.setText(String.format("%.2f", istop));
			kdv.setText(String.format("%.2f", topVergi));
			net_tutar.setText(String.format("%.2f", net_tutr));
		}
	}

	// @SuppressWarnings("deprecation")
	public void onClick(View v) {
		TextView kodu = (TextView) v.findViewById(R.id.stokKodu);
		String kod = kodu.getText().toString().trim();

	}

	private ArrayList<String> createDialog(final int position) {
		final Dialog dialog = new Dialog(Tab3.this);
		dialog.setContentView(R.layout.prompts);
		final ArrayList<String> liste = new ArrayList<String>();
		final EditText miktar = (EditText) dialog.findViewById(R.id.miktar);
		final EditText bfiyat = (EditText) dialog.findViewById(R.id.bfiyat);
		final EditText total = (EditText) dialog.findViewById(R.id.total);

		miktar.setInputType(InputType.TYPE_NULL);
		bfiyat.setInputType(InputType.TYPE_NULL);
		total.setInputType(InputType.TYPE_NULL);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		Button ok = (Button) dialog.findViewById(R.id.ok);
		Button iptal = (Button) dialog.findViewById(R.id.iptal);
		dialog.setTitle("Düzenleme Yap");
		dialog.setCancelable(true);
		ok.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String miktari = miktar.getText().toString();
				String totali = total.getText().toString();
				String bfiyati = bfiyat.getText().toString();

				liste.add(0, bfiyati);
				liste.add(1, miktari);
				liste.add(2, totali);
				duzenle(position, miktari, bfiyati, totali);

				dialog.dismiss();
			}
		});
		iptal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialog.show();
		return liste;

	}

	public void duzenle(Integer kod, String miktari, String bfiyati, String totali) {
		ArrayList<String> liste = new ArrayList<String>();

		int i = kod;
		String VergiOran = feedList.get(i).get("vergiOran").toString();
		if (!"".equals(bfiyati)) {
			feedList.get(i).put("birimfi", bfiyati);
			String miktr = feedList.get(i).get("miktar").toString();
			liste = duzenleHesap(miktr, bfiyati, VergiOran);
			String ttr = liste.get(0).toString();
			String vergi = liste.get(1).toString();
			String toplami = liste.get(2).toString();
			feedList.get(i).put("vergiTutar", vergi);
			feedList.get(i).put("tutar", ttr);
			feedList.get(i).put("total", toplami);
		}
		if (!"".equals(miktari)) {
			feedList.get(i).put("miktar", miktari);
			String Bfiyat = feedList.get(i).get("birimfi").toString();
			liste = duzenleHesap(miktari, Bfiyat, VergiOran);
			String ttr = liste.get(0).toString();
			String vergi = liste.get(1).toString();
			String toplami = liste.get(2).toString();
			feedList.get(i).put("vergiTutar", vergi);
			feedList.get(i).put("tutar", ttr);
			feedList.get(i).put("total", toplami);
		}
		if (!"".equals(totali)) {
			feedList.get(i).put("total", totali);
			String miktr = feedList.get(i).get("miktar").toString();
			int miktara = Integer.parseInt(miktr);
			double total = Double.parseDouble(totali);
			double vergioran = Double.parseDouble(VergiOran);
			double tutari = (total * 100) / (vergioran + 100);
			double bfiyat = tutari / miktara;
			double vergi = total - tutari;

			String dx = duble(tutari);
			String dx1 = duble(vergi);
			String dx2 = duble(bfiyat);

			feedList.get(i).put("tutar", dx);
			feedList.get(i).put("birimfi", dx2);
			feedList.get(i).put("vergiTutar", dx1);

		}

		main();
		tutar();

	}

	public ArrayList<String> duzenleHesap(String miktari, String birimfiyati, String vergi_oran) {
		int miktara = Integer.parseInt(miktari);
		ArrayList<String> liste = new ArrayList<String>();
		double tutari;// = Double.parseDouble(totali);
		tutari = miktara * Double.parseDouble(birimfiyati);
		String ttr = duble(tutari);

		double vergittr = tutari * Double.parseDouble(vergi_oran) / 100;
		String vergittri = duble(vergittr);

		double tplm = tutari + vergittr;
		String tplmi = duble(tplm);
		// toplam.setText(dx2);
		liste.add(0, ttr);
		liste.add(1, vergittri);
		liste.add(2, tplmi);
		return liste;
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
