package jls.com.sippyog.View.Admin.Laporan;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jls.com.sippyog.API.ApiClient_Laporan;
import jls.com.sippyog.Adapter.Adapter_DetilPendapatanTKP;
import jls.com.sippyog.Adapter.Adapter_DetilTiketHilangdanJumlahKendaraan;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.Model.Model_KendaraanKeluar;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class laporan_tiket_hilang extends AppCompatActivity {
    TextView setTanggal, tiketHilang;
    Intent i;
    TextInputEditText waktuLaporan;
    ImageView searchLaporan;
    String waktu_laporan, date;
    LinearLayout laporan_harian, laporan_bulanan, laporan_tahunan;
    private List<Model_KendaraanMasuk> mListKendaraan = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_DetilTiketHilangdanJumlahKendaraan adapterListKendaraan;
    Adapter_DetilTiketHilangdanJumlahKendaraan.RecyclerViewClickListener listener;
    Integer total_hilang=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_tiket_hilang);

        laporan_harian = findViewById(R.id.laporan_harian);
        laporan_bulanan = findViewById(R.id.laporan_bulanan);
        laporan_tahunan = findViewById(R.id.laporan_tahunan);
        tiketHilang = findViewById(R.id.total_tiket_hilang);
        recyclerView = findViewById(R.id.recycler_view_laporan_tiket_hilang);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterListKendaraan);
        i = getIntent();
        waktu_laporan = i.getStringExtra("WAKTU_LAPORAN");
        setTitle("Tiket Hilang " +waktu_laporan);
        tiketHilang.setText(total_hilang.toString());

        if(waktu_laporan.equals("Harian"))
        {
            laporan_bulanan.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_harian);
            waktuLaporan = findViewById(R.id.text_input_tanggalLaporan);
            searchLaporan = findViewById(R.id.searchTanggal);
            //create a date string.
            String date_now = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault()).format(new Date());
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
            setRecycleViewLaporanTiketHilangHarian();
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (waktuLaporan.getText().toString().isEmpty())
                    {
                        Toast.makeText(laporan_tiket_hilang.this, "Masukan tanggal terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final DateFormat inputFormat =  new SimpleDateFormat("dd/MM/yyyy");
                        final DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        final String inputDateStr=waktuLaporan.getText().toString();
                        Date date2 = null;
                        try
                        {
                            date2 = inputFormat.parse(inputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = outputFormat.format(date2);
                        setRecycleViewLaporanTiketHilangHarian();
                    }
                }
            });
        }
        else if(waktu_laporan.equals("Bulanan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            waktuLaporan = findViewById(R.id.text_input_bulanLaporan);
            setTanggal = findViewById(R.id.tanggal_laporan_bulanan);
            String date_now = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(new Date());
            setTanggal.setText(date_now);
            date = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
            setRecycleViewLaporanTiketHilangBulanan();
            searchLaporan = findViewById(R.id.searchBulan);
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (waktuLaporan.getText().toString().isEmpty())
                    {
                        Toast.makeText(laporan_tiket_hilang.this, "Masukan bulan dan tahun terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final DateFormat inputFormat =  new SimpleDateFormat("MM/yyyy");
                        final DateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
                        final String inputDateStr=waktuLaporan.getText().toString();
                        Date date2 = null;
                        try
                        {
                            date2 = inputFormat.parse(inputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = outputFormat.format(date2);
                        setRecycleViewLaporanTiketHilangBulanan();
                    }
                }
            });
        }
        else if(waktu_laporan.equals("Tahunan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_bulanan.setVisibility(View.GONE);
            waktuLaporan = findViewById(R.id.text_input_tahunLaporan);
            setTanggal = findViewById(R.id.tanggal_laporan_tahunan);
            String date_now = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
            setTanggal.setText(date_now);
            date = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
            setRecycleViewLaporanTiketHilangTahunan();
            searchLaporan = findViewById(R.id.searchTahun);
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (waktuLaporan.getText().toString().isEmpty())
                    {
                        Toast.makeText(laporan_tiket_hilang.this, "Masukan tahun terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final DateFormat inputFormat =  new SimpleDateFormat("yyyy");
                        final DateFormat outputFormat = new SimpleDateFormat("yyyy");
                        final String inputDateStr=waktuLaporan.getText().toString();
                        Date date2 = null;
                        try
                        {
                            date2 = inputFormat.parse(inputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = outputFormat.format(date2);
                        setRecycleViewLaporanTiketHilangTahunan();
                    }
                }
            });
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_laporan, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(laporan_tiket_hilang.this, admin_main_menu.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_jumlah_kendaraan){
            Intent i = new Intent(laporan_tiket_hilang.this, laporan_jumlah_kendaraan.class);
            i.putExtra("WAKTU_LAPORAN",waktu_laporan);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_pendapatan)
        {
            Intent i = new Intent(laporan_tiket_hilang.this, laporan_pendapatan_tkp.class);
            i.putExtra("WAKTU_LAPORAN",waktu_laporan);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_tiket_hilang)
        {
            Toast.makeText(this, "Halaman Laporan Tiket Hilang", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecycleViewLaporanTiketHilangHarian() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Laporan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Laporan apiclientLaporan =retrofit.create(ApiClient_Laporan.class);

        Call<LD_KendaraanMasuk> laporanModelCall = apiclientLaporan.showByStatusTiketAll_Harian("Hilang",date);

        laporanModelCall.enqueue(new Callback<LD_KendaraanMasuk>() {
            @Override
            public void onResponse (Call<LD_KendaraanMasuk> call, Response<LD_KendaraanMasuk> response) {
                mListKendaraan= response.body().getData();
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    tiketHilang.setText(total_hilang.toString());
                    Toast.makeText(laporan_tiket_hilang.this,"Tidak ada tiket hilang pada tanggal tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    adapterListKendaraan = new Adapter_DetilTiketHilangdanJumlahKendaraan(mListKendaraan, laporan_tiket_hilang.this,listener);
                    recyclerView.setAdapter(adapterListKendaraan);
                    adapterListKendaraan.notifyDataSetChanged();
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                       // pisahin jenis kendaraannya disini
                    }
                    total_hilang = mListKendaraan.size();
                    tiketHilang.setText(total_hilang.toString());
                    total_hilang=0;
                    Toast.makeText(laporan_tiket_hilang.this,"Welcome", Toast.LENGTH_SHORT).show();
                    final DateFormat inputFormat =  new SimpleDateFormat("yyyy-MM-dd");
                    final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                    Date date2 = null;
                    try
                    {
                        date2 = inputFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    setTanggal.setText(outputFormat.format(date2));
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanMasuk> call, Throwable t) {
                Toast.makeText(laporan_tiket_hilang.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setRecycleViewLaporanTiketHilangBulanan() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Laporan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Laporan apiclientLaporan =retrofit.create(ApiClient_Laporan.class);

        Call<LD_KendaraanMasuk> laporanModelCall = apiclientLaporan.showByStatusTiketAll_Bulanan("Hilang",date);

        laporanModelCall.enqueue(new Callback<LD_KendaraanMasuk>() {
            @Override
            public void onResponse (Call<LD_KendaraanMasuk> call, Response<LD_KendaraanMasuk> response) {
                mListKendaraan= response.body().getData();
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    tiketHilang.setText(total_hilang.toString());
                    Toast.makeText(laporan_tiket_hilang.this,"Tidak ada tiket hilang pada bulan dan tahun tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    adapterListKendaraan = new Adapter_DetilTiketHilangdanJumlahKendaraan(mListKendaraan, laporan_tiket_hilang.this,listener);
                    recyclerView.setAdapter(adapterListKendaraan);
                    adapterListKendaraan.notifyDataSetChanged();
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                        // pisahin jenis kendaraannya disini
                    }
                    total_hilang = mListKendaraan.size();
                    tiketHilang.setText(total_hilang.toString());
                    total_hilang=0;
                    Toast.makeText(laporan_tiket_hilang.this,"Welcome", Toast.LENGTH_SHORT).show();
//                    final DateFormat inputFormat =  new SimpleDateFormat("yyyy-MM-dd");
//                    final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy");
//                    Date date2 = null;
//                    try
//                    {
//                        date2 = inputFormat.parse(date);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    setTanggal.setText(outputFormat.format(date2));
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanMasuk> call, Throwable t) {
                Toast.makeText(laporan_tiket_hilang.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setRecycleViewLaporanTiketHilangTahunan() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Laporan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Laporan apiclientLaporan =retrofit.create(ApiClient_Laporan.class);

        Call<LD_KendaraanMasuk> laporanModelCall = apiclientLaporan.showByStatusTiketAll_Tahunan("Hilang",date);

        laporanModelCall.enqueue(new Callback<LD_KendaraanMasuk>() {
            @Override
            public void onResponse (Call<LD_KendaraanMasuk> call, Response<LD_KendaraanMasuk> response) {
                mListKendaraan= response.body().getData();
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    tiketHilang.setText(total_hilang.toString());
                    Toast.makeText(laporan_tiket_hilang.this,"Tidak ada tiket hilang pada tahun tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    adapterListKendaraan = new Adapter_DetilTiketHilangdanJumlahKendaraan(mListKendaraan, laporan_tiket_hilang.this,listener);
                    recyclerView.setAdapter(adapterListKendaraan);
                    adapterListKendaraan.notifyDataSetChanged();
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                        // pisahin jenis kendaraannya disini
                    }
                    total_hilang = mListKendaraan.size();
                    tiketHilang.setText(total_hilang.toString());
                    total_hilang=0;
                    Toast.makeText(laporan_tiket_hilang.this,"Welcome", Toast.LENGTH_SHORT).show();
//                    final DateFormat inputFormat =  new SimpleDateFormat("yyyy-MM-dd");
//                    final DateFormat outputFormat = new SimpleDateFormat("EEE, d MMM yyyy");
//                    Date date2 = null;
//                    try
//                    {
//                        date2 = inputFormat.parse(date);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    setTanggal.setText(outputFormat.format(date2));
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanMasuk> call, Throwable t) {
                Toast.makeText(laporan_tiket_hilang.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}