package edu.uga.cs.cs4060.stocksimulator.User;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Chart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Minute;

@IgnoreExtraProperties
public class Holding {
    public Double shares;
    public Double costBasis;
    public Double buyPrice;
    public Double value;
    public String symbol;
    public Double latestLivePrice;
    public Double percentChange;
    public Double dayPercentChange;
    public Double dayAmountChange;
    public String timeUpdate;
    public List<Minute> dayChart;

    public Holding(){}

    public Holding(String symbol, double shares, double buyPrice){
        this.latestLivePrice = buyPrice;
        this.symbol = symbol;
        this.costBasis = buyPrice;
        this.shares = shares;
        this.buyPrice = buyPrice;
        this.value = (shares * latestLivePrice);
        this.percentChange = 0.0;
        this.dayPercentChange = 0.0;
        this.dayAmountChange = 0.0;
        this.timeUpdate = "0";
    }

    @Exclude
    protected Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("symbol", symbol);
        result.put("shares", shares);
        result.put("buyPrice", buyPrice);
        result.put("latestLivePrice", latestLivePrice);
        result.put("costBasis", costBasis);
        result.put("percentChange", percentChange);
        result.put("dayPercentChange", dayPercentChange);
        result.put("dayAmountChange", dayAmountChange);
        result.put("timeUpdate", timeUpdate);
        result.put("chartData", dayChart);



        return result;
    }
}
