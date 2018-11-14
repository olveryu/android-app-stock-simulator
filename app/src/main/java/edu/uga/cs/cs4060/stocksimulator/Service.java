package edu.uga.cs.cs4060.stocksimulator;
import java.util.HashMap;

import retrofit2.http.*;
import retrofit2.Call;

public interface Service {

    @GET("stock/{ticker}/quote")
    Call<Quote> getQuote(@Path("ticker") String ticker);


    @GET("stock/market/batch?")
    Call<HashMap<String, Stock>> getStocks(@Query("symbols") String symbols, @Query("types") String types);
    //TODO : ADD HASK MAP RETURN SVALUES!

//
//    @GET("stock/{ticker}/quote")
//    Call<Quote> getQuote(@Path("ticker") String ticker);

    // https://api.iextrading.com/1.0/stock/market/batch?symbols=aapl,fb&types=quote,stats
}
