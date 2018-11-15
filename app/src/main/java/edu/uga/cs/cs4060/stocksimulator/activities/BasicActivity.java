package edu.uga.cs.cs4060.stocksimulator.activities;

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

import java.lang.reflect.Method;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;

public class BasicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Intent intent;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (UserAccount.userIsLogin()) {
            MenuItem login = menu.findItem(R.id.logIn);
            login.setVisible(false);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.SignOutToo:
                signOut();
                startActivity(intent);
                break;
            default:
                return false;
        }
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
        }
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
}
