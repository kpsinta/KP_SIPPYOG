package jls.com.sippyog.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jls.com.sippyog.CustomFilter.CustomFilter_DetilTiketHilang;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;

public class Adapter_DetilTiketHilang extends RecyclerView.Adapter<Adapter_DetilTiketHilang.MyViewHolder> implements Filterable {
    public List<Model_KendaraanMasuk> kndFilter;
    public List<Model_KendaraanMasuk> kendaraan = new ArrayList<>();
    private Context context;
    private Adapter_DetilTiketHilang.RecyclerViewClickListener mListener;
    CustomFilter_DetilTiketHilang filter_kendaraan;

    public Adapter_DetilTiketHilang(List<Model_KendaraanMasuk> kendaraan, Context context, Adapter_DetilTiketHilang.RecyclerViewClickListener mListener) {
        this.kndFilter = kendaraan;
        this.kendaraan = kendaraan;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Adapter_DetilTiketHilang.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_detil_tiket_hilang_dan_jumlah_kendaraan, viewGroup, false);
        return new Adapter_DetilTiketHilang.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_DetilTiketHilang.MyViewHolder myViewHolder, int i) {
        final Model_KendaraanMasuk knd = kendaraan.get(i);
        Log.d("ID KendaraanMasuk : ", knd.getId_tiket().toString());
        Log.d("Jenis Kendaraan : ", knd.getJenis_kendaraan());
        Log.d("No Plat : ", knd.getNo_plat());
        Log.d("Waktu Keluar : ", knd.getWaktu_keluar());

        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
        final String inputDateStr = knd.getWaktu_keluar();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String outputDateStr = outputFormat.format(date);

        myViewHolder.jenis_kendaraan.setText(knd.getJenis_kendaraan());
        myViewHolder.no_plat.setText(knd.getNo_plat());
        myViewHolder.waktu_keluar.setText(outputDateStr);

    }

    @Override
    public int getItemCount() {
        return kendaraan.size();
    }

    @Override
    public Filter getFilter() {
        if (filter_kendaraan == null) {
            filter_kendaraan = new CustomFilter_DetilTiketHilang((ArrayList<Model_KendaraanMasuk>) kndFilter, this);
        }
        return filter_kendaraan;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_DetilTiketHilang.RecyclerViewClickListener mListener;
        protected TextView waktu_keluar, no_plat, jenis_kendaraan;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView, Adapter_DetilTiketHilang.RecyclerViewClickListener listener) {
            super(itemView);
            waktu_keluar = (TextView) itemView.findViewById(R.id.waktu_keluar);
            no_plat = (TextView) itemView.findViewById(R.id.no_plat);
            jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.row_container:
                    //                mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
       // void onRowClick(View view, int position);
    }
}
    

