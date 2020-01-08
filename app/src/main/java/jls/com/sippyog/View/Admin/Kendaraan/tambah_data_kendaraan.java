package jls.com.sippyog.View.Admin.Kendaraan;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_data_kendaraan extends AppCompatActivity {
    private Button btnBatal, btnSimpan;
    private TextInputEditText jenis_kendaraan,kapasitas_maksimum,biaya_parkir,biaya_denda;
    private String jenisKendaraan,kapasitasMaksimum,biayaParkir,biayaDenda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kendaraan);

        jenis_kendaraan = findViewById(R.id.text_input_jenisKendaraan);
        kapasitas_maksimum = findViewById(R.id.text_input_jumlahKapasitasKendaraan);
        biaya_parkir = findViewById(R.id.text_input_biayaParkir);
        biaya_denda = findViewById(R.id.text_input_biayaDenda);

        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenis_kendaraan.getText().clear();
                kapasitas_maksimum.getText().clear();
                biaya_parkir.getText().clear();
                biaya_denda.getText().clear();
            }
        });

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });

    }
    private void startIntent() {
        Intent intent = new Intent(getApplicationContext(), tampil_data_kendaraan.class);
        startActivity(intent);
    }

    private void onClickRegister() {
        jenisKendaraan = jenis_kendaraan.getText().toString();
        kapasitasMaksimum = kapasitas_maksimum.getText().toString();
        biayaParkir = biaya_parkir.getText().toString();
        biayaDenda = biaya_denda.getText().toString();

        if (jenisKendaraan.isEmpty() || kapasitasMaksimum.isEmpty() || biayaParkir.isEmpty() || biayaDenda.isEmpty())
        {
            Toast.makeText(this,"Semua Field harus diisi", Toast.LENGTH_SHORT).show();
        } else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder = new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_Kendaraan.baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            ApiClient_Kendaraan apiClientKendaraan = retrofit.create(ApiClient_Kendaraan.class);
            Call<Model_Kendaraan> kendaraanDAOCall = apiClientKendaraan.create(jenisKendaraan,Integer.parseInt(kapasitasMaksimum),biayaParkir,biayaDenda);
            kendaraanDAOCall.enqueue(new Callback<Model_Kendaraan>() {
                @Override
                public void onResponse(Call<Model_Kendaraan> call, Response<Model_Kendaraan> response) {
                    Toast.makeText(tambah_data_kendaraan.this, "Tambah Kendaraan Sukses!", Toast.LENGTH_SHORT).show();
                    startIntent();
                }
                @Override
                public void onFailure(Call<Model_Kendaraan> call, Throwable t) {
                    Toast.makeText(tambah_data_kendaraan.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
