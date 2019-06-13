package com.ahsailabs.ui.fkm.e_kpsp.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.ahsailabs.ui.fkm.e_kpsp.BuildConfig;
import com.ahsailabs.ui.fkm.e_kpsp.R;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("KPSP");
        getSupportActionBar().setSubtitle("Tentang Aplikasi");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvVersion = findViewById(R.id.tvVersion);
        tvVersion.setText("Version : "+BuildConfig.VERSION_NAME);
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
