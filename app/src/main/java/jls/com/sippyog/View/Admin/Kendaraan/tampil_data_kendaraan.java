package jls.com.sippyog.View.Admin.Kendaraan;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.Adapter.Adapter_Kendaraan;
import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;
import jls.com.sippyog.View.Admin.admin_pengelolaan_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tampil_data_kendaraan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_Kendaraan adapterKendaraan;
    private List<Model_Kendaraan> mListKendaraan = new ArrayList<>();
    Adapter_Kendaraan.RecyclerViewClickListener listener;
    FloatingActionButton btn_tambahKendaraan;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_kendaraan);

        recyclerView = findViewById(R.id.recycler_view_kendaraan);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterKendaraan);
        listener = new Adapter_Kendaraan.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), edit_data_kendaraan.class);
                intent.putExtra("id_kendaraan", mListKendaraan.get(position).getId_kendaraan());
                intent.putExtra("jenis_kendaraan", mListKendaraan.get(position).getJenis_kendaraan());
                intent.putExtra("kapasitas_maksimum", mListKendaraan.get(position).getKapasitas_maksimum());
                intent.putExtra("biaya_parkir", mListKendaraan.get(position).getBiaya_parkir());
                intent.putExtra("biaya_denda", mListKendaraan.get(position).getBiaya_denda());

                startActivity(intent);
            }
        };
        btn_tambahKendaraan = (FloatingActionButton) findViewById(R.id.btn_tambahDataKendaraan);
        btn_tambahKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tampil_data_kendaraan.this, tambah_data_kendaraan.class);
                startActivity(i);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kendaraan, menu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(tampil_data_kendaraan.this, admin_main_menu.class);
            startActivity(i);
            return true;
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
                Log.i(tampil_data_kendaraan.class.getSimpleName(), response.body().toString());
                adapterKendaraan = new Adapter_Kendaraan(mListKendaraan,tampil_data_kendaraan.this,listener);
                recyclerView.setAdapter(adapterKendaraan);
                adapterKendaraan.notifyDataSetChanged();
                Toast.makeText(tampil_data_kendaraan.this,"Berhasil Memuat Data Kendaraan!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LD_Kendaraan> call, Throwable t) {
                Toast.makeText(tampil_data_kendaraan.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRecycleViewKendaraan();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(tampil_data_kendaraan.this, admin_pengelolaan_data.class);
        startActivity(intent);
        finish();
    }
}

