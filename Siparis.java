package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Siparis {
	SQLiteDatabase Db;
	SQLiteDatabase Dbread;
	Context Ctx;
	Dbcreate DbHelper;

	public Siparis(Context context) {
		this.Ctx = context;
	}

	public Siparis open() throws SQLException {
		DbHelper = new Dbcreate(Ctx);
		Db = DbHelper.getWritableDatabase();
		Dbread = DbHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		DbHelper.close();
	}
	
	public String siparisEkle(String mikrokodu,String belgeSeriNo,String belgeNo,String sipTarih,String tesTarih,String stokKodu,String bfiyat,String miktar,String aratoplam,String vergiTutar,String vergiKodu,String isk1,String isk2,String isk3,String isk4,String isk5,String isk6,String CariId,int satirno,int dbKayit,String toplam){
		ContentValues values = new ContentValues();
		values.put("mikroKodu", mikrokodu);
		values.put("belgeSeriNo", belgeSeriNo);
		values.put("belgeNo", belgeNo);
		values.put("siparisTarih", sipTarih);
		values.put("teslimTarih",tesTarih);
		values.put("stokKodu", stokKodu);
		values.put("miktar", miktar);
		values.put("bFiyat", bfiyat);
		values.put("aratoplam", aratoplam);
		values.put("vergiTutar", vergiTutar);
		values.put("vergiKodu", vergiKodu);
		values.put("iskonto1", isk1);
		values.put("iskonto2", isk2);
		values.put("iskonto3", isk3);
		values.put("iskonto4", isk4);
		values.put("iskonto5", isk5);
		values.put("iskonto6", isk6);
		values.put("cariID", CariId);
		values.put("satirNo",satirno);
		values.put("sip_dbKayit", dbKayit);
		values.put("toplam", toplam);
		
		Db.insert("siparis", null, values);
		
		return "kayit baþarili";	
	}
	
	public String siparisDbKayit(String id,String dbKayit){
		ContentValues cv = new ContentValues();
		if(dbKayit.equals("")||dbKayit==null){
		cv.put("sip_dbKayit", "0");
		}
		else{
		cv.put("sip_dbKayit", "1");
		}
		Db.update("siparis", cv, "id="+id, null);
		return "basarýlý.";
	}
	
	public ArrayList<HashMap<String, String>> SiparisGetir(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM siparis;", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id= mCursor.getString(mCursor.getColumnIndex("id"));
					String belgeSeriNo = mCursor.getString(mCursor.getColumnIndex("belgeSeriNo"));
					String belgeNo= mCursor.getString(mCursor.getColumnIndex("belgeNo"));
					String siparisTarih= mCursor.getString(mCursor.getColumnIndex("siparisTarih"));
					String teslimTarih= mCursor.getString(mCursor.getColumnIndex("teslimTarih"));
					String stokkodu= mCursor.getString(mCursor.getColumnIndex("stokKodu"));
					String bFiyat= mCursor.getString(mCursor.getColumnIndex("bFiyat"));
					String miktar= mCursor.getString(mCursor.getColumnIndex("miktar"));
					String aratoplam= mCursor.getString(mCursor.getColumnIndex("aratoplam"));
					String vergiTutar = mCursor.getString(mCursor.getColumnIndex("vergiTutar"));
					String vergiKodu = mCursor.getString(mCursor.getColumnIndex("vergikodu"));
					String iskonto1 = mCursor.getString(mCursor.getColumnIndex("iskonto1"));
					String iskonto2 = mCursor.getString(mCursor.getColumnIndex("iskonto2"));
					String iskonto3 = mCursor.getString(mCursor.getColumnIndex("iskonto3"));
					String iskonto4 = mCursor.getString(mCursor.getColumnIndex("iskonto4"));
					String iskonto5 = mCursor.getString(mCursor.getColumnIndex("iskonto5"));
					String iskonto6 = mCursor.getString(mCursor.getColumnIndex("iskonto6"));
					String cari_id= mCursor.getString(mCursor.getColumnIndex("cariID"));
					String satirNo = mCursor.getString(mCursor.getColumnIndex("satirNo"));
					String dbKayit = mCursor.getString(mCursor.getColumnIndex("sip_dbKayit"));

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("belgeSeriNo", belgeSeriNo);
					map.put("belgeNo", belgeNo);
					map.put("siparisTarih", siparisTarih);
					map.put("teslimTarih", teslimTarih);
					map.put("stokKodu", stokkodu);
					map.put("bFiyat", bFiyat);
					map.put("miktar", miktar);
					map.put("aratoplam", aratoplam);
					map.put("vergiTutar", vergiTutar);
					map.put("vergikodu", vergiKodu);
					map.put("iskonto1", iskonto1);
					map.put("iskonto2", iskonto2);
					map.put("iskonto3", iskonto3);
					map.put("iskonto4", iskonto4);
					map.put("iskonto5", iskonto5);
					map.put("iskonto6", iskonto6);
					map.put("cariID", cari_id);
					map.put("satirNo", satirNo);
					map.put("dbKayit", dbKayit);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
		
	}
	
	public  ArrayList<HashMap<String, String>> SiparisGetirBelge(String seri,String sira){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM siparis where belgeSeriNo ='"+seri+"' and belgeNo='"+sira+"' ;", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id= mCursor.getString(mCursor.getColumnIndex("id"));
					String belgeSeriNo = mCursor.getString(mCursor.getColumnIndex("belgeSeriNo"));
					String belgeNo= mCursor.getString(mCursor.getColumnIndex("belgeNo"));
					String siparisTarih= mCursor.getString(mCursor.getColumnIndex("siparisTarih"));
					String teslimTarih= mCursor.getString(mCursor.getColumnIndex("teslimTarih"));
					String stokkodu= mCursor.getString(mCursor.getColumnIndex("stokKodu"));
					String bFiyat= mCursor.getString(mCursor.getColumnIndex("bFiyat"));
					String miktar= mCursor.getString(mCursor.getColumnIndex("miktar"));
					String aratoplam= mCursor.getString(mCursor.getColumnIndex("aratoplam"));
					String vergiTutar = mCursor.getString(mCursor.getColumnIndex("vergiTutar"));
					String vergiKodu = mCursor.getString(mCursor.getColumnIndex("vergikodu"));
					String iskonto1 = mCursor.getString(mCursor.getColumnIndex("iskonto1"));
					String iskonto2 = mCursor.getString(mCursor.getColumnIndex("iskonto2"));
					String iskonto3 = mCursor.getString(mCursor.getColumnIndex("iskonto3"));
					String iskonto4 = mCursor.getString(mCursor.getColumnIndex("iskonto4"));
					String iskonto5 = mCursor.getString(mCursor.getColumnIndex("iskonto5"));
					String iskonto6 = mCursor.getString(mCursor.getColumnIndex("iskonto6"));
					String cari_id= mCursor.getString(mCursor.getColumnIndex("cariID"));
					String satirNo = mCursor.getString(mCursor.getColumnIndex("satirNo"));
					String dbKayit = mCursor.getString(mCursor.getColumnIndex("sip_dbKayit"));
					String toplam = mCursor.getString(mCursor.getColumnIndex("toplam"));

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("belgeSeriNo", belgeSeriNo);
					map.put("belgeNo", belgeNo);
					map.put("siparisTarih", siparisTarih);
					map.put("teslimTarih", teslimTarih);
					map.put("stokKodu", stokkodu);
					map.put("bFiyat", bFiyat);
					map.put("miktar", miktar);
					map.put("aratoplam", aratoplam);
					map.put("vergiTutar", vergiTutar);
					map.put("vergikodu", vergiKodu);
					map.put("iskonto1", iskonto1);
					map.put("iskonto2", iskonto2);
					map.put("iskonto3", iskonto3);
					map.put("iskonto4", iskonto4);
					map.put("iskonto5", iskonto5);
					map.put("iskonto6", iskonto6);
					map.put("cariID", cari_id);
					map.put("satirNo", satirNo);
					map.put("dbKayit", dbKayit);
					map.put("toplam", toplam);

					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
	public  ArrayList<HashMap<String, String>> SiparisGetirByCari(String cari){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM siparis where cariID ='"+cari+"';", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id= mCursor.getString(mCursor.getColumnIndex("id"));
					String belgeSeriNo = mCursor.getString(mCursor.getColumnIndex("belgeSeriNo"));
					String belgeNo= mCursor.getString(mCursor.getColumnIndex("belgeNo"));
					String siparisTarih= mCursor.getString(mCursor.getColumnIndex("siparisTarih"));
					String teslimTarih= mCursor.getString(mCursor.getColumnIndex("teslimTarih"));
					String stokkodu= mCursor.getString(mCursor.getColumnIndex("stokKodu"));
					String bFiyat= mCursor.getString(mCursor.getColumnIndex("bFiyat"));
					String miktar= mCursor.getString(mCursor.getColumnIndex("miktar"));
					String aratoplam= mCursor.getString(mCursor.getColumnIndex("aratoplam"));
					String vergiTutar = mCursor.getString(mCursor.getColumnIndex("vergiTutar"));
					String vergiKodu = mCursor.getString(mCursor.getColumnIndex("vergikodu"));
					String iskonto1 = mCursor.getString(mCursor.getColumnIndex("iskonto1"));
					String iskonto2 = mCursor.getString(mCursor.getColumnIndex("iskonto2"));
					String iskonto3 = mCursor.getString(mCursor.getColumnIndex("iskonto3"));
					String iskonto4 = mCursor.getString(mCursor.getColumnIndex("iskonto4"));
					String iskonto5 = mCursor.getString(mCursor.getColumnIndex("iskonto5"));
					String iskonto6 = mCursor.getString(mCursor.getColumnIndex("iskonto6"));
					String cari_id= mCursor.getString(mCursor.getColumnIndex("cariID"));
					String satirNo = mCursor.getString(mCursor.getColumnIndex("satirNo"));
					String dbKayit = mCursor.getString(mCursor.getColumnIndex("sip_dbKayit"));
					String toplam = mCursor.getString(mCursor.getColumnIndex("toplam"));

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("belgeSeriNo", belgeSeriNo);
					map.put("belgeNo", belgeNo);
					map.put("siparisTarih", siparisTarih);
					map.put("teslimTarih", teslimTarih);
					map.put("stokKodu", stokkodu);
					map.put("bFiyat", bFiyat);
					map.put("miktar", miktar);
					map.put("aratoplam", aratoplam);
					map.put("vergiTutar", vergiTutar);
					map.put("vergikodu", vergiKodu);
					map.put("iskonto1", iskonto1);
					map.put("iskonto2", iskonto2);
					map.put("iskonto3", iskonto3);
					map.put("iskonto4", iskonto4);
					map.put("iskonto5", iskonto5);
					map.put("iskonto6", iskonto6);
					map.put("cariID", cari_id);
					map.put("satirNo", satirNo);
					map.put("dbKayit", dbKayit);
					map.put("toplam", toplam);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
	public String getSiparisNobySeri(String seri){
		Cursor mCursor = Dbread.rawQuery("SELECT belgeNo FROM siparis where belgeSeriNo ='"+seri+"';", null);
		String no = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					no = mCursor.getString(mCursor.getColumnIndex("belgeNo"));
				} while (mCursor.moveToNext());
			}
			else {
				no = "00000";
			}
		}
		else {
			no = "00000";
		}
		return no;
	}
	
}
