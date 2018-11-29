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
    private Button refreshButton;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private DecimalFormat df = new DecimalFormat("%.##");
    private List<Holding> stocks;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private RVAdapter adapter;

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
        refreshButton = (Button) findViewById(R.id.refreshButton2);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount.getInstance().update(new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        refresh();
                    }

                    @Override
                    public void onTaskFailed() {

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

