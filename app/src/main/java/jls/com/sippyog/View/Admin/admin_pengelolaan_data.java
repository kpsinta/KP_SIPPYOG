package jls.com.sippyog.View.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.Kendaraan.tampil_data_kendaraan;
import jls.com.sippyog.View.Admin.Pegawai.tampil_data_pegawai;
import jls.com.sippyog.View.Admin.Shift.tampil_data_shift;

public class admin_pengelolaan_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pengelolaan_data);
    }

    public void data_shift(View view) {
        Intent i = new Intent(admin_pengelolaan_data.this, tampil_data_shift.class);
        startActivity(i);
    }

    public void data_kendaraan(View view) {
        Intent i = new Intent(admin_pengelolaan_data.this, tampil_data_kendaraan.class);
        startActivity(i);
    }

    public void data_pegawai(View view) {
        Intent i = new Intent(admin_pengelolaan_data.this, tampil_data_pegawai.class);
        startActivity(i);
    }
}
