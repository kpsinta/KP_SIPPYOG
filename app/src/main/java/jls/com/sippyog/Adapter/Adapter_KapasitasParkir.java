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

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.R;

public class Adapter_KapasitasParkir extends RecyclerView.Adapter<Adapter_KapasitasParkir.MyViewHolder>{
    public List<Model_Kendaraan> kendaraan = new ArrayList<>();
    private Context context;
    private Adapter_KapasitasParkir.RecyclerViewClickListener mListener;

    public Adapter_KapasitasParkir(List<Model_Kendaraan> kendaraan, Context context, Adapter_KapasitasParkir.RecyclerViewClickListener mListener) {
        this.kendaraan = kendaraan;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Adapter_KapasitasParkir.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_kapasitas_parkir, viewGroup, false);
        return new Adapter_KapasitasParkir.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_KapasitasParkir.MyViewHolder myViewHolder, int i) {
        final Model_Kendaraan knd = kendaraan.get(i);
        Log.d("ID Kendaraan : ",knd.getId_kendaraan().toString());
        Log.d("Jenis Kendaraan : ",knd.getJenis_kendaraan());
        Log.d("Kapasitas Maksimum : ",knd.getKapasitas_maksimum().toString());

        myViewHolder.jenis_kendaraan.setText        (knd.getJenis_kendaraan());
        myViewHolder.kapasitas_maksimum.setText     (knd.getKapasitas_maksimum().toString());


    }

    @Override
    public int getItemCount() {
        return kendaraan.size();
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_KapasitasParkir.RecyclerViewClickListener mListener;
        protected TextView jenis_kendaraan, kapasitas_maksimum, biaya_parkir, biaya_denda;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_KapasitasParkir.RecyclerViewClickListener listener) {
            super(itemView);
            jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
            kapasitas_maksimum = (TextView) itemView.findViewById(R.id.kapasitas_maksimum);
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
}
