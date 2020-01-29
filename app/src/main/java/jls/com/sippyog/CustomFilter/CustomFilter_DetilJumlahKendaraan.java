package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_DetilJumlahKendaraan;
import jls.com.sippyog.Model.Model_KendaraanKeluar;

public class CustomFilter_DetilJumlahKendaraan extends Filter {
    Adapter_DetilJumlahKendaraan adapterDetilJumlahKendaraan;
    ArrayList<Model_KendaraanKeluar> filterList;

    public CustomFilter_DetilJumlahKendaraan(ArrayList<Model_KendaraanKeluar> filterList, Adapter_DetilJumlahKendaraan adapterDetilTiketHilangdanJumlahKendaraan)
    {
        this.adapterDetilJumlahKendaraan = adapterDetilTiketHilangdanJumlahKendaraan;
        this.filterList=filterList;
    }

    //FILTERING OCCURS
    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Model_KendaraanKeluar> filteredKndMsk =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNo_plat().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredKndMsk.add(filterList.get(i));
                }
            }

            results.count=filteredKndMsk.size();
            results.values=filteredKndMsk;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

        adapterDetilJumlahKendaraan.kendaraan= (ArrayList<Model_KendaraanKeluar>) results.values;

        //REFRESH
        adapterDetilJumlahKendaraan.notifyDataSetChanged();

    }
}

