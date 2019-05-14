package id.ac.ui.fkm.e_kpsp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.ac.ui.fkm.e_kpsp.databases.DataSource;
import id.ac.ui.fkm.e_kpsp.models.Pasien;

public class FormActivity extends AppCompatActivity{
    private EditText etTanggalPeriksa;
    private EditText etTanggalLahir;
    private EditText etNamaAnak;
    private EditText etNamaAyah;
    private EditText etNamaIbu;
    private EditText etAlamat;
    private Button btnLanjut;

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
    }

    private void nextProces() {
        //validasi form
        String strNamaAnak = etNamaAnak.getText().toString();
        String strNamaAyah = etNamaAyah.getText().toString();
        String strNamaIbu = etNamaIbu.getText().toString();
        String strAlamat = etAlamat.getText().toString();
        String strTanggalPeriksa = etTanggalPeriksa.getText().toString();
        String strTanggalLahir = etTanggalLahir.getText().toString();

        /*
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
        */


        //proses : simpan ke database -> buka halaman kpsp
        //TODO : simpan ke database
        Pasien pasien = new Pasien();
        pasien.setNamaAnak(strNamaAnak);
        pasien.setNamaAyah(strNamaAyah);
        pasien.setNamaIbu(strNamaIbu);
        pasien.setAlamat(strAlamat);
        pasien.setTanggalPeriksa(strTanggalPeriksa);
        pasien.setTanggalLahir(strTanggalLahir);

        //DataSource.getInstance(FormActivity.this).savePasien(pasien);


        //TODO : buka halaman kpsp
        Intent kpspIntent  = new Intent(FormActivity.this, KPSPActivity.class);
        kpspIntent.putExtra("usia", getAge(strTanggalLahir));
        startActivity(kpspIntent);

    }

    private int getAge(String dobString){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        Date dobDate = null;
        try {
            dobDate = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dobDate != null){
            Calendar dob = Calendar.getInstance();
            dob.setTime(dobDate);
            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if(today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }

            return age;
        }

        return -1;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //handle up click
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
