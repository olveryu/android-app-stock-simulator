package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.Retrofit.Stock;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class TradeActivity extends BasicActivity {
    private Button buyStock;
    private Button sellStock;
    private Intent intent;
    private String symbolString;
    private TextView symbol;
    private TextView total;
    private TextView trade;
    private TextView fund;
    private TextView currentPrice;
    private TextView updateTime;
    private EditText stockToBuy;
    private currentPriceTask mTask;
    private Stock stock;
    private double price;
    private int stockNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        drawerNavigation();

        // get intent from previous activity
        intent = getIntent();
        symbolString = intent.getExtras().getString("symbol");

        // set range to 1d
        UserAccount.range = "1d";

        //initialize reference
        symbol = findViewById(R.id.tradeSymbol);
        total = findViewById(R.id.total);
        trade = findViewById(R.id.trade);
        fund = findViewById(R.id.fundText);
        currentPrice = findViewById(R.id.currentPrice);
        buyStock = findViewById(R.id.buyButton);
        sellStock = findViewById(R.id.sellButton);
        stockToBuy = findViewById(R.id.stocksToTrade);
        updateTime = findViewById(R.id.updateTime);

        // init UI
        symbol.setText("symbol: " + symbolString);
        fund.setText("Funds: " + formatter.format(UserAccount.portflio.cashToTrade));
        trade.setText("Numbers to trade");
        currentPrice.setText("price: loading");
        total.setText("total use: 0.00");
        stockToBuy.setText("0");
        updateTime.setText("update time : loading");
        price = 0;
        stockNumber = 0;

        // total price listener
        stockToBuy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(stockToBuy.getText().toString().equals("")){
                    stockNumber = 0;
                }else{
                    stockNumber = Integer.parseInt(stockToBuy.getText().toString());
                }
                double result = price * stockNumber;
                total.setText("total use: " + result);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // sell stock button
        sellStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount.getInstance().sellStock(symbolString, stockNumber, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        System.out.println("FINISHED SELLING!");
                        Toast.makeText(getApplicationContext(), "Sold: " +  stockNumber  + " share of " + symbolString, Toast.LENGTH_SHORT).show();
                        UIupdate();
                    }

                    @Override
                    public void onTaskFailed() {
                        System.out.println("FAiled to sell");
                    }
                });
            }
        });

        // buy stock button
        buyStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Buying now");
                System.out.println(stockNumber + "attempign to buy x shares");
                double tempStockNum = stockNumber;

                UserAccount.getInstance().buyStock(symbolString, stockNumber, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        System.out.println("UPDATEEEDDDDD NOOOOW");
                        Toast.makeText(getApplicationContext(), "Bought " + symbolString + ": " + tempStockNum + " shares", Toast.LENGTH_SHORT).show();
                        UIupdate();
                    }

                    @Override
                    public void onTaskFailed() {
                        System.out.println("Failed to purcahse: Not enough funds " + symbolString);
                    }
                });

            }
        });

        // refreash every 2 seconds
        update(2);
    }

    public void update(int seconds){
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            mTask = new currentPriceTask();
                            mTask.execute((Void) null);
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, seconds*1000);  // interval of one minute

    }

    public void UIupdate(){
        fundsLabel.setText("Funds: " + formatter.format(UserAccount.portflio.cashToTrade));
        fund.setText("Funds: " + formatter.format(UserAccount.portflio.cashToTrade));
        symbol.setText("symbol: " + symbolString);
        trade.setText("Numbers to trade");
        currentPrice.setText("price: loading");
        total.setText("total use: 0.00");
        stockToBuy.setText("0");
        updateTime.setText("update time : loading");
    }

    public class currentPriceTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            UserAccount.getInstance().getSingleStock(symbolString, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted() {
                    stock = UserAccount.latestStockLoaded;
                    price = stock.oneDayCharts.get(stock.oneDayCharts.size()-1).getAverage();
                    currentPrice.setText("current price: " + price);
                    updateTime.setText("update time : " + stock.quote.getLatestTime());
                }
                @Override
                public void onTaskFailed() {
                    System.out.println("fail to load the stock");
                    symbol.setText("Failed to load stock: " + symbolString);
                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
