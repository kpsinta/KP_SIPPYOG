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

import jls.com.sippyog.CustomFilter.CustomFilter_DetilPendapatanTKP;
import jls.com.sippyog.Model.Model_KendaraanKeluar;
import jls.com.sippyog.R;

public class Adapter_DetilPendapatanTKP  extends RecyclerView.Adapter<Adapter_DetilPendapatanTKP.MyViewHolder> implements Filterable {
    public List<Model_KendaraanKeluar> kndkeluarFilter;
    public List<Model_KendaraanKeluar> detilpendapatantkp = new ArrayList<>();
    private Context context;
    private Adapter_DetilPendapatanTKP.RecyclerViewClickListener mListener;
    CustomFilter_DetilPendapatanTKP filter_kendaraankeluar;

    public Adapter_DetilPendapatanTKP(List<Model_KendaraanKeluar> detilpendapatantkp, Context context, Adapter_DetilPendapatanTKP.RecyclerViewClickListener mListener) {
        this.kndkeluarFilter = detilpendapatantkp;
        this.detilpendapatantkp = detilpendapatantkp;
        this.context = context;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public Adapter_DetilPendapatanTKP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_detil_pendapatan_tkp, viewGroup, false);
        return new Adapter_DetilPendapatanTKP.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_DetilPendapatanTKP.MyViewHolder myViewHolder, int i) {
        final Model_KendaraanKeluar kndkeluar = detilpendapatantkp.get(i);
        Log.d("ID Kendaraan Keluar : ",kndkeluar.getId_transaksi().toString());
        Log.d("Kode Tiket : ",kndkeluar.getKode_tiket());
        Log.d("Jenis Kendaraan : ",kndkeluar.getJenis_kendaraan());
        Log.d("No Plat : ",kndkeluar.getNo_plat());
        Log.d("Waktu Transaksi : ",kndkeluar.getWaktu_transaksi());
        Log.d("Status Parkir : ",kndkeluar.getStatus_parkir());
        Log.d("Status Tiket : ",kndkeluar.getStatus_tiket());
        Log.d("Total Transaksi : ",String.format("%.0f",kndkeluar.getTotal_transaksi()));


        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
        final String inputDateStrTransaksi=kndkeluar.getWaktu_transaksi();

        Date dateTransaksi = null;
        try
        {
            dateTransaksi = inputFormat.parse(inputDateStrTransaksi);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String outputDateStrTransaksi = outputFormat.format(dateTransaksi);

        myViewHolder.jenis_kendaraan.setText(kndkeluar.getJenis_kendaraan());
        myViewHolder.no_plat.setText(kndkeluar.getNo_plat());
        myViewHolder.waktu_keluar.setText(outputDateStrTransaksi);
        myViewHolder.status_tiket.setText(kndkeluar.getStatus_tiket());
        myViewHolder.total_transaksi.setText(String.format("%.0f",kndkeluar.getTotal_transaksi()));

    }

    @Override
    public int getItemCount() {
        return detilpendapatantkp.size();
    }

    @Override
    public Filter getFilter() {
        if (filter_kendaraankeluar==null) {
            filter_kendaraankeluar=new CustomFilter_DetilPendapatanTKP((ArrayList<Model_KendaraanKeluar>) kndkeluarFilter, this);
        }
        return filter_kendaraankeluar;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_DetilPendapatanTKP.RecyclerViewClickListener mListener;
        protected TextView no_plat,jenis_kendaraan,status_tiket, waktu_keluar, total_transaksi;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_DetilPendapatanTKP.RecyclerViewClickListener listener) {
            super(itemView);
            no_plat = (TextView) itemView.findViewById(R.id.no_plat);
            jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
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
                    //mListener.onRowClick(mRowContainer, getAdapterPosition());
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
