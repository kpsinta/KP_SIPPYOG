package jls.com.sippyog.CustomFilter;

import android.widget.Filter;

import java.util.ArrayList;

import jls.com.sippyog.Adapter.Adapter_Pegawai;
import jls.com.sippyog.Model.Model_Pegawai;

public class CustomFilter_Pegawai extends Filter {
    Adapter_Pegawai adapterPegawai;
    ArrayList<Model_Pegawai> filterList;

    public CustomFilter_Pegawai(ArrayList<Model_Pegawai> filterList, Adapter_Pegawai adapterPegawai)
    {
        this.adapterPegawai = adapterPegawai;
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
            ArrayList<Model_Pegawai> filteredPgw =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_pegawai().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPgw.add(filterList.get(i));
                }
            }

            results.count=filteredPgw.size();
            results.values=filteredPgw;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

        adapterPegawai.pegawai= (ArrayList<Model_Pegawai>) results.values;

        //REFRESH
        adapterPegawai.notifyDataSetChanged();

    }
}

