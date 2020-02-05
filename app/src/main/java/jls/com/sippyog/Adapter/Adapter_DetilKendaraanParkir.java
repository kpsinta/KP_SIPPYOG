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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.CustomFilter.CustomFilter_DetilKendaraanParkir;
import jls.com.sippyog.ListData.LD_PengawaiOnDuty;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.Model.Model_PegawaiOnDuty;
import jls.com.sippyog.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_DetilKendaraanParkir extends RecyclerView.Adapter<Adapter_DetilKendaraanParkir.MyViewHolder> implements Filterable {
public List<Model_KendaraanMasuk> kndprkFilter;
public List<Model_PegawaiOnDuty> pod = new ArrayList<>();
public List<Model_KendaraanMasuk> kendaraanparkir = new ArrayList<>();
private Context context;
private Adapter_DetilKendaraanParkir.RecyclerViewClickListener mListener;
CustomFilter_DetilKendaraanParkir filter_kendaraanparkir;

public Adapter_DetilKendaraanParkir(List<Model_KendaraanMasuk> kendaraanparkir, Context context, Adapter_DetilKendaraanParkir.RecyclerViewClickListener mListener) {
        this.kndprkFilter = kendaraanparkir;
        this.kendaraanparkir = kendaraanparkir;
        this.context = context;
        this.mListener = mListener;
        }
@NonNull
@Override
public Adapter_DetilKendaraanParkir.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_recycle_adapter_detil_kendaraan_parkir, viewGroup, false);
        return new Adapter_DetilKendaraanParkir.MyViewHolder(v, mListener);
        }

@Override
public void onBindViewHolder(@NonNull final Adapter_DetilKendaraanParkir.MyViewHolder myViewHolder, int i) {
final Model_KendaraanMasuk kndprk = kendaraanparkir.get(i);
        Log.d("ID KendaraanMasuk : ",kndprk.getId_tiket().toString());
        Log.d("Kode Tiket : ",kndprk.getKode_tiket());
        Log.d("Jenis Kendaraan : ",kndprk.getJenis_kendaraan());
        Log.d("No Plat : ",kndprk.getNo_plat());
        Log.d("Waktu Masuk : ",kndprk.getWaktu_masuk());
        Log.d("Status Parkir : ",kndprk.getStatus_parkir());
        Log.d("Status Tiket : ",kndprk.getStatus_tiket());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_KendaraanMasuk.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_KendaraanMasuk apiclientKendaraanMasuk =retrofit.create(ApiClient_KendaraanMasuk.class);

        Call<LD_PengawaiOnDuty> kendaraanMasukModelCall = apiclientKendaraanMasuk.showByIdTiket(kndprk.getId_tiket());
        kendaraanMasukModelCall.enqueue(new Callback<LD_PengawaiOnDuty>() {
            @Override
            public void onResponse (Call<LD_PengawaiOnDuty> call, Response<LD_PengawaiOnDuty> response) {
                   pod = response.body().getData();
                   for (int i = 0; i < pod.size(); i++) {
                   myViewHolder.petugas_parkir.setText(pod.get(i).getNama_pegawai());
                   }
            }
            @Override
            public void onFailure(Call<LD_PengawaiOnDuty> call, Throwable t) {
                Toast.makeText(context,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy\tHH:mm:ss");
final String inputDateStr=kndprk.getWaktu_masuk();
        Date date = null;
        try
        {
        date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
        e.printStackTrace();
        }
final String outputDateStr = outputFormat.format(date);

        myViewHolder.kode_tiket.setText(kndprk.getKode_tiket());
        myViewHolder.jenis_kendaraan.setText(kndprk.getJenis_kendaraan());
        myViewHolder.no_plat.setText(kndprk.getNo_plat());
        myViewHolder.waktu_masuk.setText(outputDateStr);
        myViewHolder.status_parkir.setText(kndprk.getStatus_parkir());
        }

@Override
public int getItemCount() {
        return kendaraanparkir.size();
        }

@Override
public Filter getFilter() {
        if (filter_kendaraanparkir==null) {
        filter_kendaraanparkir=new CustomFilter_DetilKendaraanParkir((ArrayList<Model_KendaraanMasuk>) kndprkFilter, this);
        }
        return filter_kendaraanparkir;
        }

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Adapter_DetilKendaraanParkir.RecyclerViewClickListener mListener;
    protected TextView waktu_masuk,kode_tiket,no_plat,jenis_kendaraan,status_parkir, petugas_parkir;
    private RelativeLayout mRowContainer;
    public MyViewHolder(@NonNull View itemView, Adapter_DetilKendaraanParkir.RecyclerViewClickListener listener) {
        super(itemView);
        waktu_masuk = itemView.findViewById(R.id.waktu_masuk);
        kode_tiket = itemView.findViewById(R.id.kode_tiket);
        no_plat = itemView.findViewById(R.id.no_plat);
        jenis_kendaraan = itemView.findViewById(R.id.jenis_kendaraan);
        status_parkir = itemView.findViewById(R.id.status_parkir);
        petugas_parkir = itemView.findViewById(R.id.nama_pegawai);
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