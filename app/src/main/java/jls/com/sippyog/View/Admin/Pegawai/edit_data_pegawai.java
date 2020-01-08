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
import jls.com.sippyog.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_data_pegawai extends AppCompatActivity {
    private Button btnBatal, btnEdit, btnDelete;
    private TextInputEditText nama_pegawai,nip_pegawai,username_pegawai,password_pegawai;
    private Integer id_role_fk, id_pegawai;
    private String namaPegawai,nipPegawai,usernamePegawai,passwordPegawai;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_pegawai);

        nama_pegawai = findViewById(R.id.text_input_namaPegawai);
        nip_pegawai = findViewById(R.id.text_input_NIPPegawai);
        username_pegawai = findViewById(R.id.text_input_usernamePegawai);
        password_pegawai = findViewById(R.id.text_input_passwordPegawai);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePegawai();
                startIntent();
            }
        });
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
        password_pegawai.setText(i.getStringExtra("password_pegawai"));
        id_pegawai=Integer.parseInt(i.getStringExtra("id_pegawai"));
    }
    public void startIntent()
    {
        Intent intent= new Intent(getApplicationContext(), tampil_data_pegawai.class);
        startActivity(intent);
    }
    private void UpdatePegawai()
    {
        namaPegawai = nama_pegawai.getText().toString();
        nipPegawai = nip_pegawai.getText().toString();
        usernamePegawai = username_pegawai.getText().toString();
        passwordPegawai = password_pegawai.getText().toString();
        
        if(namaPegawai.isEmpty() || nipPegawai.isEmpty() || usernamePegawai.isEmpty() || passwordPegawai.isEmpty())
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
            Call<ResponseBody> pegawaiDAOCall= apiClientPegawai.update(2,namaPegawai,nipPegawai,usernamePegawai,passwordPegawai,id_pegawai);
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
