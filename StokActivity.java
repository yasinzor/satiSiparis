package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

public class StokActivity extends Activity {
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://192.168.100.103:8081/Servis.asmx?WSDL";
	private final String SOAP_ACTION = "http://tempuri.org/Stoklar";
	private final String METHOD_NAME = "Stoklar";

	private final String URL1 = "http://192.168.100.103:8081/Servis.asmx?WSDL";
	private final String SOAP_ACTION1 = "http://tempuri.org/Spinner";
	private final String METHOD_NAME1 = "Spinner";

	List<String> categories = new ArrayList<String>();
	private String TAG = "JASEEN";
	private static String isim, cvp, skodu, birim, birim_fiyat, vergio, iskonto, doviz;
	
	
	int count;
	private ArrayAdapter<String> dataAdapterForSadi;
	TextView tv;
	Button bara;
	EditText skodutv, birimtv, birim_fiyattv, vergiotv, iskontotv, doviztv,stokaratv;
	Spinner spinner;
	AutoCompleteTextView actv;
	TableRow show;
	Stok stok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stok);
	
		stok = new Stok(this);
		stok.open();
		

		bara = (Button) findViewById(R.id.bara);
		//actv = (AutoCompleteTextView) findViewById(R.id.otoisim);
		skodutv = (EditText) findViewById(R.id.skodu);
		birimtv = (EditText) findViewById(R.id.birim);
		birim_fiyattv = (EditText) findViewById(R.id.birim_fiyat);
		vergiotv = (EditText) findViewById(R.id.vergi_oran);
		iskontotv = (EditText) findViewById(R.id.iskonto_kodu);
		doviztv = (EditText) findViewById(R.id.doviz);
		stokaratv = (EditText) findViewById(R.id.stokara);
		spinner =(Spinner) findViewById(R.id.spinner);
		show =(TableRow) findViewById(R.id.spinnerrow);
		
		//dataAdapterForSadi = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, categories);
		
		
		bara.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				categories.clear();
				//AsyncCallWS task = new AsyncCallWS();
				//task.execute();
				isim = stokaratv.getText().toString();
				AsyncCallWS task = new AsyncCallWS();
				task.execute();
				
			}
		});
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String secilmis=spinner.getSelectedItem().toString();
			/// db fonksiyon olusturdum buranýn kodlarý kaldý
				List<String> cevaplist = new ArrayList<String>();
				cevaplist=stok.stokBilgi(secilmis);
				//Toast.makeText(getBaseContext(), cevaplist.toString(), Toast.LENGTH_SHORT).show();
				stokaratv.setText(cevaplist.get(1));
				skodutv.setText(cevaplist.get(0) + " ");
				birimtv.setText(cevaplist.get(2) + " ");
				birim_fiyattv.setText(cevaplist.get(3) + " ");
				vergiotv.setText(cevaplist.get(4) + " ");
				iskontotv.setText(cevaplist.get(5) + " ");
				doviztv.setText(cevaplist.get(6) + " ");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		

	}
	/*
	 * public class AsyncCallWS1 extends AsyncTask<String, Void, List<String>> {
	 * protected List<String> doInBackground(String... params) { Log.i(TAG,
	 * "doInBackground"); getSpinner(); return null; }
	 * 
	 * @Override protected void onPostExecute(List<String> result) { Log.i(TAG,
	 * "onPostExecute"); //skodutv.setText(skodu +" ");
	 * actv.setAdapter(dataAdapterForSadi); actv.setThreshold(1);
	 * //spinner.setAdapter(dataAdapterForSadi); }
	 * 
	 * @Override protected void onPreExecute() { Log.i(TAG, "onPreExecute");
	 * //tv.setText("Calculating..."); }
	 * 
	 * @Override protected void onProgressUpdate(Void... values) { Log.i(TAG,
	 * "onProgressUpdate"); }
	 * 
	 * 
	 * private void getSpinner() { // TODO Auto-generated method stub
	 * 
	 * SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
	 * SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
	 * SoapEnvelope.VER11); envelope.dotNet = true; //Set output SOAP object
	 * envelope.setOutputSoapObject(request); //envelope.headerOut = header;
	 * //Create HTTP call object HttpTransportSE androidHttpTransport = new
	 * HttpTransportSE(URL1); try { //Invole web service
	 * androidHttpTransport.call(SOAP_ACTION1, envelope); //Get the response
	 * SoapObject response = (SoapObject) envelope.bodyIn; response=(SoapObject)
	 * ((SoapObject) response.getProperty(0)).getProperty("diffgram");
	 * //response=(SoapObject) ((SoapObject)
	 * response.getProperty(0)).getProperty("Table"); SoapPrimitive result;
	 * response = (SoapObject)((SoapObject)
	 * response.getProperty(0));//.getProperty(1); count =
	 * response.getPropertyCount();
	 * 
	 * for(int i=0;i<count;i++){ SoapObject response1=(SoapObject)
	 * response.getProperty(i); result = (SoapPrimitive)
	 * response1.getProperty("Adi"); categories.add(result.toString()); }
	 * 
	 * //skodu = response.toString(); //SoapPrimitive result = (SoapPrimitive)
	 * response.getProperty("Adi"); //skodu = categories.toString();
	 * 
	 * }catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * }
	 
*/
	private class AsyncCallWS extends AsyncTask<String, Void, List<String>> {
		protected List<String> doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			//categories= dbAdapter.stok(isim);
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			Log.i(TAG, "onPostExecute");
			dataAdapterForSadi = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, categories);
			dataAdapterForSadi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(dataAdapterForSadi);
			show.setVisibility(View.VISIBLE);
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

		public void getStoklar(String isim) {
			// Create request
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			// Property which holds input parameters

			PropertyInfo adiPI = new PropertyInfo();
			// Set Name
			adiPI.setName("adi");
			// Set Value
			adiPI.setValue(isim);
			// Set dataType
			adiPI.setType(String.class);
			// Add the property to request object
			request.addProperty(adiPI);
			// Property which holds input parameters

			// Create envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			// envelope.headerOut = header;
			// Create HTTP call object
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.bodyIn;

				response = (SoapObject) ((SoapObject) response.getProperty(0)).getProperty("diffgram");
				response = (SoapObject) ((SoapObject) response.getProperty(0)).getProperty("Table");
				SoapPrimitive result = (SoapPrimitive) response.getProperty("Id");
				skodu = result.toString();
				SoapPrimitive rAdi = (SoapPrimitive) response.getProperty("Adi");
				cvp = rAdi.toString();
				SoapPrimitive rbirim = (SoapPrimitive) response.getProperty("Birim");
				birim = rbirim.toString();
				SoapPrimitive rBfiyat = (SoapPrimitive) response.getProperty("Birim_fiyat");
				birim_fiyat = rBfiyat.toString();
				SoapPrimitive rvergi = (SoapPrimitive) response.getProperty("vergi_oran");
				vergio = rvergi.toString();
				SoapPrimitive riskonto = (SoapPrimitive) response.getProperty("iskonto_kodu");
				iskonto = riskonto.toString();
				SoapPrimitive rdoviz = (SoapPrimitive) response.getProperty("döviz_cinsi");
				doviz = rdoviz.toString();
				// Toast.makeText(getApplicationContext(), cvp,
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
