package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_KendaraanKeluar;

public class LD_KendaraanKeluar {
    @SerializedName("data")
    @Expose
    private List<Model_KendaraanKeluar> kendaraankeluars = new ArrayList<>();

    public List<Model_KendaraanKeluar> getData() {
        return kendaraankeluars;
    }

    public void setData(List<Model_KendaraanKeluar> data) {
        this.kendaraankeluars = data;
    }
}
