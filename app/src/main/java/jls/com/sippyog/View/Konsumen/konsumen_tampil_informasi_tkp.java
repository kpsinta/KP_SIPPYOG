package jls.com.sippyog.View.Konsumen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import jls.com.sippyog.R;

public class konsumen_tampil_informasi_tkp extends AppCompatActivity {

    TextView waktu_operasional, alamat_tkp, nomor_telepon, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen_tampil_informasi_tkp);
        waktu_operasional = findViewById(R.id.waktu_operasional);
        alamat_tkp = findViewById(R.id.alamat_tkp);
        nomor_telepon = findViewById(R.id.nomor_telepon);
        deskripsi = findViewById(R.id.deskripsi);

        waktu_operasional.setText("08.00 - 22.00");
        alamat_tkp.setText("Jl. Beskalan No.28, RW.08, Ngupasan, Kec. Gondomanan, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55122 (sebelah selatan Ramai Mall)");
        nomor_telepon.setText("0274-xxxxxx");
        deskripsi.setText("Tempat Khusus Parkir (TKP) Beskalan, siap melayani anda dengan sepenuh hati :)");
    }
}
