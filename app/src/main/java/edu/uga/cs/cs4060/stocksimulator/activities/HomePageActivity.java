package edu.uga.cs.cs4060.stocksimulator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.NumberFormat;
import java.util.ArrayList;

import edu.uga.cs.cs4060.stocksimulator.UIFunctions.ShowPrograssingBar;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Quote;
import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class HomePageActivity extends BasicActivity {
    private TextView welcome, value, cash, info;
    private ListView stockList;
    private EditText symbolInput;
    private Button checkStock, buyStock, sellStock, refresh, accountButton;
    private UserAccount account;
    boolean loaded = false;

    ArrayList<String> possibleSymbols = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Initalize and Declare views
        welcome = findViewById(R.id.userwelcome);
        value = findViewById(R.id.value);
        cash = findViewById(R.id.funds);
        checkStock = findViewById(R.id.loadStock);
        stockList = findViewById(R.id.stockList);
        info =findViewById(R.id.stockinfo);
        symbolInput = findViewById(R.id.symbolInput);
        buyStock = findViewById(R.id.buyButton);
        sellStock = findViewById(R.id.sellButton);
        refresh = findViewById(R.id.refresh);
        accountButton = findViewById(R.id.account);

        //User account made
        account = UserAccount.getInstance();
        account.load(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                loaded = true;
                loadUI();
            }

            @Override
            public void onTaskFailed() {

            }
        });

        possibleSymbols.add("AAPL");
        possibleSymbols.add("VGT");
        possibleSymbols.add("TSLA");
        possibleSymbols.add("AMZN");
        possibleSymbols.add("NOC");
        possibleSymbols.add("FB");
        double buyShares = 1.0;

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.update(new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        loadUI();
                        System.out.println("Loaded update db api");
                    }

                    @Override
                    public void onTaskFailed() {
                        System.out.println("Failed to update db api");

                    }
                });
            }
        });

        buyStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get symbol to buy!
                int index = (int)(Math.random() * possibleSymbols.size());
                UserAccount.getInstance().buyStock(possibleSymbols.get(index), buyShares, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        loadUI();

                    }

                    @Override
                    public void onTaskFailed() {
                        loadUI();
                        System.out.println("Failed to purcahse: Not enough funds " + possibleSymbols.get(index) );
                    }
                });

            }
        });


        sellStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int)(Math.random() * possibleSymbols.size());
                UserAccount.getInstance().sellStock(possibleSymbols.get(index), 1, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        loadUI();
                    }

                    @Override
                    public void onTaskFailed() {

                    }
                });
            }
        });
        checkStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeys();
                account.getSingleStock(symbolInput.getText().toString(), new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        updateSingleStockInfo();
                    }

                    @Override
                    public void onTaskFailed() {

                    }
                });
            }
        });




    }

    //Updates UI with current stock loaded from the API
    private void updateSingleStockInfo(){
        Quote stock = UserAccount.latestStockLoaded;
       info.setText(stock.getSymbol() + " | Live Price: $" + stock.getLatestPrice() + " | Percent Change %" + stock.getChangePercent());
    }

    //Hide keyboard
    private void hideKeys(){
        InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(RegisterActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(symbolInput.getWindowToken(), 0);
    }


    //Update UI wth important stats from User Acccount
    private void loadUI(){
        // initialize elements

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        welcome.setText("welcome " + UserAccount.getInstance().user.getEmail() + ".");
        value.setText("Value: " + formatter.format( account.portflio.getValue()) +  "   PERCENT: " + account.portflio.getTotalPercent()) ;
        cash.setText("Funds: " + formatter.format( account.portflio.cashToTrade));

        ArrayList<String> stocks = account.portflio.getSymbolList();
        ArrayAdapter<String> stockAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        stockList.setAdapter(stockAdapter);


    }

}
