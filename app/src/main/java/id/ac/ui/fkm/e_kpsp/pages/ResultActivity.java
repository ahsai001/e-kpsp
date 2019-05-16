package id.ac.ui.fkm.e_kpsp.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import id.ac.ui.fkm.e_kpsp.R;

public class ResultActivity extends AppCompatActivity {
    private int usia;
    private String jawabanTidakString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        usia = getIntent().getIntExtra("usia", -1);
        boolean[] resultArray = getIntent().getBooleanArrayExtra("result_array");

        getSupportActionBar().setTitle("KPSP");
        getSupportActionBar().setSubtitle("Hasil Perkembangan Anak");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvUsia = findViewById(R.id.tvUsia);
        TextView tvContent = findViewById(R.id.tvContent);
        final TextView tvJawabanYa = findViewById(R.id.tvJawabanYa);
        final TextView tvJawabanTidak = findViewById(R.id.tvJawabanTidak);
        Button btnRincian = findViewById(R.id.btnRincian);
        Button btnUlangi = findViewById(R.id.btnUlangi);


        int totalJawabanYa = 0;
        int totalJawabanTidak = 0;
        for (int i = 0; i < resultArray.length; i++){
            if(resultArray[i]){
                totalJawabanYa++;
            } else {
                totalJawabanTidak++;
                jawabanTidakString += " "+(i+1);
            }
        }
        jawabanTidakString += " ";

        if(totalJawabanYa >= 9){
            //sesuai
            tvTitle.setText("Sesuai (S)");
            tvContent.setText("Selamat perkembangan anak anda sesuai. Teruskan polah asuh dan ikutkan dalam kegiatan posyandu.");
        } else if(totalJawabanYa >= 7){
            //meragukan
            tvTitle.setText("Meragukan (M)");
            tvContent.setText("Pantau terus perkembangan anak anda. Cari kemungkinan penyakit, ulangi uji KPSP 2 minggu lagi. " +
                    "Jika masih meragukan, segera rujuk ke RS atau poli anak.");
        } else {
            //penyimpangan
            tvTitle.setText("Penyimpangan (P)");
            tvContent.setText("Ada penyimpangan dalam perkembangan anak anda, segera rujuk ke RS atau poli anak.");
        }

        tvUsia.setText("(Umur "+usia+" bulan)");

        tvJawabanYa.setText("Jawaban Ya : "+totalJawabanYa);
        tvJawabanTidak.setText("Jawaban Tidak : "+totalJawabanTidak);



        btnRincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ResultDetailActivity.class);
                intent.putExtra("title", tvTitle.getText().toString());
                intent.putExtra("usia", usia);
                intent.putExtra("jawaban_tidak_string", jawabanTidakString);
                intent.putExtra("total_jawaban_ya_string", tvJawabanYa.getText().toString());
                intent.putExtra("total_jawaban_tidak_string", tvJawabanTidak.getText().toString());
                startActivity(intent);

                finish();
            }
        });

        btnUlangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kpspIntent  = new Intent(ResultActivity.this, KPSPActivity.class);
                kpspIntent.putExtra("usia", usia);
                startActivity(kpspIntent);

                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
