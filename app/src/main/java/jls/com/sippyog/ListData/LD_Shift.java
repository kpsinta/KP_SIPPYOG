package jls.com.sippyog.ListData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import jls.com.sippyog.Model.Model_Shift;

public class LD_Shift {
    @SerializedName("data")
    @Expose
    private List<Model_Shift> shifts = new ArrayList<>();

    public List<Model_Shift> getData() {
        return shifts;
    }

    public void setData(List<Model_Shift> data) {
        this.shifts = data;
    }
}
