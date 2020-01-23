package jls.com.sippyog.View.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.Laporan.tampil_laporan_bulanan;
import jls.com.sippyog.View.Admin.Laporan.tampil_laporan_harian;
import jls.com.sippyog.View.Admin.Laporan.tampil_laporan_tahunan;

public class admin_laporan_tkp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_laporan_tkp);
    }
    public void laporan_harian(View view) {
        Intent i = new Intent(admin_laporan_tkp.this, tampil_laporan_harian.class);
        startActivity(i);
    }

    public void laporan_bulanan(View view) {
        Intent i = new Intent(admin_laporan_tkp.this, tampil_laporan_bulanan.class);
        startActivity(i);
    }

    public void laporan_tahunan(View view) {
        Intent i = new Intent(admin_laporan_tkp.this, tampil_laporan_tahunan.class);
        startActivity(i);
    }
}
