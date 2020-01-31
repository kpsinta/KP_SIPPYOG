package jls.com.sippyog.View.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.Laporan.laporan_pendapatan_tkp;

public class admin_laporan_tkp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_laporan_tkp);
    }

    public void laporan_harian(View view) {
        Intent i = new Intent(this, laporan_pendapatan_tkp.class);
        String waktu_laporan = "Harian";
        i.putExtra("WAKTU_LAPORAN",waktu_laporan);
        startActivity(i);
    }

    public void laporan_bulanan(View view) {
        Intent i = new Intent(this, laporan_pendapatan_tkp.class);
        String waktu_laporan = "Bulanan";
        i.putExtra("WAKTU_LAPORAN",waktu_laporan);
        startActivity(i);
    }

    public void laporan_tahunan(View view) {
        Intent i = new Intent(this, laporan_pendapatan_tkp.class);
        String waktu_laporan = "Tahunan";
        i.putExtra("WAKTU_LAPORAN",waktu_laporan);
        startActivity(i);
    }
    public void onBackPressed()
    {
        Intent intent = new Intent(admin_laporan_tkp.this, admin_main_menu.class);
        startActivity(intent);
        finish();
    }
}
