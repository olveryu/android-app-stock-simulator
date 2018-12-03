package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.ExecutionException;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.Retrofit.Stock;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.FiveYearChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneDayChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneMonthChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneYearChart;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.Portflio;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class StockActivity extends BasicActivity {
    private TextView symbol;
    private TextView percentToday;
    private TextView livePrice;
    private TextView highLow;
    private TextView sharesOwned;
    private TextView costBasis;
    private TextView returnText;
    private GraphView graph;
    private Stock stock;
    private Button trade;
    private Button oneDay;
    private Button oneMonth;
    private Button oneYear;
    private Button fiveYear;
    private String symbolString;
    private Intent intent;

    public StockActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        // get drawer layout
        drawerNavigation();

        // reference element
        symbol = (TextView) findViewById(R.id.symbol_label);
        graph = (GraphView) findViewById(R.id.graph);
        percentToday = findViewById(R.id.percentToday);
        livePrice = findViewById(R.id.livePrice);
        highLow = findViewById(R.id.highlow);
        sharesOwned = findViewById(R.id.sharesOwned);
        costBasis = findViewById(R.id.costBasis);
        returnText = findViewById(R.id.returnLabel);
        oneDay = findViewById(R.id.oneDay);
        oneMonth = findViewById(R.id.oneMonth);
        oneYear = findViewById(R.id.oneYear);
        fiveYear = findViewById(R.id.fiveYear);
        trade = findViewById(R.id.tradeButton);

        oneDay.setOnClickListener(new ButtonClickListener());
        oneMonth.setOnClickListener(new ButtonClickListener());
        oneYear.setOnClickListener(new ButtonClickListener());
        fiveYear.setOnClickListener(new ButtonClickListener());
        trade.setOnClickListener(new ButtonClickListener());

        // set graph attribute
        graph.getViewport().setMaxX(400);
        // digits
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMinimumIntegerDigits(2);
        // label renderer
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setTextSize(35f);
        graph.getGridLabelRenderer().setNumVerticalLabels(10);
        graph.getGridLabelRenderer().setNumHorizontalLabels(10000);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setGridColor(Color.BLACK);

        //grid label
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " $";
                }
            }
        });

        // get intent from previous activity
        intent = getIntent();
        symbolString = intent.getExtras().getString("symbol");

        UserAccount.getInstance().getSingleStock(symbolString, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                refresh();

            }

            @Override
            public void onTaskFailed() {

            }
        });
    }

    public void refresh() {
        graph.removeAllSeries();
        //retrieval information of the stock
        switch (UserAccount.range){
            case "1d":
                oneDay();
                break;
            case "1m":
                oneMonth();
                break;
            case "1y":
                oneYear();
                break;
            case "5y":
                fiveYear();
                break;
        }
    }

    public void oneDay(){
        UserAccount.getInstance().getDayData(symbolString, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                stock = UserAccount.latestStockLoaded;
                symbol.setText(stock.quote.getCompanyName());
                //change color based on red or green now
                graph();
                Information();
            }
            @Override
            public void onTaskFailed() {
                System.out.println("fail to load the stock");
                symbol.setText("Failed to load stock: " + symbolString);
            }
        });
    }

    public void oneMonth(){
        UserAccount.getInstance().getMonthData(symbolString, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                stock = UserAccount.latestStockLoaded;
                symbol.setText(stock.quote.getCompanyName());
                //change color based on red or green now
                graph();
                Information();
            }
            @Override
            public void onTaskFailed() {
                System.out.println("fail to load the stock");
                symbol.setText("Failed to load stock: " + symbolString);
            }
        });
    }

    public void oneYear(){
        UserAccount.getInstance().getYearData(symbolString, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                stock = UserAccount.latestStockLoaded;
                symbol.setText(stock.quote.getCompanyName());
                //change color based on red or green now
                graph();
                Information();
            }
            @Override
            public void onTaskFailed() {
                System.out.println("fail to load the stock");
                symbol.setText("Failed to load stock: " + symbolString);
            }
        });
    }

    public void fiveYear(){
        UserAccount.getInstance().getFiveYearData(symbolString, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                stock = UserAccount.latestStockLoaded;
                symbol.setText(stock.quote.getCompanyName());
                //change color based on red or green now
                graph();
                Information();
            }
            @Override
            public void onTaskFailed() {
                System.out.println("fail to load the stock");
                symbol.setText("Failed to load stock: " + symbolString);
            }
        });
    }

    /**
     * draw graph base on precent change over time
     */
    public void graph(){

                try {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    series.setAnimated(true);
                    int x = 0;
                    clearColors();
                    switch (UserAccount.range) {

                        case "1d":
                            oneDay.setBackgroundColor(Color.GREEN);
                            for (OneDayChart m : stock.oneDayCharts) {
                                x++;
                                if (m.getAverage() > 0) {
                                    // add new data point
                                    DataPoint point = new DataPoint(x, m.getAverage());
                                    series.appendData(point, false, Integer.MAX_VALUE, false);
                                }
                            }
                            if (stock.oneDayCharts.get(0).getClose() - stock.oneDayCharts.get(stock.oneDayCharts.size() - 1).getClose() >= 0) {
                                series.setColor(Color.argb(255, 69, 244, 66));
                            } else {
                                series.setColor(Color.RED);
                            }
                            break;
                        case "1m":
                            oneMonth.setBackgroundColor(Color.GREEN);

                            if (stock.oneMonthCharts == null) {
                                break;
                            }
                            for (OneMonthChart m : stock.oneMonthCharts) {
                                x++;
                                if (m.getClose() > 0) {
                                    // add new data point
                                    DataPoint point = new DataPoint(x, m.getClose());
                                    series.appendData(point, false, Integer.MAX_VALUE, false);
                                }
                            }
                            if (stock.oneMonthCharts.get(0).getClose() - stock.oneMonthCharts.get(stock.oneMonthCharts.size() - 1).getClose() >= 0) {
                                series.setColor(Color.argb(255, 69, 244, 66));
                            } else {
                                series.setColor(Color.RED);
                            }
                            break;
                        case "1y":
                            oneYear.setBackgroundColor(Color.GREEN);

                            if (stock.oneYearCharts == null) {
                                break;
                            }
                            for (OneYearChart m : stock.oneYearCharts) {
                                x++;
                                if (m.getClose() > 0) {
                                    // add new data point
                                    DataPoint point = new DataPoint(x, m.getClose());
                                    series.appendData(point, false, Integer.MAX_VALUE, false);
                                }
                            }
                            if (stock.oneYearCharts.get(0).getClose() - stock.oneYearCharts.get(stock.oneYearCharts.size() - 1).getClose() >= 0) {
                                series.setColor(Color.argb(255, 69, 244, 66));
                            } else {
                                series.setColor(Color.RED);
                            }
                            break;
                        case "5y":
                            fiveYear.setBackgroundColor(Color.GREEN);

                            if (stock.fiveYearCharts == null) {
                                break;
                            }
                            for (FiveYearChart m : stock.fiveYearCharts) {
                                x++;
                                if (m.getClose() > 0) {
                                    // add new data point
                                    DataPoint point = new DataPoint(x, m.getClose());
                                    series.appendData(point, false, Integer.MAX_VALUE, false);
                                }
                            }
                            if (stock.fiveYearCharts.get(0).getClose() - stock.fiveYearCharts.get(stock.fiveYearCharts.size() - 1).getClose() >= +0) {
                                series.setColor(Color.argb(255, 69, 244, 66));
                            } else {
                                series.setColor(Color.RED);
                            }
                            break;

                    }
                    // show the graph
                    graph.addSeries(series);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
    }

    private void clearColors(){
        oneDay.setBackgroundColor(Color.WHITE);
        oneMonth.setBackgroundColor(Color.WHITE);
        oneYear.setBackgroundColor(Color.WHITE);
        fiveYear.setBackgroundColor(Color.WHITE);

    }


    public void Information(){
        //Add the busniess info f the stock
        Portflio userPort = UserAccount.portflio;
        DecimalFormat df = new DecimalFormat("%.##");


        // set information text
        percentToday.setText("Percent Today: " + df.format(stock.quote.getChangePercent()));
        livePrice.setText("Live Price: " + stock.quote.getLatestPrice());
        highLow.setText("52 Week High/Low: " + stock.quote.getWeek52High() + "  |   " + stock.quote.getWeek52Low());
        if (userPort.getHolding(symbolString) != null) {
            System.out.println("USER OWNS< add inco");
            sharesOwned.setText("Shares Owned: " + userPort.getHolding(symbolString).shares);
            costBasis.setText("Invested: " + formatter.format(userPort.getTotalInvested(symbolString)));
            returnText.setText("Return : " + formatter.format(userPort.calculateValueReturn(symbolString)) + " ( "+ df.format(userPort.getHolding(symbolString).percentChange)+ " )");
            sharesOwned.setVisibility(View.VISIBLE);
            costBasis.setVisibility(View.VISIBLE);
            System.out.println("MAKE VISIBLE");
            returnText.setVisibility(View.VISIBLE);
        }
    }



    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.oneDay:
                    UserAccount.range = "1d";
                    break;
                case R.id.oneMonth:
                    UserAccount.range = "1m";
                    break;
                case R.id.oneYear:
                    UserAccount.range = "1y";
                    break;
                case R.id.fiveYear:
                    UserAccount.range = "5y";
                    break;
                case R.id.tradeButton:
                    Intent intent = new Intent(view.getContext(), TradeActivity.class);
                    intent.putExtra("symbol", symbolString);
                    view.getContext().startActivity(intent);
                    break;
            }
            refresh();
        }
    }

}
