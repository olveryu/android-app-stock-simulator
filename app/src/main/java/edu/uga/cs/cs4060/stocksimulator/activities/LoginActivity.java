package edu.uga.cs.cs4060.stocksimulator.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.UIFunctions.ShowPrograssingBar;
import edu.uga.cs.cs4060.stocksimulator.User.OnTaskCompleted;
import edu.uga.cs.cs4060.stocksimulator.User.UserAccount;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BasicActivity {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ShowPrograssingBar showPrograssingBar;
    private Activity currentActivity;
    private UserAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.uga.cs.cs4060.stocksimulator.R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(edu.uga.cs.cs4060.stocksimulator.R.id.email);
        mLoginFormView = (ConstraintLayout) findViewById(edu.uga.cs.cs4060.stocksimulator.R.id.formLayout);
        mPasswordView = (EditText) findViewById(edu.uga.cs.cs4060.stocksimulator.R.id.password);

        SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
        String username = sharedPre.getString("username", "");
        String password = sharedPre.getString("password", "");
        mEmailView.setText(username);
        mPasswordView.setText(password);
        showPrograssingBar = new ShowPrograssingBar();
        currentActivity = this;

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(edu.uga.cs.cs4060.stocksimulator.R.id.email_sigin_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(edu.uga.cs.cs4060.stocksimulator.R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(edu.uga.cs.cs4060.stocksimulator.R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(edu.uga.cs.cs4060.stocksimulator.R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showPrograssingBar.showProgress(currentActivity, mProgressView, mLoginFormView, true);
            saveLoginInfo(this, email, password);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        CharSequence s = email;
        return !TextUtils.isEmpty(s) && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */


    public void hideKeys() {
        InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(RegisterActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Void> {

        private final String mEmail;
        private final String mPassword;
        private Intent home;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            hideKeys();
            // Simulate network access.
            account = UserAccount.getInstance();
            UserAccount.auth.signInWithEmailAndPassword(mEmail, mPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    //WE LOGGED IN!
                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //WE FAILED
                    UserAccount.signOut();
                    Toast.makeText(getApplicationContext(), "Invalid login credentials", Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mAuthTask = null;
                }
            });

            return null;
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showPrograssingBar.showProgress(currentActivity, mProgressView, mLoginFormView, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            account.load(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted() {
                    System.out.println("loaded portfolio");
                    UserActivity.loaded = true;
                }

                @Override
                public void onTaskFailed() {
                    System.out.println("fail to load portfolio");
                    UserActivity.loaded = false;
                }
            });
            System.out.println("wowwowow");
            if(UserActivity.loaded) {
                showPrograssingBar.showProgress(currentActivity, mProgressView, mLoginFormView, false);
                home = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(home);
            }
        }
    }

    public void saveLoginInfo(Context context, String username, String password) {
        SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }
}

