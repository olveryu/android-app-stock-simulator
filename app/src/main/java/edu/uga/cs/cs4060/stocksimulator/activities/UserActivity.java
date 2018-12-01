package edu.uga.cs.cs4060.stocksimulator.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    public static TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // get drawer layout
        drawerNavigation();
        initUI();
        UserAccount.range = "1d";


        //refresh every 1 minute second
      update(2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUserActivityTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopUserActivityTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopUserActivityTask();
    }

    public void update(int seconds){
        final Handler handler = new Handler();
        Timer timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                           refresh();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, seconds*1000);  // interval of one minute

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
        UserAccount.getInstance().update(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
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

            @Override
            public void onTaskFailed() {
                Toast.makeText(getApplicationContext(), "Failed to refresh...", Toast.LENGTH_SHORT).show();

            }
        });



    }



}

