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

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.CustomFilter.CustomFilter_Pegawai;
import jls.com.sippyog.Model.Model_Pegawai;
import jls.com.sippyog.R;

public class Adapter_Pegawai extends RecyclerView.Adapter<Adapter_Pegawai.MyViewHolder> implements Filterable {
        public List<Model_Pegawai> pgwFilter;
        public List<Model_Pegawai> pegawai = new ArrayList<>();
        private Context context;
        private RecyclerViewClickListener mListener;
        CustomFilter_Pegawai filter_pegawai;

        public Adapter_Pegawai(List<Model_Pegawai> pegawai, Context context, Adapter_Pegawai.RecyclerViewClickListener mListener) {
            this.pgwFilter = pegawai;
            this.pegawai = pegawai;
            this.context = context;
            this.mListener = mListener;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_pegawai, viewGroup, false);
            return new Adapter_Pegawai.MyViewHolder(v, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final Model_Pegawai pgw = pegawai.get(i);
            Log.d("ID Pegawai : ",pgw.getId_pegawai().toString());
            Log.d("Nama Pegawai : ",pgw.getNama_pegawai());
            Log.d("Username Pegawai : ",pgw.getUsername_pegawai());
            Log.d("ID Role : ",pgw.getId_role_fk().toString());

            myViewHolder.nama_pegawai.setText       ("  Nama Pegawai        : "+ pgw.getNama_pegawai());
            myViewHolder.nip_pegawai.setText        ("  NIP Pegawai         : "+ pgw.getNip_pegawai());
            myViewHolder.username_pegawai.setText   ("  Username Pegawai    : "+ pgw.getUsername_pegawai());

        }

        @Override
        public int getItemCount() {
            return pegawai.size();
        }

        @Override
        public Filter getFilter() {
            if (filter_pegawai==null) {
                filter_pegawai=new CustomFilter_Pegawai((ArrayList<Model_Pegawai>) pgwFilter, this);
            }
            return filter_pegawai;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private Adapter_Pegawai.RecyclerViewClickListener mListener;
            protected TextView nama_pegawai, nip_pegawai, username_pegawai;
            private RelativeLayout mRowContainer;
            public MyViewHolder(@NonNull View itemView, Adapter_Pegawai.RecyclerViewClickListener listener) {
                super(itemView);
                nama_pegawai = (TextView) itemView.findViewById(R.id.nama_pegawai);
                nip_pegawai = (TextView) itemView.findViewById(R.id.nip_pegawai);
                username_pegawai = (TextView) itemView.findViewById(R.id.username_pegawai);
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
