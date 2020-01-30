package jls.com.sippyog.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.R;

public class Adapter_DetilKendaraanLaporan extends RecyclerView.Adapter<Adapter_DetilKendaraanLaporan.MyViewHolder>{
    public List<Model_Kendaraan> kendaraan = new ArrayList<>();
    private Context context;
    private Adapter_DetilKendaraanLaporan.RecyclerViewClickListener mListener;
    List<String> list_JumlahKendaraan = new ArrayList<>();
    Integer counter;
    public Adapter_DetilKendaraanLaporan(List<Model_Kendaraan> kendaraan, Context context, Adapter_DetilKendaraanLaporan.RecyclerViewClickListener mListener, List<String> total) {
        this.kendaraan = kendaraan;
        this.context = context;
        this.mListener = mListener;
        this.list_JumlahKendaraan = total;
    }

    @NonNull
    @Override
    public Adapter_DetilKendaraanLaporan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_detil_kendaraan_laporan, viewGroup, false);
        return new Adapter_DetilKendaraanLaporan.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_DetilKendaraanLaporan.MyViewHolder myViewHolder, int i) {
        final Model_Kendaraan knd = kendaraan.get(i);
        Log.d("ID Kendaraan : ",knd.getId_kendaraan().toString());
        Log.d("Jenis Kendaraan : ",knd.getJenis_kendaraan());

        myViewHolder.jenis_kendaraan.setText        (knd.getJenis_kendaraan());
        myViewHolder.total_kendaraan.setText     (list_JumlahKendaraan.get(i));
    }

    @Override
    public int getItemCount() {
        return kendaraan.size();
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_DetilKendaraanLaporan.RecyclerViewClickListener mListener;
        protected TextView jenis_kendaraan, total_kendaraan;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_DetilKendaraanLaporan.RecyclerViewClickListener listener) {
            super(itemView);
            jenis_kendaraan = itemView.findViewById(R.id.jenis_kendaraan);
            total_kendaraan = itemView.findViewById(R.id.total_kendaraan);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.row_container:
                 //   mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
}
