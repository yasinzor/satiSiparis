package com.ulku.ulkubilgisayar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class Tab1 extends Activity implements OnKeyListener {
	EditText faturaNo, faturaTarih, ozelKod, plasiyer,fseri,dcinsi,cariId,cariAdi;
	String fno, ozelKodu, plasiyeri,faturaSeri,tarih,dovizCins,cariID,CariAdi;
	CariHesap cariHesap;
	Fatura fatura;
	
	Boolean yenimi=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab1);
		
		cariHesap =new CariHesap(this);
		cariHesap.open();
		
		fatura=new Fatura(this);
		fatura.open();
		
		faturaNo = (EditText) findViewById(R.id.FaturaNo);
		faturaTarih = (EditText) findViewById(R.id.ftarih);
		ozelKod = (EditText) findViewById(R.id.ozelKod);
		plasiyer = (EditText) findViewById(R.id.plasiyer);
		fseri = (EditText) findViewById(R.id.fseri);
		dcinsi = (EditText) findViewById(R.id.dcinsi);
		cariId=(EditText)findViewById(R.id.CariId);
		cariAdi=(EditText)findViewById(R.id.CariAdi);
		faturaNo.setOnKeyListener(this);
		ozelKod.setOnKeyListener(this);
		plasiyer.setOnKeyListener(this);
		fseri.setOnKeyListener(this);
		dcinsi.setOnKeyListener(this);
		cariId.setOnKeyListener(this);
		cariAdi.setOnKeyListener(this);
		main();
		

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.fseri) {
					faturaSeri = fseri.getText().toString();
					
					String sayi=fatura.getFaturaNobySeri(faturaSeri);
					if(sayi.equals("")||sayi==""){
						faturaNo.setText("00001");
					}else{
						int a =Integer.parseInt(sayi);
						a++;
						//sayi=Integer.toString(a);
						final DecimalFormat decimalFormat = new DecimalFormat("00000");                
					    sayi= decimalFormat.format(a);
						faturaNo.setText(sayi);
						faturaNo.setFocusableInTouchMode(true);
						faturaNo.requestFocus();
					}
				} else if (id == R.id.FaturaNo) {
					faturaNo.clearFocus();
					cariId.setFocusableInTouchMode(true);
					cariId.requestFocus();
					fno = faturaNo.getText().toString();
					getParent().getIntent().putExtra("faturaSeri",faturaSeri);
					getParent().getIntent().putExtra("fno",fno);
				} else if (id == R.id.CariId) {
					cariId.clearFocus();
					cariAdi.setFocusableInTouchMode(true);
					cariAdi.requestFocus();
					cariID = cariId.getText().toString();
					CariAdi=cariHesap.cariIDSorgu(cariID);
					cariAdi.setText(CariAdi);
				} else if (id == R.id.CariAdi) {
					cariAdi.clearFocus();
					ozelKod.setFocusableInTouchMode(true);
					ozelKod.requestFocus();
					CariAdi = cariAdi.getText().toString();
					createExampleDialog(CariAdi);
					ozelKodu="";
					plasiyeri="";
					
					cariID = cariId.getText().toString();
					getParent().getIntent().putExtra("tarih", tarih);
					getParent().getIntent().putExtra("cariid",cariID);	
					getParent().getIntent().putExtra("CariAdi",CariAdi);
					getParent().getIntent().putExtra("faturaSeri",faturaSeri);
					getParent().getIntent().putExtra("fno",fno);
					getParent().getIntent().putExtra("ozelKodu", ozelKodu);
					getParent().getIntent().putExtra("plasiyeri",plasiyeri);
				} else if (id == R.id.ozelKod) {
					ozelKod.clearFocus();
					plasiyer.setFocusableInTouchMode(true);
					plasiyer.requestFocus();
					ozelKodu = ozelKod.getText().toString();
				} else if (id == R.id.plasiyer) {
					plasiyer.clearFocus();
					dcinsi.setFocusableInTouchMode(true);
					dcinsi.requestFocus();
					plasiyeri = plasiyer.getText().toString();
				} else if (id == R.id.dcinsi) {
					dovizCins= dcinsi.getText().toString();

					getParent().getIntent().putExtra("tarih", tarih);
					getParent().getIntent().putExtra("cariid",cariID);
					
					getParent().getIntent().putExtra("faturaSeri",faturaSeri);
					getParent().getIntent().putExtra("fno",fno);
					getParent().getIntent().putExtra("ozelKodu", ozelKodu);
					getParent().getIntent().putExtra("plasiyeri",plasiyeri);
				}
			}
			return true;
		}
		return false;
	}
	private ArrayList<String> createExampleDialog(String CariAdim) {
		final Dialog dialog = new Dialog(Tab1.this);
		dialog.setContentView(R.layout.cariliste);
		dialog.setTitle("Cari Seç");
		dialog.setCancelable(true);
		ArrayList<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		final ListView list =(ListView) dialog.findViewById(R.id.liste);
		liste=(ArrayList<HashMap<String, String>>) cariHesap.CariAdiSorgu(CariAdim);
		//Toast.makeText(this, liste.toString(), Toast.LENGTH_LONG).show();
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,liste,R.layout.cliste,new String[] { "id", "adi"},
				new int[] { R.id.id, R.id.adi});
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    //          android.R.layout.simple_list_item_1, android.R.id.text1, liste);
		list.setAdapter(simpleAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> itemValue=(HashMap<String, String>)list.getItemAtPosition(position);
				cariID = itemValue.get("id");
				CariAdi=  itemValue.get("adi");
				cariId.setText(cariID);
				cariAdi.setText(CariAdi);
				getParent().getIntent().putExtra("tarih", tarih);
				getParent().getIntent().putExtra("cariid",cariID);
				
				getParent().getIntent().putExtra("faturaSeri",faturaSeri);
				getParent().getIntent().putExtra("fno",fno);
				getParent().getIntent().putExtra("ozelKodu", ozelKodu);
				getParent().getIntent().putExtra("plasiyeri",plasiyeri);
				dialog.dismiss();
			}
		});
		
		dialog.show();
		return null;

	}
	public void getFaturaNo(){
		String faturaNumarasi = fatura.getFaturaNo();
		if(faturaNumarasi.equals("00000")){
			faturaNumarasi="00001";
			faturaNo.setText(faturaNumarasi);
		}
		else if(faturaNumarasi.isEmpty()){
			faturaNumarasi="00001";
			faturaNo.setText(faturaNumarasi);
		}
		else{
			int ftrNo = Integer.parseInt(faturaNumarasi); 
			ftrNo=ftrNo+1;
			final DecimalFormat decimalFormat = new DecimalFormat("00000");                
		    faturaNumarasi= decimalFormat.format(ftrNo);
			faturaNo.setText(faturaNumarasi);
			}
	}
	public void main(){
		
			Calendar c = Calendar.getInstance(); 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			tarih = df.format(c.getTime());
			faturaTarih.setText(tarih);
			getFaturaNo();
			CariAdi=cariAdi.getText().toString();
			cariAdi.setText(CariAdi);
			
			String cariAdim = getParent().getIntent().getStringExtra("CariAdi");
			String cariid = getParent().getIntent().getStringExtra("cariid");
			String fserisi = getParent().getIntent().getStringExtra("faturaSeri");
			String ozelkodu=getParent().getIntent().getStringExtra("ozelKodu");
			String plasiyeri =getParent().getIntent().getStringExtra("plasiyeri");
			
			fseri.setText(fserisi);
			cariId.setText(cariid);
			cariAdi.setText(cariAdim);
			ozelKod.setText(ozelkodu);
			plasiyer.setText(plasiyeri);
		
	}
}