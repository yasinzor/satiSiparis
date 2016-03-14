package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StokHareket {
	SQLiteDatabase Db;
	SQLiteDatabase Dbread;
	Context Ctx;
	Dbcreate DbHelper;

	public StokHareket(Context context) {
		this.Ctx = context;
	}

	public StokHareket open() throws SQLException {
		DbHelper = new Dbcreate(Ctx);
		Db = DbHelper.getWritableDatabase();
		Dbread = DbHelper.getReadableDatabase();
		return this;
	}
	
	
	public void close() {
		DbHelper.close();
	}
	public String stokHareketKayit(String kodu, String miktar, String birim_fiyat, String vergiOran, String tarih,
			String iskonto1, String iskonto2, String iskonto3, String iskonto4, String iskonto5, String iskonto6,
			String iskonto_toplam, String toplamVergi, String net_tutr, String faturaId, String cari_id,int satirNo ) {
		ContentValues values = new ContentValues();
		values.put("stok_id", kodu);
		values.put("miktar", miktar);
		values.put("birim_fiyat", birim_fiyat);
		values.put("vergi_oran", vergiOran);
		values.put("iskonto1", iskonto1);
		values.put("iskonto2", iskonto2);
		values.put("iskonto3", iskonto3);
		values.put("iskonto4", iskonto4);
		values.put("iskonto5", iskonto5);
		values.put("iskonto6", iskonto6);
		values.put("tutar", iskonto_toplam);
		values.put("vergi_tutar", toplamVergi);
		values.put("net_tutar", net_tutr);
		values.put("fatura_id", faturaId);
		values.put("tarih", tarih);
		values.put("cari_id", cari_id);
		values.put("sh_dbkayit", "0");
		values.put("satirNo",satirNo);

		Db.insert("stok_hareket", null, values);
		
		return "kayit baþarili";
	}
	 
	public String stokHareketSil(String kodu,String faturaId) {
		ContentValues values = new ContentValues();
		// values.put("tutar", iskonto_toplam);
		// values.put("vergi_tutar", toplamVergi);
		// values.put("net_tutar", net_tutr);
		// Db.update("stok_hareket", values, "id=?", new
		// String[]{kodu});//("stok_hareket", "id=?", new String[]{kodu});
		// Db.delete("stok_hareket", "stok_id=? and fatura_id=?", new String[] { kodu, faturaId });
		return "silindi";
	}
	// ürünlerin bilgilerini getirmek için 
	public ArrayList<HashMap<String, String>> StokHareketBilgi(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM stok_hareket AS sh ,fatura As f WHERE f.fat_id = sh.fatura_id and f.CariID=sh.cari_id;", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String faturaSeriNo= mCursor.getString(mCursor.getColumnIndex("faturaSeriNo"));
					String faturaNo = mCursor.getString(mCursor.getColumnIndex("faturaNo"));
					String cari_id= mCursor.getString(mCursor.getColumnIndex("CariID"));
					String toplam= mCursor.getString(mCursor.getColumnIndex("Toplam"));
					String toplamVergi= mCursor.getString(mCursor.getColumnIndex("toplamVergi"));
					String tarih= mCursor.getString(mCursor.getColumnIndex("tarih"));
					String ozel= mCursor.getString(mCursor.getColumnIndex("Ozel"));
					String plasiyer= mCursor.getString(mCursor.getColumnIndex("Plasiyer"));
					String stok_id = mCursor.getString(mCursor.getColumnIndex("stok_id"));
					String miktar = mCursor.getString(mCursor.getColumnIndex("miktar"));
					String birim_fiyat = mCursor.getString(mCursor.getColumnIndex("birim_fiyat"));
					String sh_dbkayit = mCursor.getString(mCursor.getColumnIndex("sh_dbkayit"));
					String fat_dbkayit = mCursor.getString(mCursor.getColumnIndex("fat_dbkayit"));
					String iskonto1 = mCursor.getString(mCursor.getColumnIndex("iskonto1"));
					String iskonto2 = mCursor.getString(mCursor.getColumnIndex("iskonto2"));
					String iskonto3 = mCursor.getString(mCursor.getColumnIndex("iskonto3"));
					String iskonto4 = mCursor.getString(mCursor.getColumnIndex("iskonto4"));
					String iskonto5 = mCursor.getString(mCursor.getColumnIndex("iskonto5"));
					String iskonto6 = mCursor.getString(mCursor.getColumnIndex("iskonto6"));
					String net_tutar = mCursor.getString(mCursor.getColumnIndex("net_tutar"));
					String tutar = mCursor.getString(mCursor.getColumnIndex("tutar"));
					String vergi_oran = mCursor.getString(mCursor.getColumnIndex("vergi_oran"));
					String vergi_tutar = mCursor.getString(mCursor.getColumnIndex("vergi_tutar"));
					String satirNo = mCursor.getString(mCursor.getColumnIndex("satirNo"));
					String shid=mCursor.getString(mCursor.getColumnIndex("sh_id"));
					String fat_id=mCursor.getString(mCursor.getColumnIndex("fat_id"));
					String fat_toplam=mCursor.getString(mCursor.getColumnIndex("Gtoplam"));
					
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("faturaSeriNo", faturaSeriNo);
					map.put("faturaNo", faturaNo);
					map.put("cari_id", cari_id);
					map.put("toplam", toplam);
					map.put("toplamVergi", toplamVergi);
					map.put("tarih", tarih);
					map.put("ozel", ozel);
					map.put("plasiyer", plasiyer);
					map.put("stok_id", stok_id);
					map.put("miktar", miktar);
					map.put("birimFiyat", birim_fiyat);
					map.put("sh_dbKayit", sh_dbkayit);
					map.put("fat_dbKayit", fat_dbkayit);
					map.put("iskonto1", iskonto1);
					map.put("iskonto2", iskonto2);
					map.put("iskonto3", iskonto3);
					map.put("iskonto4", iskonto4);
					map.put("iskonto5", iskonto5);
					map.put("iskonto6", iskonto6);
					map.put("net_tutar", net_tutar);
					map.put("tutar", tutar);
					map.put("vergi_oran", vergi_oran);
					map.put("vergi_tutar", vergi_tutar);
					map.put("satirNo", satirNo);
					map.put("sh_id", shid);
					map.put("fat_id", fat_id);
					map.put("fat_toplam", fat_toplam);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
	public void StokHareketdbKayitGuncelle(String fatID,String shID ){
		ContentValues cv = new ContentValues();
		cv.put("fat_dbkayit", "1"); 
		ContentValues cv1 = new ContentValues();
		cv1.put("sh_dbkayit", "1"); // These Fields should be your String values
		Db.update("stok_hareket", cv1, "sh_id " + "=" + shID, null);
		Db.update("fatura", cv, "fat_id " + "=" + fatID, null);
	}
	
}
