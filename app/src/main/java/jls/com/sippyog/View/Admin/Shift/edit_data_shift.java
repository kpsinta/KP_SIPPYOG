package jls.com.sippyog.View.Admin.Shift;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import jls.com.sippyog.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_data_shift extends AppCompatActivity implements TimePickerFragment.TimePickerListener {
    TextInputLayout textInputNama;
    TextInputEditText nama_shift;
    TextView showJamMasuk, showJamKeluar;
    ImageView addJamMasuk, addJamKeluar;
    Button btnEdit, btnBatal, btnDelete;
    String namaShift, jamMasuk, jamKeluar;
    int id=0;
    Integer id_shift;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_shift);

        textInputNama = findViewById(R.id.text_layout_namaShift);
        nama_shift = findViewById(R.id.text_input_namaShift);
        showJamMasuk = findViewById(R.id.textView_tampilJamMasuk);
        showJamKeluar = findViewById(R.id.textView_tampilJamKeluar);
        addJamMasuk = findViewById(R.id.addJamMasuk);
        addJamKeluar = findViewById(R.id.addJamKeluar);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateShift();
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
                startIntent();

            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_data_shift.this);

                // Set a title for alert dialog
                builder.setTitle("Hapus Shift " +nama_shift.getText().toString());

                // Ask the final question
                builder.setMessage("Apakah anda yakin untuk hapus shift?");
                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteShift();
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

        i = getIntent();
        nama_shift.setText(i.getStringExtra("nama_shift"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        try {
            Date date = dateFormat.parse(i.getStringExtra("jam_masuk"));
            Date date2 = dateFormat.parse(i.getStringExtra("jam_keluar"));
            jamMasuk = dateFormat2.format(date);
            jamKeluar = dateFormat2.format(date2);
            Log.d("Jam Masuk", jamMasuk);
            Log.d("Jam Keluar", jamKeluar);
        } catch (ParseException e) {
        }
        showJamMasuk.setText(jamMasuk);
        showJamKeluar.setText(jamKeluar);
        id_shift = i.getIntExtra("id_shift", -1);
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
    public void startIntent()
    {
        Intent intent= new Intent(getApplicationContext(), tampil_data_shift.class);
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
    private void UpdateShift()
    {
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
        else
        {
            Retrofit.Builder builder=new Retrofit
                    .Builder()
                    .baseUrl(ApiClient_Shift.baseURL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit=builder.build();

            ApiClient_Shift apiClientShift =retrofit.create(ApiClient_Shift.class);
            Call<ResponseBody> shiftDAOCall= apiClientShift.update(namaShift,jamMasuk,jamKeluar,id_shift);
            shiftDAOCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {
                        Toast.makeText(getApplicationContext(), "Berhasil Update Shift!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal Update Shift!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
    private void DeleteShift() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_Shift.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ApiClient_Shift apiClientShift =retrofit.create(ApiClient_Shift.class);

        Call<ResponseBody> shiftDAOCall = apiClientShift.delete(id_shift);
        shiftDAOCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Berhasil Hapus Shift!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal Hapus Shift!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
