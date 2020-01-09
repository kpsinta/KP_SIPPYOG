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

import jls.com.sippyog.Model.Model_Shift;
import jls.com.sippyog.R;

public class Adapter_Shift extends RecyclerView.Adapter<Adapter_Shift.MyViewHolder> {
    public List<Model_Shift> shift = new ArrayList<>();
    private Context context;
    private Adapter_Shift.RecyclerViewClickListener mListener;


    public Adapter_Shift(List<Model_Shift> shift, Context context, Adapter_Shift.RecyclerViewClickListener mListener) {
        this.shift = shift;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Adapter_Shift.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_shift, viewGroup, false);
        return new Adapter_Shift.MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Shift.MyViewHolder myViewHolder, int i) {
        final Model_Shift shf = shift.get(i);
        Log.d("ID Shift : ",shf.getId_shift().toString());
        Log.d("Nama Shift : ",shf.getNama_shift());
        Log.d("Jam Masuk : ",shf.getJam_masuk().toString());
        Log.d("Jam Keluar : ",shf.getJam_keluar().toString());

        myViewHolder.nama_shift.setText (shf.getNama_shift());
        myViewHolder.jam_masuk.setText  (shf.getJam_masuk().toString());
        myViewHolder.jam_keluar.setText (shf.getJam_keluar().toString());
    }

    @Override
    public int getItemCount() {
        return shift.size();
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Adapter_Shift.RecyclerViewClickListener mListener;
        protected TextView nama_shift, jam_masuk, jam_keluar;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_Shift.RecyclerViewClickListener listener) {
            super(itemView);
            nama_shift = (TextView) itemView.findViewById(R.id.nama_shift);
            jam_masuk = (TextView) itemView.findViewById(R.id.jam_masuk);
            jam_keluar = (TextView) itemView.findViewById(R.id.jam_keluar);
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
}
