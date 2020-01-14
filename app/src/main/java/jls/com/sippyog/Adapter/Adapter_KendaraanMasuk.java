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

import jls.com.sippyog.CustomFilter.CustomFilter_KendaraanMasuk;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;

public class Adapter_KendaraanMasuk  extends RecyclerView.Adapter<Adapter_KendaraanMasuk.MyViewHolder> implements Filterable {
    public List<Model_KendaraanMasuk> kndmskFilter;
    public List<Model_KendaraanMasuk> kendaraanmasuk = new ArrayList<>();
    private Context context;
    private Adapter_KendaraanMasuk.RecyclerViewClickListener mListener;
    CustomFilter_KendaraanMasuk filter_kendaraanmasuk;

    public Adapter_KendaraanMasuk(List<Model_KendaraanMasuk> kendaraanmasuk, Context context, Adapter_KendaraanMasuk.RecyclerViewClickListener mListener) {
        this.kndmskFilter = kendaraanmasuk;
        this.kendaraanmasuk = kendaraanmasuk;
        this.context = context;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public Adapter_KendaraanMasuk.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_kendaraan_masuk, viewGroup, false);
        return new Adapter_KendaraanMasuk.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_KendaraanMasuk.MyViewHolder myViewHolder, int i) {
        final Model_KendaraanMasuk kndmsk = kendaraanmasuk.get(i);
        Log.d("ID KendaraanMasuk : ",kndmsk.getId_tiket().toString());
        Log.d("Kode Tiket : ",kndmsk.getKode_tiket());
        Log.d("Jenis Kendaraan : ",kndmsk.getJenis_kendaraan());
        Log.d("No Plat : ",kndmsk.getNo_plat());
        Log.d("Waktu Masuk : ",kndmsk.getWaktu_masuk());
        Log.d("Status Parkir : ",kndmsk.getStatus_parkir());
        Log.d("Status Tiket : ",kndmsk.getStatus_tiket());

        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
        final String inputDateStr=kndmsk.getWaktu_masuk();
        Date date = null;
        try
        {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String outputDateStr = outputFormat.format(date);

        myViewHolder.kode_tiket.setText(kndmsk.getKode_tiket());
        myViewHolder.jenis_kendaraan.setText(kndmsk.getJenis_kendaraan());
        myViewHolder.no_plat.setText(kndmsk.getNo_plat());
        myViewHolder.waktu_masuk.setText(outputDateStr);
        myViewHolder.status_parkir.setText(kndmsk.getStatus_parkir());
        myViewHolder.status_tiket.setText(kndmsk.getStatus_tiket());

    }

    @Override
    public int getItemCount() {
        return kendaraanmasuk.size();
    }

    @Override
    public Filter getFilter() {
        if (filter_kendaraanmasuk==null) {
            filter_kendaraanmasuk=new CustomFilter_KendaraanMasuk((ArrayList<Model_KendaraanMasuk>) kndmskFilter, this);
        }
        return filter_kendaraanmasuk;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_KendaraanMasuk.RecyclerViewClickListener mListener;
        protected TextView waktu_masuk,kode_tiket,no_plat,jenis_kendaraan,status_parkir, status_tiket;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_KendaraanMasuk.RecyclerViewClickListener listener) {
            super(itemView);
            waktu_masuk = (TextView) itemView.findViewById(R.id.waktu_masuk);
            kode_tiket = (TextView) itemView.findViewById(R.id.kode_tiket);
            no_plat = (TextView) itemView.findViewById(R.id.no_plat);
            jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
            status_parkir = (TextView) itemView.findViewById(R.id.status_parkir);
            status_tiket = (TextView) itemView.findViewById(R.id.status_tiket);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }
    
}
