package jls.com.sippyog.View.Admin.Kendaraan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
    private TextInputLayout textInputJenis, textInputKapasitas, textInputParkir, textInputDenda;
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

        textInputJenis = findViewById(R.id.text_layout_jenisKendaraan);
        textInputKapasitas = findViewById(R.id.text_layout_jumlahKapasitasKendaraan);
        textInputParkir = findViewById(R.id.text_layout_biayaParkir);
        textInputDenda = findViewById(R.id.text_layout_biayaDenda);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_data_kendaraan.this);
                builder.setTitle("Hapus Kendaraan " +jenis_kendaraan.getText().toString());
                builder.setMessage("Apakah anda yakin untuk hapus kendaraan?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteKendaraan();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setIcon(R.drawable.icon_kapasitas_3);
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
    private void UpdateKendaraan()
    {
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
        else
        {
            jenisKendaraan = jenis_kendaraan.getText().toString();
            kapasitasMaksimum = Integer.parseInt(kapasitas_maksimum.getText().toString());
            biayaParkir = Double.parseDouble(biaya_parkir.getText().toString());
            biayaDenda = Double.parseDouble(biaya_denda.getText().toString());
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
                        Toast.makeText(getApplicationContext(), "Berhasil Edit Kendaraan!", Toast.LENGTH_SHORT).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startIntent();
                            }
                        }, 300);
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal Edit Kendaraan!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Berhasil Hapus Kendaraan!", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startIntent();
                        }
                    }, 300);
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal Hapus Kendaraan!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
