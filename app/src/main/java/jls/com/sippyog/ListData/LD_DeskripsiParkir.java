package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_DeskripsiParkir;

public class LD_DeskripsiParkir {
    @SerializedName("data")
    @Expose
    private List<Model_DeskripsiParkir> deskripsis = new ArrayList<>();

    public List<Model_DeskripsiParkir> getData() {
        return deskripsis;
    }

    public void setData(List<Model_DeskripsiParkir> data) {
        this.deskripsis = data;
    }
}
