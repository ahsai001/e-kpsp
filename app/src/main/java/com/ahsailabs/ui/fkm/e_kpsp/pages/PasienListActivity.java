package com.ahsailabs.ui.fkm.e_kpsp.pages;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.ahsailabs.ui.fkm.e_kpsp.R;
import com.ahsailabs.ui.fkm.e_kpsp.adapters.PasienAdapter;
import com.ahsailabs.ui.fkm.e_kpsp.databases.DataSource;
import com.ahsailabs.ui.fkm.e_kpsp.models.Pasien;

import static com.ahsailabs.ui.fkm.e_kpsp.Constant.PASIEN_ID;

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

        registerForContextMenu(lvPasien);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.lvPasien){
            menu.setHeaderTitle("Choose action");
            getMenuInflater().inflate(R.menu.list_contex_menu, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();

        if (item.getItemId() == R.id.list_context_menu_action_delete) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Pasien pasien = pasienList.get(acmi.position);

            DataSource.getInstance(PasienListActivity.this).removePasien(pasien);

            //refresh list
            pasienList.remove(acmi.position);
            pasienAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
