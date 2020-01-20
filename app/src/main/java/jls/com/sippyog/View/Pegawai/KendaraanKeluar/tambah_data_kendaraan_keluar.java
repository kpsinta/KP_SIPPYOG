package jls.com.sippyog.View.Pegawai.KendaraanKeluar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import jls.com.sippyog.R;

public class tambah_data_kendaraan_keluar extends AppCompatActivity {
    RadioGroup rgStatusTiket;
    RadioButton rbStatusTiket;
    int selectedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_kendaraan_keluar);

        rgStatusTiket = findViewById(R.id.rgStatusTiket);
        selectedID = rgStatusTiket.getCheckedRadioButtonId();
        rbStatusTiket = findViewById(selectedID);
        Toast.makeText(getBaseContext(),
                "Status : " + rbStatusTiket.getText(),
                Toast.LENGTH_SHORT).show();
    }
}
