package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.Retrofit.Stock;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.Minute;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.Portflio;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class StockActivity extends BasicActivity {
    private TextView symbol;
    private GraphView graph;
    private Stock stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        symbol = (TextView)findViewById(R.id.symbol_label);
        graph = (GraphView)findViewById(R.id.graph);

        // get message from previous activity
        Intent intent = getIntent();
        String symbolString = intent.getExtras().getString("symbol");
        //retrieval information of the stock
        UserAccount.getInstance().getSingleStock(symbolString, new OnTaskCompleted(){

            @Override
            public void onTaskCompleted() {
                stock = UserAccount.latestStockLoaded;
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                DecimalFormat df = new DecimalFormat("%.###");
                symbol.setText(symbolString);
                //change color based on red or green now
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                int x = 0;
                for(Minute m : stock.minutes){
                    x++;
                    if(m.getAverage() > 0){
                        // add new data point
                        DataPoint point = new DataPoint(x, m.getAverage());
                        series.appendData(point, false, 2147000000, false);
                    }
                }

                // also change color based on red or green for the graph
                graph.addSeries(series);
                //graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );
                //graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
                //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                //graph.getGridLabelRenderer().draw(new Canvas() );
            }

            @Override
            public void onTaskFailed() {
                System.out.println("fail to load the stock");
            }
        });
    }
}
