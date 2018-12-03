package edu.uga.cs.cs4060.stocksimulator.User;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uga.cs.cs4060.stocksimulator.Retrofit.Stock;

@IgnoreExtraProperties
public class Portflio {
    private static Portflio portflio;
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

    public static Portflio getInstance(){
        if(portflio == null){
            portflio = new Portflio();
        }
            return portflio;
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
        double percent = ((lastest - costBasis) / costBasis) ;
        double value = lastest * holdings.get(symbol).shares;
        holdings.get(symbol).percentChange = percent;
        holdings.get(symbol).dayPercentChange = stock.quote.getChangePercent();
        holdings.get(symbol).value = value;
        holdings.get(symbol).dayAmountChange = stock.quote.getChange();
        holdings.get(symbol).timeUpdate = stock.quote.getLatestTime();
        holdings.get(symbol).oneDayCharts = stock.oneDayCharts;

        System.out.println( "TIMEEEE: " + stock.quote.getLatestTime());

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
    public double getTotalShares(){
        double shares = 0.0;
        for (String str : holdings.keySet()){
            shares += holdings.get(str).shares;
        }
        return shares;
    }

    @Exclude
    public double getTotalInvested(String key){
        if(getHolding(key) != null){
            return getHolding(key).costBasis * getHolding(key).shares;
        }else{
            return 0;
        }
    }


    @Exclude
    public double getTotalPercent(){

            double percent = 0;
            //how much did i start with ?
            //what my value now?

            double start = getCostBasisDouble();
            double current = getValue();

             double totalChange = (current  / start) - 1;
             System.out.println("Start value: " + start);

             if(start == 0 && current == 0){
                 return 0;
             }


            System.out.println("Current value: " + current);
            System.out.println("Total Change: " + totalChange);
            return totalChange;

    }

    @Exclude
    public ArrayList<Holding> getArrayListHolding(){
        ArrayList<Holding> toReturn  = new ArrayList<>();
        for(String sym : holdings.keySet()){
            toReturn.add(holdings.get(sym));
        }

        return toReturn;
    }

    @Exclude
    public String getDaySummary(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormat df = new DecimalFormat("%.###");

        String summary = "Today: ";

        summary += formatter.format(getDayAmountChange()) ;

        if(getDayPercentChange() > 0.00){
            summary += " ( "+ df.format(getDayPercentChange()) + "%)" ;
        }else{
            summary += " ( " + df.format(getDayPercentChange()) + "%)";
        }
        return summary;
    }

    @Exclude
    public String getCostBasis(){
        String cost = "";
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        double costDouble = 0.00;
        for(String str: holdings.keySet()){
            costDouble += holdings.get(str).costBasis * holdings.get(str).shares;
        }
        cost = formatter.format((costDouble));
        return cost;
    }

    @Exclude
    public double getCostBasisDouble(){
        double costDouble = 0.00;
        for(String str: holdings.keySet()){
            costDouble += holdings.get(str).costBasis * holdings.get(str).shares;
        }
        return costDouble;
    }
    @Exclude
    public double getDayPercentChange(){

        double totalValue = getValue();
        System.out.println("Value! " + totalValue );
        double dayPercent = 0.0;
        for(String str: holdings.keySet()){
            dayPercent += (holdings.get(str).value / totalValue) * holdings.get(str).dayPercentChange;
            System.out.println(str + " " + holdings.get(str).dayPercentChange + " " + holdings.get(str).value + " / " + totalValue + "    " +  (holdings.get(str).value / totalValue));
        }
        System.out.println(dayPercent);
        return dayPercent;
    }

    @Exclude
    public double calculateValueReturn(String holding){
        System.out.println(holdings.get(holding).value + " VLAUE OF " + holding);
        System.out.println(holdings.get(holding).costBasis * holdings.get(holding).shares + " cb * sh OF " + holding);

        return holdings.get(holding).value - (holdings.get(holding).costBasis * holdings.get(holding).shares );
    }

    @Exclude
    public double getDayAmountChange(){
        double amount = 0.0;
        for(String str : holdings.keySet()){
            amount += holdings.get(str).dayAmountChange * holdings.get(str).shares;
        }
        return amount;
    }

    @Exclude
    public  ArrayList<String> getSymbolList(){
        ArrayList<String> symbols = new ArrayList<>();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        for(String sym : holdings.keySet()){
            Holding stock = holdings.get(sym);
           symbols.add(sym + " Shares: " + stock.shares + " Price: " + formatter.format(stock.latestLivePrice) + " Day Change: %" + (stock.dayPercentChange * 100) + " Value: " + formatter.format( stock.value) + " Total Percant Change: %" +  (stock.percentChange ));
        }
        return symbols;
    }
    //TODO
    //Add various methods to help sell and buy shares of a stock!


}
