package com.ulku.ulkubilgisayar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SiparisActivity extends Activity implements OnClickListener, OnKeyListener{
EditText cari, seri ,sira,stok;
TextView tsipTarih,ttslmTrh,taratoplam,tkdv,ttoplam,tv4,tv5;
Button BStokAra,BKayit,BCikis,BcariAra;
ListView listview;
String cariAdi,belgeSeri,belgeSýra,stokAdi,sipTarih,tslmtrh,aratplm,vergi,tpm,CariKodu;
HashMap<String, String> map;
ArrayList<HashMap<String, String>> feedList;
private int year, month, day;
private SimpleDateFormat dateFormatter;

private DatePickerDialog fromDatePickerDialog;
private DatePickerDialog toDatePickerDialog;

CariHesap cariHesap;
Siparis siparis;
Stok stokDb;

@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_siparis);
		
		cariHesap=new CariHesap(this);
		cariHesap.open();
		
		siparis=new Siparis(this);
		siparis.open();
		
		stokDb=new Stok(this);
		stokDb.open();
		
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		
		cari=(EditText)findViewById(R.id.cari);
		seri=(EditText)findViewById(R.id.seri);
		sira=(EditText)findViewById(R.id.sira);
		stok=(EditText)findViewById(R.id.stokAdi);
		tsipTarih=(TextView)findViewById(R.id.sipTarih);
		tsipTarih.setInputType(InputType.TYPE_NULL);
		ttslmTrh=(TextView)findViewById(R.id.TslmTrh);
		ttslmTrh.setInputType(InputType.TYPE_NULL);
		taratoplam=(TextView)findViewById(R.id.AraToplam);
		tkdv=(TextView)findViewById(R.id.kdv);
		ttoplam=(TextView)findViewById(R.id.toplam);
		BStokAra=(Button)findViewById(R.id.stokEkle);
		BKayit=(Button)findViewById(R.id.kayit);
		BCikis=(Button)findViewById(R.id.cik);
		BcariAra=(Button)findViewById(R.id.cariAra);
		listview=(ListView)findViewById(R.id.listView1);
		tv4=(TextView)findViewById(R.id.textView4);
		tv5=(TextView)findViewById(R.id.textView5);
		
		main();
	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == 100 && resultCode == RESULT_OK){
    	String stokid=data.getStringExtra("id");
    	String stokKodu=data.getStringExtra("kodu");
    	String adi= data.getStringExtra("adi");
    	Double toplam1=data.getDoubleExtra("toplam",0);
    	String birim_fiyat=data.getStringExtra("birim_fiyat");
    	String birim=data.getStringExtra("birim");
    	String miktar=data.getStringExtra("miktar");
    	String vergi_oran=data.getStringExtra("vergi_oran");
    	Double vergiTutar1=data.getDoubleExtra("vergi_tutar",0);
    	Double tutar1=data.getDoubleExtra("tutar",0);
    	Double istoplam = data.getDoubleExtra("isToplam",0);
    	Double iskont1=data.getDoubleExtra("iskont1",0);
    	Double iskont2=data.getDoubleExtra("iskont2",0);
    	Double iskont3=data.getDoubleExtra("iskont3",0);
    	Double iskont4=data.getDoubleExtra("iskont4",0);
    	Double iskont5=data.getDoubleExtra("iskont5",0);
    	Double iskont6=data.getDoubleExtra("iskont6",0);
 
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
        listview.setAdapter(simpleAdapter);
        tutar();
    }
}
	public void main(){
		setDateTimeField();
		cariArama();
		stokArama();
		
		seri.setOnKeyListener(this);
		sira.setOnKeyListener(this);
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
		
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, feedList, R.layout.tab2, new String[]{"kodu", "adi", "miktar","birimfi", "tutar","vergiTutar","total"}, new int[]{R.id.stokKodu, R.id.stokAdi, R.id.miktar,R.id.birim_fiyat, R.id.Tutar,R.id.VergiTutar,R.id.Total});
        listview.setAdapter(simpleAdapter);
		
        BKayit.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				belgeSeri=seri.getText().toString();
				belgeSýra=sira.getText().toString();
				sipTarih=tsipTarih.getText().toString();
				tslmtrh=ttslmTrh.getText().toString();
				
				int lng = feedList.size() - 1;
				for (int i = 1; i <= lng; i++) {

					String kodu = feedList.get(i).get("kodu").toString();
					String adi = feedList.get(i).get("adi").toString();
					String miktar = feedList.get(i).get("miktar").toString();
					String bfiyat = feedList.get(i).get("birimfi").toString();
					String VergiOran = feedList.get(i).get("vergiOran").toString();
					String vergiTutar = feedList.get(i).get("vergiTutar").toString();
					String aratoplam = feedList.get(i).get("tutar").toString();
					String isk1 = feedList.get(i).get("iskont1").toString();
					String isk2 = feedList.get(i).get("iskont2").toString();
					String isk3 = feedList.get(i).get("iskont3").toString();
					String isk4 = feedList.get(i).get("iskont4").toString();
					String isk5 = feedList.get(i).get("iskont5").toString();
					String isk6 = feedList.get(i).get("iskont6").toString();

					double toplam = Double.parseDouble(aratoplam) + Double.parseDouble(vergiTutar);
					String tplm=Double.toString(toplam);
					String cvp = siparis.siparisEkle("", belgeSeri, belgeSýra, sipTarih, tslmtrh, kodu, bfiyat, miktar, aratoplam, vergiTutar, VergiOran, isk1, isk2, isk3, isk4, isk5, isk6, CariKodu, i - 1, 0,tplm);

					String stokSayisi = stokDb.getStokSayisi(adi);
					int no = Integer.parseInt(stokSayisi);
					int MiktarNo = Integer.parseInt(miktar);
					no = no - MiktarNo;
					cvp = stokDb.stokSayisiGuncel(adi, Integer.toString(no));
				}				
				finish();
			}
		});
        
        BCikis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void cariArama(){
		BcariAra.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cariAdi=cari.getText().toString();
				createExampleDialog(cariAdi);
			}
		});
		
	}
	
	private void stokArama(){
		BStokAra.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stokAdi=stok.getText().toString();
				Intent intent=new Intent(SiparisActivity.this, StokGirisActivity.class);
				Bundle sendBundle =new Bundle();
				sendBundle.putString("stokAdi", stokAdi);
				intent.putExtras(sendBundle);
				startActivityForResult(intent,  100);
				
			}
		});
		
	}
	
	private void setDateTimeField() {
		tsipTarih.setOnClickListener(this);
		ttslmTrh.setOnClickListener(this);
		
		Calendar newCalendar = Calendar.getInstance();
		fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            tsipTarih.setText(dateFormatter.format(newDate.getTime()));
	        }

	    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	            Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            ttslmTrh.setText(dateFormatter.format(newDate.getTime()));
	        }

	    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onClick(View view) {
		if(view == tsipTarih) {
			fromDatePickerDialog.show();
			tv4.setVisibility(view.VISIBLE);
		} else if(view == ttslmTrh) {
			toDatePickerDialog.show();
			tv5.setVisibility(view.VISIBLE);
		}		
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			if (!event.isShiftPressed()) {
				int id = v.getId();
				if (id == R.id.seri) {
					belgeSeri=seri.getText().toString();
					sira.requestFocus();
					String sayi=siparis.getSiparisNobySeri(belgeSeri);
					if(sayi.equals("")||sayi==""){
						sira.setText("00001");
					}else{
						int a =Integer.parseInt(sayi);
						a++;
						//sayi=Integer.toString(a);
						final DecimalFormat decimalFormat = new DecimalFormat("00000");                
					    sayi= decimalFormat.format(a);
					sira.setText(sayi);
					}
				} else if (id == R.id.sira) {
					belgeSýra=sira.getText().toString();
					sira.clearFocus();
				}
			}
		return true;
		}
		
	return false;
	}
	private ArrayList<String> createExampleDialog(String CariAdim) {
		final Dialog dialog = new Dialog(SiparisActivity.this);
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
				cariAdi=  itemValue.get("adi");
				CariKodu= itemValue.get("id");
				cari.setText(cariAdi);
				dialog.dismiss();
			}
		});
		
		dialog.show();
		return null;

	}
	public String duble(double duble){
		DecimalFormat df=new DecimalFormat("0.00");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(symbols);
		String cvp =df.format(duble);
		return cvp;
	}
	private void tutar() {
		double istop = 0;
		double topVergi = 0;
		double toplam=0;

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

			toplam = istop + topVergi;
			taratoplam.setText(String.format("%.2f", istop));
			tkdv.setText(String.format("%.2f", topVergi));
			ttoplam.setText(String.format("%.2f", toplam));
		}
	}
}
