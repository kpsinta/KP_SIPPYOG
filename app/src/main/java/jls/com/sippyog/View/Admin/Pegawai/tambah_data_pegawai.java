package jls.com.sippyog.View.Admin.Pegawai;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jls.com.sippyog.API.ApiClient_Pegawai;
import jls.com.sippyog.Model.Model_Pegawai;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.Kendaraan.tampil_data_kendaraan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_data_pegawai extends AppCompatActivity {
    private Button btnBatal, btnSimpan;
    private TextInputEditText nama_pegawai,nip_pegawai,username_pegawai,password_pegawai;
    private Integer id_role_fk;
    private String namaPegawai,nipPegawai,usernamePegawai,passwordPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pegawai);

        nama_pegawai = findViewById(R.id.text_input_namaPegawai);
        nip_pegawai = findViewById(R.id.text_input_NIPPegawai);
        username_pegawai = findViewById(R.id.text_input_usernamePegawai);
        password_pegawai = findViewById(R.id.text_input_passwordPegawai);

        btnBatal = findViewById(R.id.btnBatal);
        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama_pegawai.getText().clear();
                nip_pegawai.getText().clear();
                username_pegawai.getText().clear();
                password_pegawai.getText().clear();
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
        namaPegawai = nama_pegawai.getText().toString();
        nipPegawai = nip_pegawai.getText().toString();
        usernamePegawai = username_pegawai.getText().toString();
        passwordPegawai = password_pegawai.getText().toString();

        if (namaPegawai.isEmpty() || nipPegawai.isEmpty() || usernamePegawai.isEmpty() || passwordPegawai.isEmpty())
        {
            Toast.makeText(this,"Semua Field harus diisi", Toast.LENGTH_SHORT).show();
        } else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit.Builder builder = new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_Pegawai.baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            ApiClient_Pegawai apiClientPegawai = retrofit.create(ApiClient_Pegawai.class);
            Call<Model_Pegawai> pegawaiDAOCall = apiClientPegawai.create(2,namaPegawai,nipPegawai,usernamePegawai,passwordPegawai);
            pegawaiDAOCall.enqueue(new Callback<Model_Pegawai>() {
                @Override
                public void onResponse(Call<Model_Pegawai> call, Response<Model_Pegawai> response) {
                    Toast.makeText(tambah_data_pegawai.this, "Tambah Pegawai Sukses!", Toast.LENGTH_SHORT).show();
                    startIntent();
                }
                @Override
                public void onFailure(Call<Model_Pegawai> call, Throwable t) {
                    Toast.makeText(tambah_data_pegawai.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
