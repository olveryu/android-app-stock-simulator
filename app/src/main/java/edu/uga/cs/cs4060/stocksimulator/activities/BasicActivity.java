package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.lang.reflect.Method;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.userSessions;

public class BasicActivity extends AppCompatActivity {
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(userSessions.userIsLogin()){
            MenuItem item = menu.findItem(R.id.logIn);
            item.setVisible(false);
        }
        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
                Log.e("TAG", "onMenuOpened", e);
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
    public void signIn(){
        intent = new Intent(this, LoginActivity.class);
    }

    public void signOut(){
        userSessions.signOut();
        intent = new Intent(this, SplashActivity.class);
    }

    public void home(){
        if(!userSessions.userIsLogin()){
            intent = new Intent(this, SplashActivity.class);
        }else{
            intent = new Intent(this, UserActivity.class);
        }
    }

}
