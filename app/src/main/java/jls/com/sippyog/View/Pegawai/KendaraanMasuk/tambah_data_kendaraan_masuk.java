package jls.com.sippyog.View.Pegawai.KendaraanMasuk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jls.com.sippyog.API.ApiClient_Kendaraan;
import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.API.ApiClient_Shift;
import jls.com.sippyog.ListData.LD_Kendaraan;
import jls.com.sippyog.ListData.LD_Shift;
import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.Model.Model_PegawaiOnDuty;
import jls.com.sippyog.Model.Model_Shift;
import jls.com.sippyog.R;
import jls.com.sippyog.SessionManager.SessionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_data_kendaraan_masuk extends AppCompatActivity {

    Spinner spinner_jenis_kendaraan, spinner_nama_shift;
    TextView waktu_masuk;
    Integer selectedIDKendaraan, selectedIDShift;
    TextInputEditText nomor_plat;
    Button btnSimpan, btnBatal;
    String nomorPlat, waktuMasuk, jenisKendaraan;
    SessionManager sessionManager;
    //ini untuk load data
    List<Model_Kendaraan> spinnerJenisKendaraanArray = new ArrayList<>();
    List<Model_Shift> spinnerNamaShiftArray = new ArrayList<>();

    List<String> spinner_IDKendaraan = new ArrayList<>();
    List<String> string_jenisKendaraan = new ArrayList<>();

    List<String> spinner_IDShift = new ArrayList<>();
    List<String> string_namaShift = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kendaraan_masuk);

        sessionManager = new SessionManager(getApplicationContext());

        nomor_plat = findViewById(R.id.text_input_platKendaraan);
        spinner_jenis_kendaraan = findViewById(R.id.spinner_jenis_kendaraan);
        spinner_nama_shift = findViewById(R.id.spinner_nama_shift);

        loadNamaShift();
        loadJenisKendaraan();
        spinner_nama_shift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown nama shift saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIDShift = Integer.parseInt(spinner_IDShift.get(position)); //Mendapatkan id dari dropdown yang dipilih
                Log.d("Selected ID Shift : ",selectedIDShift.toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedIDShift=1;
                Log.d("Selected ID Shift : ",selectedIDShift.toString());
            }
        });
        spinner_jenis_kendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown jenis kendaraan saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIDKendaraan = Integer.parseInt(spinner_IDKendaraan.get(position)); //Mendapatkan id dari dropdown yang dipilih
                jenisKendaraan = string_jenisKendaraan.get(position);
                Log.d("Selected ID Ken : ",selectedIDKendaraan.toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedIDKendaraan=1;
                jenisKendaraan = string_jenisKendaraan.get(selectedIDKendaraan);
                Log.d("Selected ID Ken : ",selectedIDKendaraan.toString());
            }
        });
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

        //ngeload jenis kendaraan dari database
        ApiClient_Kendaraan apiclientKendaraan = retrofit.create(ApiClient_Kendaraan.class);
        Call<LD_Kendaraan> callKendaraan = apiclientKendaraan.show();
        callKendaraan.enqueue(new Callback<LD_Kendaraan>() {
            @Override
            public void onResponse(Call<LD_Kendaraan> callKendaraan, Response<LD_Kendaraan> response) {

                spinnerJenisKendaraanArray = response.body().getData();
                for (int i = 0; i < spinnerJenisKendaraanArray.size(); i++) {
                    string_jenisKendaraan.add(spinnerJenisKendaraanArray.get(i).getJenis_kendaraan());
                    spinner_IDKendaraan.add(spinnerJenisKendaraanArray.get(i).getId_kendaraan().toString());
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
    void loadNamaShift(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Shift.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        //ngeload nama shift dari database
        ApiClient_Shift apiclientShift = retrofit.create(ApiClient_Shift.class);
        Call<LD_Shift> callShift = apiclientShift.show();
        callShift.enqueue(new Callback<LD_Shift>() {
            @Override
            public void onResponse(Call<LD_Shift> callShift, Response<LD_Shift> response) {

                spinnerNamaShiftArray = response.body().getData();
                for (int i = 0; i < spinnerNamaShiftArray.size(); i++) {
                    string_namaShift.add(spinnerNamaShiftArray.get(i).getNama_shift());
                    spinner_IDShift.add(spinnerNamaShiftArray.get(i).getId_shift().toString());
                }
                ArrayAdapter<String> adapterNamaShift = new ArrayAdapter<>(tambah_data_kendaraan_masuk.this, R.layout.spinner_shift_layout,R.id.txtNamaShift, string_namaShift);
                spinner_nama_shift.setAdapter(adapterNamaShift);
            }
            @Override
            public void onFailure(Call<LD_Shift> call, Throwable t) {
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

        nomorPlat = nomor_plat.getText().toString();
        waktuMasuk = waktu_masuk.getText().toString();

        final DateFormat inputFormat =  new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
        final DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final String inputDateStr=waktuMasuk;
        Date date = null;
        try
        {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String outputDateStr = outputFormat.format(date);

        if (nomorPlat.isEmpty())
        {
            Toast.makeText(this, "Semua Field harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
            // Build an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(tambah_data_kendaraan_masuk.this);

            // Set a title for alert dialog
            builder.setTitle("Konfirmasi Kendaraan Masuk Plat " + nomorPlat);

            // Ask the final question
            builder.setMessage("Waktu Masuk :\t\t" +waktuMasuk+
                    "\nJenis Kendaraan  :\t\t" +jenisKendaraan+
                    "\n\nKonfirmasi Kendaraan Masuk?");
            // Set the alert dialog yes button click listener
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder = new Retrofit
                        .Builder()
                        .baseUrl(ApiClient_KendaraanMasuk.baseURL)
                        .addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit = builder.build();
                ApiClient_KendaraanMasuk apiClientKendaraanMasuk= retrofit.create(ApiClient_KendaraanMasuk.class);
                Call<ResponseBody> kendaraanMasukDAOCall = apiClientKendaraanMasuk.create(outputDateStr,nomorPlat,selectedIDKendaraan);
                kendaraanMasukDAOCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201) {
                            try {
                                JSONObject jsonresponse = new JSONObject(response.body().string());
                                String idTiket = jsonresponse.getJSONObject("data").getString("id_tiket");
                                Log.d( "ID Tiket: ",idTiket);

                                    Gson gson = new GsonBuilder()
                                            .setLenient()
                                            .create();
                                    Retrofit.Builder builder=new Retrofit.
                                            Builder().baseUrl(ApiClient_KendaraanMasuk.baseURL).
                                            addConverterFactory(GsonConverterFactory.create(gson));
                                    Retrofit retrofit=builder.build();
                                    ApiClient_KendaraanMasuk apiClientPODKendaraanMasuk = retrofit.create(ApiClient_KendaraanMasuk.class);
                                    Log.d("ID Pegawai : ",sessionManager.getKeyId());
                                    Call<ResponseBody> tambah_pod_kendaraanmasukDAOCall = apiClientPODKendaraanMasuk.create_pod_kendaraan_masuk(
                                            Integer.parseInt(idTiket),selectedIDShift,Integer.parseInt(sessionManager.getKeyId()));

                                tambah_pod_kendaraanmasukDAOCall.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 201) {
                                                Toast.makeText(tambah_data_kendaraan_masuk.this, "Tambah Kendaraan Masuk Sukses!", Toast.LENGTH_SHORT).show();
                                                startIntent();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),response.message(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),response.message(), Toast.LENGTH_SHORT).show();
                        }
                        Log.d("on respon : ",String.valueOf(response.code()));

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(tambah_data_kendaraan_masuk .this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
        }
    }
}
