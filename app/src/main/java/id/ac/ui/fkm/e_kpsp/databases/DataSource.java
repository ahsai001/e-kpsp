package id.ac.ui.fkm.e_kpsp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.fkm.e_kpsp.models.KPSP;
import id.ac.ui.fkm.e_kpsp.models.Pasien;

public class DataSource {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    private static volatile DataSource instance;

    private DataSource(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public static DataSource getInstance(Context context) {
        if (instance == null) {
            synchronized (DataSource.class){
                if (instance == null) {
                    instance = new DataSource(context);
                }
            }
        }
        return instance;
    }


    private void open() {
        this.database = openHelper.getWritableDatabase();
    }


    private void close() {
        if (database != null) {
            database.close();
        }
    }

    public List<KPSP> getKPSP(int usia) {
        open();
        List<KPSP> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM kpsp WHERE usia=?",
                new String[]{String.valueOf(usia)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KPSP kpsp = new KPSP();

            //convert from db to KPSP object
            kpsp.setId(cursor.getLong(0));
            kpsp.setUsia(cursor.getInt(1));
            kpsp.setPertanyaan(cursor.getString(2));

            byte[] byteGambar = cursor.getBlob(3);
            if(byteGambar != null) {
                Bitmap bmpGambar = BitmapFactory.decodeByteArray(byteGambar, 0, byteGambar.length);
                kpsp.setGambar(bmpGambar);
            } else {
                kpsp.setGambar(null);
            }


            list.add(kpsp);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }


    public boolean savePasien(Pasien pasien){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_anak", pasien.getNamaAnak());
        contentValues.put("nama_ayah", pasien.getNamaAyah());
        contentValues.put("nama_ibu", pasien.getNamaIbu());
        contentValues.put("alamat", pasien.getAlamat());
        contentValues.put("tanggal_periksa", pasien.getTanggalPeriksa());
        contentValues.put("tanggal_lahir", pasien.getTanggalLahir());

        long id = database.insert("pasien", null, contentValues);
        close();

        return id > 0;
    }


}
