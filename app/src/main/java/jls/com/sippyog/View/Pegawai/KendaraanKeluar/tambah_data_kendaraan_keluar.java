package jls.com.sippyog.View.Pegawai.KendaraanKeluar;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.API.ApiClient_Shift;
import jls.com.sippyog.ListData.LD_Shift;
import jls.com.sippyog.Model.Model_Shift;
import jls.com.sippyog.R;
import jls.com.sippyog.SessionManager.SessionManager;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tambah_data_kendaraan_masuk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_data_kendaraan_keluar extends AppCompatActivity {
    RadioGroup rgStatusTiket;
    RadioButton rbStatusTiket;

    Spinner spinner_nama_shift;
    TextInputEditText nomor_plat,uang_pembayaran;
    Integer selectedIDRG,selectedIDShift;
    Button btnSimpan, btnBatal, btnSearch;
    TextView waktu_keluar, jenis_kendaran, durasi_parkir, biaya_parkir;
    SessionManager sessionManager;
    List<Model_Shift> spinnerNamaShiftArray = new ArrayList<>();
    List<String> spinner_IDShift = new ArrayList<>();
    List<String> string_namaShift = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kendaraan_keluar);

        sessionManager = new SessionManager(getApplicationContext());
        spinner_nama_shift = findViewById(R.id.spinner_nama_shift);
        rgStatusTiket = findViewById(R.id.rgStatusTiket);
        loadNamaShift();
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
        Thread t = new Thread(){
            @Override
            public void run(){
                try{
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                waktu_keluar = findViewById(R.id.textView_waktuKeluar);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
                                String dateString = sdf.format(date);
                                waktu_keluar.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), tampil_data_kendaraan_keluar.class);
                startActivity(intent);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedIDRG = rgStatusTiket.getCheckedRadioButtonId();
                rbStatusTiket = findViewById(selectedIDRG);
                Toast.makeText(tambah_data_kendaraan_keluar.this,"Status Tiket : "
                        +rbStatusTiket.getText(), Toast.LENGTH_SHORT).show();
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
                ArrayAdapter<String> adapterNamaShift = new ArrayAdapter<>(tambah_data_kendaraan_keluar.this, R.layout.spinner_shift_layout,R.id.txtNamaShift, string_namaShift);
                spinner_nama_shift.setAdapter(adapterNamaShift);
            }
            @Override
            public void onFailure(Call<LD_Shift> call, Throwable t) {
                Toast.makeText(tambah_data_kendaraan_keluar.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure: ",t.getLocalizedMessage());
            }
        });
    }
}
