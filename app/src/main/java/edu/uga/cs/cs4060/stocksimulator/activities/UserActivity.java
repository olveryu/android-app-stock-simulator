package edu.uga.cs.cs4060.stocksimulator.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Minute;
import edu.uga.cs.cs4060.stocksimulator.User.Holding;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.Portflio;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class UserActivity extends BasicActivity {

    private TextView totalValue, daySummary, totalGainLost, totalCostBasisView;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private DecimalFormat df = new DecimalFormat("%.##");
    private List<Holding> stocks;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private RVAdapter adapter;

    private Button buyStock, refreshButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // get drawer layout
        drawerNavigation();
        initUI();
        refresh();
    }

    //LOADED MUST BE TRUE
    public void initUI() {
        System.out.println("INIT UI");
        totalValue = (TextView) findViewById(R.id.valueTextView);
        daySummary = (TextView) findViewById(R.id.dailyTextView);
        totalGainLost = (TextView) findViewById(R.id.totalPercentTextView);
        totalCostBasisView = (TextView) findViewById(R.id.totalCostTextView);

        buyStock = findViewById(R.id.buyButton);
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
                UserAccount.getInstance().update(new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        System.out.println("UPdated man!");
                        refresh();
                    }

                    @Override
                    public void onTaskFailed() {

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
                        UserAccount.getInstance().update(new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted() {
                                System.out.println("UPdated man!");
                                refresh();
                            }

                            @Override
                            public void onTaskFailed() {

                            }
                        });

                    }

                    @Override
                    public void onTaskFailed() {
                        refresh();
                        System.out.println("Failed to purcahse: Not enough funds " + possibleSymbols.get(index) );
                    }
                });

            }
        });



        stocks = UserAccount.portflio.getArrayListHolding();

        rv = findViewById(R.id.recView);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this.getBaseContext());
        rv.setLayoutManager(llm);

        adapter = new RVAdapter(UserAccount.portflio, stocks);
        rv.setAdapter(adapter);

    }

    public void refresh() {

        System.out.println("REFRESH IF");

        System.out.println("REFRESH");
        if (UserAccount.portflio.getDayAmountChange() < 0.00) {
            daySummary.setTextColor(Color.RED);
        } else {
            daySummary.setTextColor(Color.parseColor("#508c00"));
        }
        totalValue.setText(formatter.format(UserAccount.portflio.getValue()));
        daySummary.setText(UserAccount.portflio.getDaySummary());
        totalGainLost.setText("Total Gain/Lost: " + df.format(UserAccount.portflio.getTotalPercent()) + "%");
        totalCostBasisView.setText("Total Cost Basis: " + UserAccount.portflio.getCostBasis());

    }

}

class RVAdapter extends RecyclerView.Adapter<RVAdapter.StockViewHolder> implements  View.OnClickListener {

    List<Holding> stocks;
    Portflio portflio;

    RVAdapter(Portflio portflio, List<Holding> stocks) {
        this.stocks = stocks;
        this.portflio = portflio;

    }



    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        StockViewHolder pvh = new StockViewHolder(portflio, v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder stockViewHolder, int i) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormat df = new DecimalFormat("%.###");
        System.out.println("UPDATING UI NOW");
        stockViewHolder.symbol.setText((stocks.get(i).symbol));
        stockViewHolder.totalPercent.setText("Gain/Loss: " + df.format(stocks.get(i).percentChange));
        stockViewHolder.totalCostBasis.setText("Cost Basis: " + formatter.format(stocks.get(i).costBasis));
        stockViewHolder.dayPercent.setText("Day Change: " + df.format(stocks.get(i).dayPercentChange));
        stockViewHolder.dayAmount.setText("Day Change: " + formatter.format(stocks.get(i).dayAmountChange));
        stockViewHolder.shares.setText("Shares Owned: " + (stocks.get(i).shares));
        stockViewHolder.livePrice.setText( df.format(stocks.get(i).dayPercentChange));

        stockViewHolder.timeUpdate.setText("Last updated: " + stocks.get(i).timeUpdate);


        //change color based on red or green now

        if(stocks.get(i).dayPercentChange >= 0){
            stockViewHolder.livePrice.setBackgroundColor(Color.argb(255, 69, 244, 66  ));
        }else{
            stockViewHolder.livePrice.setBackgroundColor(Color.RED);

        }

        //

        //we need to clean graph minutes first









        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        int x = 0;
        for(Minute m : stocks.get(i).dayChart){
            x++;
            if(m.getAverage() > 0){
                // add new data point
                DataPoint point = new DataPoint(x, m.getAverage());
                series.appendData(point, false, 2147000000, false);
            }
        }


        stockViewHolder.graph.addSeries(series);
        stockViewHolder.graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        stockViewHolder.graph.getGridLabelRenderer().setVerticalLabelsVisible(false);//
        stockViewHolder.graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);//
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        System.out.println("CLICKKKKED ");
    }


    public static class StockViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView symbol, totalPercent, totalCostBasis, dayPercent, dayAmount, shares, livePrice, timeUpdate;
        GraphView graph;
        StockViewHolder(Portflio p, View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            symbol = (TextView) itemView.findViewById(R.id.symbol_label);
            totalPercent = (TextView) itemView.findViewById(R.id.total_percent_label);
            totalCostBasis = (TextView) itemView.findViewById(R.id.total_cost_basis_label);
            dayPercent = (TextView) itemView.findViewById(R.id.day_change_percent_label);
            dayAmount = (TextView) itemView.findViewById(R.id.day_change_dollar_label);
            shares = (TextView) itemView.findViewById(R.id.shares_label);
            livePrice = (TextView) itemView.findViewById(R.id.latestLivePrice);
            timeUpdate = (TextView) itemView.findViewById(R.id.lastUpdate);

            graph = (GraphView) itemView.findViewById(R.id.graph);





        }


    }
}
