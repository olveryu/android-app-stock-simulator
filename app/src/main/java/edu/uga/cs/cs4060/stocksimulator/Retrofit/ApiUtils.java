package edu.uga.cs.cs4060.stocksimulator.Retrofit;

public class ApiUtils {

    public static final String BASE_URL = "https://api.iextrading.com/1.0/";

    public static Service getService() {
        return RetrofitClient.getClient(BASE_URL).create(Service.class);
    }

}
