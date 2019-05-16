package id.ac.ui.fkm.e_kpsp.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.ac.ui.fkm.e_kpsp.R;

public class ResultDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        String title = getIntent().getStringExtra("title");
        int usia = getIntent().getIntExtra("usia", -1);
        String jawabanTidakString = getIntent().getStringExtra("jawaban_tidak_string");
        String totalJawabanYaString = getIntent().getStringExtra("total_jawaban_ya_string");
        String totalJawabanTidakString = getIntent().getStringExtra("total_jawaban_tidak_string");


        getSupportActionBar().setTitle("KPSP");
        getSupportActionBar().setSubtitle("Rincian Hasil Perkembangan Anak");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvUsia = findViewById(R.id.tvUsia);
        TextView tvContent1 = findViewById(R.id.tvContent1);
        TextView tvContent2 = findViewById(R.id.tvContent2);
        TextView tvContent3 = findViewById(R.id.tvContent3);
        TextView tvJawabanYa = findViewById(R.id.tvJawabanYa);
        TextView tvJawabanTidak = findViewById(R.id.tvJawabanTidak);
        Button btnHome = findViewById(R.id.btnHome);


        tvTitle.setText(title);
        tvUsia.setText("(Umur "+usia+" bulan)");
        tvJawabanYa.setText(totalJawabanYaString);
        tvJawabanTidak.setText(totalJawabanTidakString);


        if(title.startsWith("Sesuai")){
            tvContent1.setText("Orangtua atau pengasuh anak sudah mengasuh anak dengan baik. " +
                    "Pola asuh anak selanjutnya terus lakukan sesuai dengan stimulasi, sesuai dengan umur dan kesiapan anak." +
                    "Keterlibatan orangtua sangat baik dalam setiap kesempatan stimulasi. " +
                    "Tidak usah mengambil momen khusus. Laksanakan stimulasi sebagai kegiatan sehari-hari yang terarah. " +
                    "Ikutkan anak setiap kegiatan posyandu. Teruskan pola asuh dan ikutkan dalam kegiatan posyandu.");
            tvContent2.setVisibility(View.GONE);
            tvContent3.setVisibility(View.GONE);
        } else if(title.startsWith("Meragukan")){
            tvContent1.setText("Fokuskan pada nomor jawaban tidak berikut ini.");
            tvContent2.setText(jawabanTidakString);
            tvContent3.setText("Pantau terus perkembangan anak anda. Konsultasikan nomor jawaban tidak, " +
                    "mintalah jenis stimulasi apa yang diberikan lebih sering, cari kemungkinan penyakit, " +
                    "ulangi uji kpsp 2 minggu lagi. Jika masih meragukan, segera rujuk ke RS atau poli anak.");
        } else if(title.startsWith("Penyimpangan")){
            tvContent1.setText("Terdapat penyimpangan perkembangan anak pada nomor berikut ini.");
            tvContent2.setText(jawabanTidakString);
            tvContent3.setText("Segera rujuk ke RS atau poli anak.");
        }


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
