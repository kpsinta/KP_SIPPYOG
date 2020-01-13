package jls.com.sippyog.View.Pegawai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tampil_data_kendaraan_masuk;

public class pegawai_main_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_main_menu);
    }
    public void kelola_kendaraan_masuk(View view) {
        Intent i= new Intent(pegawai_main_menu.this, tampil_data_kendaraan_masuk.class);
        startActivity(i);
    }
}

