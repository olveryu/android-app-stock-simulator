package edu.uga.cs.cs4060.stocksimulator.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.UIFunctions.RVAdapter;
import edu.uga.cs.cs4060.stocksimulator.User.Holding;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class UserActivity extends BasicActivity {

    private TextView totalValue, daySummary, totalGainLost, totalCostBasisView, fundsText;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private DecimalFormat df = new DecimalFormat("%.##");
    private List<Holding> stocks;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private RVAdapter adapter;

    private Button buyStock, sellStock, refreshButton;

    private boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // get drawer layout
        drawerNavigation();
        initUI();
        refresh();

//        UserAccount.getInstance().load(new OnTaskCompleted() {
//            @Override
//            public void onTaskCompleted() {
//                refresh();
//                loaded = true;
//            }
//
//            @Override
//            public void onTaskFailed() {
//
//            }
//        });



    }

    //LOADED MUST BE TRUE
    public void initUI() {
        System.out.println("INIT UI");
        totalValue = (TextView) findViewById(R.id.valueTextView);
        daySummary = (TextView) findViewById(R.id.dailyTextView);
        totalGainLost = (TextView) findViewById(R.id.totalPercentTextView);
        totalCostBasisView = (TextView) findViewById(R.id.totalCostTextView);
        fundsText = (TextView) findViewById(R.id.fundsText);

        buyStock = findViewById(R.id.buyButton);
        sellStock = findViewById(R.id.sellButton);

        refreshButton = findViewById(R.id.refreshButton);


        ArrayList<String> possibleSymbols = new ArrayList<>();

        possibleSymbols.add("AAPL");
        possibleSymbols.add("TSLA");
        possibleSymbols.add("AMZN");
        possibleSymbols.add("NOC");
        possibleSymbols.add("FB");


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("refreshing now now");
               refresh();
            }
        });


        sellStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount.getInstance().sellStock("AAPL", 1, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        System.out.println("FINISHED SELLING!");
                        Toast.makeText(getApplicationContext(), "Sold: 1 share AAPL ... add data" , Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onTaskFailed() {
                        System.out.println("FAiled to sell");
                    }
                });
            }
        });

        buyStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Buying now");

                //get symbol to buy!
                int index = (int)(Math.random() * possibleSymbols.size());
                UserAccount.getInstance().buyStock(possibleSymbols.get(index), 1, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                       System.out.println("UPDATEEEDDDDD NOOOOW");
                       Toast.makeText(getApplicationContext(), "Bought " + possibleSymbols.get(index) + " 1 shares" , Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onTaskFailed() {
                        refresh();
                        System.out.println("Failed to purcahse: Not enough funds " + possibleSymbols.get(index) );
                    }
                });

            }
        });



        rv = findViewById(R.id.recView);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this.getBaseContext());
        rv.setLayoutManager(llm);


    }

    public void refresh() {
                if (UserAccount.portflio.getDayAmountChange() < 0.00) {
                    daySummary.setTextColor(Color.RED);
                } else {
                    daySummary.setTextColor(Color.parseColor("#508c00"));
                }
                totalValue.setText(formatter.format(UserAccount.portflio.getValue()));
                daySummary.setText(UserAccount.portflio.getDaySummary());
                fundsText.setText("Funds: " + formatter.format(UserAccount.portflio.cashToTrade));
                totalGainLost.setText("Total Return: " + df.format(UserAccount.portflio.getTotalPercent()) + "%");
                totalCostBasisView.setText("Total Invested: " + UserAccount.portflio.getCostBasis());

        stocks = UserAccount.portflio.getArrayListHolding();
        adapter = new RVAdapter(UserAccount.portflio, stocks);
        rv.setAdapter(adapter);



    }

}

