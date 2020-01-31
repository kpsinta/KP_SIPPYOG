package jls.com.sippyog.View.Admin.Shift;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jls.com.sippyog.API.ApiClient_Shift;
import jls.com.sippyog.Model.Model_Shift;
import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.Pegawai.tambah_data_pegawai;
import jls.com.sippyog.View.Admin.Pegawai.tampil_data_pegawai;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_data_shift extends AppCompatActivity implements TimePickerFragment.TimePickerListener {
    TextInputLayout textInputNama;
    TextInputEditText nama_shift;
    TextView showJamMasuk, showJamKeluar;
    ImageView addJamMasuk, addJamKeluar;
    Button btnSimpan, btnBatal;
    String namaShift,jamKeluar, jamMasuk;
    Calendar currentTime;
    int jam, menit,id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_shift);

        textInputNama = findViewById(R.id.text_layout_namaShift);
        nama_shift = findViewById(R.id.text_input_namaShift);
        showJamMasuk = findViewById(R.id.textView_tampilJamMasuk);
        showJamKeluar = findViewById(R.id.textView_tampilJamKeluar);
        addJamMasuk = findViewById(R.id.addJamMasuk);
        addJamKeluar = findViewById(R.id.addJamKeluar);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startIntent();
                    }
                }, 700);
                return;
            }
        });
        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama_shift.getText().clear();
            }
        });

        currentTime = Calendar.getInstance();
        jam = currentTime.get(Calendar.HOUR_OF_DAY);
        menit = currentTime.get(Calendar.MINUTE);
        showJamMasuk.setText(String.format("%02d:%02d", jam, menit));
        showJamKeluar.setText(String.format("%02d:%02d", jam, menit));

        addJamMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragmentMasuk=new TimePickerFragment();
                timePickerFragmentMasuk.setCancelable(false);
                timePickerFragmentMasuk.show(getSupportFragmentManager(),"timePickerMasuk");
                id=1;
            }
        });

        addJamKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragmentKeluar=new TimePickerFragment();
                timePickerFragmentKeluar.setCancelable(false);
                timePickerFragmentKeluar.show(getSupportFragmentManager(),"timePickerKeluar");
                id=2;
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int jam, int menit) {
        if(id==1)
        {
            showJamMasuk.setText(String.format("%02d:%02d", jam, menit));
        }
        else if(id==2)
        {
            showJamKeluar.setText(String.format("%02d:%02d", jam, menit));
        }
    }
    private void startIntent() {
        Intent intent = new Intent(getApplicationContext(), tampil_data_shift.class);
        startActivity(intent);
    }
    private boolean validateNama() {
        String namaShift = nama_shift.getText().toString();
        if (namaShift.isEmpty()) {
            textInputNama.setError("Harus diisi!");

            return false;
        } else if (namaShift.length() > 50) {
            textInputNama.setError("Maksimal 50 karakter!");
            return false;
        } else {
            textInputNama.setError(null);
            return true;
        }
    }
    private void onClickRegister() {
        namaShift = nama_shift.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = dateFormat.parse(showJamMasuk.getText().toString());
            Date date2 = dateFormat.parse(showJamKeluar.getText().toString());
            jamMasuk = dateFormat2.format(date);
            jamKeluar = dateFormat2.format(date2);
            Log.d("Jam Masuk", jamMasuk);
            Log.d("Jam Keluar", jamKeluar);
        } catch (ParseException e) {
        }

        if (!validateNama()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textInputNama.setError(null);
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
                    .baseUrl(ApiClient_Shift.baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            ApiClient_Shift apiClientShift = retrofit.create(ApiClient_Shift.class);
            Call<Model_Shift> shiftDAOCall = apiClientShift.create(namaShift,jamMasuk,jamKeluar);
            shiftDAOCall.enqueue(new Callback<Model_Shift>() {
                @Override
                public void onResponse(Call<Model_Shift> call, Response<Model_Shift> response) {
                    Toast.makeText(tambah_data_shift.this, "Berhasil Tambah Shift!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Model_Shift> call, Throwable t) {
                    Toast.makeText(tambah_data_shift.this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
