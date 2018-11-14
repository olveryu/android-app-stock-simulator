package edu.uga.cs.cs4060.stocksimulator;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("primaryExchange")
    @Expose
    private String primaryExchange;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("calculationPrice")
    @Expose
    private String calculationPrice;
    @SerializedName("open")
    @Expose
    private Double open;
    @SerializedName("openTime")
    @Expose
    private Double openTime;
    @SerializedName("close")
    @Expose
    private Double close;
    @SerializedName("closeTime")
    @Expose
    private Double closeTime;
    @SerializedName("high")
    @Expose
    private Double high;
    @SerializedName("low")
    @Expose
    private Double low;
    @SerializedName("latestPrice")
    @Expose
    private Double latestPrice;
    @SerializedName("latestSource")
    @Expose
    private String latestSource;
    @SerializedName("latestTime")
    @Expose
    private String latestTime;
    @SerializedName("latestUpdate")
    @Expose
    private Double latestUpdate;
    @SerializedName("latestVolume")
    @Expose
    private Double latestVolume;
    @SerializedName("iexRealtimePrice")
    @Expose
    private Double iexRealtimePrice;
    @SerializedName("iexRealtimeSize")
    @Expose
    private Double iexRealtimeSize;
    @SerializedName("iexLastUpdated")
    @Expose
    private Double iexLastUpdated;
    @SerializedName("delayedPrice")
    @Expose
    private Double delayedPrice;
    @SerializedName("delayedPriceTime")
    @Expose
    private Double delayedPriceTime;
    @SerializedName("extendedPrice")
    @Expose
    private Double extendedPrice;
    @SerializedName("extendedChange")
    @Expose
    private Double extendedChange;
    @SerializedName("extendedChangePercent")
    @Expose
    private Double extendedChangePercent;
    @SerializedName("extendedPriceTime")
    @Expose
    private Double extendedPriceTime;
    @SerializedName("previousClose")
    @Expose
    private Double previousClose;
    @SerializedName("change")
    @Expose
    private Double change;
    @SerializedName("changePercent")
    @Expose
    private Double changePercent;
    @SerializedName("iexMarketPercent")
    @Expose
    private Double iexMarketPercent;
    @SerializedName("iexVolume")
    @Expose
    private Double iexVolume;
    @SerializedName("avgTotalVolume")
    @Expose
    private Double avgTotalVolume;
    @SerializedName("iexBidPrice")
    @Expose
    private Double iexBidPrice;
    @SerializedName("iexBidSize")
    @Expose
    private Double iexBidSize;
    @SerializedName("iexAskPrice")
    @Expose
    private Double iexAskPrice;
    @SerializedName("iexAskSize")
    @Expose
    private Double iexAskSize;
    @SerializedName("marketCap")
    @Expose
    private Double marketCap;
    @SerializedName("peRatio")
    @Expose
    private Double peRatio;
    @SerializedName("week52High")
    @Expose
    private Double week52High;
    @SerializedName("week52Low")
    @Expose
    private Double week52Low;
    @SerializedName("ytdChange")
    @Expose
    private Double ytdChange;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCalculationPrice() {
        return calculationPrice;
    }

    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Double openTime) {
        this.openTime = openTime;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Double closeTime) {
        this.closeTime = closeTime;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getLatestSource() {
        return latestSource;
    }

    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public Double getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Double latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public Double getLatestVolume() {
        return latestVolume;
    }

    public void setLatestVolume(Double latestVolume) {
        this.latestVolume = latestVolume;
    }

    public Double getIexRealtimePrice() {
        return iexRealtimePrice;
    }

    public void setIexRealtimePrice(Double iexRealtimePrice) {
        this.iexRealtimePrice = iexRealtimePrice;
    }

    public Double getIexRealtimeSize() {
        return iexRealtimeSize;
    }

    public void setIexRealtimeSize(Double iexRealtimeSize) {
        this.iexRealtimeSize = iexRealtimeSize;
    }

    public Double getIexLastUpdated() {
        return iexLastUpdated;
    }

    public void setIexLastUpdated(Double iexLastUpdated) {
        this.iexLastUpdated = iexLastUpdated;
    }

    public Double getDelayedPrice() {
        return delayedPrice;
    }

    public void setDelayedPrice(Double delayedPrice) {
        this.delayedPrice = delayedPrice;
    }

    public Double getDelayedPriceTime() {
        return delayedPriceTime;
    }

    public void setDelayedPriceTime(Double delayedPriceTime) {
        this.delayedPriceTime = delayedPriceTime;
    }

    public Double getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(Double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public Double getExtendedChange() {
        return extendedChange;
    }

    public void setExtendedChange(Double extendedChange) {
        this.extendedChange = extendedChange;
    }

    public Double getExtendedChangePercent() {
        return extendedChangePercent;
    }

    public void setExtendedChangePercent(Double extendedChangePercent) {
        this.extendedChangePercent = extendedChangePercent;
    }

    public Double getExtendedPriceTime() {
        return extendedPriceTime;
    }

    public void setExtendedPriceTime(Double extendedPriceTime) {
        this.extendedPriceTime = extendedPriceTime;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(Double changePercent) {
        this.changePercent = changePercent;
    }

    public Double getIexMarketPercent() {
        return iexMarketPercent;
    }

    public void setIexMarketPercent(Double iexMarketPercent) {
        this.iexMarketPercent = iexMarketPercent;
    }

    public Double getIexVolume() {
        return iexVolume;
    }

    public void setIexVolume(Double iexVolume) {
        this.iexVolume = iexVolume;
    }

    public Double getAvgTotalVolume() {
        return avgTotalVolume;
    }

    public void setAvgTotalVolume(Double avgTotalVolume) {
        this.avgTotalVolume = avgTotalVolume;
    }

    public Double getIexBidPrice() {
        return iexBidPrice;
    }

    public void setIexBidPrice(Double iexBidPrice) {
        this.iexBidPrice = iexBidPrice;
    }

    public Double getIexBidSize() {
        return iexBidSize;
    }

    public void setIexBidSize(Double iexBidSize) {
        this.iexBidSize = iexBidSize;
    }

    public Double getIexAskPrice() {
        return iexAskPrice;
    }

    public void setIexAskPrice(Double iexAskPrice) {
        this.iexAskPrice = iexAskPrice;
    }

    public Double getIexAskSize() {
        return iexAskSize;
    }

    public void setIexAskSize(Double iexAskSize) {
        this.iexAskSize = iexAskSize;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(Double peRatio) {
        this.peRatio = peRatio;
    }

    public Double getWeek52High() {
        return week52High;
    }

    public void setWeek52High(Double week52High) {
        this.week52High = week52High;
    }

    public Double getWeek52Low() {
        return week52Low;
    }

    public void setWeek52Low(Double week52Low) {
        this.week52Low = week52Low;
    }

    public Double getYtdChange() {
        return ytdChange;
    }

    public void setYtdChange(Double ytdChange) {
        this.ytdChange = ytdChange;
    }

}
