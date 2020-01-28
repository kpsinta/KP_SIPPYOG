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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.API.ApiClient_Laporan;
import jls.com.sippyog.Adapter.Adapter_DetilKendaraanParkir;
import jls.com.sippyog.Adapter.Adapter_DetilPendapatanTKP;
import jls.com.sippyog.ListData.LD_KendaraanKeluar;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.Model.Model_KendaraanKeluar;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.KapasitasParkir.tampil_detil_kendaraan_parkir;
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
    private List<Model_KendaraanKeluar> mListKendaraan = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_DetilPendapatanTKP adapterListKendaraan;
    Adapter_DetilPendapatanTKP.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pendapatan_tkp);
        pendapatan=0.0;
        recyclerView = findViewById(R.id.recycler_view_laporan_pendapatan_tkp);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterListKendaraan);

        laporan_harian = findViewById(R.id.laporan_harian);
        laporan_bulanan = findViewById(R.id.laporan_bulanan);
        laporan_tahunan = findViewById(R.id.laporan_tahunan);
        totalPendapatan = findViewById(R.id.total_pendapatan);
        i = getIntent();
        waktu_laporan = i.getStringExtra("WAKTU_LAPORAN");
        setTitle("Pendapatan TKP " +waktu_laporan);

        if(waktu_laporan.equals("Harian"))
        {
            laporan_bulanan.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_harian);
            waktuLaporan = findViewById(R.id.text_input_tanggalLaporan);
            searchLaporan = findViewById(R.id.searchTanggal);
            //create a date string.
            String date_now = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
            setRecycleViewLaporanPendapatanTKPHarian_Today();
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
                        setRecycleViewLaporanPendapatanTKPHarian_Today();
                    }
                }
            });

        }
        else if(waktu_laporan.equals("Bulanan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_bulanan);
            String date_now = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
        }
        else if(waktu_laporan.equals("Tahunan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_bulanan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_tahunan);
            String date_now = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
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
            Intent i = new Intent(laporan_pendapatan_tkp.this, admin_main_menu.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_jumlah_kendaraan){
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
    public void setRecycleViewLaporanPendapatanTKPHarian_Today() {
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
                if(mListKendaraan.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    totalPendapatan.setText(String.format("%.0f",pendapatan));
                    Toast.makeText(laporan_pendapatan_tkp.this,"Tidak ada transaksi pada tanggal tersebut", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    Log.i(laporan_pendapatan_tkp.class.getSimpleName(), response.body().toString());
                    adapterListKendaraan = new Adapter_DetilPendapatanTKP(mListKendaraan, laporan_pendapatan_tkp.this,listener);
                    recyclerView.setAdapter(adapterListKendaraan);
                    adapterListKendaraan.notifyDataSetChanged();
                    for (int i = 0; i < mListKendaraan.size(); i++) {
                        pendapatan = pendapatan+mListKendaraan.get(i).getTotal_transaksi();
                        Log.d("Total Transaksi : ",mListKendaraan.get(i).getTotal_transaksi().toString());
                        Log.d("Pendapatan : ",pendapatan.toString());
                    }
                    totalPendapatan.setText(String.format("%.0f",pendapatan));
                    pendapatan=0.0;
                    Toast.makeText(laporan_pendapatan_tkp.this,"Welcome", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LD_KendaraanKeluar> call, Throwable t) {
                Toast.makeText(laporan_pendapatan_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
