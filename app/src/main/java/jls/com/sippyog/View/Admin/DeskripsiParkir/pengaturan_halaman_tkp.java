package jls.com.sippyog.View.Admin.DeskripsiParkir;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_DeskripsiParkir;
import jls.com.sippyog.ListData.LD_DeskripsiParkir;
import jls.com.sippyog.Model.Model_DeskripsiParkir;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;
import jls.com.sippyog.View.Konsumen.konsumen_tampil_informasi_tkp;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pengaturan_halaman_tkp extends AppCompatActivity {
    EditText waktu_operasional, alamat_tkp, nomor_telepon, deskripsi;
    String waktuOperasional, alamatTKP, nomorTelepon, Deskripsi;
    private Button btnBatal, btnEdit;
    private Switch switchUbahDeskripsi;
    private List<Model_DeskripsiParkir> mListDeskripsi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_halaman_tkp);

        waktu_operasional = findViewById(R.id.waktu_operasional);
        alamat_tkp = findViewById(R.id.alamat_tkp);
        nomor_telepon = findViewById(R.id.nomor_telepon);
        deskripsi = findViewById(R.id.deskripsi);
        btnBatal = findViewById(R.id.btnBatal);
        btnEdit = findViewById(R.id.btnEdit);

        btnBatal.setVisibility(View.INVISIBLE);
        btnEdit.setVisibility(View.INVISIBLE);
        waktu_operasional.setEnabled(false);
        alamat_tkp.setEnabled(false);
        nomor_telepon.setEnabled(false);
        deskripsi.setEnabled(false);

        loadDeskripsiParkir();

        switchUbahDeskripsi = findViewById(R.id.switch_ubah_deskripsi);
        switchUbahDeskripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchUbahDeskripsi.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "Tidak Ubah Deskripsi", Toast.LENGTH_SHORT).show();
                    btnBatal.setVisibility(View.INVISIBLE);
                    btnEdit.setVisibility(View.INVISIBLE);
                    waktu_operasional.setEnabled(false);
                    alamat_tkp.setEnabled(false);
                    nomor_telepon.setEnabled(false);
                    deskripsi.setEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(), "Ubah Deskripsi", Toast.LENGTH_SHORT).show();
                    btnBatal.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    waktu_operasional.setEnabled(true);
                    alamat_tkp.setEnabled(true);
                    nomor_telepon.setEnabled(true);
                    deskripsi.setEnabled(true);
                }
            }
        });
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDeskripsiParkir();
            }
        });
        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent();
            }
        });
    }
    public void startIntent() {
        Intent intent = new Intent(getApplicationContext(), admin_main_menu.class);
        startActivity(intent);
    }
    private void UpdateDeskripsiParkir()
    {
        waktuOperasional = waktu_operasional.getText().toString();
        alamatTKP = alamat_tkp.getText().toString();
        nomorTelepon = nomor_telepon.getText().toString();
        Deskripsi = deskripsi.getText().toString();

        if (waktuOperasional.isEmpty() || alamatTKP.isEmpty() || nomorTelepon.isEmpty() || Deskripsi.isEmpty()) {
            Toast.makeText(this, "Semua Field harus diisi!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Integer id = mListDeskripsi.get(0).getId_deskripsi_parkir();
            Retrofit.Builder builder=new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_DeskripsiParkir.baseURL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit=builder.build();

            ApiClient_DeskripsiParkir apiClientPegawai =retrofit.create(ApiClient_DeskripsiParkir.class);
            Call<ResponseBody> pegawaiDAOCall= apiClientPegawai.update(waktuOperasional,alamatTKP,nomorTelepon,Deskripsi,id);
            pegawaiDAOCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        Toast.makeText(getApplicationContext(), "Berhasil Edit Dekripsi Parkir!", Toast.LENGTH_SHORT).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startIntent();
                            }
                        }, 300);
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal Edit Deskripsi Parkir!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void loadDeskripsiParkir(){
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

                    Toast.makeText(pengaturan_halaman_tkp.this,"Tidak Ada Deskripsi Parkir!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.i(konsumen_tampil_informasi_tkp.class.getSimpleName(), response.body().toString());
                    waktu_operasional.setText(mListDeskripsi.get(0).getWaktu_operasional());
                    alamat_tkp.setText(mListDeskripsi.get(0).getAlamat_tkp());
                    nomor_telepon.setText(mListDeskripsi.get(0).getNomor_telepon());
                    deskripsi.setText(mListDeskripsi.get(0).getDeskripsi());
                    Toast.makeText(pengaturan_halaman_tkp.this,"Berhasil Memuat Deskripsi Parkir!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LD_DeskripsiParkir> call, Throwable t) {
                Toast.makeText(pengaturan_halaman_tkp.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
