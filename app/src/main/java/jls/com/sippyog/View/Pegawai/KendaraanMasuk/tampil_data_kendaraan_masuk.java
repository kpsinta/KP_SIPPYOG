package jls.com.sippyog.View.Pegawai.KendaraanMasuk;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.Pegawai.tambah_data_pegawai;
import jls.com.sippyog.View.Admin.Pegawai.tampil_data_pegawai;

public class tampil_data_kendaraan_masuk extends AppCompatActivity {

    FloatingActionButton btn_tambahKendaraanMasuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_kendaraan_masuk);

        btn_tambahKendaraanMasuk = (FloatingActionButton) findViewById(R.id.btn_tambahDataKendaraanMasuk);
        btn_tambahKendaraanMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tampil_data_kendaraan_masuk.this, tambah_data_kendaraan_masuk.class);
                startActivity(i);
            }
        });
    }
}
