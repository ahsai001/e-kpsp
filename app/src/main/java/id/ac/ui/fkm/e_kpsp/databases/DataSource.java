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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import id.ac.ui.fkm.e_kpsp.models.KPSP;
import id.ac.ui.fkm.e_kpsp.models.Pasien;
import id.ac.ui.fkm.e_kpsp.models.Statistic;

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

    private Pasien fetchRow(Cursor cursor){
        Pasien pasien = new Pasien();

        //isi objek pasien dengan data dari cursor
        pasien.setId(cursor.getLong(0));
        pasien.setNamaAnak(cursor.getString(1));
        pasien.setNamaAyah(cursor.getString(2));
        pasien.setNamaIbu(cursor.getString(3));
        pasien.setAlamat(cursor.getString(4));
        pasien.setTanggalPeriksa(cursor.getString(5));
        pasien.setTanggalLahir(cursor.getString(6));

        return pasien;
    }


    public long savePasien(Pasien pasien){
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

        return id;
    }

    public List<Pasien> getAll(){
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM pasien", null);
        cursor.moveToFirst();

        List<Pasien> pasienList = new ArrayList<>();
        //

        while(!cursor.isAfterLast()){
            Pasien pasien = fetchRow(cursor);

            pasienList.add(pasien);

            cursor.moveToNext();
        }

        cursor.close();
        close();

        return pasienList;
    }

    public Pasien getPasien(long id){
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM pasien WHERE id=?", new String[]{Long.toString(id)});
        cursor.moveToFirst();

        Pasien pasien = fetchRow(cursor);

        cursor.close();
        close();
        return pasien;
    }

    public List<Pasien> search(String keyword){
        open();
        List<Pasien> pasienList = new ArrayList<>();
        String sql = "SELECT * FROM pasien WHERE nama_anak LIKE ? OR nama_ayah LIKE ? OR nama_ibu LIKE ?";
        Cursor cursor = database.rawQuery(sql,
                new String[]{"%"+keyword+"%","%"+keyword+"%", "%"+keyword+"%"});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Pasien pasien = fetchRow(cursor);
            pasienList.add(pasien);

            cursor.moveToNext();
        }

        cursor.close();
        close();
        return pasienList;
    }


    public long saveHistory(long pasienId, int usia, String jawabanYa, String jawabanTidak, int hasilId){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pasien_id", pasienId);
        contentValues.put("usia", usia);
        contentValues.put("jawaban_ya", jawabanYa);
        contentValues.put("jawaban_tidak", jawabanTidak);
        contentValues.put("hasil_id", hasilId);


        Calendar today = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String todayDate = simpleDateFormat.format(today.getTime());

        contentValues.put("timestamp", todayDate);

        long id = database.insert("history", null, contentValues);
        close();

        return id;
    }

    public Statistic getStatistic(String monthYear){
        open();

        if(monthYear.length() < 7){
            monthYear = "0"+monthYear;
        }

        Cursor cursor = database.rawQuery("SELECT count(id), hasil_id FROM history WHERE timestamp LIKE ? GROUP BY hasil_id",
                new String[]{"%"+monthYear});

        cursor.moveToFirst();

        Statistic statistic = new Statistic();

        while (!cursor.isAfterLast()){
            int count = cursor.getInt(0);
            int hasil_id = cursor.getInt(1);

            if(hasil_id == 1){
                statistic.setJumlahSesuai(count);
            } else if(hasil_id == 2){
                statistic.setJumlahMeragukan(count);
            } else if (hasil_id == 3){
                statistic.setJumlahMeragukan(count);
            }

            cursor.moveToNext();
        }

        cursor.close();

        close();

        return statistic;
    }


}
