package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_PegawaiOnDuty;

public class LD_PengawaiOnDuty {
    @SerializedName("data")
    @Expose
    private List<Model_PegawaiOnDuty> pegawais = new ArrayList<>();

    public List<Model_PegawaiOnDuty> getData() {
        return pegawais;
    }

    public void setData(List<Model_PegawaiOnDuty> data) {
        this.pegawais = data;
    }
}
