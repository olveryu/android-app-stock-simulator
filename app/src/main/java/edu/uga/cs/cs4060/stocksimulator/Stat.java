
package edu.uga.cs.cs4060.stocksimulator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("marketcap")
    @Expose
    private Double marketcap;
    @SerializedName("beta")
    @Expose
    private Double beta;
    @SerializedName("week52high")
    @Expose
    private Double week52high;
    @SerializedName("week52low")
    @Expose
    private Double week52low;
    @SerializedName("week52change")
    @Expose
    private Double week52change;
    @SerializedName("shortInterest")
    @Expose
    private Double shortInterest;
    @SerializedName("shortDate")
    @Expose
    private Double shortDate;
    @SerializedName("dividendRate")
    @Expose
    private Double dividendRate;
    @SerializedName("dividendYield")
    @Expose
    private Double dividendYield;
    @SerializedName("exDividendDate")
    @Expose
    private String exDividendDate;
    @SerializedName("latestEPS")
    @Expose
    private Double latestEPS;
    @SerializedName("latestEPSDate")
    @Expose
    private String latestEPSDate;
    @SerializedName("sharesOutstanding")
    @Expose
    private Double sharesOutstanding;
    @SerializedName("float")
    @Expose
    private Double _float;
    @SerializedName("returnOnEquity")
    @Expose
    private Double returnOnEquity;
    @SerializedName("consensusEPS")
    @Expose
    private Double consensusEPS;
    @SerializedName("numberOfEstimates")
    @Expose
    private Double numberOfEstimates;
    @SerializedName("EPSSurpriseDollar")
    @Expose
    private Object ePSSurpriseDollar;
    @SerializedName("EPSSurprisePercent")
    @Expose
    private Double ePSSurprisePercent;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("EBITDA")
    @Expose
    private Double eBITDA;
    @SerializedName("revenue")
    @Expose
    private Double revenue;
    @SerializedName("grossProfit")
    @Expose
    private Double grossProfit;
    @SerializedName("cash")
    @Expose
    private Double cash;
    @SerializedName("debt")
    @Expose
    private Double debt;
    @SerializedName("ttmEPS")
    @Expose
    private Double ttmEPS;
    @SerializedName("revenuePerShare")
    @Expose
    private Double revenuePerShare;
    @SerializedName("revenuePerEmployee")
    @Expose
    private Double revenuePerEmployee;
    @SerializedName("peRatioHigh")
    @Expose
    private Double peRatioHigh;
    @SerializedName("peRatioLow")
    @Expose
    private Double peRatioLow;
    @SerializedName("returnOnAssets")
    @Expose
    private Double returnOnAssets;
    @SerializedName("returnOnCapital")
    @Expose
    private Object returnOnCapital;
    @SerializedName("profitMargin")
    @Expose
    private Double profitMargin;
    @SerializedName("priceToSales")
    @Expose
    private Double priceToSales;
    @SerializedName("priceToBook")
    @Expose
    private Double priceToBook;
    @SerializedName("day200MovingAvg")
    @Expose
    private Double day200MovingAvg;
    @SerializedName("day50MovingAvg")
    @Expose
    private Double day50MovingAvg;
    @SerializedName("institutionPercent")
    @Expose
    private Double institutionPercent;
    @SerializedName("insiderPercent")
    @Expose
    private Object insiderPercent;
    @SerializedName("shortRatio")
    @Expose
    private Object shortRatio;
    @SerializedName("year5ChangePercent")
    @Expose
    private Double year5ChangePercent;
    @SerializedName("year2ChangePercent")
    @Expose
    private Double year2ChangePercent;
    @SerializedName("year1ChangePercent")
    @Expose
    private Double year1ChangePercent;
    @SerializedName("ytdChangePercent")
    @Expose
    private Double ytdChangePercent;
    @SerializedName("month6ChangePercent")
    @Expose
    private Double month6ChangePercent;
    @SerializedName("month3ChangePercent")
    @Expose
    private Double month3ChangePercent;
    @SerializedName("month1ChangePercent")
    @Expose
    private Double month1ChangePercent;
    @SerializedName("day5ChangePercent")
    @Expose
    private Double day5ChangePercent;
    @SerializedName("day30ChangePercent")
    @Expose
    private Double day30ChangePercent;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getMarketcap() {
        return marketcap;
    }

    public void setMarketcap(Double marketcap) {
        this.marketcap = marketcap;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getWeek52high() {
        return week52high;
    }

    public void setWeek52high(Double week52high) {
        this.week52high = week52high;
    }

    public Double getWeek52low() {
        return week52low;
    }

    public void setWeek52low(Double week52low) {
        this.week52low = week52low;
    }

    public Double getWeek52change() {
        return week52change;
    }

    public void setWeek52change(Double week52change) {
        this.week52change = week52change;
    }

    public Double getShortInterest() {
        return shortInterest;
    }

    public void setShortInterest(Double shortInterest) {
        this.shortInterest = shortInterest;
    }

    public Double getShortDate() {
        return shortDate;
    }

    public void setShortDate(Double shortDate) {
        this.shortDate = shortDate;
    }

    public Double getDividendRate() {
        return dividendRate;
    }

    public void setDividendRate(Double dividendRate) {
        this.dividendRate = dividendRate;
    }

    public Double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public String getExDividendDate() {
        return exDividendDate;
    }

    public void setExDividendDate(String exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    public Double getLatestEPS() {
        return latestEPS;
    }

    public void setLatestEPS(Double latestEPS) {
        this.latestEPS = latestEPS;
    }

    public String getLatestEPSDate() {
        return latestEPSDate;
    }

    public void setLatestEPSDate(String latestEPSDate) {
        this.latestEPSDate = latestEPSDate;
    }

    public Double getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(Double sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public Double getFloat() {
        return _float;
    }

    public void setFloat(Double _float) {
        this._float = _float;
    }

    public Double getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(Double returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    public Double getConsensusEPS() {
        return consensusEPS;
    }

    public void setConsensusEPS(Double consensusEPS) {
        this.consensusEPS = consensusEPS;
    }

    public Double getNumberOfEstimates() {
        return numberOfEstimates;
    }

    public void setNumberOfEstimates(Double numberOfEstimates) {
        this.numberOfEstimates = numberOfEstimates;
    }

    public Object getEPSSurpriseDollar() {
        return ePSSurpriseDollar;
    }

    public void setEPSSurpriseDollar(Object ePSSurpriseDollar) {
        this.ePSSurpriseDollar = ePSSurpriseDollar;
    }

    public Double getEPSSurprisePercent() {
        return ePSSurprisePercent;
    }

    public void setEPSSurprisePercent(Double ePSSurprisePercent) {
        this.ePSSurprisePercent = ePSSurprisePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getEBITDA() {
        return eBITDA;
    }

    public void setEBITDA(Double eBITDA) {
        this.eBITDA = eBITDA;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getTtmEPS() {
        return ttmEPS;
    }

    public void setTtmEPS(Double ttmEPS) {
        this.ttmEPS = ttmEPS;
    }

    public Double getRevenuePerShare() {
        return revenuePerShare;
    }

    public void setRevenuePerShare(Double revenuePerShare) {
        this.revenuePerShare = revenuePerShare;
    }

    public Double getRevenuePerEmployee() {
        return revenuePerEmployee;
    }

    public void setRevenuePerEmployee(Double revenuePerEmployee) {
        this.revenuePerEmployee = revenuePerEmployee;
    }

    public Double getPeRatioHigh() {
        return peRatioHigh;
    }

    public void setPeRatioHigh(Double peRatioHigh) {
        this.peRatioHigh = peRatioHigh;
    }

    public Double getPeRatioLow() {
        return peRatioLow;
    }

    public void setPeRatioLow(Double peRatioLow) {
        this.peRatioLow = peRatioLow;
    }

    public Double getReturnOnAssets() {
        return returnOnAssets;
    }

    public void setReturnOnAssets(Double returnOnAssets) {
        this.returnOnAssets = returnOnAssets;
    }

    public Object getReturnOnCapital() {
        return returnOnCapital;
    }

    public void setReturnOnCapital(Object returnOnCapital) {
        this.returnOnCapital = returnOnCapital;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getPriceToSales() {
        return priceToSales;
    }

    public void setPriceToSales(Double priceToSales) {
        this.priceToSales = priceToSales;
    }

    public Double getPriceToBook() {
        return priceToBook;
    }

    public void setPriceToBook(Double priceToBook) {
        this.priceToBook = priceToBook;
    }

    public Double getDay200MovingAvg() {
        return day200MovingAvg;
    }

    public void setDay200MovingAvg(Double day200MovingAvg) {
        this.day200MovingAvg = day200MovingAvg;
    }

    public Double getDay50MovingAvg() {
        return day50MovingAvg;
    }

    public void setDay50MovingAvg(Double day50MovingAvg) {
        this.day50MovingAvg = day50MovingAvg;
    }

    public Double getInstitutionPercent() {
        return institutionPercent;
    }

    public void setInstitutionPercent(Double institutionPercent) {
        this.institutionPercent = institutionPercent;
    }

    public Object getInsiderPercent() {
        return insiderPercent;
    }

    public void setInsiderPercent(Object insiderPercent) {
        this.insiderPercent = insiderPercent;
    }

    public Object getShortRatio() {
        return shortRatio;
    }

    public void setShortRatio(Object shortRatio) {
        this.shortRatio = shortRatio;
    }

    public Double getYear5ChangePercent() {
        return year5ChangePercent;
    }

    public void setYear5ChangePercent(Double year5ChangePercent) {
        this.year5ChangePercent = year5ChangePercent;
    }

    public Double getYear2ChangePercent() {
        return year2ChangePercent;
    }

    public void setYear2ChangePercent(Double year2ChangePercent) {
        this.year2ChangePercent = year2ChangePercent;
    }

    public Double getYear1ChangePercent() {
        return year1ChangePercent;
    }

    public void setYear1ChangePercent(Double year1ChangePercent) {
        this.year1ChangePercent = year1ChangePercent;
    }

    public Double getYtdChangePercent() {
        return ytdChangePercent;
    }

    public void setYtdChangePercent(Double ytdChangePercent) {
        this.ytdChangePercent = ytdChangePercent;
    }

    public Double getMonth6ChangePercent() {
        return month6ChangePercent;
    }

    public void setMonth6ChangePercent(Double month6ChangePercent) {
        this.month6ChangePercent = month6ChangePercent;
    }

    public Double getMonth3ChangePercent() {
        return month3ChangePercent;
    }

    public void setMonth3ChangePercent(Double month3ChangePercent) {
        this.month3ChangePercent = month3ChangePercent;
    }

    public Double getMonth1ChangePercent() {
        return month1ChangePercent;
    }

    public void setMonth1ChangePercent(Double month1ChangePercent) {
        this.month1ChangePercent = month1ChangePercent;
    }

    public Double getDay5ChangePercent() {
        return day5ChangePercent;
    }

    public void setDay5ChangePercent(Double day5ChangePercent) {
        this.day5ChangePercent = day5ChangePercent;
    }

    public Double getDay30ChangePercent() {
        return day30ChangePercent;
    }

    public void setDay30ChangePercent(Double day30ChangePercent) {
        this.day30ChangePercent = day30ChangePercent;
    }

}
