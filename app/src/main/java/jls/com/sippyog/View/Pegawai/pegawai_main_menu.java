package jls.com.sippyog.View.Pegawai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.SessionManager.SessionManager;
import jls.com.sippyog.View.Pegawai.KendaraanKeluar.tampil_data_kendaraan_keluar;
import jls.com.sippyog.View.Pegawai.KendaraanMasuk.tampil_data_kendaraan_masuk;

public class pegawai_main_menu extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_main_menu);
        session = new SessionManager(getApplicationContext());

    }
    public void kelola_kendaraan_masuk(View view) {
        Intent i= new Intent(pegawai_main_menu.this, tampil_data_kendaraan_masuk.class);
        startActivity(i);
    }

    public void kelola_kendaraan_keluar(View view) {
        Intent i= new Intent(pegawai_main_menu.this, tampil_data_kendaraan_keluar.class);
        startActivity(i);
    }
    public void logout(View view) {
        session.logoutUser();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

