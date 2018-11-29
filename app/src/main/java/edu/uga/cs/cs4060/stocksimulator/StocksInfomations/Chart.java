package edu.uga.cs.cs4060.stocksimulator.StocksInfomations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chart {

    @SerializedName("oneDayCharts")
    @Expose
    public List<OneDayChart> oneDayCharts;

    @SerializedName("oneMonthChart")
    @Expose
    public List<OneMonthChart> OneMonthCharts;
}
