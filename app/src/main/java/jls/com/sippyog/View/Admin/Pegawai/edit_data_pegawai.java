package jls.com.sippyog.View.Admin.Pegawai;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jls.com.sippyog.API.ApiClient_Pegawai;
import jls.com.sippyog.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_data_pegawai extends AppCompatActivity {
    private Button btnBatal, btnEdit, btnDelete;
    private Switch switchUbahPassword;
    private TextInputEditText nama_pegawai, nip_pegawai, username_pegawai, password_pegawai_lama, password_pegawai_baru;
    private TextInputLayout til_password_pegawai_lama, til_password_pegawai_baru;
    private Integer id_role_fk, id_pegawai;
    private String namaPegawai, nipPegawai, usernamePegawai, passwordPegawaiLama, passwordPegawaiBaru;
    private Intent i;
    private Integer count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_pegawai);

        nama_pegawai = findViewById(R.id.text_input_namaPegawai);
        nip_pegawai = findViewById(R.id.text_input_NIPPegawai);
        username_pegawai = findViewById(R.id.text_input_usernamePegawai);
        password_pegawai_lama = findViewById(R.id.text_input_passwordPegawaiLama);
        til_password_pegawai_lama = findViewById(R.id.text_input_layout_passwordPegawaiLama);
        password_pegawai_baru = findViewById(R.id.text_input_passwordPegawaiBaru);
        til_password_pegawai_baru = findViewById(R.id.text_input_layout_passwordPegawaiBaru);

        switchUbahPassword = findViewById(R.id.switch_ubah_Password);
        switchUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchUbahPassword.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "Tidak Ubah Password", Toast.LENGTH_SHORT).show();
                    password_pegawai_lama.setVisibility(View.INVISIBLE);
                    til_password_pegawai_lama.setVisibility(View.INVISIBLE);
                    password_pegawai_baru.setVisibility(View.INVISIBLE);
                    til_password_pegawai_baru.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Ubah Password", Toast.LENGTH_SHORT).show();
                    password_pegawai_lama.setVisibility(View.VISIBLE);
                    til_password_pegawai_lama.setVisibility(View.VISIBLE);
                    password_pegawai_baru.setVisibility(View.VISIBLE);
                    til_password_pegawai_baru.setVisibility(View.VISIBLE);
                }
            }
        });

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchUbahPassword.isChecked() == true)
                {
                    UpdatePasswordPegawai();
                }
                else
                {
                    UpdatePegawai();
                }

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
                DeletePegawai();
                startIntent();
            }
        });

        i = getIntent();
        nama_pegawai.setText(i.getStringExtra("nama_pegawai"));
        nip_pegawai.setText(i.getStringExtra("nip_pegawai"));
        username_pegawai.setText(i.getStringExtra("username_pegawai"));
        id_pegawai = i.getIntExtra("id_pegawai", -1);
    }

    public void startIntent() {
        Intent intent = new Intent(getApplicationContext(), tampil_data_pegawai.class);
        startActivity(intent);
    }

    private void UpdatePasswordPegawai() {
        usernamePegawai = i.getStringExtra("username_pegawai").toString();
        passwordPegawaiLama = password_pegawai_lama.getText().toString();
        passwordPegawaiBaru = password_pegawai_baru.getText().toString();
        if (passwordPegawaiLama.isEmpty() || passwordPegawaiBaru.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
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

            Call<ResponseBody> pegawaiDAOCall = apiClientPegawai.updatePassword(usernamePegawai, passwordPegawaiLama, passwordPegawaiBaru);
            pegawaiDAOCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        Log.d("Berhasil : ", response.message());
                        Toast.makeText(edit_data_pegawai.this, "Update Password berhasil!", Toast.LENGTH_SHORT).show();
                        UpdatePegawai();

                    } else {
                        Toast.makeText(edit_data_pegawai.this, "Password lama tidak sesuai!", Toast.LENGTH_SHORT).show();
                        Log.d("Rsponse gagal : ", response.message());
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(edit_data_pegawai.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Response failure : ", t.getLocalizedMessage());
                }
            });
        }
    }



    private void UpdatePegawai()
    {
        namaPegawai = nama_pegawai.getText().toString();
        nipPegawai = nip_pegawai.getText().toString();
        usernamePegawai = username_pegawai.getText().toString();
        
        if(namaPegawai.isEmpty() || nipPegawai.isEmpty() || usernamePegawai.isEmpty())
        {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Retrofit.Builder builder=new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_Pegawai.baseURL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit=builder.build();

            ApiClient_Pegawai apiClientPegawai =retrofit.create(ApiClient_Pegawai.class);
            Call<ResponseBody> pegawaiDAOCall= apiClientPegawai.update(2,namaPegawai,nipPegawai,usernamePegawai,id_pegawai);
            pegawaiDAOCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        Toast.makeText(getApplicationContext(), "Success Update", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed Update", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
            startIntent();
        }
    }
    private void DeletePegawai() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Pegawai.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Pegawai apiClientPegawai =retrofit.create(ApiClient_Pegawai.class);

        Call<ResponseBody> pegawaiDAOCall = apiClientPegawai.delete(id_pegawai);
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
