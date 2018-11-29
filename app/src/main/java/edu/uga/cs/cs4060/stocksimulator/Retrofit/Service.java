package edu.uga.cs.cs4060.stocksimulator.Retrofit;
import java.util.HashMap;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneMonthChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Quote;
import retrofit2.http.*;
import retrofit2.Call;

public interface Service {

    @GET("stock/market/batch?")
    Call<HashMap<String, Stock>> getStock(@Query("symbols") String symbols, @Query("types") String types,  @Query("range") String range );
    //http://api.iextrading.com/1.0/stock/market/batch?symbols=aapl&types=quote&range=1d

    @GET("stock/{symbol}/chart/1m")
    Call<List<OneMonthChart>> getMonth(@Path("symbol") String symbols );
    //http://api.iextrading.com/1.0/stock/market/batch?symbols=aapl&types=quote&range=1d



    @GET("stock/market/batch?")
    Call<HashMap<String, Stock>> getStocks(@Query("symbols") String symbols, @Query("types") String types,  @Query("range") String range );





//
//    @GET("stock/{ticker}/quote")
//    Call<Quote> getQuote(@Path("ticker") String ticker);

    // https://api.iextrading.com/1.0/stock/market/batch?symbols=aapl,fb&types=quote,stats
}
