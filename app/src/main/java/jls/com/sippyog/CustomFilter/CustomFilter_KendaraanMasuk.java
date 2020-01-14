package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_KendaraanMasuk;
import jls.com.sippyog.Model.Model_KendaraanMasuk;

public class CustomFilter_KendaraanMasuk extends Filter {
    Adapter_KendaraanMasuk adapterKendaraanMasuk;
    ArrayList<Model_KendaraanMasuk> filterList;

    public CustomFilter_KendaraanMasuk(ArrayList<Model_KendaraanMasuk> filterList, Adapter_KendaraanMasuk adapterKendaraanMasuk)
    {
        this.adapterKendaraanMasuk = adapterKendaraanMasuk;
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

        adapterKendaraanMasuk.kendaraanmasuk= (ArrayList<Model_KendaraanMasuk>) results.values;

        //REFRESH
        adapterKendaraanMasuk.notifyDataSetChanged();

    }
}
