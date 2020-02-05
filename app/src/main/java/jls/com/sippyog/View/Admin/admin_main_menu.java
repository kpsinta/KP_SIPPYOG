package jls.com.sippyog.View.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.SessionManager.SessionManager;
import jls.com.sippyog.View.Admin.KapasitasParkir.admin_tampil_kapasitas_parkir;
import jls.com.sippyog.tentang_aplikasi;

public class admin_main_menu extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);
        session = new SessionManager(getApplicationContext());
    }

    public void pengelolaan_data(View view) {
        Intent i = new Intent(admin_main_menu.this, admin_pengelolaan_data.class);
        startActivity(i);
    }

    public void tampil_kapasitas_parkir(View view) {
        Intent i = new Intent(admin_main_menu.this, admin_tampil_kapasitas_parkir.class);
        startActivity(i);
    }
    public void laporan_tkp(View view) {
        Intent i = new Intent(admin_main_menu.this, admin_laporan_tkp.class);
        startActivity(i);
    }
    public void pengaturan_halaman_tkp(View view) {
//        Intent i = new Intent(admin_main_menu.this, tentang_aplikasi.class);
//        startActivity(i);
    }
    public void tentang_aplikasi(View view) {
        Intent i = new Intent(admin_main_menu.this, tentang_aplikasi.class);
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