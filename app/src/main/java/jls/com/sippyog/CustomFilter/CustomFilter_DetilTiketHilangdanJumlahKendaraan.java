package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_DetilTiketHilangdanJumlahKendaraan;
import jls.com.sippyog.Model.Model_KendaraanMasuk;

public class CustomFilter_DetilTiketHilangdanJumlahKendaraan extends Filter {
    Adapter_DetilTiketHilangdanJumlahKendaraan adapterDetilTiketHilangdanJumlahKendaraan;
    ArrayList<Model_KendaraanMasuk> filterList;

    public CustomFilter_DetilTiketHilangdanJumlahKendaraan(ArrayList<Model_KendaraanMasuk> filterList, Adapter_DetilTiketHilangdanJumlahKendaraan adapterDetilTiketHilangdanJumlahKendaraan)
    {
        this.adapterDetilTiketHilangdanJumlahKendaraan = adapterDetilTiketHilangdanJumlahKendaraan;
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
            ArrayList<Model_KendaraanMasuk> filteredKndMsk =new ArrayList<>();

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

        adapterDetilTiketHilangdanJumlahKendaraan.kendaraan= (ArrayList<Model_KendaraanMasuk>) results.values;

        //REFRESH
        adapterDetilTiketHilangdanJumlahKendaraan.notifyDataSetChanged();

    }
}

