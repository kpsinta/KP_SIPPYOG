package jls.com.sippyog.View.Pegawai.KendaraanKeluar;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import jls.com.sippyog.API.ApiClient_KendaraanKeluar;
import jls.com.sippyog.API.ApiClient_KendaraanMasuk;
import jls.com.sippyog.API.ApiClient_Shift;
import jls.com.sippyog.Adapter.Adapter_KendaraanMasuk;
import jls.com.sippyog.ListData.LD_KendaraanMasuk;
import jls.com.sippyog.ListData.LD_Shift;
import jls.com.sippyog.Model.Model_KendaraanMasuk;
import jls.com.sippyog.Model.Model_Shift;
import jls.com.sippyog.R;
import jls.com.sippyog.SessionManager.SessionManager;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tambah_data_kendaraan_masuk;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tampil_data_kendaraan_masuk;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class tambah_data_kendaraan_keluar extends AppCompatActivity {
    RadioGroup rgStatusTiket;
    RadioButton rbStatusTiket;

    Spinner spinner_nama_shift;
    TextInputEditText nomor_plat, uang_pembayaran;
    Integer selectedIDRG, selectedIDShift, selectedIDTiket;
    Button btnSimpan, btnBatal;
    ImageView ivSearch;
    TextView waktu_keluar, jenis_kendaraan, durasi_parkir, biaya_parkir, kode_tiket;
    SessionManager sessionManager;
    List<Model_Shift> spinnerNamaShiftArray = new ArrayList<>();
    List<String> spinner_IDShift = new ArrayList<>();
    List<String> string_namaShift = new ArrayList<>();
    List<String> spinner_IDKendaraan = new ArrayList<>();
    List<String> string_noPlat = new ArrayList<>();
    private List<Model_KendaraanMasuk> mListKendaraanMasuk = new ArrayList<>();
    String nomorPlat, waktuMasuk, waktuKeluar;
    Double uangPembayaran, biayaDenda=0.0, biayaParkir=0.0, biayaParkirDenda=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kendaraan_keluar);

        sessionManager = new SessionManager(getApplicationContext());
        spinner_nama_shift = findViewById(R.id.spinner_nama_shift);
        rgStatusTiket = findViewById(R.id.rgStatusTiket);
        selectedIDRG = rgStatusTiket.getCheckedRadioButtonId();
        rbStatusTiket = findViewById(selectedIDRG);
        nomor_plat = findViewById(R.id.text_input_platKendaraan);
        jenis_kendaraan = findViewById(R.id.jenis_kendaraan);
        durasi_parkir = findViewById(R.id.durasi_parkir);
        biaya_parkir = findViewById(R.id.biaya_parkir);
        kode_tiket = findViewById(R.id.kode_tiket);
        uang_pembayaran = findViewById(R.id.text_input_uangPembayaran);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
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
        loadKendaraanMasuk();
        loadNamaShift();
        spinner_nama_shift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //Listener dropdown nama shift saat dipilih
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIDShift = Integer.parseInt(spinner_IDShift.get(position)); //Mendapatkan id dari dropdown yang dipilih
                Log.d("Selected ID Shift : ", selectedIDShift.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedIDShift = 1;
                Log.d("Selected ID Shift : ", selectedIDShift.toString());

            }
        });
        ivSearch = findViewById(R.id.searchKendaraan);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKendaraan();
            }
        });


        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), tampil_data_kendaraan_keluar.class);
                startActivity(intent);
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
    void loadNamaShift() {
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
                ArrayAdapter<String> adapterNamaShift = new ArrayAdapter<>(tambah_data_kendaraan_keluar.this, R.layout.spinner_shift_layout, R.id.txtNamaShift, string_namaShift);
                spinner_nama_shift.setAdapter(adapterNamaShift);
            }

            @Override
            public void onFailure(Call<LD_Shift> call, Throwable t) {
                Toast.makeText(tambah_data_kendaraan_keluar.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure: ", t.getLocalizedMessage());
            }
        });
    }

    public void loadKendaraanMasuk() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(ApiClient_KendaraanMasuk.baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiClient_KendaraanMasuk apiclientKendaraanMasuk = retrofit.create(ApiClient_KendaraanMasuk.class);

        // status parkir 0 = sedang parkir

        Call<LD_KendaraanMasuk> kendaraanMasukModelCall = apiclientKendaraanMasuk.showByStatusParkir(0);

        kendaraanMasukModelCall.enqueue(new Callback<LD_KendaraanMasuk>() {
            @Override
            public void onResponse(Call<LD_KendaraanMasuk> call, Response<LD_KendaraanMasuk> response) {
                mListKendaraanMasuk = response.body().getData();
                for (int i = 0; i < mListKendaraanMasuk.size(); i++) {
                    string_noPlat.add(mListKendaraanMasuk.get(i).getNo_plat());
                    spinner_IDKendaraan.add(mListKendaraanMasuk.get(i).getId_tiket().toString());
                }
                Log.i(tampil_data_kendaraan_masuk.class.getSimpleName(), response.body().toString());

                Toast.makeText(tambah_data_kendaraan_keluar.this, "Load Kendaraan Sukses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LD_KendaraanMasuk> call, Throwable t) {
                Toast.makeText(tambah_data_kendaraan_keluar.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchKendaraan() {
        String plat;
        int find=0;
        nomorPlat = nomor_plat.getText().toString();
        for (int i = 0; i < mListKendaraanMasuk.size(); i++) {
            plat = mListKendaraanMasuk.get(i).getNo_plat();
            if (plat.equals(nomorPlat)) {
                find=1;
                Toast.makeText(tambah_data_kendaraan_keluar.this, "No Plat Ditemukan", Toast.LENGTH_SHORT).show();
                jenis_kendaraan.setText(mListKendaraanMasuk.get(i).getJenis_kendaraan());
                kode_tiket.setText(mListKendaraanMasuk.get(i).getKode_tiket());
                biayaDenda=mListKendaraanMasuk.get(i).getBiaya_denda();
                biayaParkir=mListKendaraanMasuk.get(i).getBiaya_parkir();
                biaya_parkir.setText(String.format("%.0f",mListKendaraanMasuk.get(i).getBiaya_parkir()));
                waktuKeluar = waktu_keluar.getText().toString();
                waktuMasuk = mListKendaraanMasuk.get(i).getWaktu_masuk();
                selectedIDTiket = mListKendaraanMasuk.get(i).getId_tiket();
                rgStatusTiket.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if (checkedId == R.id.rbStatusAda) {
                            //do work when radioButton1 is active
                            if(!durasi_parkir.getText().toString().equals("-") || !durasi_parkir.getText().equals("Durasi Parkir"))
                            {
                                biaya_parkir.setText(String.format("%.0f",biayaParkir));
                            }
                            Toast.makeText(tambah_data_kendaraan_keluar.this, "Ada kakaa", Toast.LENGTH_SHORT).show();
                        } else  if (checkedId == R.id.rbStatusHilang) {
                            //do work when radioButton2 is active
                            if(!durasi_parkir.getText().toString().equals("-") || !durasi_parkir.getText().equals("Durasi Parkir"))
                            {
                                biayaParkirDenda=0.0;
                                biayaParkirDenda = Double.parseDouble(biaya_parkir.getText().toString())+biayaDenda;
                                biaya_parkir.setText(String.format("%.0f",biayaParkirDenda));
                            }
                            Toast.makeText(tambah_data_kendaraan_keluar.this, "Gaada kakaa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final DateFormat inputFormat2 = new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
                final DateFormat outputFormat =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = null;
                Date date2 = null;
                try
                {
                    date = inputFormat.parse(waktuMasuk);
                    date2 = inputFormat2.parse(waktuKeluar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                final String outputDateMasuk = outputFormat.format(date);
                final String outputDateKeluar = outputFormat.format(date2);

                Log.i("Tanggal Keluar : ",outputDateKeluar);
                Log.i("Tanggal Masuk : ",outputDateMasuk);

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                Date d1 = null;
                Date d2 = null;

                try {
                    d1 = format.parse(outputDateMasuk);
                    d2 = format.parse(outputDateKeluar);

                    //in milliseconds
                    long diff = d2.getTime() - d1.getTime();

                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    durasi_parkir.setText(diffDays+" Hari "+diffHours+" Jam \n"+diffMinutes+" Menit "+diffSeconds+" Detik");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if(find==0) {
            Toast.makeText(tambah_data_kendaraan_keluar.this, "No Plat Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            jenis_kendaraan.setText("-");
            kode_tiket.setText("-");
            biaya_parkir.setText("-");
            durasi_parkir.setText("-");
        }
    }
    public void onClickRegister() {

        nomorPlat = nomor_plat.getText().toString();

        if (nomorPlat.isEmpty() || uang_pembayaran.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Semua Field harus diisi!", Toast.LENGTH_SHORT).show();
        }
        else if(biaya_parkir.getText().toString().equals("Biaya Parkir") ||biaya_parkir.getText().toString().equals("-"))
        {
            Toast.makeText(this, "Kendaraan tidak ditemukan!", Toast.LENGTH_SHORT).show();
        }
        else if(Double.parseDouble(uang_pembayaran.getText().toString())<Double.parseDouble(biaya_parkir.getText().toString()))
        {
            Toast.makeText(this, "Uang Pembayaran Kurang!", Toast.LENGTH_SHORT).show();
        }
        else {
            final DateFormat inputFormat =  new SimpleDateFormat("EEE, d MMM yyyy\t\tHH:mm:ss");
            final DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            final String inputDateStr=waktuKeluar;
            Date date = null;
            try
            {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final String outputDateStr = outputFormat.format(date);
            // Build an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(tambah_data_kendaraan_keluar.this);

            // Set a title for alert dialog
            builder.setTitle("Konfirmasi Kendaraan Keluar Plat " + nomorPlat);

            // Ask the final question
            double kembalian = Double.parseDouble(uang_pembayaran.getText().toString()) - Double.parseDouble(biaya_parkir.getText().toString());
            builder.setMessage("Uang Pembayaran :\t\t" +uang_pembayaran.getText().toString()+
                               "\nBiaya Parkir  :\t\t" +biaya_parkir.getText().toString()+
                               "\nKembalian     :\t\t" +String.format("%.0f",kembalian)+
                                "\nKonfirmasi Pembayaran?");

            // Set the alert dialog yes button click listener
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do something when user clicked the Yes button
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit.Builder builder = new Retrofit
                            .Builder()
                            .baseUrl(ApiClient_KendaraanKeluar.baseURL)
                            .addConverterFactory(GsonConverterFactory.create(gson));
                    Retrofit retrofit = builder.build();
                    ApiClient_KendaraanKeluar apiClientKendaraanKeluar= retrofit.create(ApiClient_KendaraanKeluar.class);

                    Log.d("Status Tiket : ",rbStatusTiket.getText().toString());
                    Log.d("Selected ID Tiket : ",selectedIDTiket.toString());
                    Log.d("Biaya Parkir : ",biaya_parkir.getText().toString());
                    Log.d("Waktu Transaksi : ",outputDateStr);

                    Double biayaParkir = Double.parseDouble(biaya_parkir.getText().toString());

                    Call<ResponseBody> kendaraanKeluarDAOCall = apiClientKendaraanKeluar.create(selectedIDTiket,outputDateStr,biayaParkir,rbStatusTiket.getText().toString());
                    kendaraanKeluarDAOCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 201) {
                                try {
                                    JSONObject jsonresponse = new JSONObject(response.body().string());
                                    String idTransaksi = jsonresponse.getJSONObject("data").getString("id_transaksi");
                                    Log.d( "ID Transaksi: ",idTransaksi);

                                    Gson gson = new GsonBuilder()
                                            .setLenient()
                                            .create();
                                    Retrofit.Builder builder=new Retrofit.
                                            Builder().baseUrl(ApiClient_KendaraanKeluar.baseURL).
                                            addConverterFactory(GsonConverterFactory.create(gson));
                                    Retrofit retrofit=builder.build();
                                    ApiClient_KendaraanKeluar apiClientPODKendaraanKeluar = retrofit.create(ApiClient_KendaraanKeluar.class);
                                    Log.d("ID Pegawai : ",sessionManager.getKeyId());
                                    Call<ResponseBody> tambah_pod_kendaraankeluarDAOCall = apiClientPODKendaraanKeluar.create_pod_kendaraan_keluar(
                                            selectedIDTiket,selectedIDShift,Integer.parseInt(sessionManager.getKeyId()),Integer.parseInt(idTransaksi));

                                    tambah_pod_kendaraankeluarDAOCall.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.code() == 201) {
                                                Toast.makeText(tambah_data_kendaraan_keluar.this, "Tambah Kendaraan Keluar Sukses!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(tambah_data_kendaraan_keluar .this,  t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
    private void startIntent() {
        Intent intent = new Intent(getApplicationContext(), tampil_data_kendaraan_keluar.class);
        startActivity(intent);
    }
}
