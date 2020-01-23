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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.KapasitasParkir.admin_tampil_kapasitas_parkir;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tampil_data_kendaraan_masuk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_KapasitasParkir extends RecyclerView.Adapter<Adapter_KapasitasParkir.MyViewHolder>{
    public List<Model_Kendaraan> kendaraan = new ArrayList<>();
    private Context context;
    private Adapter_KapasitasParkir.RecyclerViewClickListener mListener;

    private List<Model_KendaraanMasuk> mListKendaraanMasuk = new ArrayList<>();
    Integer terisi=0;
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
    public void onBindViewHolder(@NonNull final Adapter_KapasitasParkir.MyViewHolder myViewHolder, int i) {
        final Model_Kendaraan knd = kendaraan.get(i);
        final Integer sisa_slot=0,terisi=0;
        Log.d("ID Kendaraan : ",knd.getId_kendaraan().toString());
        Log.d("Jenis Kendaraan : ",knd.getJenis_kendaraan());
        Log.d("Kapasitas Maksimum : ",knd.getKapasitas_maksimum().toString());
        myViewHolder.jenis_kendaraan.setText        (knd.getJenis_kendaraan());
        myViewHolder.kapasitas_maksimum.setText     (knd.getKapasitas_maksimum().toString());
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_KendaraanMasuk.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_KendaraanMasuk apiclientKendaraanMasuk =retrofit.create(ApiClient_KendaraanMasuk.class);
        Call<LD_KendaraanMasuk> kendaraanMasukModelCall = apiclientKendaraanMasuk.showByKendaraanWhereStatusSedangParkir(knd.getId_kendaraan());
        Toast.makeText(context, "ID : " +knd.getId_kendaraan(), Toast.LENGTH_SHORT).show();
        kendaraanMasukModelCall.enqueue(new Callback<LD_KendaraanMasuk>() {
            @Override
            public void onResponse (Call<LD_KendaraanMasuk> call, Response<LD_KendaraanMasuk> response) {
                mListKendaraanMasuk= response.body().getData();
                Integer terisi = mListKendaraanMasuk.size();
                Log.i("Terisi : ",terisi.toString());
                Log.i(tampil_data_kendaraan_masuk.class.getSimpleName(), response.body().toString());
                myViewHolder.terisi.setText(terisi.toString());
                Integer sisa_slot=knd.getKapasitas_maksimum()-terisi;
                myViewHolder.sisa_slot.setText(sisa_slot.toString());
                Toast.makeText(context, "Load Data Kendaraan Sukses!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LD_KendaraanMasuk> call, Throwable t) {
            }
        });;
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
        protected TextView jenis_kendaraan, kapasitas_maksimum, biaya_parkir, terisi, sisa_slot;
        private RelativeLayout mRowContainer;
        public MyViewHolder(@NonNull View itemView, Adapter_KapasitasParkir.RecyclerViewClickListener listener) {
            super(itemView);
            jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
            kapasitas_maksimum = (TextView) itemView.findViewById(R.id.kapasitas_maksimum);
            terisi = itemView.findViewById(R.id.terisi);
            sisa_slot = itemView.findViewById(R.id.sisa_slot);
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
