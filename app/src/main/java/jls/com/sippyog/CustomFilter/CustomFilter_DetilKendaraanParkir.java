package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_DetilKendaraanParkir;
import jls.com.sippyog.Adapter.Adapter_KendaraanMasuk;
import jls.com.sippyog.Model.Model_KendaraanMasuk;

public class CustomFilter_DetilKendaraanParkir extends Filter {
    Adapter_DetilKendaraanParkir adapterDetilKendaraanParkir;
    ArrayList<Model_KendaraanMasuk> filterList;

    public CustomFilter_DetilKendaraanParkir(ArrayList<Model_KendaraanMasuk> filterList, Adapter_DetilKendaraanParkir adapterDetilKendaraanParkir)
    {
        this.adapterDetilKendaraanParkir = adapterDetilKendaraanParkir;
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

        adapterDetilKendaraanParkir.kendaraanparkir= (ArrayList<Model_KendaraanMasuk>) results.values;

        //REFRESH
        adapterDetilKendaraanParkir.notifyDataSetChanged();

    }
}