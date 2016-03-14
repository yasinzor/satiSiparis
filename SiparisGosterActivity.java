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
import android.widget.TextView;
import android.widget.Toast;

public class SiparisGosterActivity extends Activity implements OnKeyListener {
	EditText efseri,efno;
	TextView unvan,tarih,aratoplam,kdv,ttoplam;
	String fseri,fno,unvani,cariid,siparisTarih;
	ListView lv,footer;
	HashMap<String, String> map;
	Siparis siparis;
	CariHesap cariHesap;
	Button kapat;
	
	ArrayList<HashMap<String, String>> list,footerList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_siparis_goster);
		
		siparis=new Siparis(this);
		siparis.open();
		
		cariHesap=new CariHesap(this);
		cariHesap.open();
		
		main();
	}
	public void main(){
		kapat=(Button) findViewById(R.id.kapat);
		lv=(ListView)findViewById(R.id.lv);
		footer=(ListView)findViewById(R.id.footer);
		efseri=(EditText)findViewById(R.id.fseri);
		efno=(EditText)findViewById(R.id.fno);
		unvan=(TextView)findViewById(R.id.unvan);
		tarih=(TextView)findViewById(R.id.tarih);
		aratoplam=(TextView)findViewById(R.id.aratoplam);
		kdv=(TextView)findViewById(R.id.kdv);
		ttoplam=(TextView)findViewById(R.id.toplam);
		list  = new ArrayList<HashMap<String, String>>();
		footerList= new ArrayList<HashMap<String, String>>();
		efseri.setOnKeyListener(this);
		efno.setOnKeyListener(this);
		efseri.requestFocus();
		
		map = new HashMap<String, String>();
		map.put("teslimTarih", "Teslim Tarihi");
		map.put("stokKodu", "Stok Kodu");
		map.put("miktar", "Miktar");
		map.put("bFiyat", "B.Fiyat");
		map.put("aratoplam", "Ara Toplam");
		map.put("vergiTutar","KDV");
		map.put("satirNo", "Satýr No");
		footerList.add(map);
		
		SpecialAdapter adapter = new SpecialAdapter(this,footerList,R.layout.siplist,new String[]{"teslimTarih","stokKodu","miktar","bFiyat","aratoplam","vergiTutar","satirNo"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
		footer.setAdapter(adapter);
		
		kapat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
					String sayi=siparis.getSiparisNobySeri(fseri);
					if(sayi.equals("")||sayi==""){
						efno.setText("00000");
					}else{
					efno.setText(sayi);
					
					}
				} else if (id == R.id.fno) {
					fno=efno.getText().toString();
					efno.clearFocus();
					list=siparis.SiparisGetirBelge(fseri,fno);
					for(int i =0;i<list.size();i++){
					cariid=list.get(i).get("cariID");
					siparisTarih=list.get(i).get("siparisTarih");
					
					}
					SpecialAdapter adapter = new SpecialAdapter(this,list,R.layout.siplist,new String[]{"teslimTarih","stokKodu","miktar","bFiyat","aratoplam","vergiTutar","satirNo"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
					lv.setAdapter(adapter);
					unvani=cariHesap.cariAdiGetir(cariid);
					unvan.setText(unvani);
					tarih.setText(siparisTarih);
					tutar();
				}
			}
		return true;
		}
		return false;
	}
	private void tutar() {
		double istop = 0;
		double topVergi = 0;
		double toplam=0;

		if (list == null || list.equals("")) {
			Toast.makeText(getApplicationContext(), "Ürün Girmeden Fatura Tutar Görülemez.", Toast.LENGTH_LONG).show();
		} else {
			for (int i = 0; i <= list.size() - 1; i++) {
				String istoplam = list.get(i).get("aratoplam").toString();
				double istoplami = Double.parseDouble(istoplam);
				String vergi = list.get(i).get("vergiTutar").toString();
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
}
