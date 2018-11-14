package edu.uga.cs.cs4060.stocksimulator;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@IgnoreExtraProperties
class Stock {
    @SerializedName("quote")
    @Expose
    Quote quote;

}

