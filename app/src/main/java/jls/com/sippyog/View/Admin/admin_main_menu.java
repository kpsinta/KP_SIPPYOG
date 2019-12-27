package jls.com.sippyog.View.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jls.com.sippyog.R;
import jls.com.sippyog.admin_pengelolaan_data;

public class admin_main_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);
    }

    public void pengelolaan_data(View view) {
        Intent i= new Intent(admin_main_menu.this, admin_pengelolaan_data.class);
        startActivity(i);
    }
}
