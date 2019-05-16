package id.ac.ui.fkm.e_kpsp.pages;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.w3c.dom.Text;

import java.util.Calendar;

import id.ac.ui.fkm.e_kpsp.R;
import id.ac.ui.fkm.e_kpsp.databases.DataSource;
import id.ac.ui.fkm.e_kpsp.models.Statistic;

public class ReportActivity extends AppCompatActivity {
    private TextView tvSesuai, tvMeragukan, tvPenyimpangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        getSupportActionBar().setTitle("KPSP");
        getSupportActionBar().setSubtitle("Laporan Hasil Perkembangan Anak");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView tvStringDate = findViewById(R.id.tvStringDate);
        Button btnChooseDate = findViewById(R.id.btnChooseDate);

        tvSesuai = findViewById(R.id.tvSesuai);
        tvMeragukan = findViewById(R.id.tvMeragukan);
        tvPenyimpangan = findViewById(R.id.tvPenyimpangan);


        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ReportActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                tvStringDate.setText((selectedMonth+1)+"-"+selectedYear);

                                Statistic statistic = DataSource.getInstance(ReportActivity.this).getStatistic((selectedMonth+1)+"-"+selectedYear);

                                tvSesuai.setText("Jumlah Sesuai : " + statistic.getJumlahSesuai());
                                tvMeragukan.setText("Jumlah Meragukan : " + statistic.getJumlahMeragukan());
                                tvPenyimpangan.setText("Jumlah Penyimpangan : " + statistic.getJumlahPenyimpangan());

                            }
                        },today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setTitle("Select trading month")
                        .build()
                        .show();

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
