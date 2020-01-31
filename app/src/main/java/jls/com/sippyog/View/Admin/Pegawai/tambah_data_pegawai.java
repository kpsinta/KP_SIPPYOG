package jls.com.sippyog.View.Admin.Pegawai;

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
    private TextInputLayout textInputUsername, textInputPassword, textInputNama, textInputNIP;
    private TextInputEditText nama_pegawai,nip_pegawai,username_pegawai,password_pegawai;
    private Integer id_role_fk;
    private String namaPegawai,nipPegawai,usernamePegawai,passwordPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pegawai);

        textInputNama = findViewById(R.id.text_input_nama);
        textInputNIP = findViewById(R.id.text_input_nip);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);

        nama_pegawai = findViewById(R.id.text_input_namaPegawai);
        nip_pegawai = findViewById(R.id.text_input_NIPPegawai);
        username_pegawai = findViewById(R.id.text_input_usernamePegawai);
        password_pegawai = findViewById(R.id.text_input_passwordPegawai);

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
        Intent intent = new Intent(getApplicationContext(), tampil_data_pegawai.class);
        startActivity(intent);
    }
    private boolean validateNama() {
        String namaInput = nama_pegawai.getText().toString();
        if (namaInput.isEmpty()) {
            textInputNama.setError("Harus diisi!!");

            return false;
        } else if (namaInput.length() > 100) {
            textInputNama.setError("Maksimal 100 karakter!");
            return false;
        } else {
            textInputNama.setError(null);
            return true;
        }
    }
    private boolean validateNIP() {
        String nipInput = nip_pegawai.getText().toString();
        if (nipInput.isEmpty()) {
            textInputNIP.setError("Harus diisi!!");

            return false;
        } else if (nipInput.length() > 30) {
            textInputNIP.setError("Maksimal 30 karakter!");
            return false;
        } else {
            textInputNIP.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        String usernameInput = username_pegawai.getText().toString();
        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Harus diisi!!");

            return false;
        } else if (usernameInput.length() > 15 || usernameInput.length() < 6) {
            textInputUsername.setError("Username terdiri dari 6-15 karakter!");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = password_pegawai.getText().toString();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Harus diisi!!");
            return false;
        } else if (passwordInput.length() > 15 || passwordInput.length() < 6) {
            textInputPassword.setError("Password terdiri dari 6-15 karakter!");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
    private void onClickRegister() {
        namaPegawai = nama_pegawai.getText().toString();
        nipPegawai = nip_pegawai.getText().toString();
        usernamePegawai = username_pegawai.getText().toString();
        passwordPegawai = password_pegawai.getText().toString();

        if (!validateNama() | !validateNIP() | !validateUsername() | !validatePassword()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textInputNama.setError(null);
                    textInputNIP.setError(null);
                    textInputUsername.setError(null);
                    textInputPassword.setError(null);
                }
            }, 2000);
            return;
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
                    Toast.makeText(tambah_data_pegawai.this, "Berhasil Tambah Pegawai!", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<Model_Pegawai> call, Throwable t) {
                    Toast.makeText(tambah_data_pegawai.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
