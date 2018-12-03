package edu.uga.cs.cs4060.stocksimulator.Retrofit;

import java.util.HashMap;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.FiveYearChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneMonthChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneYearChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Symbol;
import edu.uga.cs.cs4060.stocksimulator.User.symbol;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("stock/market/batch?")
    Call<HashMap<String, Stock>> getStock(@Query("symbols") String symbols, @Query("types") String types,  @Query("range") String range );
    //http://api.iextrading.com/1.0/stock/market/batch?symbols=aapl&types=quote&range=1d

    @GET("stock/{symbol}/chart/1m")
    Call<List<OneMonthChart>> getMonth(@Path("symbol") String symbols );

    @GET("stock/{symbol}/chart/1y")
    Call<List<OneYearChart>> getYear(@Path("symbol") String symbols );

    @GET("stock/{symbol}/chart/5y")
    Call<List<FiveYearChart>> getFiveYear(@Path("symbol") String symbols );

    @GET("stock/market/batch?")
    Call<HashMap<String, Stock>> getStocks(@Query("symbols") String symbols, @Query("types") String types,  @Query("range") String range );

    @GET("ref-data/symbols")
    Call<List<Symbol>> getSymbols();





//
//    @GET("stock/{ticker}/quote")
//    Call<Quote> getQuote(@Path("ticker") String ticker);

    // https://api.iextrading.com/1.0/stock/market/batch?symbols=aapl,fb&types=quote,stats
}
