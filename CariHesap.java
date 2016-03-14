package com.ulku.ulkubilgisayar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CariHesap {
	SQLiteDatabase Db;
	SQLiteDatabase Dbread;
	Context Ctx;
	Dbcreate DbHelper;

	public CariHesap(Context context) {
		this.Ctx = context;
	}

	public CariHesap open() throws SQLException {
		DbHelper = new Dbcreate(Ctx);
		Db = DbHelper.getWritableDatabase();
		Dbread = DbHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		DbHelper.close();
	}
	

	public List<HashMap<String, String>> CariAdiSorgu(String cari) throws SQLException {
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM cari WHERE adi LIKE'%" + cari + "%';", null);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		//HashMap<String, String> map;
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String adi = mCursor.getString(mCursor.getColumnIndex("adi"));
					String id = mCursor.getString(mCursor.getColumnIndex("kodu"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("adi", adi);
					list.add(map);
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					// list.add(id);
				} while (mCursor.moveToNext());

			}
		}
		return list;
	}
	public String cariIDSorgu(String CariID) throws SQLException {
		// cari id ile barkod kýsmý için cagýrma
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM cari WHERE id ='" + CariID + "';", null);
		String adi = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					adi = mCursor.getString(mCursor.getColumnIndex("adi"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					// list.add(id);
				} while (mCursor.moveToNext());

			}
		}

		return adi;
	}
	public ArrayList<HashMap<String, String>> cariYetkili(String CariAdi) throws SQLException {
		// cari id ile barkod kýsmý için cagýrma
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM adayCari WHERE unvani ='" + CariAdi+ "';", null);
		String yetkiliAdi1 = "";
		String yetkiliAdi2 = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					yetkiliAdi1 = mCursor.getString(mCursor.getColumnIndex("yetkili_1"));
					yetkiliAdi2 = mCursor.getString(mCursor.getColumnIndex("yetkili_2"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("yetkili1", yetkiliAdi1);
					map.put("yetkili2", yetkiliAdi2);
					list.add(map);
				} while (mCursor.moveToNext());

			}
		}

		return list;
	}

	public String cariEkleme(String kodu, String unvan, String unvan2){
		ContentValues values = new ContentValues();
		
		values.put("kodu", kodu);
		values.put("adi", unvan);
		values.put("unvan2", unvan2);
	
		Db.insert("cari", null, values);
		//Db.close();
		return "kayit tamamlandý.";
	}
	
	public String CariDbKontrol(String Id,String adi){
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM cari WHERE adi='"+adi+"';", null);
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
	
	public String AdayCariEkleme(String kodu,String unvan,String tel,String fax,String mail,String site,String adres,String il,String ilce,String yetkili1,String yetkili2,String dbKayit){
		ContentValues values = new ContentValues();
		values.put("kodu", kodu);
		values.put("unvani", unvan);
		values.put("tel",tel);
		values.put("fax", fax);
		values.put("mail", mail);
		values.put("site", site);
		values.put("adres", adres);
		values.put("adres_il", il);
		values.put("adres_ilce", ilce);
		values.put("yetkili_1", yetkili1);
		values.put("yetkili_2", yetkili2);
		values.put("dbKayit",dbKayit);
		Db.insert("adayCari", null, values);
		return "Kayýt Tamamlandý.";
	}
	public List<HashMap<String, String>> AdayCariSorgu(String unvan){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM adayCari WHERE unvani LIKE'%" + unvan + "%';", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String unvani = mCursor.getString(mCursor.getColumnIndex("unvani"));
					String kodu=mCursor.getString(mCursor.getColumnIndex("kodu"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", kodu);
					map.put("adi", unvani);
					list.add(map);
				} while (mCursor.moveToNext());
			}
		}
		
		return list;
	}
	public String AdayCariDbKontrol(String Id,String unvan){
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM adayCari WHERE unvani='"+unvan+"';", null);
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
	

	public ArrayList<HashMap<String, String>> AdayCariBilgi(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM adayCari;", null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id= mCursor.getString(mCursor.getColumnIndex("id"));
					String unvan = mCursor.getString(mCursor.getColumnIndex("unvani"));
					String tel= mCursor.getString(mCursor.getColumnIndex("tel"));
					String mail= mCursor.getString(mCursor.getColumnIndex("mail"));
					String fax= mCursor.getString(mCursor.getColumnIndex("fax"));
					String site= mCursor.getString(mCursor.getColumnIndex("site"));
					String adres= mCursor.getString(mCursor.getColumnIndex("adres"));
					String il= mCursor.getString(mCursor.getColumnIndex("adres_il"));
					String ilce= mCursor.getString(mCursor.getColumnIndex("adres_ilce"));
					String yetkiliAdi1 = mCursor.getString(mCursor.getColumnIndex("yetkili_1"));
					String yetkiliAdi2 = mCursor.getString(mCursor.getColumnIndex("yetkili_2"));
					String dbKayit = mCursor.getString(mCursor.getColumnIndex("dbKayit"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("id", id);
					map.put("unvani", unvan);
					map.put("tel", tel);
					map.put("mail", mail);
					map.put("fax", fax);
					map.put("site", site);
					map.put("adres", adres);
					map.put("il", il);
					map.put("ilce", ilce);
					map.put("yetkili1", yetkiliAdi1);
					map.put("yetkili2", yetkiliAdi2);
					map.put("dbKayit", dbKayit);
					list.add(map);
				
				}while (mCursor.moveToNext());
			}
		}
		return list;
	}
    
	public String cariAdiGetir(String mikroKodu) throws SQLException {
		// cari id ile barkod kýsmý için cagýrma
		Cursor mCursor = Dbread.rawQuery("SELECT * FROM cari WHERE kodu ='" + mikroKodu + "';", null);
		String adi = "";
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					adi = mCursor.getString(mCursor.getColumnIndex("adi"));
					// String
					// id=mCursor.getString(mCursor.getColumnIndex("id"));
					// list.add(id);
				} while (mCursor.moveToNext());

			}
		}

		return adi;
	}
	public void adayCariKoduGuncele(String ID,String kod){
		ContentValues cv = new ContentValues();
		cv.put("kodu", kod); 
		cv.put("dbKayit", "1");
		Db.update("adayCari", cv, "id " + "=" + ID, null);
	}

}
