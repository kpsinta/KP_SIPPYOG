package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_KendaraanKeluar;
import jls.com.sippyog.Model.Model_KendaraanKeluar;

public class CustomFilter_KendaraanKeluar extends Filter {
    Adapter_KendaraanKeluar adapterKendaraanKeluar;
    ArrayList<Model_KendaraanKeluar> filterList;

    public CustomFilter_KendaraanKeluar(ArrayList<Model_KendaraanKeluar> filterList, Adapter_KendaraanKeluar adapterKendaraanKeluar)
    {
        this.adapterKendaraanKeluar = adapterKendaraanKeluar;
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

        adapterKendaraanKeluar.kendaraankeluar= (ArrayList<Model_KendaraanKeluar>) results.values;

        //REFRESH
        adapterKendaraanKeluar.notifyDataSetChanged();

    }
}
