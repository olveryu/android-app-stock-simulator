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

    private TextView totalValue;
    private TextView daySummary;
    private TextView totalGainLost;
    private TextView totalCostBasisView;
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
        UserAccount.range = "1d";
        refresh();


        //refresh every 1 minute second
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(60000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }

    //LOADED MUST BE TRUE
    public void initUI() {
        System.out.println("INIT UI");
        totalValue = (TextView) findViewById(R.id.valueTextView);
        daySummary = (TextView) findViewById(R.id.dailyTextView);
        totalGainLost = (TextView) findViewById(R.id.totalPercentTextView);
        totalCostBasisView = (TextView) findViewById(R.id.totalCostTextView);
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
                totalGainLost.setText("Total Return: " + df.format(UserAccount.portflio.getTotalPercent()) + "%");
                totalCostBasisView.setText("Total Invested: " + UserAccount.portflio.getCostBasis());

        stocks = UserAccount.portflio.getArrayListHolding();
        adapter = new RVAdapter(UserAccount.portflio, stocks);
        rv.setAdapter(adapter);



    }

}

