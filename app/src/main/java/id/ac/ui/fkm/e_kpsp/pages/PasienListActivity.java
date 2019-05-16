package id.ac.ui.fkm.e_kpsp.pages;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.fkm.e_kpsp.R;
import id.ac.ui.fkm.e_kpsp.adapters.PasienAdapter;
import id.ac.ui.fkm.e_kpsp.databases.DataSource;
import id.ac.ui.fkm.e_kpsp.models.Pasien;

import static id.ac.ui.fkm.e_kpsp.Constant.PASIEN_ID;

public class PasienListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lvPasien;
    private SearchView svData;
    private List<Pasien> pasienList;
    private PasienAdapter pasienAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_list);

        svData = findViewById(R.id.svData);
        lvPasien = findViewById(R.id.lvPasien);



        getSupportActionBar().setTitle("KPSP");
        getSupportActionBar().setSubtitle("Pilih pasien");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        svData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                pasienAdapter.clear();

                List<Pasien> resultList = DataSource.getInstance(PasienListActivity.this).search(keyword);

                pasienList.addAll(resultList);

                pasienAdapter.notifyDataSetChanged();
                return false;
            }
        });

        pasienList = new ArrayList<>();

        pasienList.addAll(DataSource.getInstance(PasienListActivity.this).getAll());
        pasienAdapter = new PasienAdapter(PasienListActivity.this, pasienList);

        lvPasien.setAdapter(pasienAdapter);

        lvPasien.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pasien pasien = pasienList.get(position);
        Intent intent = new Intent();
        intent.putExtra(PASIEN_ID, pasien.getId());
        setResult(Activity.RESULT_OK, intent);
        finish();
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
