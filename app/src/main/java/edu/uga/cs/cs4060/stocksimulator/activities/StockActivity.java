package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.Retrofit.Stock;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneDayChart;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneMonthChart;
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
    private TextView fundsLabel;
    private GraphView graph;
    private Stock stock;
    private Button buyStock;
    private Button sellStock;
    private Button oneDay;
    private Button oneMonth;
    private Button oneYear;
    private Button fiveYear;
    private String symbolString;

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
        buyStock = findViewById(R.id.buyButton);
        sellStock = findViewById(R.id.sellButton);
        percentToday = findViewById(R.id.percentToday);
        livePrice = findViewById(R.id.livePrice);
        highLow = findViewById(R.id.highlow);
        sharesOwned = findViewById(R.id.sharesOwned);
        costBasis = findViewById(R.id.costBasis);
        returnText = findViewById(R.id.returnLabel);
        fundsLabel = findViewById(R.id.fundsLabel);
        oneDay = findViewById(R.id.oneDay);
        oneMonth = findViewById(R.id.oneMonth);
        oneYear = findViewById(R.id.oneYear);
        fiveYear = findViewById(R.id.fiveYear);

        oneDay.setOnClickListener(new ButtonClickListener());
        oneMonth.setOnClickListener(new ButtonClickListener());
        oneYear.setOnClickListener(new ButtonClickListener());
        fiveYear.setOnClickListener(new ButtonClickListener());

        // sell stock button
        sellStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount.getInstance().sellStock(symbolString, 1, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        System.out.println("FINISHED SELLING!");
                        Toast.makeText(getApplicationContext(), "Sold: 1 share" + symbolString + ". add data", Toast.LENGTH_SHORT).show();
                        refresh();
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

                UserAccount.getInstance().buyStock(symbolString, 1, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        System.out.println("UPDATEEEDDDDD NOOOOW");
                        Toast.makeText(getApplicationContext(), "Bought " + symbolString + " 1 shares", Toast.LENGTH_SHORT).show();
                        refresh();
                    }

                    @Override
                    public void onTaskFailed() {
                        System.out.println("Failed to purcahse: Not enough funds " + symbolString);
                    }
                });

            }
        });

        Intent intent = getIntent();
        symbolString = intent.getExtras().getString("symbol");
        refresh();
    }

    public void refresh() {
        graph.getViewport().setMaxX(400);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        //retrieval information of the stock
        UserAccount.getInstance().getSingleStock(symbolString, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                stock = UserAccount.latestStockLoaded;
                System.out.print("got a stock: " + stock.quote.getCompanyName() + "\t");
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                DecimalFormat df = new DecimalFormat("%.###");
                symbol.setText(stock.quote.getCompanyName());
                //change color based on red or green now
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                int x = 0;
                switch (UserAccount.range) {
                    case "1d":
                        for (OneDayChart m : stock.oneDayCharts) {
                            x++;
                            if (m.getAverage() > 0) {
                                // add new data point
                                DataPoint point = new DataPoint(x, m.getAverage());
                                series.appendData(point, false, 2147000000, false);
                            }
                        }
                        break;
                    case "1m":
                        for (OneMonthChart m : stock.oneMonthCharts) {
                            x++;
                            if (m.getClose() > 0) {
                                // add new data point
                                DataPoint point = new DataPoint(x, m.getClose());
                                series.appendData(point, false, 2147000000, false);
                            }
                        }
                        break;
                }

                // show the graph
                graph.addSeries(series);


                //Add the busniess info f the stock
                Portflio userPort = UserAccount.portflio;
                percentToday.setText("Percent Today: " + stock.quote.getChangePercent());
                livePrice.setText("Live Price: " + stock.quote.getLatestPrice());
                highLow.setText("52 Week High/Low: " + stock.quote.getWeek52High() + "  |   " + stock.quote.getWeek52Low());
                fundsLabel.setText("Funds: " + userPort.cashToTrade);
                if (userPort.getHolding(symbolString) != null) {
                    sharesOwned.setText("Shares Owned: " + userPort.getHolding(symbolString).shares);
                    costBasis.setText("Invested: " + userPort.getTotalInvested(symbolString));
                    returnText.setText("Return %: " + userPort.getHolding(symbolString).percentChange);
                    sharesOwned.setVisibility(View.VISIBLE);
                    costBasis.setVisibility(View.VISIBLE);
                    returnText.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onTaskFailed() {
                System.out.println("fail to load the stock");
                symbol.setText("Failed to load stock: " + symbolString);
            }
        });
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
            }
            refresh();
        }
    }

}
