package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_Pegawai;

public class LD_Pegawai {
    @SerializedName("data")
    @Expose
    private List<Model_Pegawai> pegawais = new ArrayList<>();

    public List<Model_Pegawai> getData() {
        return pegawais;
    }

    public void setData(List<Model_Pegawai> data) {
        this.pegawais = data;
    }
}
