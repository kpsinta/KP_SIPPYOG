package jls.com.sippyog.View.Admin.Laporan;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.API.ApiClient_Laporan;
import jls.com.sippyog.Adapter.Adapter_DetilKendaraanLaporan;
import jls.com.sippyog.Adapter.Adapter_DetilKendaraanParkir;
import jls.com.sippyog.Adapter.Adapter_DetilPendapatanTKP;
import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.ListData.LD_KendaraanKeluar;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.Model.Model_KendaraanKeluar;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.KapasitasParkir.tampil_detil_kendaraan_parkir;
import jls.com.sippyog.View.Admin.admin_laporan_tkp;
import jls.com.sippyog.View.Admin.admin_main_menu;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tampil_data_kendaraan_masuk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class laporan_pendapatan_tkp extends AppCompatActivity {

    TextView setTanggal, totalPendapatan;
    Intent i;
    TextInputEditText waktuLaporan;
    ImageView searchLaporan;
    String waktu_laporan,date;
    LinearLayout laporan_harian, laporan_bulanan, laporan_tahunan;
    Double pendapatan=0.0;
    List<Model_KendaraanKeluar> mListKendaraan = new ArrayList<>();
    List<Model_Kendaraan> mListJenisKendaraan = new ArrayList<>();
    List<String> list_IDKendaraan = new ArrayList<>();
    List<String> list_JumlahKendaraan = new ArrayList<>();
    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager,layoutManager2;
    public Adapter_DetilPendapatanTKP adapterListKendaraan;
    Adapter_DetilPendapatanTKP.RecyclerViewClickListener listener;
    public Adapter_DetilKendaraanLaporan adapterKendaraan;
    public Adapter_DetilKendaraanLaporan.RecyclerViewClickListener listener2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pendapatan_tkp);

        i = getIntent();
        waktu_laporan = i.getStringExtra("WAKTU_LAPORAN");
        setTitle("Pendapatan TKP");
