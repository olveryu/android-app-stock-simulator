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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.User.Holding;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.Portflio;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class UserActivity extends BasicActivity {

    private TextView  totalValue, daySummary, totalGainLost, totalCostBasisView;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private DecimalFormat df = new DecimalFormat("%.##");
    private UserAccount account;
    private Portflio portflio;
    private List<Holding> stocks;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private RVAdapter adapter;
    public static boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        account = UserAccount.getInstance();
        initUI();
        refresh();
    }

    //LOADED MUST BE TRUE
    public void initUI(){
        System.out.println("INIT UI");
        totalValue = (TextView)findViewById(R.id.valueTextView);
        daySummary = (TextView)findViewById(R.id.dailyTextView);
        totalGainLost = (TextView)findViewById(R.id.totalPercentTextView);
        totalCostBasisView = (TextView)findViewById(R.id.totalCostTextView);


        portflio = UserAccount.getInstance().portflio;
        stocks = UserAccount.getInstance().portflio.getArrayListHolding();

        rv = findViewById(R.id.recView);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this.getBaseContext());
        rv.setLayoutManager(llm);

        adapter = new RVAdapter(portflio, stocks);
        rv.setAdapter(adapter);

    }
    public void refresh(){
        System.out.println("REFRESH IF");

        if(loaded) {
            System.out.println("REFRESH");
            if (portflio.getDayAmountChange() < 0.00) {
                daySummary.setTextColor(Color.RED);
            } else {
                daySummary.setTextColor(Color.parseColor("#508c00"));
            }
            totalValue.setText(formatter.format(portflio.getValue()));
            daySummary.setText(portflio.getDaySummary());
            totalGainLost.setText("Total Gain/Lost: " + df.format(portflio.getTotalPercent()) + "%");
            totalCostBasisView.setText("Total Cost Basis: " + portflio.getCostBasis());
        }

    }

}

class Data{
    String title;
    public Data(String t){
        title = t;
    }
}

class RVAdapter extends RecyclerView.Adapter<RVAdapter.StockViewHolder>{

    List<Holding> stocks;
    Portflio portflio;
    RVAdapter(Portflio portflio, List<Holding> stocks){
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

        stockViewHolder.symbol.setText((stocks.get(i).symbol));
        stockViewHolder.totalPercent.setText("Gain/Loss: "+ df.format(stocks.get(i).percentChange));
        stockViewHolder.totalCostBasis.setText("Cost Basis: " + formatter.format(stocks.get(i).costBasis));
        stockViewHolder.dayPercent.setText("Day Change: " + df.format(stocks.get(i).dayPercentChange));
        stockViewHolder.dayAmount.setText("Day Change: " + formatter.format(stocks.get(i).dayAmountChange));
        stockViewHolder.shares.setText("Shares Owned: " + (stocks.get(i).shares));
        stockViewHolder.livePrice.setText("Live Price: " + formatter.format(stocks.get(i).latestLivePrice));
        stockViewHolder.timeUpdate.setText("Last updated: " + stocks.get(i).timeUpdate);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView symbol, totalPercent, totalCostBasis, dayPercent, dayAmount, shares, livePrice, timeUpdate;

        StockViewHolder(Portflio p, View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            symbol = (TextView)itemView.findViewById(R.id.symbol_label);
            totalPercent = (TextView)itemView.findViewById(R.id.total_percent_label);
            totalCostBasis = (TextView)itemView.findViewById(R.id.total_cost_basis_label);
            dayPercent = (TextView)itemView.findViewById(R.id.day_change_percent_label);
            dayAmount = (TextView)itemView.findViewById(R.id.day_change_dollar_label);
            shares = (TextView)itemView.findViewById(R.id.shares_label);
            livePrice = (TextView)itemView.findViewById(R.id.latestLivePrice);
            timeUpdate = (TextView)itemView.findViewById(R.id.lastUpdate);


            GraphView graph = (GraphView)itemView.findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 5),
                    new DataPoint(1, 4),
                    new DataPoint(2, 3),
                    new DataPoint(3, 3.2)

            });


            graph.addSeries(series);
//            graph.lineC

            graph.getViewport().setMinY(3);
            graph.getViewport().setMaxY(5);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getGridLabelRenderer().setHumanRounding(false);
        }
    }
}
