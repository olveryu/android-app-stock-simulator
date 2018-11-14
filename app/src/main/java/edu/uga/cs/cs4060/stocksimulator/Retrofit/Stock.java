package edu.uga.cs.cs4060.stocksimulator.Retrofit;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Quote;


@IgnoreExtraProperties
public class Stock {
    @SerializedName("quote")
    @Expose
    public Quote quote;

}

