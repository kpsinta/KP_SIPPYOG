package jls.com.sippyog.View.Konsumen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;

public class konsumen_main_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen_main_menu);
    }
    public void tentang_tkp(View view) {
        Intent i = new Intent(konsumen_main_menu.this, konsumen_tampil_informasi_tkp.class);
        startActivity(i);
    }

    public void tampil_kapasitas_parkir(View view) {
        Intent i = new Intent(konsumen_main_menu.this, konsumen_tampil_kapasitas_parkir.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

