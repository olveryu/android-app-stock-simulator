package edu.uga.cs.cs4060.stocksimulator.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.text.NumberFormat;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

import static edu.uga.cs.cs4060.stocksimulator.activities.TradeActivity.priceTimerTask;
import static edu.uga.cs.cs4060.stocksimulator.activities.UserActivity.timerTask;

public class BasicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Intent intent;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private TextView navUsername;
    private View headerView;
    private SearchView searchView;
    public TextView fundsLabel;
    public NumberFormat formatter = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // hide search button before login
        MenuItem search = menu.findItem(R.id.search);
        MenuItem login = menu.findItem(R.id.logIn);
        search.setVisible(false);
        if (UserAccount.userIsLogin()) {
            // show search button and hide login button
            login.setVisible(false);
            search.setVisible(true);

            //set up search functions
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setQueryHint("Search for stock");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(true);


            // set on query text listener

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

                public boolean onQueryTextChange(String newText) {
                    return false;
                }

                public boolean onQueryTextSubmit(String query) {
                    System.out.println("you submit search: " + query);
                    Intent intent = new Intent(searchView.getContext(), StockActivity.class);
                    query = query.toUpperCase();
                    intent.putExtra("symbol", query);
                    searchView.getContext().startActivity(intent);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (NoSuchMethodException e) {
                Log.e("TAG", "onMenuOpened", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public void setupSearch(){

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.SignOutToo:
                signOut();
                break;
            case R.id.homeToo:
                home();
                break;
            default:
                return false;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mDrawerLayout != null && mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (id) {
            case R.id.logIn:
                signIn();
                break;
            case R.id.SignOut:
                signOut();
                break;
            case R.id.home:
                home();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }

    // switch options
    public void signIn() {
        intent = new Intent(this, LoginActivity.class);
    }

    public void signOut() {
        UserAccount.signOut();
        intent = new Intent(this, SplashActivity.class);
    }

    public void home() {
        if (!UserAccount.userIsLogin()) {
            intent = new Intent(this, SplashActivity.class);
        } else {
            intent = new Intent(this, UserActivity.class);
        }
    }

    // initialize drawer navigation
    public void drawerNavigation() {
        // initialize view
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mNavigationView != null) {
            // this line spend me 2 hours!!! OMG
            mNavigationView.bringToFront();
            mNavigationView.setNavigationItemSelectedListener(this);
            headerView = mNavigationView.getHeaderView(0);
            navUsername = (TextView) headerView.findViewById(R.id.user);
            navUsername.setText(UserAccount.user.getEmail());
            fundsLabel = (TextView) headerView.findViewById(R.id.fundsLabel);
            fundsLabel.setText("Funds: " + formatter.format(UserAccount.portflio.cashToTrade));
            mNavigationView.setItemIconTintList(null);
        }
    }

    public void stopUserActivityTask(){
        if(timerTask != null){
            timerTask.cancel();
        }
    }

    public void stopTradeActivityTask(){
        if(priceTimerTask != null){
            priceTimerTask.cancel();
        }

    }
}
