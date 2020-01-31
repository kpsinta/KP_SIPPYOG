package jls.com.sippyog.View.Admin.Kendaraan;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
    private TextInputLayout textInputJenis, textInputKapasitas, textInputParkir, textInputDenda;
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

        textInputJenis = findViewById(R.id.text_layout_jenisKendaraan);
        textInputKapasitas = findViewById(R.id.text_layout_jumlahKapasitasKendaraan);
        textInputParkir = findViewById(R.id.text_layout_biayaParkir);
        textInputDenda = findViewById(R.id.text_layout_biayaDenda);

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
    private boolean validateJenisKendaraan() {
        String jenisInput = jenis_kendaraan.getText().toString();
        if (jenisInput.isEmpty()) {
            textInputJenis.setError("Harus diisi!");

            return false;
        } else if (jenisInput.length() > 30) {
            textInputJenis.setError("Maksimal 30 karakter!");
            return false;
        } else {
            textInputJenis.setError(null);
            return true;
        }
    }
    private boolean validateKapasitasKendaraan() {
        String kapasitasInput = kapasitas_maksimum.getText().toString();
        if (kapasitasInput.isEmpty()) {
            textInputKapasitas.setError("Harus diisi!");

            return false;
        } else if (kapasitasInput.length() > 5) {
            textInputKapasitas.setError("Maksimal 5 digit!");
            return false;
        } else {
            textInputKapasitas.setError(null);
            return true;
        }
    }
    private boolean validateBiayaParkir() {
        String parkirInput = biaya_parkir.getText().toString();
        if (parkirInput.isEmpty()) {
            textInputParkir.setError("Harus diisi!");

            return false;
        } else if (parkirInput.length() > 6) {
            textInputParkir.setError("Maksimal 6 digit!");
            return false;
        } else {
            textInputParkir.setError(null);
            return true;
        }
    }
    private boolean validateBiayaDenda() {
        String dendaInput = biaya_denda.getText().toString();
        if (dendaInput.isEmpty()) {
            textInputDenda.setError("Harus diisi!");

            return false;
        } else if (dendaInput.length() > 6) {
            textInputDenda.setError("Maksimal 6 digit!");
            return false;
        } else {
            textInputDenda.setError(null);
            return true;
        }
    }
    private void onClickRegister() {
        if (!validateJenisKendaraan() | !validateKapasitasKendaraan() | !validateBiayaParkir() | !validateBiayaDenda()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textInputDenda.setError(null);
                    textInputJenis.setError(null);
                    textInputParkir.setError(null);
                    textInputKapasitas.setError(null);
                }
            }, 2000);
            return;
        }
        else {
            jenisKendaraan = jenis_kendaraan.getText().toString();
            kapasitasMaksimum = kapasitas_maksimum.getText().toString();
            biayaParkir = biaya_parkir.getText().toString();
            biayaDenda = biaya_denda.getText().toString();
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
                    Toast.makeText(tambah_data_kendaraan.this, "Berhasil Tambah Kendaraan!", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startIntent();
                        }
                    }, 700);
                    return;
                }
                @Override
                public void onFailure(Call<Model_Kendaraan> call, Throwable t) {
                    Toast.makeText(tambah_data_kendaraan.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
