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

import jls.com.sippyog.CustomFilter.CustomFilter_DetilKendaraanParkir;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;

public class Adapter_DetilKendaraanParkir extends RecyclerView.Adapter<Adapter_DetilKendaraanParkir.MyViewHolder> implements Filterable {
public List<Model_KendaraanMasuk> kndprkFilter;
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
public void onBindViewHolder(@NonNull Adapter_DetilKendaraanParkir.MyViewHolder myViewHolder, int i) {
final Model_KendaraanMasuk kndprk = kendaraanparkir.get(i);
        Log.d("ID KendaraanMasuk : ",kndprk.getId_tiket().toString());
        Log.d("Kode Tiket : ",kndprk.getKode_tiket());
        Log.d("Jenis Kendaraan : ",kndprk.getJenis_kendaraan());
        Log.d("No Plat : ",kndprk.getNo_plat());
        Log.d("Waktu Masuk : ",kndprk.getWaktu_masuk());
        Log.d("Status Parkir : ",kndprk.getStatus_parkir());
        Log.d("Status Tiket : ",kndprk.getStatus_tiket());

final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
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
    protected TextView waktu_masuk,kode_tiket,no_plat,jenis_kendaraan,status_parkir, status_tiket;
    private RelativeLayout mRowContainer;
    public MyViewHolder(@NonNull View itemView, Adapter_DetilKendaraanParkir.RecyclerViewClickListener listener) {
        super(itemView);
        waktu_masuk = (TextView) itemView.findViewById(R.id.waktu_masuk);
        kode_tiket = (TextView) itemView.findViewById(R.id.kode_tiket);
        no_plat = (TextView) itemView.findViewById(R.id.no_plat);
        jenis_kendaraan = (TextView) itemView.findViewById(R.id.jenis_kendaraan);
        status_parkir = (TextView) itemView.findViewById(R.id.status_parkir);
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