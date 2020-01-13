package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_Kendaraan;
import jls.com.sippyog.Model.Model_KendaraanMasuk;

public class LD_KendaraanMasuk {
    @SerializedName("data")
    @Expose
    private List<Model_KendaraanMasuk> kendaraanmasuks = new ArrayList<>();

    public List<Model_KendaraanMasuk> getData() {
        return kendaraanmasuks;
    }

    public void setData(List<Model_KendaraanMasuk> data) {
        this.kendaraanmasuks = data;
    }
}
