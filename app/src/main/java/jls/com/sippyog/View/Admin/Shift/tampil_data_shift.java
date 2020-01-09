package jls.com.sippyog.View.Admin.Shift;

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

import jls.com.sippyog.API.ApiClient_Shift;
import jls.com.sippyog.Adapter.Adapter_Shift;
import jls.com.sippyog.ListData.LD_Shift;
import jls.com.sippyog.Model.Model_Shift;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tampil_data_shift extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public Adapter_Shift adapterShift;
    private List<Model_Shift> mListShift = new ArrayList<>();
    Adapter_Shift.RecyclerViewClickListener listener;
    FloatingActionButton btn_tambahShift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_shift);

        recyclerView = findViewById(R.id.recycler_view_shift);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterShift);
        listener = new Adapter_Shift.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), edit_data_shift.class);
                intent.putExtra("id_shift", mListShift.get(position).getId_shift());
                intent.putExtra("nama_shift", mListShift.get(position).getNama_shift());
                intent.putExtra("jam_masuk", mListShift.get(position).getJam_masuk());
                intent.putExtra("jam_keluar", mListShift.get(position).getJam_keluar());

                startActivity(intent);
            }
        };
        btn_tambahShift = (FloatingActionButton) findViewById(R.id.btn_tambahDataShift);
        btn_tambahShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tampil_data_shift.this, tambah_data_shift.class);
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
            Intent i = new Intent(tampil_data_shift.this, admin_main_menu.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRecycleViewShift() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Shift.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Shift apiclientShift =retrofit.create(ApiClient_Shift.class);

        Call<LD_Shift> kendaraanModelCall = apiclientShift.show();

        kendaraanModelCall.enqueue(new Callback<LD_Shift>() {
            @Override
            public void onResponse (Call<LD_Shift> call, Response<LD_Shift> response) {
                mListShift= response.body().getData();
                Log.i(tampil_data_shift.class.getSimpleName(), response.body().toString());
                adapterShift = new Adapter_Shift(mListShift,tampil_data_shift.this,listener);
                recyclerView.setAdapter(adapterShift);
                adapterShift.notifyDataSetChanged();
                Toast.makeText(tampil_data_shift.this,"Welcome", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LD_Shift> call, Throwable t) {
                Toast.makeText(tampil_data_shift.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setRecycleViewShift();
    }
}