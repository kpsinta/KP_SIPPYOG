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

import jls.com.sippyog.CustomFilter.CustomFilter_KendaraanKeluar;
import jls.com.sippyog.Model.Model_KendaraanKeluar;
import jls.com.sippyog.R;

public class Adapter_KendaraanKeluar extends RecyclerView.Adapter<Adapter_KendaraanKeluar.MyViewHolder> implements Filterable {
    public List<Model_KendaraanKeluar> kndkeluarFilter;
    public List<Model_KendaraanKeluar> kendaraankeluar = new ArrayList<>();
    private Context context;
    private Adapter_KendaraanKeluar.RecyclerViewClickListener mListener;
    CustomFilter_KendaraanKeluar filter_kendaraankeluar;

    public Adapter_KendaraanKeluar(List<Model_KendaraanKeluar> kendaraankeluar, Context context, Adapter_KendaraanKeluar.RecyclerViewClickListener mListener) {
        this.kndkeluarFilter = kendaraankeluar;
        this.kendaraankeluar = kendaraankeluar;
        this.context = context;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public Adapter_KendaraanKeluar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_kendaraan_keluar, viewGroup, false);
        return new Adapter_KendaraanKeluar.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_KendaraanKeluar.MyViewHolder myViewHolder, int i) {
        final Model_KendaraanKeluar kndkeluar = kendaraankeluar.get(i);
        Log.d("ID Kendaraan Keluar : ",kndkeluar.getId_transaksi().toString());
        Log.d("Kode Tiket : ",kndkeluar.getKode_tiket());
        Log.d("Jenis Kendaraan : ",kndkeluar.getJenis_kendaraan());
        Log.d("No Plat : ",kndkeluar.getNo_plat());
        Log.d("Waktu Masuk : ",kndkeluar.getWaktu_masuk());
        Log.d("Waktu Transaksi : ",kndkeluar.getWaktu_transaksi());
        Log.d("Status Parkir : ",kndkeluar.getStatus_parkir());
        Log.d("Status Tiket : ",kndkeluar.getStatus_tiket());
        Log.d("Total Transaksi : ",String.format("%.0f",kndkeluar.getTotal_transaksi()));


        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
        final String inputDateStr=kndkeluar.getWaktu_masuk();
        final String inputDateStrTransaksi=kndkeluar.getWaktu_transaksi();

        Date date = null;
        Date dateTransaksi = null;
        try
        {
            date = inputFormat.parse(inputDateStr);
            dateTransaksi = inputFormat.parse(inputDateStrTransaksi);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String outputDateStr = outputFormat.format(date);
        final String outputDateStrTransaksi = outputFormat.format(dateTransaksi);

        myViewHolder.kode_tiket.setText(kndkeluar.getKode_tiket());
        myViewHolder.jenis_kendaraan.setText(kndkeluar.getJenis_kendaraan());
        myViewHolder.no_plat.setText(kndkeluar.getNo_plat());
        myViewHolder.waktu_masuk.setText(outputDateStr);
        myViewHolder.waktu_keluar.setText(outputDateStrTransaksi);
        myViewHolder.status_parkir.setText(kndkeluar.getStatus_parkir());
        myViewHolder.status_tiket.setText(kndkeluar.getStatus_tiket());
        myViewHolder.total_transaksi.setText(String.format("%.0f",kndkeluar.getTotal_transaksi()));

    }

    @Override
    public int getItemCount() {
        return kendaraankeluar.size();
    }

    @Override
    public Filter getFilter() {
        if (filter_kendaraankeluar==null) {
            filter_kendaraankeluar=new CustomFilter_KendaraanKeluar((ArrayList<Model_KendaraanKeluar>) kndkeluarFilter, this);
        }
        return filter_kendaraankeluar;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_KendaraanKeluar.RecyclerViewClickListener mListener;
        protected TextView waktu_masuk,kode_tiket,no_plat,jenis_kendaraan,status_parkir, status_tiket, waktu_keluar, total_transaksi;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_KendaraanKeluar.RecyclerViewClickListener listener) {
            super(itemView);
            waktu_masuk = (TextView) itemView.findViewById(R.id.waktu_masuk);
            kode_tiket = (TextView) itemView.findViewById(R.id.kode_tiket);
            no_plat = (TextView) itemView.findViewById(R.id.no_plat);
            jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
            status_parkir = (TextView) itemView.findViewById(R.id.status_parkir);
            status_tiket = (TextView) itemView.findViewById(R.id.status_tiket);
            waktu_keluar = (TextView) itemView.findViewById(R.id.waktu_keluar);
            total_transaksi = (TextView) itemView.findViewById(R.id.total_transaksi);

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
        void onRowClick(View view, int position);
    }
}
