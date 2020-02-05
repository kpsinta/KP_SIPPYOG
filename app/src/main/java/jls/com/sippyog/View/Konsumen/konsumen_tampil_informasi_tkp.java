package jls.com.sippyog.View.Konsumen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_DeskripsiParkir;
import jls.com.sippyog.ListData.LD_DeskripsiParkir;
import jls.com.sippyog.Model.Model_DeskripsiParkir;
import jls.com.sippyog.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class konsumen_tampil_informasi_tkp extends AppCompatActivity {

    TextView waktu_operasional, alamat_tkp, nomor_telepon, deskripsi;
    private List<Model_DeskripsiParkir> mListDeskripsi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen_tampil_informasi_tkp);
        waktu_operasional = findViewById(R.id.waktu_operasional);
        alamat_tkp = findViewById(R.id.alamat_tkp);
        nomor_telepon = findViewById(R.id.nomor_telepon);
        deskripsi = findViewById(R.id.deskripsi);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_DeskripsiParkir.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_DeskripsiParkir apiClientDeskripsiParkir =retrofit.create(ApiClient_DeskripsiParkir.class);

        Call<LD_DeskripsiParkir> kendaraanModelCall = apiClientDeskripsiParkir.show();

        kendaraanModelCall.enqueue(new Callback<LD_DeskripsiParkir>() {
            @Override
            public void onResponse (Call<LD_DeskripsiParkir> call, Response<LD_DeskripsiParkir> response) {
                mListDeskripsi= response.body().getData();
                if(mListDeskripsi.isEmpty())
                {

                    Toast.makeText(konsumen_tampil_informasi_tkp.this,"Tidak Ada Deskripsi Parkir!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.i(konsumen_tampil_informasi_tkp.class.getSimpleName(), response.body().toString());
                    waktu_operasional.setText(mListDeskripsi.get(0).getWaktu_operasional());
                    alamat_tkp.setText(mListDeskripsi.get(0).getAlamat_tkp());
                    nomor_telepon.setText(mListDeskripsi.get(0).getNomor_telepon());
                    deskripsi.setText(mListDeskripsi.get(0).getDeskripsi());
                    Toast.makeText(konsumen_tampil_informasi_tkp.this,"Berhasil Memuat Deskripsi Parkir!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LD_DeskripsiParkir> call, Throwable t) {
                Toast.makeText(konsumen_tampil_informasi_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