//        setTitle("Pendapatan TKP " +waktu_laporan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        laporan_harian = findViewById(R.id.laporan_harian);
        laporan_bulanan = findViewById(R.id.laporan_bulanan);
        laporan_tahunan = findViewById(R.id.laporan_tahunan);

        totalPendapatan = findViewById(R.id.total_pendapatan);
        totalPendapatan.setText(pendapatan.toString());

        recyclerView = findViewById(R.id.recycler_view_laporan_pendapatan_tkp);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterListKendaraan);

        recyclerView2 = findViewById(R.id.recycler_view_detil_kendaraan_laporan);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(adapterKendaraan);



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
            setRecycleViewLaporanPendapatanTKPHarian();
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (waktuLaporan.getText().toString().isEmpty())
                    {
                        Toast.makeText(laporan_pendapatan_tkp.this, "Masukan tanggal terlebih dahulu!", Toast.LENGTH_SHORT).show();
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
                        setRecycleViewLaporanPendapatanTKPHarian();
                    }
                }
            });

        }
        else if(waktu_laporan.equals("Bulanan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_bulanan);
            waktuLaporan = findViewById(R.id.text_input_bulanLaporan);
            String date_now = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(new Date());
            setTanggal.setText(date_now);
            date = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
            setRecycleViewLaporanPendapatanTKPBulanan();
            searchLaporan = findViewById(R.id.searchBulan);
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (waktuLaporan.getText().toString().isEmpty())
                    {
                        Toast.makeText(laporan_pendapatan_tkp.this, "Masukan bulan dan tahun terlebih dahulu!", Toast.LENGTH_SHORT).show();
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
                        setRecycleViewLaporanPendapatanTKPBulanan();
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
            setRecycleViewLaporanPendapatanTKPTahunan();
            setTanggal = findViewById(R.id.tanggal_laporan_tahunan);
            searchLaporan = findViewById(R.id.searchTahun);
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (waktuLaporan.getText().toString().isEmpty())
                    {
                        Toast.makeText(laporan_pendapatan_tkp.this, "Masukan tahun terlebih dahulu!", Toast.LENGTH_SHORT).show();
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
                        setRecycleViewLaporanPendapatanTKPTahunan();
                    }
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_laporan, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.searchMenu);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Cari Kendaraan");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterListKendaraan.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterListKendaraan.getFilter().filter(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent i = new Intent(laporan_pendapatan_tkp.this, admin_laporan_tkp.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_jumlah_kendaraan)
        {
            Intent i = new Intent(laporan_pendapatan_tkp.this, laporan_jumlah_kendaraan.class);
            i.putExtra("WAKTU_LAPORAN",waktu_laporan);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_pendapatan)
        {
            Toast.makeText(this, "Halaman Laporan Pendapatan TKP", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_tiket_hilang)
        {
            Intent i = new Intent(laporan_pendapatan_tkp.this, laporan_tiket_hilang.class);
            i.putExtra("WAKTU_LAPORAN",waktu_laporan);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecycleViewLaporanPendapatanTKPHarian() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Laporan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Laporan apiclientLaporan =retrofit.create(ApiClient_Laporan.class);

        Call<LD_KendaraanKeluar> laporanModelCall = apiclientLaporan.showTransaksiAll_Harian(date);

        laporanModelCall.enqueue(new Callback<LD_KendaraanKeluar>() {
            @Override
            public void onResponse (Call<LD_KendaraanKeluar> call, Response<LD_KendaraanKeluar> response) {
                mListKendaraan= response.body().getData();
                adapterListKendaraan = new Adapter_DetilPendapatanTKP(mListKendaraan, laporan_pendapatan_tkp.this,listener);
                recyclerView.setAdapter(adapterListKendaraan);
                adapterListKendaraan.notifyDataSetChanged();
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    totalPendapatan.setText(formatRupiah.format(pendapatan));
                    final DateFormat inputFormat =  new SimpleDateFormat("yyyy-MM-dd");
                    final DateFormat outputFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
                    Date date2 = null;
                    try
                    {
                        date2 = inputFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    setTanggal.setText(outputFormat.format(date2));
                    Toast.makeText(laporan_pendapatan_tkp.this,"Tidak ada transaksi pada tanggal tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                        pendapatan = pendapatan+mListKendaraan.get(i).getTotal_transaksi();
                        Log.d("Total Transaksi : ",mListKendaraan.get(i).getTotal_transaksi().toString());
                        Log.d("Pendapatan : ",pendapatan.toString());
                    }
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    totalPendapatan.setText(formatRupiah.format(pendapatan));
                    pendapatan=0.0;
                    Toast.makeText(laporan_pendapatan_tkp.this,"Welcome", Toast.LENGTH_SHORT).show();
                    final DateFormat inputFormat =  new SimpleDateFormat("yyyy-MM-dd");
                    final DateFormat outputFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
                    Date date2 = null;
                    try
                    {
                        date2 = inputFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    setTanggal.setText(outputFormat.format(date2));
                    Toast.makeText(laporan_pendapatan_tkp.this,"Berhasil Memuat Laporan Pendapatan Harian!", Toast.LENGTH_SHORT).show();
                    // show by Jenis Kendaraan
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit.Builder builder = new Retrofit
                            .Builder()
                            .baseUrl(ApiClient_Kendaraan.baseURL)
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit=builder.build();
                    ApiClient_Kendaraan apiclientKendaraan =retrofit.create(ApiClient_Kendaraan.class);

                    Call<LD_Kendaraan> kendaraanModelCall = apiclientKendaraan.show();
                    kendaraanModelCall.enqueue(new Callback<LD_Kendaraan>() {
                        @Override
                        public void onResponse (Call<LD_Kendaraan> call, Response<LD_Kendaraan> response) {
                            mListJenisKendaraan= response.body().getData();

                            for (int i = 0; i < mListJenisKendaraan.size(); i++)
                            {
                                list_IDKendaraan.add(mListJenisKendaraan.get(i).getId_kendaraan().toString());
                                Log.d("ID Kendaraan Load : ",list_IDKendaraan.get(i));
                                Double counter=0.0;
                                for (int j = 0; j < mListKendaraan.size(); j++) {
                                    if (list_IDKendaraan.get(i).equals(mListKendaraan.get(j).getId_kendaraan_fk().toString()))
                                    {
                                        counter=counter+mListKendaraan.get(j).getTotal_transaksi();
                                    }
                                }
                                Log.d("Jenis Kendaraan : ",mListJenisKendaraan.get(i).getJenis_kendaraan());
                                Log.d("Total : ",counter.toString());
                                Locale localeID = new Locale("in", "ID");
                                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                                list_JumlahKendaraan.add(formatRupiah.format(counter));
                                Log.d("=========","========");
                                adapterKendaraan = new Adapter_DetilKendaraanLaporan(mListJenisKendaraan, laporan_pendapatan_tkp.this,listener2, list_JumlahKendaraan);
                                recyclerView2.setAdapter(adapterKendaraan);
                                adapterKendaraan.notifyDataSetChanged();
                                Log.d("b00m","test");
                            }
                            list_JumlahKendaraan = new ArrayList<>();
                        }
                        @Override
                        public void onFailure(Call<LD_Kendaraan> call, Throwable t) {
                            Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanKeluar> call, Throwable t) {
                Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setRecycleViewLaporanPendapatanTKPBulanan() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Laporan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Laporan apiclientLaporan =retrofit.create(ApiClient_Laporan.class);

        Call<LD_KendaraanKeluar> laporanModelCall = apiclientLaporan.showTransaksiAll_Bulanan(date);

        laporanModelCall.enqueue(new Callback<LD_KendaraanKeluar>() {
            @Override
            public void onResponse (Call<LD_KendaraanKeluar> call, Response<LD_KendaraanKeluar> response) {
                mListKendaraan= response.body().getData();
                adapterListKendaraan = new Adapter_DetilPendapatanTKP(mListKendaraan, laporan_pendapatan_tkp.this,listener);
                recyclerView.setAdapter(adapterListKendaraan);
                adapterListKendaraan.notifyDataSetChanged();
                final DateFormat inputFormat =  new SimpleDateFormat("yyyy-MM");
                final DateFormat outputFormat = new SimpleDateFormat("MMMM yyyy");
                Date date2 = null;
                try
                {
                    date2 = inputFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setTanggal.setText(outputFormat.format(date2));
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    totalPendapatan.setText(formatRupiah.format(pendapatan));
                    Toast.makeText(laporan_pendapatan_tkp.this,"Tidak ada transaksi pada bulan dan tahun tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                        pendapatan = pendapatan+mListKendaraan.get(i).getTotal_transaksi();
                        Log.d("Total Transaksi : ",mListKendaraan.get(i).getTotal_transaksi().toString());
                        Log.d("Pendapatan : ",pendapatan.toString());
                    }
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    totalPendapatan.setText(formatRupiah.format(pendapatan));
                    pendapatan=0.0;
                    Toast.makeText(laporan_pendapatan_tkp.this,"Berhasil Memuat Laporan Pendapatan Bulanan!", Toast.LENGTH_SHORT).show();
                    // show by Jenis Kendaraan
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit.Builder builder = new Retrofit
                            .Builder()
                            .baseUrl(ApiClient_Kendaraan.baseURL)
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit=builder.build();
                    ApiClient_Kendaraan apiclientKendaraan =retrofit.create(ApiClient_Kendaraan.class);

                    Call<LD_Kendaraan> kendaraanModelCall = apiclientKendaraan.show();
                    kendaraanModelCall.enqueue(new Callback<LD_Kendaraan>() {
                        @Override
                        public void onResponse (Call<LD_Kendaraan> call, Response<LD_Kendaraan> response) {
                            mListJenisKendaraan= response.body().getData();

                            for (int i = 0; i < mListJenisKendaraan.size(); i++)
                            {
                                list_IDKendaraan.add(mListJenisKendaraan.get(i).getId_kendaraan().toString());
                                Log.d("ID Kendaraan Load : ",list_IDKendaraan.get(i));
                                Double counter=0.0;
                                for (int j = 0; j < mListKendaraan.size(); j++) {
                                    if (list_IDKendaraan.get(i).equals(mListKendaraan.get(j).getId_kendaraan_fk().toString()))
                                    {
                                        counter=counter+mListKendaraan.get(j).getTotal_transaksi();
                                    }
                                }
                                Log.d("Jenis Kendaraan : ",mListJenisKendaraan.get(i).getJenis_kendaraan());
                                Log.d("Total : ",counter.toString());
                                Locale localeID = new Locale("in", "ID");
                                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                                list_JumlahKendaraan.add(formatRupiah.format(counter));
                                Log.d("=========","========");
                                adapterKendaraan = new Adapter_DetilKendaraanLaporan(mListJenisKendaraan, laporan_pendapatan_tkp.this,listener2, list_JumlahKendaraan);
                                recyclerView2.setAdapter(adapterKendaraan);
                                adapterKendaraan.notifyDataSetChanged();
                                Log.d("b00m","test");
                            }
                            list_JumlahKendaraan = new ArrayList<>();
                        }
                        @Override
                        public void onFailure(Call<LD_Kendaraan> call, Throwable t) {
                            Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanKeluar> call, Throwable t) {
                Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setRecycleViewLaporanPendapatanTKPTahunan() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Laporan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Laporan apiclientLaporan =retrofit.create(ApiClient_Laporan.class);

        Call<LD_KendaraanKeluar> laporanModelCall = apiclientLaporan.showTransaksiAll_Tahunan(date);

        laporanModelCall.enqueue(new Callback<LD_KendaraanKeluar>() {
            @Override
            public void onResponse (Call<LD_KendaraanKeluar> call, Response<LD_KendaraanKeluar> response) {
                mListKendaraan= response.body().getData();
                adapterListKendaraan = new Adapter_DetilPendapatanTKP(mListKendaraan, laporan_pendapatan_tkp.this,listener);
                recyclerView.setAdapter(adapterListKendaraan);
                adapterListKendaraan.notifyDataSetChanged();
                final DateFormat inputFormat =  new SimpleDateFormat("yyyy");
                final DateFormat outputFormat = new SimpleDateFormat("yyyy");
                Date date2 = null;
                try
                {
                    date2 = inputFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setTanggal.setText(outputFormat.format(date2));
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    totalPendapatan.setText(formatRupiah.format(pendapatan));
                    Toast.makeText(laporan_pendapatan_tkp.this,"Tidak ada transaksi pada tahun tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                        pendapatan = pendapatan+mListKendaraan.get(i).getTotal_transaksi();
                        Log.d("Total Transaksi : ",mListKendaraan.get(i).getTotal_transaksi().toString());
                        Log.d("Pendapatan : ",pendapatan.toString());
                    }
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    totalPendapatan.setText(formatRupiah.format(pendapatan));
                    pendapatan=0.0;
                    Toast.makeText(laporan_pendapatan_tkp.this,"Berhasil Memuat Laporan Pendapatan Tahunan!", Toast.LENGTH_SHORT).show();
                    // show by Jenis Kendaraan
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit.Builder builder = new Retrofit
                            .Builder()
                            .baseUrl(ApiClient_Kendaraan.baseURL)
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit=builder.build();
                    ApiClient_Kendaraan apiclientKendaraan =retrofit.create(ApiClient_Kendaraan.class);

                    Call<LD_Kendaraan> kendaraanModelCall = apiclientKendaraan.show();
                    kendaraanModelCall.enqueue(new Callback<LD_Kendaraan>() {
                        @Override
                        public void onResponse (Call<LD_Kendaraan> call, Response<LD_Kendaraan> response) {
                            mListJenisKendaraan= response.body().getData();

                            for (int i = 0; i < mListJenisKendaraan.size(); i++)
                            {
                                list_IDKendaraan.add(mListJenisKendaraan.get(i).getId_kendaraan().toString());
                                Log.d("ID Kendaraan Load : ",list_IDKendaraan.get(i));
                                Double counter=0.0;
                                for (int j = 0; j < mListKendaraan.size(); j++) {
                                    if (list_IDKendaraan.get(i).equals(mListKendaraan.get(j).getId_kendaraan_fk().toString()))
                                    {
                                        counter=counter+mListKendaraan.get(j).getTotal_transaksi();
                                    }
                                }
                                Log.d("Jenis Kendaraan : ",mListJenisKendaraan.get(i).getJenis_kendaraan());
                                Log.d("Total : ",counter.toString());
                                Locale localeID = new Locale("in", "ID");
                                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                                list_JumlahKendaraan.add(formatRupiah.format(counter));
                                Log.d("=========","========");
                                adapterKendaraan = new Adapter_DetilKendaraanLaporan(mListJenisKendaraan, laporan_pendapatan_tkp.this,listener2, list_JumlahKendaraan);
                                recyclerView2.setAdapter(adapterKendaraan);
                                adapterKendaraan.notifyDataSetChanged();
                                Log.d("b00m","test");
                            }
                            list_JumlahKendaraan = new ArrayList<>();
                        }
                        @Override
                        public void onFailure(Call<LD_Kendaraan> call, Throwable t) {
                            Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanKeluar> call, Throwable t) {
                Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
