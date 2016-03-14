package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FaturaShowActivity extends Activity implements OnKeyListener {
	EditText efseri,efno;
	TextView unvan,tarih,aratoplam,kdv,ttoplam;
	String fseri,fno,cariid,Tarih,unvani;
	ListView lv,lvtv;
	Button kapat;
	HashMap<String, String> map;
	ArrayList<HashMap<String, String>> tvList;
	
	Fatura fatura;
	CariHesap cariHesap;
	
	ArrayList<HashMap<String, String>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fatura_show);
		
		fatura = new Fatura(this);
		fatura.open();
		
		cariHesap = new CariHesap(this);
		cariHesap.open();
		
		main();
	}
	
	public void main(){
		efseri=(EditText)findViewById(R.id.fseri);
		efno=(EditText)findViewById(R.id.fno);
		unvan=(TextView)findViewById(R.id.unvan);
		tarih=(TextView)findViewById(R.id.tarih);
		aratoplam=(TextView)findViewById(R.id.aratoplam);
		kdv=(TextView)findViewById(R.id.kdv);
		ttoplam=(TextView)findViewById(R.id.toplam);
		list  = new ArrayList<HashMap<String, String>>();
		efseri.setOnKeyListener(this);
		efno.setOnKeyListener(this);
		lv=(ListView)findViewById(R.id.listView1);
		lvtv=(ListView)findViewById(R.id.lvtv);
		kapat=(Button)findViewById(R.id.kapat);
		tvList= new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		map.put("kodu", "Id");
		map.put("stok_id", "Stok Kodu");
		map.put("miktar", "Miktar");
		map.put("birimFiyat", "B.Fiyat");
		map.put("tutar", "Ara Toplam");
		map.put("vergi_tutar","KDV");
		map.put("net_tutar", "Toplam");
		tvList.add(map);
		efseri.requestFocus();
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, tvList, R.layout.tab2, new String[]{"kodu", "stok_id", "miktar","birimFiyat", "tutar","vergi_tutar","net_tutar"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
        lvtv.setAdapter(simpleAdapter);
		kapat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private void tutar() {
		double istop = 0;
		double topVergi = 0;
		double toplam=0;

		if (list == null || list.equals("")) {
			Toast.makeText(getApplicationContext(), "Ürün Girmeden Fatura Tutar Görülemez.", Toast.LENGTH_LONG).show();
		} else {
			for (int i = 0; i <= list.size() - 1; i++) {
				String istoplam = list.get(i).get("tutar").toString();
				double istoplami = Double.parseDouble(istoplam);
				String vergi = list.get(i).get("vergi_tutar").toString();
				double vergii = Double.parseDouble(vergi);

				istop = istop + istoplami;
				topVergi = topVergi + vergii;
			}

			toplam = istop + topVergi;
			aratoplam.setText(String.format("%.2f", istop));
			kdv.setText(String.format("%.2f", topVergi));
			ttoplam.setText(String.format("%.2f", toplam));
		}
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.fseri) {
					fseri=efseri.getText().toString();
					efno.requestFocus();
					String sayi=fatura.getFaturaNobySeri(fseri);
					if(sayi.equals("")||sayi==""){
						efno.setText("00000");
					}else{
					efno.setText(sayi);
					}
				} else if (id == R.id.fno) {
					fno=efno.getText().toString();
					efno.clearFocus();
					list=fatura.FaturaBilgi(fseri, fno);
					for(int i =0;i<list.size();i++){
						cariid=list.get(i).get("cari_id");
						Tarih=list.get(i).get("tarih");
						}
					unvani=cariHesap.cariAdiGetir(cariid);
					unvan.setText(unvani);
					tarih.setText(Tarih);
					SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.tab2, new String[]{"kodu", "stok_id", "miktar","birimFiyat", "tutar","vergi_tutar","net_tutar"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
			        lv.setAdapter(simpleAdapter);
			        tutar();
				}
			}
		return true;
		}
		
	return false;
	}
}
