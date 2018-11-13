package edu.uga.cs.cs4060.stocksimulator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// this class to track the current user
public class userSessions {
    public FirebaseAuth auth;
    public static FirebaseUser user;
    public int area;

    public userSessions(){
        auth = null;
        user = null;
    }

    public void initialize(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    public static boolean userIsLogin(){
        return user != null;
    }

    public static void signOut(){
        user = null;
    }

}
