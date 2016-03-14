package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Stok {

	SQLiteDatabase Db;
	SQLiteDatabase Dbread;
	Context Ctx;
	Dbcreate DbHelper;

	public Stok(Context context) {
		this.Ctx = context;
	}

	public Stok open() throws SQLException {
		DbHelper = new Dbcreate(Ctx);
		Db = DbHelper.getWritableDatabase();
		Dbread = DbHelper.getReadableDatabase();
		return this;
	}
	
	
	public void close() {
		DbHelper.close();
	}
	
	public String getStokSayisi(String stok){
		Cursor mCursor = Dbread.rawQuery("SELECT eldekiStok FROM Stoklar where adi='"+stok+"';", null);
		String no = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					no = mCursor.getString(mCursor.getColumnIndex("eldekiStok"));
				
				} while (mCursor.moveToNext());

			}
		}
		
		return no;
	}
	public String stokEkler(String kodu, String adi, String birim, String birim_fiyat, 
			String vergiOran, String iskonto_kodu, String doviz_cinsi,String stokSayisi) { 

		ContentValues values = new ContentValues();
		values.put("kodu", kodu);
		values.put("adi", adi);
		values.put("birim", birim);
		values.put("birim_fiyat", birim_fiyat);
		values.put("vergi_oran", vergiOran);
		values.put("iskonto_kodu", iskonto_kodu);
		values.put("doviz_cinsi", doviz_cinsi);
		values.put("eldekiStok", stokSayisi);
		
		Db.insert("Stoklar", null, values);
		return "kayýt basarýlý";
	}
	public String stokGuncel(String adi,String birimFiyat,String stokSayisi){
		ContentValues cv = new ContentValues();
		cv.put("eldekiStok", stokSayisi); 
		cv.put("birim_fiyat", birimFiyat);
		
		Db.update("Stoklar", cv, "adi='"+adi+"'", null);
		return "Kayýt güncellendi.";
	}
	public String stokSayisiGuncel(String adi,String stokSayisi){
		ContentValues cv = new ContentValues();
		cv.put("eldekiStok", stokSayisi); 
		
		Db.update("Stoklar", cv, "adi='"+adi+"'", null);
		return "Kayýt güncellendi.";
	}
	public List<HashMap<String, String>> stok(String Stok) throws SQLException {
		Cursor mCursor = Dbread.rawQuery("SELECT id, adi,birim_fiyat,eldekiStok FROM Stoklar WHERE adi LIKE'%" + Stok + "%';", null);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String adi = mCursor.getString(mCursor.getColumnIndex("adi"));
					String id = mCursor.getString(mCursor.getColumnIndex("id"));
					String bfiyat = mCursor.getString(mCursor.getColumnIndex("birim_fiyat"));
					String stokSayisi = mCursor.getString(mCursor.getColumnIndex("eldekiStok"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("adi", adi);
					map.put("b_fiyat", bfiyat);
					map.put("stokSayisi", stokSayisi);
					list.add(map);
				} while (mCursor.moveToNext());
			}
		}
		return list;
	}
	public List<String> stokBilgi(String StokBilgi) {
		ArrayList<String> list = new ArrayList<String>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM Stoklar WHERE id='" + StokBilgi + "';", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id = mCursor.getString(mCursor.getColumnIndex("id"));
					String kodu=mCursor.getString(mCursor.getColumnIndex("kodu"));
					String adi = mCursor.getString(mCursor.getColumnIndex("adi"));
					String birim = mCursor.getString(mCursor.getColumnIndex("birim"));
					String birim_fiyat = mCursor.getString(mCursor.getColumnIndex("birim_fiyat"));
					String vergi_oran = mCursor.getString(mCursor.getColumnIndex("vergi_oran"));
					String iskonto_oran = mCursor.getString(mCursor.getColumnIndex("iskonto_kodu"));
					String doviz_cinsi = mCursor.getString(mCursor.getColumnIndex("doviz_cinsi"));
					list.add(id);
					list.add(kodu);
					list.add(adi);
					list.add(birim);
					list.add(birim_fiyat);
					list.add(vergi_oran);
					list.add(iskonto_oran);
					list.add(doviz_cinsi);
				} while (mCursor.moveToNext());

			}
		}
		return list;
	}
	public String StokDbKontrol(String Id,String unvan){
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM Stoklar WHERE adi='"+unvan+"';", null);
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
	public List<String> stokBilgiKodu(String StokBilgi){// mikro koduna göre getiriyor
		ArrayList<String> list = new ArrayList<String>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM Stoklar WHERE kodu='" + StokBilgi + "';", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id = mCursor.getString(mCursor.getColumnIndex("id"));
					String kodu=mCursor.getString(mCursor.getColumnIndex("kodu"));
					String adi = mCursor.getString(mCursor.getColumnIndex("adi"));
					String birim = mCursor.getString(mCursor.getColumnIndex("birim"));
					String birim_fiyat = mCursor.getString(mCursor.getColumnIndex("birim_fiyat"));
					String vergi_oran = mCursor.getString(mCursor.getColumnIndex("vergi_oran"));
					String iskonto_oran = mCursor.getString(mCursor.getColumnIndex("iskonto_kodu"));
					String doviz_cinsi = mCursor.getString(mCursor.getColumnIndex("doviz_cinsi"));
					list.add(id);
					list.add(kodu);
					list.add(adi);
					list.add(birim);
					list.add(birim_fiyat);
					list.add(vergi_oran);
					list.add(iskonto_oran);
					list.add(doviz_cinsi);
				} while (mCursor.moveToNext());

			}
		}
		return list;
	}
	

	
	
}
