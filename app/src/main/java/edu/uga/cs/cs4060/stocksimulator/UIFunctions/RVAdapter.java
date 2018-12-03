package edu.uga.cs.cs4060.stocksimulator.UIFunctions;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.StocksInfomations.OneDayChart;
import edu.uga.cs.cs4060.stocksimulator.User.Holding;
import edu.uga.cs.cs4060.stocksimulator.User.Portflio;
import edu.uga.cs.cs4060.stocksimulator.activities.StockActivity;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.StockViewHolder> implements  View.OnClickListener {

    List<Holding> stocks;
    Portflio portflio;

    public RVAdapter(Portflio portflio, List<Holding> stocks) {
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
        stockViewHolder.livePrice.setText( df.format(stocks.get(i).dayPercentChange));
        //change color based on red or green now

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        int x = 0;
        System.out.println("Binding rv view: " + stocks.get(i).oneDayCharts.size());
        for(OneDayChart m : stocks.get(i).oneDayCharts){
            x++;
            if(m.getAverage() != null && m.getAverage() > 0){
                // add new data point
                DataPoint point = new DataPoint(x, m.getAverage());
                series.appendData(point, false, 2147000000, false);
            }
        }

        if(stocks.get(i).dayPercentChange >= 0){
            stockViewHolder.livePrice.setBackgroundColor(Color.argb(255, 69, 244, 66  ));
            series.setColor(Color.argb(255, 69, 244, 66  ));
        }else{
            stockViewHolder.livePrice.setBackgroundColor(Color.RED);
            series.setColor(Color.RED);
        }

        // also change color based on red or green for the graph
        stockViewHolder.graph.addSeries(series);
        stockViewHolder.graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );
        stockViewHolder.graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        stockViewHolder.graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        stockViewHolder.graph.getGridLabelRenderer().draw(new Canvas() );
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
    }


    public static class StockViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView symbol;
        TextView livePrice;
        GraphView graph;
        StockViewHolder(Portflio p, View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            symbol = (TextView) itemView.findViewById(R.id.symbol_label);
            livePrice = (TextView) itemView.findViewById(R.id.latestLivePrice);
            graph = (GraphView) itemView.findViewById(R.id.graph);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("you click " + symbol.getText());
                    Intent intent = new Intent(v.getContext(), StockActivity.class);
                    intent.putExtra("symbol", symbol.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
