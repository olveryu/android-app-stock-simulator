package edu.uga.cs.cs4060.stocksimulator.StocksInfomations;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("open")
    @Expose
    public Open open;

    @SerializedName("close")
    @Expose
    public Close close;

    @SerializedName("high")
    @Expose
    public Double high;

    @SerializedName("low")
    @Expose
    public  Double low;


    public Price(){}

}

class Open{
    @SerializedName("price")
    @Expose
    Double price;

    @SerializedName("time")
    @Expose
    Double time;
}

class Close{
    @SerializedName("price")
    @Expose
    Double price;

    @SerializedName("time")
    @Expose
    Double time;
}