package jls.com.sippyog.View.Pegawai.KendaraanMasuk;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_data_kendaraan_masuk extends AppCompatActivity {

    Spinner spinner_jenis_kendaraan;
    TextView waktu_masuk;
    Integer tempIDKendaraan;
    TextInputEditText nomor_plat;
    Button btnSimpan, btnBatal;


    //ini untuk load data
    List<Model_Kendaraan> spinnerJenisKendaraanArray = new ArrayList<>();
    List<String> string_jenisKendaraan = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kendaraan_masuk);

        nomor_plat = findViewById(R.id.text_input_platKendaraan);
        spinner_jenis_kendaraan = findViewById(R.id.spinner_jenis_kendaraan);
        loadJenisKendaraan();

        Thread t = new Thread(){
            @Override
            public void run(){
                try{
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                waktu_masuk = findViewById(R.id.textView_waktuMasuk);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
                                String dateString = sdf.format(date);
                                waktu_masuk.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomor_plat.getText().clear();
            }
        });
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegister();
            }
        });
    }

    void loadJenisKendaraan(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Kendaraan.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        //ngeload nama cabang dari database
        ApiClient_Kendaraan apiclientKendaraan = retrofit.create(ApiClient_Kendaraan.class);
        Call<LD_Kendaraan> callKendaraan = apiclientKendaraan.show();
        callKendaraan.enqueue(new Callback<LD_Kendaraan>() {
            @Override
            public void onResponse(Call<LD_Kendaraan> callKendaraan, Response<LD_Kendaraan> response) {

                spinnerJenisKendaraanArray = response.body().getData();
                for (int i = 0; i < spinnerJenisKendaraanArray.size(); i++) {
                    string_jenisKendaraan.add(spinnerJenisKendaraanArray.get(i).getJenis_kendaraan());
                }
                ArrayAdapter<String> adapterJenisKendaraan = new ArrayAdapter<>(tambah_data_kendaraan_masuk.this, R.layout.spinner_kendaraan_layout,R.id.txtJenisKendaraan, string_jenisKendaraan);
                spinner_jenis_kendaraan.setAdapter(adapterJenisKendaraan);
            }
            @Override
            public void onFailure(Call<LD_Kendaraan> call, Throwable t) {
                Toast.makeText(tambah_data_kendaraan_masuk.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure: ",t.getLocalizedMessage());
            }
        });
    }
    private void startIntent() {
        Intent intent = new Intent(getApplicationContext(), tampil_data_kendaraan_masuk.class);
        startActivity(intent);
    }
    private void onClickRegister() {
        if (nomor_plat.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Semua Field harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            Retrofit.Builder builder = new Retrofit
//                    .Builder()
//                    .baseUrl(ApiClient_Motor.baseURL)
//                    .addConverterFactory(GsonConverterFactory.create(gson));
//            Retrofit retrofit = builder.build();
//            ApiClient_MotorKonsumen apiClientMotorKonsumen= retrofit.create(ApiClient_MotorKonsumen.class);
//
//            String[] selectedTipeMotor=spinner_motor.getSelectedItem().toString().split("-");
//            String[] selectedNamaKonsumen=spinner_konsumen.getSelectedItem().toString().split("-");
//
//            for(int i=0; i<spinnerMotorArray.size();i++){
//
//                if(spinnerMotorArray.get(i).getTipe_motor().equals(selectedTipeMotor[0]))
//                {
//                    tempIDMotor=spinnerMotorArray.get(i).getId_motor();
//                }
//            }
//            Log.d("Selected  Motor : ",selectedTipeMotor[0]);
//            Log.d("Selected ID Motor : ",tempIDMotor.toString());
//            for(int i=0; i<spinnerKonsumenArray.size();i++){
//
//                if(spinnerKonsumenArray.get(i).getNama_konsumen().equals(selectedNamaKonsumen[0]))
//                {
//                    tempIDKonsumen=spinnerKonsumenArray.get(i).getId_konsumen();
//                }
//            }
//            Log.d("Selected  Konsumen : ",selectedNamaKonsumen[0]);
//            Log.d("Selected ID Konsumen : ",tempIDKonsumen.toString());
//            sNoPlat = noPlat.getText().toString();
//
//            Call<Model_MotorKonsumen> motorKonsumenDAOCall = apiClientMotorKonsumen.create(tempIDMotor,tempIDKonsumen,sNoPlat);
//            motorKonsumenDAOCall.enqueue(new Callback<Model_MotorKonsumen>() {
//                @Override
//                public void onResponse(Call<Model_MotorKonsumen> call, Response<Model_MotorKonsumen> response) {
//                    if (response.code() == 201) {
//                        Toast.makeText(tambah_data_motor_konsumen.this, "Tambah Motor Konsumen Sukses!", Toast.LENGTH_SHORT).show();
//                        startIntent();
//                    } else {
//                        Toast.makeText(getApplicationContext(),response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                    Log.d("on respon : ",String.valueOf(response.code()));
//
//                }
//                @Override
//                public void onFailure(Call<Model_MotorKonsumen> call, Throwable t) {
//                    Toast.makeText(tambah_data_motor_konsumen.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
