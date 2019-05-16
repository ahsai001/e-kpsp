package id.ac.ui.fkm.e_kpsp.pages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.ac.ui.fkm.e_kpsp.R;
import id.ac.ui.fkm.e_kpsp.databases.DataSource;
import id.ac.ui.fkm.e_kpsp.models.KPSP;
import id.ac.ui.fkm.e_kpsp.utils.KPSPUtil;

public class KPSPActivity extends AppCompatActivity {
    private List<KPSP> kpspList;
    private TextView tvPertanyaan;
    private ImageView ivGambar;

    private Button btnTidak;
    private Button btnYa;

    int indexPertanyaan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpsp);


        final int usia = getIntent().getIntExtra("usia", -1);

        getSupportActionBar().setTitle("KPSP");
        getSupportActionBar().setSubtitle("Umur "+usia+" bulan");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvPertanyaan = findViewById(R.id.tvPertanyaan);
        ivGambar = findViewById(R.id.ivGambar);
        btnYa = findViewById(R.id.btnYa);
        btnTidak = findViewById(R.id.btnTidak);




        kpspList = DataSource.getInstance(KPSPActivity.this).getKPSP(usia);

        final boolean[] resultArray = new boolean[kpspList.size()];


        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultArray[indexPertanyaan] = true;

                if(indexPertanyaan+1 >= kpspList.size()){
                    //goto result
                    saveAndGotoResultPage(usia, resultArray);
                } else {
                    indexPertanyaan++;
                    loadData();
                }
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultArray[indexPertanyaan] = false;

                if(indexPertanyaan+1 >= kpspList.size()){
                    //goto result
                    saveAndGotoResultPage(usia, resultArray);
                } else {
                    indexPertanyaan++;
                    loadData();
                }
            }
        });


        if(kpspList.size() > 0){
            indexPertanyaan = 0;
            loadData();
        } else {
            tvPertanyaan.setText("Maaf saat ini, data kpsp untuk usia "+usia+" bulan belum ada, " +
                    "silakan datang kembali ketika sudah mencapai usia "+ KPSPUtil.getNextMonthOf(usia));

            btnYa.setVisibility(View.GONE);
            btnTidak.setVisibility(View.GONE);
        }
    }

    private void saveAndGotoResultPage(int usia, boolean[] resultArray){
        //save to database

        //go to result page
        Intent intent = new Intent(KPSPActivity.this, ResultActivity.class);
        intent.putExtra("usia", usia);
        intent.putExtra("result_array", resultArray);
        startActivity(intent);

        finish();
    }

    private void loadData(){
        String pertanyaan = kpspList.get(indexPertanyaan).getPertanyaan();
        if(!TextUtils.isEmpty(pertanyaan)) {
            tvPertanyaan.setText((indexPertanyaan+1)+". "+pertanyaan);
        } else {
            tvPertanyaan.setText("Maaf pertanyaan kosong");
        }

        Bitmap bmpGambar = kpspList.get(indexPertanyaan).getGambar();
        if(bmpGambar != null){
            ivGambar.setImageBitmap(bmpGambar);
        } else {
            ivGambar.setImageDrawable(null);
        }
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
