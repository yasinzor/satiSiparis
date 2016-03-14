package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

public class SatisActivity extends Activity {


	List<String> categories = new ArrayList<String>();
	List<String> categories1 = new ArrayList<String>();
	private String TAG = "JASEEN";
	int count;
	Button bsatis,bgetir;
	AutoCompleteTextView sadi;
	private ArrayAdapter<String> dataAdapterForSadi;
	private ArrayAdapter<String> dataAdapterForcadi;
	EditText birim, birimFiyat, iskonto, vergio, vergiTutar, netTutar, tutar,stokara,cadi;
	String stok, cari,stok_id;
	Spinner spinner,spinnerCari;
	TableRow show;
	Stok stokdb;
	private static String isim, cvp, skodu, birimm, birim_fiyat, vergioran, iskontoo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_satis);
		
		stokdb = new Stok(this);
		stokdb.open();
	
		bsatis = (Button) findViewById(R.id.bsatis);
		bgetir = (Button) findViewById(R.id.bgetir);
		//sadi = (AutoCompleteTextView) findViewById(R.id.sadi);
		cadi = (EditText) findViewById(R.id.CariAra);
		stokara = (EditText) findViewById(R.id.stokAra);
		birim = (EditText) findViewById(R.id.birim);
		birimFiyat = (EditText) findViewById(R.id.birimFiyat);
		iskonto = (EditText) findViewById(R.id.iskonto);
		vergio = (EditText) findViewById(R.id.vergio);
		vergiTutar = (EditText) findViewById(R.id.vergiTutar);
		netTutar = (EditText) findViewById(R.id.netTutar);
		tutar = (EditText) findViewById(R.id.tutar);
		spinner = (Spinner) findViewById(R.id.spinnerStok);
		spinnerCari= (Spinner) findViewById(R.id.spinnerCari);
		show =(TableRow) findViewById(R.id.tableShow);
		birim.setText("1");
		
		Bundle recieveBundle = this.getIntent().getExtras();
		stok_id = recieveBundle.getString("stokid");
		
		//dataAdapterForSadi = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, categories);
		//dataAdapterForcadi = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, categories1);

		bgetir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				categories.clear();
				stok=stokara.getText().toString();
				cari=cadi.getText().toString();
				if(stok.length()!=0&&cari.length()!=0){
					AsyncCallWS1 task = new AsyncCallWS1();
					task.execute();
					AsyncCallWS2 task1 = new AsyncCallWS2();
					task1.execute();
				}
				else{
					Toast.makeText(getBaseContext(), "bos býrakýlmaz", Toast.LENGTH_LONG).show();
				}
			}
		});
	
		bsatis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	dbAdapter.stokHareket(id, fatura_id, stok_id, cari_id, miktar, birim_fiyat, net_tutar, tutar, vergi_oran, vergi_tutar);
				
				
				
			}
		});
		spinnerCari.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String secilmis=spinnerCari.getSelectedItem().toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String secilmis=spinner.getSelectedItem().toString();
				List<String> cevaplist = new ArrayList<String>();
				cevaplist=stokdb.stokBilgi(secilmis);
				birimFiyat.setText(cevaplist.get(3));
				
				vergio.setText(cevaplist.get(4));
				//birim.setText("1");
				int birimm = Integer.parseInt(birim.getText().toString());
				double bfiyat=Double.parseDouble(birimFiyat.getText().toString());
				double vergi =Double.parseDouble(vergio.getText().toString());
				double vergiTutari = birimm*vergi*bfiyat/100;
				vergiTutar.setText(Double.toString(vergiTutari));
				double net_tut=birimm*bfiyat;
				double net=vergiTutari+net_tut;
				netTutar.setText(Double.toString(net_tut));
				tutar.setText(Double.toString(net));
				stok_id=cevaplist.get(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private class AsyncCallWS1 extends AsyncTask<String, Void, List<String>> {
		protected List<String> doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			//categories= dbAdapter.stok(stok);
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			Log.i(TAG, "onPostExecute");
			dataAdapterForSadi = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, categories);
			dataAdapterForSadi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(dataAdapterForSadi);
			show.setVisibility(View.VISIBLE);
			//birimFiyat.setText(categories.get(3));
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			// tv.setText("Calculating...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}
		}
	private class AsyncCallWS2 extends AsyncTask<String, Void, List<String>> {
		protected List<String> doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			//categories1= dbAdapter.Cari(cari);
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			Log.i(TAG, "onPostExecute");
			show.setVisibility(View.VISIBLE);
			dataAdapterForcadi = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, categories1);
			dataAdapterForcadi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerCari.setAdapter(dataAdapterForcadi);
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			// tv.setText("Calculating...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}
		}
}
