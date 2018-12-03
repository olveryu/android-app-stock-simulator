package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class ScoreActivity extends BasicActivity {

    private ListView id,email, value;
    private String[] idArray, emailArray, valueArray;
    private ArrayAdapter<String> adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        //set navigation bar
        drawerNavigation();
        //set context
        context = this;

        //initialize reference
        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
        value = findViewById(R.id.value);


        // get highest score
        UserAccount.getInstance().loadHighscores(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                //initialize array
                int length = UserAccount.highscoresList.size();
                idArray = new String[length];
                emailArray = new String[length];
                valueArray = new String[length];

                // assign values
                for(int i = 0; i < length; i++){
                    idArray[i] = String.valueOf(i);
                    emailArray[i] = UserAccount.highscoresList.get(i).email;
                    valueArray[i] = formatter.format(UserAccount.highscoresList.get(i).value);
                }
                //add adapter
                addAdapter(id,idArray);
                addAdapter(email,emailArray);
                addAdapter(value,valueArray);
            }
            @Override
            public void onTaskFailed() {

            }
        });
    }

    public void addAdapter(ListView listView, String[] array){
        adapter = new ArrayAdapter<String>(context,
                R.layout.activity_listview, array);
        listView.setAdapter(adapter);
    }
}
