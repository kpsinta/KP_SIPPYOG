package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_DetilPendapatanTKP;
import jls.com.sippyog.Adapter.Adapter_DetilPendapatanTKP;
import jls.com.sippyog.Model.Model_KendaraanKeluar;

public class CustomFilter_DetilPendapatanTKP extends Filter {
    Adapter_DetilPendapatanTKP adapterDetilPendapatanTKP;
    ArrayList<Model_KendaraanKeluar> filterList;

    public CustomFilter_DetilPendapatanTKP(ArrayList<Model_KendaraanKeluar> filterList, Adapter_DetilPendapatanTKP adapterDetilPendapatanTKP)
    {
        this.adapterDetilPendapatanTKP = adapterDetilPendapatanTKP;
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

        adapterDetilPendapatanTKP.detilpendapatantkp= (ArrayList<Model_KendaraanKeluar>) results.values;

        //REFRESH
        adapterDetilPendapatanTKP.notifyDataSetChanged();

    }
}
