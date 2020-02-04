package jls.com.sippyog.View.Konsumen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.Adapter.Adapter_KapasitasParkir;
import jls.com.sippyog.Adapter.Adapter_KapasitasParkir_Konsumen;
import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.KapasitasParkir.admin_tampil_kapasitas_parkir;
import jls.com.sippyog.View.Admin.KapasitasParkir.tampil_detil_kendaraan_parkir;
import jls.com.sippyog.View.Admin.admin_main_menu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class konsumen_tampil_kapasitas_parkir extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_KapasitasParkir_Konsumen adapterKapasitasParkir;
    private List<Model_Kendaraan> mListKendaraan = new ArrayList<>();
    Adapter_KapasitasParkir_Konsumen.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen_tampil_kapasitas_parkir);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_tampil_kapasitas_parkir);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterKapasitasParkir);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(konsumen_tampil_kapasitas_parkir.this, konsumen_main_menu.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecycleViewKendaraan() {
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
                mListKendaraan= response.body().getData();
                Log.i(admin_tampil_kapasitas_parkir.class.getSimpleName(), response.body().toString());
                adapterKapasitasParkir = new Adapter_KapasitasParkir_Konsumen(mListKendaraan,konsumen_tampil_kapasitas_parkir.this,listener);
                recyclerView.setAdapter(adapterKapasitasParkir);
                adapterKapasitasParkir.notifyDataSetChanged();
                Toast.makeText(konsumen_tampil_kapasitas_parkir.this,"Berhasil Memuat Kapasitas Parkir!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LD_Kendaraan> call, Throwable t) {
                Toast.makeText(konsumen_tampil_kapasitas_parkir.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRecycleViewKendaraan();
    }
}
