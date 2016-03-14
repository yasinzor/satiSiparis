package com.ulku.ulkubilgisayar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Tab2 extends Activity {
	ListView lv;
	Button ekle, iskonto;
	String stokid, miktar,tutar;
	EditText stokId, Miktar,urunKodu;
	ArrayList<HashMap<String, String>> feedList,eskiList;
	Boolean yenimi=true;
	HashMap<String, String> map;
	double toplam =0.0;
	double toplam_vergi=0.0;
	double istoplam,vergiTutar;
	String id,adi,birim,birim_fiyat,vergi_oran,doviz_cinsi,kodu;
	Double iskont1,iskont2,iskont3,iskont4,iskont5,iskont6,vergiTutar1,toplam1,tutar1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab2);

		lv = (ListView) findViewById(R.id.listView1);
		ekle = (Button) findViewById(R.id.ekle);
		iskonto =(Button)findViewById(R.id.iskonto);
		stokId = (EditText) findViewById(R.id.stokid);
		Miktar =(EditText)findViewById(R.id.miktar);
		urunKodu =(EditText)findViewById(R.id.urunKodu);
		if(yenimi==true){
		
		
		feedList = (ArrayList<HashMap<String, String>>) getParent().getIntent().getSerializableExtra("list");
		if(feedList==null){
			feedList= new ArrayList<HashMap<String, String>>();
			map = new HashMap<String, String>();
			map.put("kodu", "Id");
			map.put("adi", "Adý");
			map.put("miktar", "Miktar");
			map.put("birimfi", "B.Fiyat");
			map.put("tutar", "Ara Toplam");
			map.put("vergiTutar","KDV");
			map.put("total", "Toplam");
			feedList.add(map);
			}
		}
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, feedList, R.layout.tab2, new String[]{"kodu", "adi", "miktar","birimfi", "tutar","vergiTutar","total"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
        lv.setAdapter(simpleAdapter);
		
		ekle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stokid=stokId.getText().toString();
				miktar=Miktar.getText().toString();
				
				if(stokid.length()==0){
					Intent intent=new Intent(getApplicationContext(), Birim_stok_Activity.class);
					kodu=urunKodu.getText().toString();
					miktar=Miktar.getText().toString();
					Bundle sendBundle =new Bundle();
					sendBundle.putString("stokid", " ");
					sendBundle.putString("kodu",kodu);
					sendBundle.putString("miktar", miktar);
					intent.putExtras(sendBundle);
					startActivityForResult(intent,  100);
				}
				else{
				stokid=stokId.getText().toString();
				miktar=Miktar.getText().toString();
				kodu=urunKodu.getText().toString();
				Bundle sendBundle =new Bundle();
				sendBundle.putString("stokid", stokid);//("toplam", Double.parseDouble(tutar));
				sendBundle.putString("miktar", miktar);
				sendBundle.putString("kodu",kodu);
				Intent intent=new Intent(getApplicationContext(), Birim_stok_Activity.class);
				intent.putExtras(sendBundle);
				startActivityForResult(intent,  100);
				}
			}
		});
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(requestCode == 100 && resultCode == RESULT_OK){
	    	stokid=data.getStringExtra("id");
	    	String stokKodu=data.getStringExtra("kodu");
	    	adi= data.getStringExtra("adi");
		    toplam1=data.getDoubleExtra("toplam",0);
		    birim_fiyat=data.getStringExtra("birim_fiyat");
		    birim=data.getStringExtra("birim");
		    miktar=data.getStringExtra("miktar");
		    vergi_oran=data.getStringExtra("vergi_oran");
		    vergiTutar1=data.getDoubleExtra("vergi_tutar",0);
		    tutar1=data.getDoubleExtra("tutar",0);
		    istoplam = data.getDoubleExtra("isToplam",0);
	        iskont1=data.getDoubleExtra("iskont1",0);
	        iskont2=data.getDoubleExtra("iskont2",0);
	        iskont3=data.getDoubleExtra("iskont3",0);
	        iskont4=data.getDoubleExtra("iskont4",0);
	        iskont5=data.getDoubleExtra("iskont5",0);
	        iskont6=data.getDoubleExtra("iskont6",0);
	 
			String vergiTutari =duble(vergiTutar1);
			String tutari =duble(tutar1);
	        String toplami=duble(toplam1);
			
			map = new HashMap<String, String>();
	        map.put("kodu", stokKodu);
	        map.put("adi", adi);
	        map.put("miktar", miktar);
	        map.put("birimfi", birim_fiyat);
	        map.put("vergiOran", vergi_oran);
	        map.put("vergiTutar", vergiTutari);
	        map.put("tutar", tutari);
	        map.put("total",toplami);
	        
	        map.put("iskont1",Double.toString(iskont1));
	        map.put("iskont2",Double.toString(iskont2));
	        map.put("iskont3",Double.toString(iskont3));
	        map.put("iskont4",Double.toString(iskont4));
	        map.put("iskont5",Double.toString(iskont5));
	        map.put("iskont6",Double.toString(iskont6));
	        feedList.add(map);
	        SimpleAdapter simpleAdapter = new SimpleAdapter(this, feedList, R.layout.tab2, new String[]{"kodu", "adi", "miktar","birimfi", "tutar","vergiTutar","total"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
	        lv.setAdapter(simpleAdapter);
	        //Toast.makeText(getApplicationContext(), "istop: "+istoplam, Toast.LENGTH_LONG).show();
	        getParent().getIntent().putExtra("isToplam", Double.toString(istoplam));
	        getParent().getIntent().putExtra("vergi", Double.toString(toplam_vergi));
	        getParent().getIntent().putExtra("toplam", Double.toString(toplam));
	        getParent().getIntent().putExtra("iskont1", Double.toString(iskont1));
			getParent().getIntent().putExtra("iskont2", Double.toString(iskont2));
			getParent().getIntent().putExtra("iskont3", Double.toString(iskont3));
			getParent().getIntent().putExtra("iskont4", Double.toString(iskont4));
			getParent().getIntent().putExtra("iskont5", Double.toString(iskont5));
			getParent().getIntent().putExtra("iskont6", Double.toString(iskont6));
			getParent().getIntent().putExtra("list", (ArrayList<HashMap<String, String>>)feedList);
			getParent().getIntent().putExtra("dovizCins", doviz_cinsi);
		
	    }
	}
	public String iskonto(String toplam,Double iskonto){
		double toplami=Double.parseDouble(toplam);
		Double temp = toplami*iskonto/100;
		toplami=toplami-temp;
		return Double.toString(toplami);
	}
	public String duble(double duble){
		DecimalFormat df=new DecimalFormat("0.00");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(symbols);
		String cvp =df.format(duble);
		return cvp;
	}
}
