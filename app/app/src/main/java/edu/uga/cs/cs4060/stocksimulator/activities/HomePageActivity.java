package edu.uga.cs.cs4060.stocksimulator.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import edu.uga.cs.cs4060.stocksimulator.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.Quote;
import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.UserAccount;
import edu.uga.cs.cs4060.stocksimulator.userSessions;

public class HomePageActivity extends BasicActivity {
    private TextView welcome, value, cash, info;
    private Button checkStock;
    private ListView stockList;
    private EditText symbolInput;
    private UserAccount account;
    boolean loaded = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);


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


        //Initalize and Declare views
        welcome = findViewById(R.id.userwelcome);
        value = findViewById(R.id.value);
        cash = findViewById(R.id.funds);
        checkStock = findViewById(R.id.loadStock);
        stockList = findViewById(R.id.stockList);
        info =findViewById(R.id.stockinfo);
        symbolInput = findViewById(R.id.symbolInput);

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
        value.setText("Value: " + formatter.format( account.portflio.getValue()));
        cash.setText("Funds: " + formatter.format( account.portflio.cashToTrade));

        ArrayList<String> stocks = account.portflio.getSymbolList();
        ArrayAdapter<String> stockAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        stockList.setAdapter(stockAdapter);


    }

}
