package edu.uga.cs.cs4060.stocksimulator.Retrofit;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Minute;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Quote;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Chart;


@IgnoreExtraProperties
public class Stock {
    @SerializedName("quote")
    @Expose
    public Quote quote;

    @SerializedName("chart")
    @Expose
    public List<Minute> minutes;



}

