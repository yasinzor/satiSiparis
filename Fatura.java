package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Fatura {
	SQLiteDatabase Db;
	SQLiteDatabase Dbread;
	Context Ctx;
	Dbcreate DbHelper;

	public Fatura(Context context) {
		this.Ctx = context;
	}

	public Fatura open() throws SQLException {
		DbHelper = new Dbcreate(Ctx);
		Db = DbHelper.getWritableDatabase();
		Dbread = DbHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		DbHelper.close();
	}
	

	public String faturaTutar(String Toplam, String ToplamVergi,String GToplam, String ID) {
		// Cursor mCursor=Db.rawQuery("UPDATE fatura SET Toplam='"+Toplam+"' AND
		// SET toplamVergi='"+ToplamVergi+"' WHERE id='"+ID+"'",null);
		ContentValues cv = new ContentValues();
		cv.put("Toplam", Toplam); // These Fields should be your String values
									// of actual column names
		cv.put("toplamVergi", ToplamVergi);
		cv.put("Gtoplam", GToplam);
		// cv.put("Field2","Male");

		Db.update("fatura", cv, "fat_id " + "=" + ID, null);
		return null;
	}
	//fatura bilgilerini doldurmak için
	public String faturaKayit(String faturaSeri, String FaturaNo, String tarih, String ozel, String plasiyer,
			String cari_id) { 

		ContentValues values = new ContentValues();
		values.put("faturaSeriNo", faturaSeri);
		values.put("faturaNo", FaturaNo);
		values.put("tarih", tarih);
		values.put("Ozel", ozel);
		values.put("Plasiyer", plasiyer);
		values.put("CariID", cari_id);
		values.put("fat_dbkayit", "0");

		Db.insert("fatura", null, values);
		String query = "SELECT fat_id from fatura order by ROWID DESC limit 1";
		Cursor c = Db.rawQuery(query, null);
		long lastId = 0;
		if (c != null && c.moveToFirst()) {
			lastId = c.getLong(0); 
		}
		return Long.toString(lastId);
	}
	


	public String getFaturaNo(){
		Cursor mCursor = Dbread.rawQuery("SELECT faturaNo FROM fatura;", null);
		String no = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					no = mCursor.getString(mCursor.getColumnIndex("faturaNo"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					// list.add(id);
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
	
	public String getFaturaNobySeri(String seri){
		Cursor mCursor = Dbread.rawQuery("SELECT faturaNo FROM fatura where faturaSeriNo ='"+seri+"';", null);
		String no = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					no = mCursor.getString(mCursor.getColumnIndex("faturaNo"));
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
	
	public ArrayList<HashMap<String, String>> FaturaBilgi(String fseri,String fno){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		/*WHERE f.faturaSeriNo='"+fseri+"' and f.faturaNo='"+fno+"'*/
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM stok_hareket AS sh INNER JOIN fatura As f ON f.fat_id=sh.fatura_id where faturaNo='"+fno+"';", null);
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
	
	public ArrayList<HashMap<String, String>> FaturaBilgiByCari(String cariID){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		/*WHERE f.faturaSeriNo='"+fseri+"' and f.faturaNo='"+fno+"'*/
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM stok_hareket AS sh INNER JOIN fatura As f ON f.fat_id=sh.fatura_id where CariID='"+cariID+"';", null);
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
	
}
