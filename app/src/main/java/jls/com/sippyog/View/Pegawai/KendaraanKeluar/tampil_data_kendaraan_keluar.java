package jls.com.sippyog.View.Pegawai.KendaraanKeluar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_KendaraanKeluar;
import jls.com.sippyog.Adapter.Adapter_KendaraanKeluar;
import jls.com.sippyog.Adapter.Adapter_KendaraanKeluar;
import jls.com.sippyog.ListData.LD_KendaraanKeluar;
import jls.com.sippyog.Model.Model_KendaraanKeluar;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Pegawai.KendaraanKeluar.tampil_data_kendaraan_keluar;
import jls.com.sippyog.View.Pegawai.KendaraanKeluar.tampil_data_kendaraan_keluar;
import jls.com.sippyog.View.Pegawai.pegawai_main_menu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tampil_data_kendaraan_keluar extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_KendaraanKeluar adapterKendaraanKeluar;
    private List<Model_KendaraanKeluar> mListKendaraanKeluar = new ArrayList<>();
    Adapter_KendaraanKeluar.RecyclerViewClickListener listener;
    FloatingActionButton btn_tambahKendaraanKeluar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_kendaraan_keluar);

        recyclerView = findViewById(R.id.recycler_view_kendaraan_keluar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterKendaraanKeluar);
        btn_tambahKendaraanKeluar = (FloatingActionButton) findViewById(R.id.btn_tambahDataKendaraanKeluar);
        btn_tambahKendaraanKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tampil_data_kendaraan_keluar.this, tambah_data_kendaraan_keluar.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_kendaraan_keluar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.searchMenu);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Kendaraan Keluar");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterKendaraanKeluar.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterKendaraanKeluar.getFilter().filter(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(tampil_data_kendaraan_keluar.this, pegawai_main_menu.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }public void setRecycleViewKendaraanKeluar() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_KendaraanKeluar.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_KendaraanKeluar apiclientKendaraanKeluar =retrofit.create(ApiClient_KendaraanKeluar.class);
        Call<LD_KendaraanKeluar> kendaraanKeluarModelCall = apiclientKendaraanKeluar.showToday();

        kendaraanKeluarModelCall.enqueue(new Callback<LD_KendaraanKeluar>() {
            @Override
            public void onResponse (Call<LD_KendaraanKeluar> call, Response<LD_KendaraanKeluar> response) {
                mListKendaraanKeluar= response.body().getData();
                Log.i(tampil_data_kendaraan_keluar.class.getSimpleName(), response.body().toString());
                adapterKendaraanKeluar = new Adapter_KendaraanKeluar(mListKendaraanKeluar,tampil_data_kendaraan_keluar.this,listener);
                recyclerView.setAdapter(adapterKendaraanKeluar);
                adapterKendaraanKeluar.notifyDataSetChanged();
                Toast.makeText(tampil_data_kendaraan_keluar.this,"Welcome", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LD_KendaraanKeluar> call, Throwable t) {
                Toast.makeText(tampil_data_kendaraan_keluar.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRecycleViewKendaraanKeluar();
    }
}