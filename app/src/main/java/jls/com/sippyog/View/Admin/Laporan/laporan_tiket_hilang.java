package jls.com.sippyog.View.Admin.Laporan;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;

public class laporan_tiket_hilang extends AppCompatActivity {
    TextView setTanggal;
    Intent i;
    TextInputEditText waktuLaporan;
    ImageView searchLaporan;
    String waktu_laporan;
    LinearLayout laporan_harian, laporan_bulanan, laporan_tahunan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_tiket_hilang);

        laporan_harian = findViewById(R.id.laporan_harian);
        laporan_bulanan = findViewById(R.id.laporan_bulanan);
        laporan_tahunan = findViewById(R.id.laporan_tahunan);
        i = getIntent();
        waktu_laporan = i.getStringExtra("WAKTU_LAPORAN");
        setTitle("Tiket Hilang " +waktu_laporan);

        if(waktu_laporan.equals("Harian"))
        {
            laporan_bulanan.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_harian);
            waktuLaporan = findViewById(R.id.text_input_tanggalLaporan);
            searchLaporan = findViewById(R.id.searchTanggal);
            //create a date string.
            String date_now = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
            searchLaporan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //searchKendaraan();
                }
            });
        }
        else if(waktu_laporan.equals("Bulanan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_tahunan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_bulanan);
            String date_now = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
        }
        else if(waktu_laporan.equals("Tahunan"))
        {
            laporan_harian.setVisibility(View.GONE);
            laporan_bulanan.setVisibility(View.GONE);
            setTanggal = findViewById(R.id.tanggal_laporan_tahunan);
            String date_now = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(new Date());
            //set it as current date.
            setTanggal.setText(date_now);
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_laporan, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(laporan_tiket_hilang.this, admin_main_menu.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_jumlah_kendaraan){
            Intent i = new Intent(laporan_tiket_hilang.this, laporan_jumlah_kendaraan.class);
            i.putExtra("WAKTU_LAPORAN",waktu_laporan);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_pendapatan)
        {
            Intent i = new Intent(laporan_tiket_hilang.this, laporan_pendapatan_tkp.class);
            i.putExtra("WAKTU_LAPORAN",waktu_laporan);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_tiket_hilang)
        {
            Toast.makeText(this, "Halaman Laporan Tiket Hilang", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}