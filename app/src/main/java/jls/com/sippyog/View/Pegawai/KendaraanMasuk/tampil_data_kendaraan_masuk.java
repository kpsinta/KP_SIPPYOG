package jls.com.sippyog.View.Pegawai.KendaraanMasuk;

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

import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.Adapter.Adapter_KendaraanMasuk;
import jls.com.sippyog.Adapter.Adapter_KendaraanMasuk;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tambah_data_kendaraan_masuk;
import jls.com.sippyog.View.Pegawai.pegawai_main_menu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tampil_data_kendaraan_masuk extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_KendaraanMasuk adapterKendaraanMasuk;
    private List<Model_KendaraanMasuk> mListKendaraanMasuk = new ArrayList<>();
    Adapter_KendaraanMasuk.RecyclerViewClickListener listener;
    FloatingActionButton btn_tambahKendaraanMasuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_kendaraan_masuk);

        recyclerView = findViewById(R.id.recycler_view_kendaraan_masuk);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterKendaraanMasuk);
        
        btn_tambahKendaraanMasuk = (FloatingActionButton) findViewById(R.id.btn_tambahDataKendaraanMasuk);
        btn_tambahKendaraanMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tampil_data_kendaraan_masuk.this, tambah_data_kendaraan_masuk.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_kendaraan_masuk, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.searchMenu);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Kendaraan Masuk");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterKendaraanMasuk.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterKendaraanMasuk.getFilter().filter(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(tampil_data_kendaraan_masuk.this, pegawai_main_menu.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecycleViewKendaraanMasuk() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_KendaraanMasuk.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_KendaraanMasuk apiclientKendaraanMasuk =retrofit.create(ApiClient_KendaraanMasuk.class);

        // status parkir 0 = sedang parkir

        Call<LD_KendaraanMasuk> kendaraanMasukModelCall = apiclientKendaraanMasuk.showByStatusParkir(0);

        kendaraanMasukModelCall.enqueue(new Callback<LD_KendaraanMasuk>() {
            @Override
            public void onResponse (Call<LD_KendaraanMasuk> call, Response<LD_KendaraanMasuk> response) {
                mListKendaraanMasuk= response.body().getData();
                Log.i(tampil_data_kendaraan_masuk.class.getSimpleName(), response.body().toString());
                adapterKendaraanMasuk = new Adapter_KendaraanMasuk(mListKendaraanMasuk,tampil_data_kendaraan_masuk.this,listener);
                recyclerView.setAdapter(adapterKendaraanMasuk);
                adapterKendaraanMasuk.notifyDataSetChanged();
                Toast.makeText(tampil_data_kendaraan_masuk.this,"Welcome", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LD_KendaraanMasuk> call, Throwable t) {
                Toast.makeText(tampil_data_kendaraan_masuk.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRecycleViewKendaraanMasuk();
    }
}
