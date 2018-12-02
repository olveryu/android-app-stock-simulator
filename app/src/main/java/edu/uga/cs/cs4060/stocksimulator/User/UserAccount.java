package edu.uga.cs.cs4060.stocksimulator.User;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.Retrofit.ApiUtils;
import edu.uga.cs.cs4060.stocksimulator.Retrofit.Service;
import edu.uga.cs.cs4060.stocksimulator.Retrofit.Stock;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.FiveYearChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneMonthChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneYearChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Quote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccount {

    public static Portflio portflio;
    public static FirebaseAuth auth;
    public static FirebaseDatabase data;
    public static FirebaseUser user;
    public static UserAccount account;
    public static Stock latestStockLoaded;
    public static List<symbol> symbols;
    public static String range;
    public static List<Highscore> highscoresList;

    public OnTaskCompleted listener; //Used to alert UI of completed tasks

    //Used for singleton constructor
    private UserAccount(){
        data = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user =  auth.getCurrentUser();
        highscoresList = new ArrayList<>();
        portflio = Portflio.getInstance(); //Create new portflio
        range = "1d";
    }

    public static void signOut(){
        data = null;
        user = null;
        account = null;
        latestStockLoaded = null;
        portflio = null;
        highscoresList = null;
        range = null;
        symbols = null;
        auth.signOut();
        data = null;
    }

    public static boolean userIsLogin(){
        return user != null;
    }


    // fucntion to load csv
    public void loadCSV(OnTaskCompleted listener) {
        this.listener = listener;
        Service service = ApiUtils.getService();
        service.getSymbols().enqueue(new Callback<List<symbol>>() {
            @Override
            public void onResponse(Call<List<symbol>> call, Response<List<symbol>> response) {
                System.out.println("API CALL FOR symbol: " + response.raw());
                if(response.isSuccessful()){ // If API Call is success
                    System.out.println("csv load sucessful");
                    symbols =  response.body(); // Load data into a Stock Quote
                    listener.onTaskCompleted();
                }else{
                    listener.onTaskFailed(); // Alert UI of failure
                }
            }

            @Override
            public void onFailure(Call<List<symbol>> call, Throwable t) {
                listener.onTaskFailed(); // Alert UI of failure
            }
        });
    }

    //Loads the user account and updates live prices
    public  void load(OnTaskCompleted listener){
        loadPortfolio();
        this.listener = listener;
    }

    //Returns singleton instance
    public static UserAccount getInstance() {
        if(account == null){
            account = new UserAccount();
        }
        return account;
    }

    //Add stock manually, simulate old purchase
    //Takes a Holding and adds to firebase database
    //Also gets live stats osn the stock added
    public void addStockManually(Holding holding){
        portflio.holdings.put(holding.symbol, holding);
        retriveLivePrices();
    }


    //Add a holding to database
    //Adds a holding to the database
    //Example use, this is called when you buy a stock
    public  void addStockToDatabase(Holding holding){
        portflio.holdings.put(holding.symbol, holding);
        updateDatabase();
    }

    //Buy Stock
    public  void buyStock(String symbol, double shares, OnTaskCompleted listener){
        this.listener = listener;
        // this should fix
        Service service = ApiUtils.getService();
        System.out.println("BUYING " + symbol);


        service.getStock(symbol, "quote,chart", "1d").enqueue(new Callback< HashMap<String, Stock>>() { //get the single stock wwe are buying
            @Override
            public void onResponse(Call< HashMap<String, Stock>> call, Response< HashMap<String, Stock>> response) {
                if (response.isSuccessful()) { // IF API responds
                    HashMap<String, Stock> stockMap = response.body(); // Gets the quote from API
                    System.out.println("RAW ASK: " + response.raw());

                    //will always be 1 answer into this!

                    Quote q = stockMap.get(symbol).quote; // Gets the quote from API
                    Stock stock = stockMap.get(symbol);

                    double sharePrice = q.getLatestPrice(); // Share Price of quote
                    double cost = sharePrice * shares; // Cost of transaction

                    //if we don't have cash, cancel
                    //8000 < 3000
                    if(portflio.cashToTrade < (cost)){
                        System.out.println("CASH CHECK IF");
                        listener.onTaskFailed(); //Update UI the trasnaction failed
                        return;
                    }

                    //Removes cash from portflio, cost of the shares bought
                    portflio.cashToTrade -= cost;

                    //check to see if user owns stock already
                    //If the symbol isnt in the holdings, add a new holding
                     if(portflio.holdings.get(symbol) == null){
                         System.out.println("DOSENT HOLD SYM, adding to port");
                         Holding holding = new Holding(symbol, shares, sharePrice, stock.oneDayCharts);
                         addStockToDatabase(holding);  //Adds to firebase
                        }else{ //Otherwise, we own shares already
                            //Number of previous shares
                         System.out.println("HAS STOCK BUYING NOW");
                            double oldShares = portflio.getHolding(symbol).shares;

                            //Number of new shares bought
                            double newShares = shares;

                            //Cost basis prior to transaction
                            double costBasis = portflio.getHolding(symbol).costBasis;

                            //Merge cost basis and calculate new one
                            double updatedCostBasis = ((oldShares * costBasis + newShares * sharePrice)/ (oldShares + newShares) );

                            System.out.println("NEW BASIS:  " + updatedCostBasis);

                            //Update portflio variables for the symbol

                            portflio.holdings.get(symbol).shares += shares;
                            portflio.holdings.get(symbol).value = portflio.getHolding(symbol).shares * q.getLatestPrice();
                            portflio.holdings.get(symbol).dayPercentChange = q.getChangePercent();



                            //Calculate percent up/down
                             double calcPercent = ((sharePrice - costBasis) / costBasis) ;
                            portflio.getHolding(symbol).percentChange = calcPercent;
                            portflio.getHolding(symbol).costBasis = updatedCostBasis;

                            updateDatabase();  // call to update firebase with new portfolio
                        }
                    listener.onTaskCompleted();

                } else {
                        System.out.println("ERROR NOT SUCCESFULL");
                        listener.onTaskFailed(); //alert UI of failure
                    }
            }

            //Failed to buy stock, problem with API/ API Call
            @Override
            public void onFailure(Call< HashMap<String, Stock>> call, Throwable t) {
                listener.onTaskFailed();
                t.printStackTrace();
                System.out.println("FAILURE ON CALL");
                return;
                }
            });


    } // end of buy method


    public void sellStock(String symbol, double shares, OnTaskCompleted listener){
        this.listener = listener;
        Service service = ApiUtils.getService();
        System.out.println("SELLING " + symbol);
        service.getStock(symbol, "quote,chart", "1d").enqueue(new Callback< HashMap<String, Stock>>() {
            @Override
            public void onResponse(Call< HashMap<String, Stock>> call, Response< HashMap<String, Stock>> response) {
                if (response.isSuccessful()) { // IF API responds
                    HashMap<String, Stock> stockMap = response.body(); // Gets the quote from API
                    Quote q = stockMap.get(symbol).quote;

                    double sharePrice = q.getLatestPrice(); // Share Price of quote
                    double salePrice = sharePrice * shares; // Cost of transaction

                    //if we don't have cash, cancel
                    if(portflio.holdings.get(symbol) == null){
                        listener.onTaskFailed(); //Update UI the trasnaction failed
                        System.out.println("NOT ENOUGH SHARES TO SELL");
                        return;
                    }else if(portflio.holdings.get(symbol).shares < shares){
                        listener.onTaskFailed(); //Update UI the trasnaction failed
                        System.out.println("NOT ENOUGH SHARES TO SELL");
                        return;
                    }


                    //Add cash to portflio, cost of the shares sold
                    portflio.cashToTrade += salePrice;

                    System.out.println("Calculation sale tranasction");
                        //Number of previous shares
                    double currentShares = portflio.getHolding(symbol).shares;

                        //Number of new shares bought
                    double sharesSold = shares ;

                        //Cost basis prior to transaction
                    double costBasis = portflio.getHolding(symbol).costBasis;


                    if(portflio.holdings.get(symbol).shares == shares ){
                        //take cash, delete
                        portflio.holdings.remove(symbol);
                        System.out.println("DELETING HOLDINGS");

                    }else {

                        //Merge cost basis and calculate new one
                        double updatedCostBasis = ((currentShares * costBasis) - (sharesSold * sharePrice)) / (currentShares - sharesSold);

                        //Update portflio variables for the symbol

                        portflio.holdings.get(symbol).shares -= sharesSold;
                        portflio.holdings.get(symbol).value = portflio.getHolding(symbol).shares * q.getLatestPrice();
                        portflio.holdings.get(symbol).dayPercentChange = q.getChangePercent();
                        portflio.cashToTrade += salePrice;


                        //Calculate percent up/down
                        double calcPercent = ((sharePrice - costBasis) / costBasis) ;
                        portflio.getHolding(symbol).percentChange = calcPercent;
                        portflio.getHolding(symbol).costBasis = updatedCostBasis;

                    }
                    updateDatabase();  // call to update firebase with new portfolio



                } else {
                    listener.onTaskFailed(); //alert UI of failure
                }
            }

            //Failed to buy stock, problem with API/ API Call
            @Override
            public void onFailure(Call< HashMap<String, Stock>> call, Throwable t) {
                listener.onTaskFailed();
                t.printStackTrace();
                return;
            }
        });
    }


    //Updates firebase database
    public  void updateDatabase(){
        //UID of current User
        String uid = user.getUid();

        System.out.println("UPDATE COMPLETE!!!!");

        //Locate user in database and update the portflio
        data.getReference().child("/users/" + uid + "/port/"  ).updateChildren(portflio.toMap());
        listener.onTaskCompleted(); // Alert UI of success
    }

    private String getAllSymbols(){
        String tickers = "";
        for(String key : portflio.holdings.keySet()){
            tickers += key + ",";
        }
        return tickers.substring(0, tickers.length() - 1); //remove last , then return
    }



    //Retrieve live prices from api and update current portflio
    private  void retriveLivePrices(){
        if(portflio.hasPortfolio) {
            //Get the Retrofit2 Service
            Service service = ApiUtils.getService();

            //Used in the API, example: www.api.iextrading.com/1.0/stock/batch?symbols=AAPL,VGT&types=quotes
            String tickers = getAllSymbols();
            String types = "quote,chart";
            System.out.println("Retrving live prices");

            //Call to API, returns hashmap of <String, Stock>
            service.getStocks(tickers, types, range).enqueue(new Callback<HashMap<String, Stock>>() {
                @Override
                public void onResponse(Call<HashMap<String, Stock>> call, Response<HashMap<String, Stock>> response) {
                    if (response.isSuccessful()) { // If api call is a success, load the data
                        HashMap<String, Stock> map = response.body(); // Map of the symbols and stocks
                        System.out.println("RAW DATA: \n " + response.raw());
                        for (String key : map.keySet()) { //Loop over map, and update our portflio

                            System.out.println("SIZE OF CHART: " + map.get(key).oneDayCharts.size());

                            portflio.updateStock(key, map.get(key)); //updates the prices and percetange
                        }
                        listener.onTaskCompleted();
                    //    updateDatabase(); //Now update the firebase database to store the data
                    }
                } // End of onResponse

                @Override
                public void onFailure(Call<HashMap<String, Stock>> call, Throwable t) {
                    System.out.println("FALURE!!!!");
                    listener.onTaskFailed();
                    t.printStackTrace();
                    return;
                }// End of onFailure

            });
        }else{
            listener.onTaskCompleted();
        }
    }// End of live prices retrive!


    //Loads a single stock in the UserAccount static variable, since its async, return onTaskComplete
    public void getMonthData(String symbol, OnTaskCompleted listener){
        this.listener = listener;
        Service service = ApiUtils.getService(); //Retrofit2 reference

        //Call to get a single stock quote
        service.getMonth(symbol).enqueue(new Callback<List<OneMonthChart>>() {
            @Override
            public void onResponse(Call<List<OneMonthChart>> call, Response<List<OneMonthChart>> response) {
                System.out.println("API CALL FOR : " + response.raw());
                if(response.isSuccessful()){ // If API Call is success
                    List<OneMonthChart> data =  response.body(); // Load data into a Stock Quote
                    latestStockLoaded.oneMonthCharts = data;
                    if(data.size() == 0){
                        listener.onTaskFailed();
                    }else{
                        System.out.println("WE HAVE DATA");
                        listener.onTaskCompleted(); // Alert UI of success
                    }
                }else{
                    listener.onTaskFailed(); // Alert UI of failure
                }
            }

            @Override
            public void onFailure(Call<List<OneMonthChart>> call, Throwable t) {
                listener.onTaskFailed();
                System.out.println(t.getMessage());
                return;
            }
        });
    }

    //Loads a single stock in the UserAccount static variable, since its async, return onTaskComplete
    public void getYearData(String symbol, OnTaskCompleted listener){
        this.listener = listener;
        Service service = ApiUtils.getService(); //Retrofit2 reference

        //Call to get a single stock quote
        service.getYear(symbol).enqueue(new Callback<List<OneYearChart>>() {
            @Override
            public void onResponse(Call<List<OneYearChart>> call, Response<List<OneYearChart>> response) {
                System.out.println("API CALL FOR : " + response.raw());
                if(response.isSuccessful()){ // If API Call is success
                    List<OneYearChart> data =  response.body(); // Load data into a Stock Quote
                    latestStockLoaded.oneYearCharts = data;
                    if(data.size() == 0){
                        listener.onTaskFailed();
                    }else{
                        System.out.println("WE HAVE DATA");
                        listener.onTaskCompleted(); // Alert UI of success
                    }
                }else{
                    listener.onTaskFailed(); // Alert UI of failure
                }
            }

            @Override
            public void onFailure(Call<List<OneYearChart>> call, Throwable t) {
                listener.onTaskFailed();
                System.out.println(t.getMessage());
                return;
            }
        });
    }

    //Loads a single stock in the UserAccount static variable, since its async, return onTaskComplete
    public void getFiveYearData(String symbol, OnTaskCompleted listener){
        this.listener = listener;
        Service service = ApiUtils.getService(); //Retrofit2 reference

        //Call to get a single stock quote
        service.getFiveYear(symbol).enqueue(new Callback<List<FiveYearChart>>() {
            @Override
            public void onResponse(Call<List<FiveYearChart>> call, Response<List<FiveYearChart>> response) {
                System.out.println("API CALL FOR : " + response.raw());
                if(response.isSuccessful()){ // If API Call is success
                    List<FiveYearChart> data =  response.body(); // Load data into a Stock Quote
                    latestStockLoaded.fiveYearCharts = data;
                    if(data.size() == 0){
                        listener.onTaskFailed();
                    }else{
                        System.out.println("WE HAVE DATA");
                        listener.onTaskCompleted(); // Alert UI of success
                    }
                }else{
                    listener.onTaskFailed(); // Alert UI of failure
                }
            }

            @Override
            public void onFailure(Call<List<FiveYearChart>> call, Throwable t) {
                listener.onTaskFailed();
                System.out.println(t.getMessage());
                return;
            }
        });
    }

    //Loads a single stock in the UserAccount static variable, since its async, return onTaskComplete
    public void getSingleStock(String symbol, OnTaskCompleted listener){
        Service service = ApiUtils.getService(); //Retrofit2 reference
        this.listener = listener;
        //Call to get a single stock quote
        service.getStock(symbol, "quote,chart", "1d").enqueue(new Callback< HashMap<String, Stock>>() {
            @Override
            public void onResponse(Call< HashMap<String, Stock>> call, Response< HashMap<String, Stock>> response) {
                System.out.println("API CALL: " + response.raw());
                if(response.isSuccessful()){ // If API Call is success
                    HashMap<String, Stock> stockMap =  response.body(); // Load data into a Stock Quote
                    latestStockLoaded = stockMap.get(symbol); // Load into static variable. //TODO Must find better way to pass back this variable
                    System.out.println(stockMap.size());
                    if(stockMap.size() == 0){
                        listener.onTaskFailed();
                    }else{
                        listener.onTaskCompleted();
                    }
                }else{
                    listener.onTaskFailed(); // Alert UI of failure
                }
            }

            //Failed to call API
            @Override
            public void onFailure(Call< HashMap<String, Stock>> call, Throwable t) {
                listener.onTaskFailed();
                t.printStackTrace();
                return;
            }
        });
    }



    public void loadHighscores(OnTaskCompleted list){
        //Get database
        highscoresList.clear();
        //Get location of user in database
        data.getReference().child("/users/" ).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Grab the portflio from database

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Portflio port = postSnapshot.getValue(Portflio.class);
                    String email = port.name;
                    Double value = port.cashToTrade + port.getValue();
                    Highscore score = new Highscore(email, value);
                    highscoresList.add(score);


                }
                list.onTaskCompleted();
//                    portflio = dataSnapshot.getValue(Portflio.class);
//                if(portflio.holdings == null || portflio.holdings.size() == 0){
//                    portflio.hasPortfolio = false;
//                    System.out.println("NO HOLDINGS FOUND, dont load live prices or it crashse");
//                    portflio.holdings.clear();
//                    listener.onTaskCompleted();
//                }else{
//                    portflio.hasPortfolio = true;
//                    retriveLivePrices(); // Now update the account with live data
//
//                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    } // end of loadPortfolio

    //Load the inital firebase portflio for stock trading
    private void loadPortfolio(){
        //Get database

        //Get location of user in database
        data.getReference().child("/users/" + user.getUid() + "/" ).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Grab the portflio from database
                portflio = dataSnapshot.getValue(Portflio.class);
                if(portflio.holdings == null || portflio.holdings.size() == 0){
                    portflio.hasPortfolio = false;
                    System.out.println("NO HOLDINGS FOUND, dont load live prices or it crashse");
                    portflio.holdings.clear();
                    listener.onTaskCompleted();
                }else{
                    portflio.hasPortfolio = true;
                    retriveLivePrices(); // Now update the account with live data

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    } // end of loadPortfolio


    //Updates the entire portflio by loading live API prices and updating database.
    public void update(OnTaskCompleted listener){
        this.listener = listener;
      //  loadPortfolio();
        retriveLivePrices();
        System.out.println("In update useraccount");
    }
}
