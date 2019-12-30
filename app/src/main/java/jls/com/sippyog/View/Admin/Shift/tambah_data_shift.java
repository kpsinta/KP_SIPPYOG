package jls.com.sippyog.View.Admin.Shift;

import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import jls.com.sippyog.R;

public class tambah_data_shift extends AppCompatActivity implements TimePickerFragment.TimePickerListener {

    TextInputEditText namaShift;
    TextView showJamMasuk, showJamKeluar;
    ImageView addJamMasuk, addJamKeluar;
    Button btnSimpan, btnBatal;

    Calendar currentTime;
    int jam, menit,id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_shift);

        namaShift = findViewById(R.id.text_input_namaShift);
        showJamMasuk = findViewById(R.id.textView_tampilJamMasuk);
        showJamKeluar = findViewById(R.id.textView_tampilJamKeluar);
        addJamMasuk = findViewById(R.id.addJamMasuk);
        addJamKeluar = findViewById(R.id.addJamKeluar);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        currentTime = Calendar.getInstance();
        jam = currentTime.get(Calendar.HOUR_OF_DAY);
        menit = currentTime.get(Calendar.MINUTE);
        if(jam<10) {
            showJamMasuk.setText("0"+jam + " : " + menit);
            showJamKeluar.setText("0"+jam + " : " + menit);
        }
        else if(menit<10)
        {
            showJamMasuk.setText(jam + " : 0" + menit);
            showJamKeluar.setText(jam + " : 0" + menit);
        }
        else if(jam<10 && menit <10)
        {
            showJamMasuk.setText("0"+jam + " : 0" + menit);
            showJamKeluar.setText("0"+jam + " : 0" + menit);
        }
        else
        {
            showJamMasuk.setText(jam + " : " + menit);
            showJamKeluar.setText(jam + " : " + menit);
        }

        addJamMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragmentMasuk=new TimePickerFragment();
                timePickerFragmentMasuk.setCancelable(false);
                timePickerFragmentMasuk.show(getSupportFragmentManager(),"timePickerMasuk");
                id=1;
            }
        });

        addJamKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragmentKeluar=new TimePickerFragment();
                timePickerFragmentKeluar.setCancelable(false);
                timePickerFragmentKeluar.show(getSupportFragmentManager(),"timePickerKeluar");
                id=2;
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int jam, int menit) {
    if(id==1)
    {
        if(jam<10) {
            showJamMasuk.setText("0"+jam + " : " + menit);
            if(menit<10)
            {
                showJamMasuk.setText("0"+jam + " : 0" + menit);
            }
        }
        else if(menit<10)
        {
            showJamMasuk.setText(jam + " : 0" + menit);
        }
        else
        {
            showJamMasuk.setText(jam + " : " + menit);
        }
    }
    else if(id==2)
    {
        if(jam<10) {
          showJamKeluar.setText("0"+jam + " : " + menit);
          if(menit<10)
          {
              showJamKeluar.setText("0"+jam + " : 0" + menit);
          }
        }
        else if(menit<10)
        {
            showJamKeluar.setText(jam + " : 0" + menit);
        }
        else
        {
            showJamKeluar.setText(jam + " : " + menit);
        }
    }


    }

}
