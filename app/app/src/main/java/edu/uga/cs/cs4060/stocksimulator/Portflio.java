package edu.uga.cs.cs4060.stocksimulator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Portflio {
    public HashMap<String, Holding> holdings = new HashMap<>();
    public String name;
    public Double cashToTrade;

    @Exclude
    public boolean hasPortfolio = false;

    public Portflio(){ } //Default used for Firebase/API

    //Create a new stock portflio, used when making a new account.
    public Portflio(String name, Double cashToTrade){
        this.name = name;
        this.cashToTrade = cashToTrade;
        holdings = new HashMap<>();
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("holdings", holdings);
        result.put("name", name);
        result.put("cashToTrade", cashToTrade);
        return result;
    }

    @Exclude
    public Holding getHolding(String symbol){
        return holdings.get(symbol);
    }

    //This updates a holding witha  stock. Used to update a holding with love prices and calculate ppercent
    @Exclude
    public void updateStock(String symbol, Stock stock){
        holdings.get(symbol).latestLivePrice = stock.quote.getLatestPrice();

        //calculate percent up!
        double costBasis = holdings.get(symbol).costBasis;
        double lastest = holdings.get(symbol).latestLivePrice;
        double percent = ((lastest - costBasis) / costBasis) * 100;
        double value = lastest * holdings.get(symbol).shares;
        holdings.get(symbol).percentChange = percent;
        holdings.get(symbol).dayPercentChange = stock.quote.getChangePercent();
        holdings.get(symbol).value = value;

        System.out.println("Updating: " + stock.quote.getSymbol());
    }


    @Exclude
    public double getValue(){
        double value = 0.0;
        for(String str: holdings.keySet()){
            value += holdings.get(str).value;
            System.out.println(holdings.get(str).value + " is the value in " + str);
        }
        return value;
    }

    @Exclude
    public  ArrayList<String> getSymbolList(){
        ArrayList<String> symbols = new ArrayList<>();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        for(String sym : holdings.keySet()){
            Holding stock = holdings.get(sym);
           symbols.add(sym + " Shares: " + stock.shares + " Value: " + formatter.format( stock.value) + " Total Percant Change: %" +  formatter.format(stock.percentChange));
        }
        return symbols;
    }
    //TODO
    //Add various methods to help sell and buy shares of a stock!


}
