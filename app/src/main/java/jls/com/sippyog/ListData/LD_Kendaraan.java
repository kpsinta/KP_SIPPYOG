package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_Kendaraan;


public class LD_Kendaraan {
    @SerializedName("data")
    @Expose
    private List<Model_Kendaraan> kendaraans = new ArrayList<>();

    public List<Model_Kendaraan> getData() {
        return kendaraans;
    }

    public void setData(List<Model_Kendaraan> data) {
        this.kendaraans = data;
    }
}
