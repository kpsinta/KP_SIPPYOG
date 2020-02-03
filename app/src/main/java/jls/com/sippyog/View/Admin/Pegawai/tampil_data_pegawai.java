package jls.com.sippyog.View.Admin.Pegawai;

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

import jls.com.sippyog.API.ApiClient_Pegawai;
import jls.com.sippyog.Adapter.Adapter_Pegawai;
import jls.com.sippyog.ListData.LD_Pegawai;
import jls.com.sippyog.Model.Model_Pegawai;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;
import jls.com.sippyog.View.Admin.admin_pengelolaan_data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tampil_data_pegawai extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_Pegawai adapterPegawai;
    private List<Model_Pegawai> mListPegawai = new ArrayList<>();
    Adapter_Pegawai.RecyclerViewClickListener listener;
    FloatingActionButton btn_tambahPegawai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_pegawai);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_pegawai);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterPegawai);

        listener = new Adapter_Pegawai.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), edit_data_pegawai.class);
                intent.putExtra("id_pegawai", mListPegawai.get(position).getId_pegawai());
                intent.putExtra("id_role_fk", mListPegawai.get(position).getId_role_fk());
                intent.putExtra("nama_pegawai", mListPegawai.get(position).getNama_pegawai());
                intent.putExtra("nip_pegawai", mListPegawai.get(position).getNip_pegawai());
                intent.putExtra("username_pegawai", mListPegawai.get(position).getUsername_pegawai());

                startActivity(intent);
            }
        };
        btn_tambahPegawai = (FloatingActionButton) findViewById(R.id.btn_tambahDataPegawai);
        btn_tambahPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tampil_data_pegawai.this, tambah_data_pegawai.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pegawai, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.searchMenu);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Cari Pegawai");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterPegawai.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterPegawai.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(tampil_data_pegawai.this, admin_pengelolaan_data.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecycleViewPegawai() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Pegawai.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Pegawai apiclientPegawai =retrofit.create(ApiClient_Pegawai.class);

        Call<LD_Pegawai> pegawaiModelCall = apiclientPegawai.show();

        pegawaiModelCall.enqueue(new Callback<LD_Pegawai>() {
            @Override
            public void onResponse (Call<LD_Pegawai> call, Response<LD_Pegawai> response) {
                mListPegawai= response.body().getData();
                adapterPegawai = new Adapter_Pegawai(mListPegawai,tampil_data_pegawai.this,listener);
                recyclerView.setAdapter(adapterPegawai);
                adapterPegawai.notifyDataSetChanged();
                if(mListPegawai.isEmpty())
                {
                    Toast.makeText(tampil_data_pegawai.this,"Tidak Ada Data Pegawai!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.i(tampil_data_pegawai.class.getSimpleName(), response.body().toString());
                    Toast.makeText(tampil_data_pegawai.this,"Berhasil Memuat Data Pegawai!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<LD_Pegawai> call, Throwable t) {
                Toast.makeText(tampil_data_pegawai.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRecycleViewPegawai();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(tampil_data_pegawai.this, admin_pengelolaan_data.class);
        startActivity(intent);
        finish();
    }
}
