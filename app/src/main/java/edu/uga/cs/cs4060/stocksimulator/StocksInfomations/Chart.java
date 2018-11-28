package edu.uga.cs.cs4060.stocksimulator.StocksInfomations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chart {

    @SerializedName("minutes")
    @Expose
    public List<Minute> minutes;
}
