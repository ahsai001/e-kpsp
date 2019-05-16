package id.ac.ui.fkm.e_kpsp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import id.ac.ui.fkm.e_kpsp.R;
import id.ac.ui.fkm.e_kpsp.models.Pasien;

public class PasienAdapter extends ArrayAdapter<Pasien> {

    public PasienAdapter(Context context, List<Pasien> pasienList){
        super(context, R.layout.pasien_item_view, pasienList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.pasien_item_view, null);
        }

        TextView tvNamaAnak = view.findViewById(R.id.tvNamaAnak);
        TextView tvNamaAyah = view.findViewById(R.id.tvNamaAyah);
        TextView tvNamaIbu = view.findViewById(R.id.tvNamaIbu);
        TextView tvTanggalLahir = view.findViewById(R.id.tvTanggalLahir);


        Pasien pasien = getItem(position);

        tvNamaAnak.setText(pasien.getNamaAnak());
        tvNamaAyah.setText(pasien.getNamaAyah());
        tvNamaIbu.setText(pasien.getNamaIbu());
        tvTanggalLahir.setText(pasien.getTanggalLahir());

        return view;
    }
}
