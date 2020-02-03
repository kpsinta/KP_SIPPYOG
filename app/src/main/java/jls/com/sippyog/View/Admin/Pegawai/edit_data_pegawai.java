package jls.com.sippyog.View.Admin.Pegawai;

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
    private TextInputLayout til_password_pegawai_lama, til_password_pegawai_baru, textInputUsername, textInputNama, textInputNIP;
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
        textInputNama = findViewById(R.id.text_input_nama);
        textInputNIP = findViewById(R.id.text_input_nip);
        textInputUsername = findViewById(R.id.text_input_username);

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

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_data_pegawai.this);

                // Set a title for alert dialog
                builder.setTitle("Hapus Pegawai " +nama_pegawai.getText().toString());

                // Ask the final question
                builder.setMessage("Apakah anda yakin untuk hapus pegawai?");
                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeletePegawai();
                    }
                });
                // Set the alert dialog no button click listener
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
                dialog.setIcon(R.drawable.icon_kapasitas_3);
            }
        });

        i = getIntent();
        nama_pegawai.setText(i.getStringExtra("nama_pegawai"));
        nip_pegawai.setText(i.getStringExtra("nip_pegawai"));
        username_pegawai.setText(i.getStringExtra("username_pegawai"));
        id_pegawai = i.getIntExtra("id_pegawai", -1);
        id_role_fk = i.getIntExtra("id_role_fk", -1);
    }

    public void startIntent() {
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
    private boolean validatePasswordLama() {
        String passwordInput = password_pegawai_lama.getText().toString();
        if (passwordInput.isEmpty()) {
            til_password_pegawai_lama.setError("Harus diisi!");
            return false;
        } else if (passwordInput.length() > 15 || passwordInput.length() < 6) {
            til_password_pegawai_lama.setError("Password terdiri dari 6-15 karakter!");
            return false;
        } else {
            til_password_pegawai_lama.setError(null);
            return true;
        }
    }
    private boolean validatePasswordBaru() {
        String passwordInput = password_pegawai_baru.getText().toString();
        if (passwordInput.isEmpty()) {
            til_password_pegawai_baru.setError("Harus diisi!");
            return false;
        } else if (passwordInput.length() > 15 || passwordInput.length() < 6) {
            til_password_pegawai_baru.setError("Password terdiri dari 6-15 karakter!");
            return false;
        } else {
            til_password_pegawai_baru.setError(null);
            return true;
        }
    }
    private void UpdatePasswordPegawai() {
        usernamePegawai = i.getStringExtra("username_pegawai");
        passwordPegawaiLama = password_pegawai_lama.getText().toString();
        passwordPegawaiBaru = password_pegawai_baru.getText().toString();
        if (!validatePasswordLama() | !validatePasswordBaru()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    til_password_pegawai_baru.setError(null);
                    til_password_pegawai_lama.setError(null);
                }
            }, 2000);
            return;
        }
        else {
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

        if (!validateNama() | !validateNIP() | !validateUsername()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textInputNama.setError(null);
                    textInputNIP.setError(null);
                    textInputUsername.setError(null);
                }
            }, 2000);
            return;
        }
        else
        {
            Retrofit.Builder builder=new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_Pegawai.baseURL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit=builder.build();

            ApiClient_Pegawai apiClientPegawai =retrofit.create(ApiClient_Pegawai.class);
            Call<ResponseBody> pegawaiDAOCall= apiClientPegawai.update(id_role_fk,namaPegawai,nipPegawai,usernamePegawai,id_pegawai);
            pegawaiDAOCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        Toast.makeText(getApplicationContext(), "Berhasil Edit Pegawai!", Toast.LENGTH_SHORT).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startIntent();
                            }
                        }, 300);
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal Edit Pegawai!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
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
                    Toast.makeText(getApplicationContext(), "Berhasil Hapus Pegawai!", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startIntent();
                        }
                    }, 300);
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal Hapus Pegawai!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
