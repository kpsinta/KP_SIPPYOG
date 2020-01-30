package jls.com.sippyog.View.Admin.Kendaraan;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_data_kendaraan extends AppCompatActivity {
    private Button btnBatal, btnEdit, btnDelete;
    private TextInputEditText jenis_kendaraan,kapasitas_maksimum,biaya_parkir,biaya_denda;
    private Integer id_kendaraan,kapasitasMaksimum;
    private String jenisKendaraan;
    private Double biayaParkir,biayaDenda;

    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_kendaraan);

        jenis_kendaraan = findViewById(R.id.text_input_jenisKendaraan);
        kapasitas_maksimum = findViewById(R.id.text_input_jumlahKapasitasKendaraan);
        biaya_parkir = findViewById(R.id.text_input_biayaParkir);
        biaya_denda = findViewById(R.id.text_input_biayaDenda);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateKendaraan();
            }
        });
        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent();

            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteKendaraan();
                startIntent();
            }
        });

        i = getIntent();

        jenis_kendaraan.setText(i.getStringExtra("jenis_kendaraan"));
        kapasitasMaksimum=i.getIntExtra("kapasitas_maksimum",-1);
        kapasitas_maksimum.setText(kapasitasMaksimum.toString());
        biayaParkir=i.getDoubleExtra("biaya_parkir",-1);
        biaya_parkir.setText(String.format("%.0f",biayaParkir));
        biayaDenda=i.getDoubleExtra("biaya_denda",-1);
        biaya_denda.setText(String.format("%.0f",biayaDenda));
        id_kendaraan=i.getIntExtra("id_kendaraan",-1);
    }

    public void startIntent()
    {
        Intent intent= new Intent(getApplicationContext(), tampil_data_kendaraan.class);
        startActivity(intent);
    }

    private void UpdateKendaraan()
    {
        jenisKendaraan = jenis_kendaraan.getText().toString();
        kapasitasMaksimum = Integer.parseInt(kapasitas_maksimum.getText().toString());
        biayaParkir = Double.parseDouble(biaya_parkir.getText().toString());
        biayaDenda = Double.parseDouble(biaya_denda.getText().toString());

        if(jenisKendaraan.isEmpty() || kapasitas_maksimum.getText().toString().isEmpty() || biaya_parkir.getText().toString().isEmpty() || biaya_denda.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Retrofit.Builder builder=new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_Kendaraan.baseURL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit=builder.build();

            ApiClient_Kendaraan apiClientKendaraan =retrofit.create(ApiClient_Kendaraan.class);
            Call<ResponseBody> kendaraanDAOCall= apiClientKendaraan.update(jenisKendaraan,kapasitasMaksimum,biayaParkir,biayaDenda,id_kendaraan);
            kendaraanDAOCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        Toast.makeText(getApplicationContext(), "Success Update", Toast.LENGTH_SHORT).show();
                        startIntent();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed Update", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
    private void DeleteKendaraan() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Kendaraan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Kendaraan apiClientPegawai =retrofit.create(ApiClient_Kendaraan.class);

        Call<ResponseBody> pegawaiDAOCall = apiClientPegawai.delete(id_kendaraan);
        pegawaiDAOCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Success Delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed Delete", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
