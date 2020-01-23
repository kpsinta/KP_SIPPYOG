package jls.com.sippyog.View.Admin.Laporan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jls.com.sippyog.R;
import jls.com.sippyog.View.Admin.admin_main_menu;

public class tampil_laporan_bulanan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_laporan_bulanan);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_laporan, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(tampil_laporan_bulanan.this, admin_main_menu.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
