package com.ahsailabs.ui.fkm.e_kpsp.pages;

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

import com.ahsailabs.ui.fkm.e_kpsp.R;
import com.ahsailabs.ui.fkm.e_kpsp.databases.DataSource;
import com.ahsailabs.ui.fkm.e_kpsp.models.KPSP;
import com.ahsailabs.ui.fkm.e_kpsp.utils.KPSPUtil;

import static com.ahsailabs.ui.fkm.e_kpsp.Constant.PASIEN_ID;
import static com.ahsailabs.ui.fkm.e_kpsp.Constant.RESULT_ARRAY;
import static com.ahsailabs.ui.fkm.e_kpsp.Constant.USIA;

public class KPSPActivity extends AppCompatActivity {
    private List<KPSP> kpspList;
    private TextView tvPertanyaan;
    private ImageView ivGambar;

    private Button btnTidak;
    private Button btnYa;

    private int indexPertanyaan = 0;


    private int usia;
    private long pasienId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpsp);


        usia = getIntent().getIntExtra(USIA, -1);
        pasienId = getIntent().getLongExtra(PASIEN_ID, -1);

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
                    gotoResultPage(usia, resultArray);
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
                    gotoResultPage(usia, resultArray);
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

    private void gotoResultPage(int usia, boolean[] resultArray){
        //go to result page
        Intent intent = new Intent(KPSPActivity.this, ResultActivity.class);
        intent.putExtra(USIA, usia);
        intent.putExtra(PASIEN_ID, pasienId);
        intent.putExtra(RESULT_ARRAY, resultArray);
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
