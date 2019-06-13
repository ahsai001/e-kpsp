package com.ahsailabs.ui.fkm.e_kpsp.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ahsailabs.ui.fkm.e_kpsp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnMulaiKPSP = findViewById(R.id.btnMulaiKPSP);
        Button btnTentang = findViewById(R.id.btnTentang);
        Button btnLaporan = findViewById(R.id.btnLaporan);
        Button btnKetentuan = findViewById(R.id.btnKetentuan);

        btnMulaiKPSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        btnLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        btnKetentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HowToActivity.class);
                startActivity(intent);
            }
        });


    }
}
