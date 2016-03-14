package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CariFSActivity extends Activity implements OnKeyListener {
Button ara;
AutoCompleteTextView caritv;
String CariAdi,cariID ;
CariHesap cariHesap;
Fatura fatura;
ListView lv,tvlv;
CheckBox cb1;
ArrayList<HashMap<String, String>> list,tvList;
HashMap<String, String> map;
Siparis siparis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cari_fs);
		cariID="0";
		cariHesap=new CariHesap(this);
		cariHesap.open();
		
		fatura= new Fatura(this);
		fatura.open();
		
		siparis=new Siparis(this);
		siparis.open();
		
		ara=(Button)findViewById(R.id.ara);
		caritv=(AutoCompleteTextView)findViewById(R.id.cari);
		lv=(ListView)findViewById(R.id.listView1);
		tvlv=(ListView)findViewById(R.id.listView2);
		cb1=(CheckBox)findViewById(R.id.cb1);
		
		caritv.setOnKeyListener(this);
		list  = new ArrayList<HashMap<String, String>>();
		
		tvList= new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		ara.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(cariID.equals("0")){
					CariAdi= caritv.getText().toString();
					ArrayList<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
					liste=(ArrayList<HashMap<String, String>>) cariHesap.CariAdiSorgu(CariAdi);
					if(!(liste.size()==0)){
					for(int i=0;i<liste.size();i++){
						cariID=liste.get(i).get("id");
					}
					if(cb1.isChecked()){
						
						map.put("belgeSeriNo", "Seri");
						map.put("belgeNo", "Sýra No");
						map.put("siparisTarih", "Sip. Tarihi");
						map.put("teslimTarih", "Tes. Tarihi");
						map.put("aratoplam", "Ara Toplam");
						map.put("vergiTutar","KDV");
						map.put("toplam", "Toplam");
						tvList.add(map);
						SimpleAdapter adapter = new SimpleAdapter(CariFSActivity.this, tvList, R.layout.tab2, new String[]{"belgeSeriNo", "belgeNo", "siparisTarih", "teslimTarih","aratoplam","vergiTutar","toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
						tvlv.setAdapter(adapter);
						
						list=siparis.SiparisGetirByCari(cariID);
						SimpleAdapter simpleAdapter = new SimpleAdapter(CariFSActivity.this, list, R.layout.tab2, new String[]{"belgeSeriNo", "belgeNo", "siparisTarih", "teslimTarih","aratoplam","vergiTutar","toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
						//SpecialAdapter adapter = new SpecialAdapter(this,list,R.layout.stokliste,new String[] { "id", "adi","b_fiyat","stokSayisi" },new int[] { R.id.id, R.id.adi,R.id.StkSayisi,R.id.Bfiyat });
						lv.setAdapter(simpleAdapter);
					}
					else{
						map.put("", "");
						map.put("faturaSeriNo", "Seri");
						map.put("faturaNo", "Sýra NO");
						map.put("tarih", "Tarih");
						map.put("toplam", "Ara Toplam");
						map.put("toplamVergi","KDV");
						map.put("fat_toplam", "Toplam");
						tvList.add(map);
						
						SimpleAdapter adapter = new SimpleAdapter(CariFSActivity.this, tvList, R.layout.tab2, new String[]{"","faturaSeriNo", "faturaNo", "tarih", "toplam","toplamVergi","fat_toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
						//SpecialAdapter adapter = new SpecialAdapter(this,list,R.layout.stokliste,new String[] { "id", "adi","b_fiyat","stokSayisi" },new int[] { R.id.id, R.id.adi,R.id.StkSayisi,R.id.Bfiyat });
						tvlv.setAdapter(adapter);
						
					list=fatura.FaturaBilgiByCari(cariID);
					SimpleAdapter simpleAdapter = new SimpleAdapter(CariFSActivity.this, list, R.layout.tab2, new String[]{"","faturaSeriNo", "faturaNo", "tarih", "toplam","toplamVergi","fat_toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
					lv.setAdapter(simpleAdapter);
					}
				}
					else{
						Toast.makeText(getApplicationContext(), "Cari Kontrol Ediniz", Toast.LENGTH_LONG).show();
						}
					}
				 else{
					 if(cb1.isChecked()){
						 map.put("belgeSeriNo", "Seri");
							map.put("belgeNo", "Sýra No");
							map.put("siparisTarih", "Sip. Tarihi");
							map.put("teslimTarih", "Tes. Tarihi");
							map.put("aratoplam", "Ara Toplam");
							map.put("vergiTutar","KDV");
							map.put("toplam", "Toplam");
							tvList.add(map);
							SimpleAdapter adapter = new SimpleAdapter(CariFSActivity.this, tvList, R.layout.tab2, new String[]{"belgeSeriNo", "belgeNo", "siparisTarih", "teslimTarih","aratoplam","vergiTutar","toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
							tvlv.setAdapter(adapter);
							list=siparis.SiparisGetirByCari(cariID);
							SimpleAdapter simpleAdapter = new SimpleAdapter(CariFSActivity.this, list, R.layout.tab2, new String[]{"belgeSeriNo", "belgeNo", "siparisTarih", "teslimTarih","aratoplam","vergiTutar","toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
							//SpecialAdapter adapter = new SpecialAdapter(this,list,R.layout.stokliste,new String[] { "id", "adi","b_fiyat","stokSayisi" },new int[] { R.id.id, R.id.adi,R.id.StkSayisi,R.id.Bfiyat });
							lv.setAdapter(simpleAdapter);
						}
						else{
							map.put("", "");
							map.put("faturaSeriNo", "Seri");
							map.put("faturaNo", "Sýra NO");
							map.put("tarih", "Tarih");
							map.put("toplam", "Ara Toplam");
							map.put("toplamVergi","KDV");
							map.put("fat_toplam", "Toplam");
							tvList.add(map);
							
							SimpleAdapter adapter = new SimpleAdapter(CariFSActivity.this, tvList, R.layout.tab2, new String[]{"","faturaSeriNo", "faturaNo", "tarih", "toplam","toplamVergi","fat_toplam"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
							//SpecialAdapter adapter = new SpecialAdapter(this,list,R.layout.stokliste,new String[] { "id", "adi","b_fiyat","stokSayisi" },new int[] { R.id.id, R.id.adi,R.id.StkSayisi,R.id.Bfiyat });
							tvlv.setAdapter(adapter);
						list=fatura.FaturaBilgiByCari(cariID);
						SimpleAdapter simpleAdapter = new SimpleAdapter(CariFSActivity.this, list, R.layout.tab2, new String[]{"","faturaSeriNo", "faturaNo", "tarih", "toplam","toplamVergi","vergiTutar"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
						//SpecialAdapter adapter = new SpecialAdapter(this,list,R.layout.stokliste,new String[] { "id", "adi","b_fiyat","stokSayisi" },new int[] { R.id.id, R.id.adi,R.id.StkSayisi,R.id.Bfiyat });
						lv.setAdapter(simpleAdapter);
						}
				 }
				}
		});
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.cari) {
					CariAdi = caritv.getText().toString();
					createExampleDialog(CariAdi);
				}
				}
			return true;
			}
		return false;
	}
	
	private ArrayList<String> createExampleDialog(String CariAdim) {
		final Dialog dialog = new Dialog(CariFSActivity.this);
		dialog.setContentView(R.layout.cariliste);
		dialog.setTitle("Cari Seç");
		dialog.setCancelable(true);
		ArrayList<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		final ListView list =(ListView) dialog.findViewById(R.id.liste);
		liste=(ArrayList<HashMap<String, String>>) cariHesap.CariAdiSorgu(CariAdim);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,liste,R.layout.cliste,new String[] { "id", "adi"},
				new int[] { R.id.id, R.id.adi});
		list.setAdapter(simpleAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> itemValue=(HashMap<String, String>)list.getItemAtPosition(position);
				cariID = itemValue.get("id");
				CariAdi=  itemValue.get("adi");
				caritv.setText(CariAdi);
				dialog.dismiss();
			}
		});
		
		dialog.show();
		return null;
	}
}
