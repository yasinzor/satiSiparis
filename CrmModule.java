package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CrmModule {
	SQLiteDatabase Db;
	SQLiteDatabase Dbread;
	Context Ctx;
	Dbcreate DbHelper;

	public CrmModule(Context context) {
		this.Ctx = context;
	}

	public CrmModule open() throws SQLException {
		DbHelper = new Dbcreate(Ctx);
		Db = DbHelper.getWritableDatabase();
		Dbread = DbHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		DbHelper.close();
	}
	

	public void crmEkleme(String kodu, String unvan,String yetkili,String noot,String sonuc,String tarih,String gorusme,String caritip,String enlem,String boylam,String dbkayit,String markaAdi,String markaFiyat){
		ContentValues values = new ContentValues();
		values.put("kodu", kodu);
		values.put("Cari_id", unvan);
		values.put("yetkili", yetkili);
		values.put("noot", noot);
		values.put("sonuc", sonuc);
		values.put("tarih",tarih);
		values.put("iliski_tipi", gorusme);
		values.put("cari_tipi", caritip);
		values.put("enlem", enlem);
		values.put("boylam", boylam);
		values.put("dbKayit", dbkayit);
		values.put("markaAdi",markaAdi);
		values.put("markaFiyat", markaFiyat);
		Db.insert("crm", null, values);
		
	}
	public void konumDb(String ID,String enlem,String boylam){
		ContentValues cv = new ContentValues();
		cv.put("enlem", enlem); // These Fields should be your String values
									// of actual column names
		cv.put("boylam", boylam);
		// cv.put("Field2","Male");

		Db.update("crm", cv, "id " + "=" + ID, null);
		
	}
	public ArrayList<HashMap<String, String>> CrmBilgi(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM crm;", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id= mCursor.getString(mCursor.getColumnIndex("id"));
					String kodu = mCursor.getString(mCursor.getColumnIndex("kodu"));
					String cari_id= mCursor.getString(mCursor.getColumnIndex("Cari_id"));
					String yetkili= mCursor.getString(mCursor.getColumnIndex("yetkili"));
					String not= mCursor.getString(mCursor.getColumnIndex("noot"));
					String sonuc= mCursor.getString(mCursor.getColumnIndex("sonuc"));
					String tarih= mCursor.getString(mCursor.getColumnIndex("tarih"));
					String iliski_tipi= mCursor.getString(mCursor.getColumnIndex("iliski_tipi"));
					String cari_tipi = mCursor.getString(mCursor.getColumnIndex("cari_tipi"));
					String enlem = mCursor.getString(mCursor.getColumnIndex("enlem"));
					String boylam = mCursor.getString(mCursor.getColumnIndex("boylam"));
					String dbkayit = mCursor.getString(mCursor.getColumnIndex("dbKayit"));
					String markaadi = mCursor.getString(mCursor.getColumnIndex("markaAdi"));
					String markafiyat = mCursor.getString(mCursor.getColumnIndex("markaFiyat"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("kodu", kodu);
					map.put("cari_id", cari_id);
					map.put("yetkili", yetkili);
					map.put("not", not);
					map.put("sonuc", sonuc);
					map.put("tarih", tarih);
					map.put("iliski_tipi", iliski_tipi);
					map.put("cari_tipi", cari_tipi);
					map.put("enlem", enlem);
					map.put("boylam", boylam);
					map.put("dbKayit", dbkayit);
					map.put("markaadi", markaadi);
					map.put("markafiyat", markafiyat);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
    public ArrayList<HashMap<String, String>> CrmBilgiCari(String cari){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM crm where Cari_id like '%"+ cari +"%';", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id= mCursor.getString(mCursor.getColumnIndex("id"));
					String kodu = mCursor.getString(mCursor.getColumnIndex("kodu"));
					String cari_id= mCursor.getString(mCursor.getColumnIndex("Cari_id"));
					String yetkili= mCursor.getString(mCursor.getColumnIndex("yetkili"));
					String not= mCursor.getString(mCursor.getColumnIndex("noot"));
					String sonuc= mCursor.getString(mCursor.getColumnIndex("sonuc"));
					String tarih= mCursor.getString(mCursor.getColumnIndex("tarih"));
					String iliski_tipi= mCursor.getString(mCursor.getColumnIndex("iliski_tipi"));
					String cari_tipi = mCursor.getString(mCursor.getColumnIndex("cari_tipi"));
					String enlem = mCursor.getString(mCursor.getColumnIndex("enlem"));
					String boylam = mCursor.getString(mCursor.getColumnIndex("boylam"));
					String dbkayit = mCursor.getString(mCursor.getColumnIndex("dbKayit"));
					String markaadi = mCursor.getString(mCursor.getColumnIndex("markaAdi"));
					String markafiyat = mCursor.getString(mCursor.getColumnIndex("markaFiyat"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("kodu", kodu);
					map.put("cari_id", cari_id);
					map.put("yetkili", yetkili);
					map.put("not", not);
					map.put("sonuc", sonuc);
					map.put("tarih", tarih);
					map.put("iliski_tipi", iliski_tipi);
					map.put("cari_tipi", cari_tipi);
					map.put("enlem", enlem);
					map.put("boylam", boylam);
					map.put("dbKayit", dbkayit);
					map.put("markaadi", markaadi);
					map.put("markafiyat", markafiyat);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
	public void CRMdbKayitGuncelle(String ID){
		ContentValues cv = new ContentValues();
		cv.put("dbKayit", "1"); // These Fields should be your String values
									// of actual column names
		// cv.put("Field2","Male");

		Db.update("crm", cv, "id " + "=" + ID, null);
	}	
	public void crmKoduGüncelle(String unvani, String kod) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("kodu", kod); 
		//Db.update("crm", cv, "Cari_id" + "=" + unvani, null);
		Db.update("crm", cv, "Cari_id='"+unvani+"'", null);
	}


	public String stokMarkaEkle(String kodu,String ismi){
		ContentValues values = new ContentValues();
		values.put("kodu", kodu);
		values.put("ismi", ismi);
		
		Db.insert("stokMarkaCrm", null, values);
		return "kayit basarili";
	}	
	public String stokMarkaDbKontrol(String Id,String ismi){
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM stokMarkaCrm WHERE ismi='"+ismi+"';", null);
		String sonuc = "0";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					sonuc = "1";
					
				} while (mCursor.moveToNext());
			}
		}
		
		return sonuc;
	}
	public ArrayList<HashMap<String, String>> stokMarkaBilgi(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM stokMarkaCrm;", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String kodu= mCursor.getString(mCursor.getColumnIndex("kodu"));
					String ismi = mCursor.getString(mCursor.getColumnIndex("ismi"));
				
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("kodu", kodu);
					map.put("ismi", ismi);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
	
	
	
}
