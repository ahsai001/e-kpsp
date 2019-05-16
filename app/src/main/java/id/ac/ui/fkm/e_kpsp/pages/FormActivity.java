package id.ac.ui.fkm.e_kpsp.pages;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.os.IResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.ui.fkm.e_kpsp.R;
import id.ac.ui.fkm.e_kpsp.databases.DataSource;
import id.ac.ui.fkm.e_kpsp.models.Pasien;
import id.ac.ui.fkm.e_kpsp.utils.KPSPUtil;

import static id.ac.ui.fkm.e_kpsp.Constant.PASIEN_ID;
import static id.ac.ui.fkm.e_kpsp.Constant.USIA;

public class FormActivity extends AppCompatActivity{
    private static final int REQUEST_CODE_FOR_PASIEN = 1221;
    private EditText etTanggalPeriksa;
    private EditText etTanggalLahir;
    private EditText etNamaAnak;
    private EditText etNamaAyah;
    private EditText etNamaIbu;
    private EditText etAlamat;
    private Button btnLanjut;
    private CheckBox cbKeluahan;

    private long pasienId;
    private Pasien pasien = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setTitle("Identitas Balita");
        getSupportActionBar().setSubtitle("lengkapi data-data berikut");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNamaAnak = findViewById(R.id.etNamaAnak);
        etNamaAyah = findViewById(R.id.etNamaAyah);
        etNamaIbu = findViewById(R.id.etNamaIbu);
        etAlamat = findViewById(R.id.etAlamat);

        etTanggalPeriksa = findViewById(R.id.etTanggalPeriksa);
        etTanggalPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar calendar  = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String strTanggalPeriksa = simpleDateFormat.format(calendar.getTime());
                                etTanggalPeriksa.setText(strTanggalPeriksa);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        etTanggalLahir = findViewById(R.id.etTanggalLahir);
        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar calendar  = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String strTanggalPeriksa = simpleDateFormat.format(calendar.getTime());
                                etTanggalLahir.setText(strTanggalPeriksa);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnLanjut = findViewById(R.id.btnLanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextProces();
            }
        });


        cbKeluahan = findViewById(R.id.cbKeluhan);
    }

    private void nextProces() {
        //validasi form
        String strNamaAnak = etNamaAnak.getText().toString();
        String strNamaAyah = etNamaAyah.getText().toString();
        String strNamaIbu = etNamaIbu.getText().toString();
        String strAlamat = etAlamat.getText().toString();
        String strTanggalPeriksa = etTanggalPeriksa.getText().toString();
        String strTanggalLahir = etTanggalLahir.getText().toString();


        if(strNamaAnak.isEmpty()){
            etNamaAnak.setError("Nama anak harus diisi");
            return;
        }

        if(strNamaAyah.isEmpty()){
            etNamaAyah.setError("Nama ayah harus diisi");
            return;
        }

        if(strNamaIbu.isEmpty()){
            etNamaIbu.setError("Nama ibu harus diisi");
            return;
        }

        if(strAlamat.isEmpty()){
            etAlamat.setError("Alamat harus diisi");
            return;
        }

        if(strTanggalPeriksa.isEmpty()){
            etTanggalPeriksa.setError("Tanggal periksa harus diisi");
            etTanggalPeriksa.requestFocus();
            return;
        }

        if(strTanggalLahir.isEmpty()){
            etTanggalLahir.setError("Tanggal lahir harus diisi");
            return;
        }



        boolean needToSaved = true;

        if(pasien != null) {
            if (strNamaAnak.equals(pasien.getNamaAnak())
                && strNamaAyah.equals(pasien.getNamaAyah())
                && strNamaIbu.equals(pasien.getNamaIbu())
                && strTanggalLahir.equals(pasien.getTanggalLahir())){
                needToSaved = false;
            }
        }

        //proses : simpan ke database -> buka halaman kpsp
        //simpan ke database
        if(needToSaved) {
            Pasien pasien = new Pasien();
            pasien.setNamaAnak(strNamaAnak);
            pasien.setNamaAyah(strNamaAyah);
            pasien.setNamaIbu(strNamaIbu);
            pasien.setAlamat(strAlamat);
            pasien.setTanggalPeriksa(strTanggalPeriksa);
            pasien.setTanggalLahir(strTanggalLahir);

            pasienId = DataSource.getInstance(FormActivity.this).savePasien(pasien);
        }


        //buka halaman kpsp
        int usia = getMonthAge(strTanggalLahir);
        if(cbKeluahan.isChecked()){
            //ada keluhan
            usia = KPSPUtil.getPrevMonthOf(usia);
        } else {
            //tidak ada keluhan
            //do nothing
        }


        Intent kpspIntent  = new Intent(FormActivity.this, KPSPActivity.class);
        kpspIntent.putExtra(USIA, usia);
        kpspIntent.putExtra(PASIEN_ID, pasienId);
        startActivity(kpspIntent);
    }



    private int getMonthAge(String dobString){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        try {
            Date dobDate = sdf.parse(dobString);

            if(dobDate != null){
                Calendar dob = Calendar.getInstance();
                dob.setTime(dobDate);

                Calendar sameDateThisMonth = Calendar.getInstance();
                sameDateThisMonth.set(Calendar.DAY_OF_MONTH,dob.get(Calendar.DAY_OF_MONTH));

                int monthsBetween = 0;
                monthsBetween += sameDateThisMonth.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
                monthsBetween += (sameDateThisMonth.get(Calendar.YEAR) - dob.get(Calendar.YEAR)) * 12;


                Calendar today = Calendar.getInstance();

                int dayDiff = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);
                if(dayDiff > 15){
                    monthsBetween++;
                } else if(dayDiff < -15){
                    monthsBetween--;
                }

                return monthsBetween;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.form_action_choose_pasien:
                Intent intent = new Intent(FormActivity.this, PasienListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_PASIEN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOR_PASIEN){
            if(resultCode == Activity.RESULT_OK){
                pasienId = data.getLongExtra(PASIEN_ID, -1);

                pasien = DataSource.getInstance(FormActivity.this).getPasien(pasienId);

                etNamaAnak.setText(pasien.getNamaAnak());
                etNamaAyah.setText(pasien.getNamaAyah());
                etNamaIbu.setText(pasien.getNamaIbu());
                etAlamat.setText(pasien.getAlamat());
                etTanggalLahir.setText(pasien.getTanggalLahir());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
