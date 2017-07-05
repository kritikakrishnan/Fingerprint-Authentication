package com.example.kritikagopalakrishnan.fingerprint_authentication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.value;

public class WelcomeActivity extends AppCompatActivity {

    boolean doneFlag = false;
    private View mProgressView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    TextView userNameTitle;
    TextView phNumberTitle;
    TextView emailIDTitle;
    EditText userName;
    EditText phNumber;
    EditText emailID;
    EditText firstName;
    EditText lastName;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        userNameTitle = (TextView) findViewById(R.id.userNameTitle);
        phNumberTitle = (TextView) findViewById(R.id.phNumberTitle);
        mLoginFormView = findViewById(R.id.container);
        mProgressView = findViewById(R.id.profile_progress);
        emailIDTitle = (TextView) findViewById(R.id.emailIDTitle);
        userName = (EditText) findViewById(R.id.userName);
        phNumber = (EditText) findViewById(R.id.phoneNumber);
        emailID = (EditText) findViewById(R.id.emailID);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        userName.setFocusable(false);
        phNumber.setFocusable(false);
        emailID.setFocusable(false);
        showProgress(true);
        GetProfleDetailsTask getProfleDetailsTask = new GetProfleDetailsTask();
        getProfleDetailsTask.execute();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }
    public class GetProfleDetailsTask extends AsyncTask<Void, Void, Void> {

        // Read from the database
        User newUser = new User();

        @Override
        protected Void doInBackground(Void... voids) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange (DataSnapshot dataSnapshot){
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    newUser = dataSnapshot.getValue(User.class);
                    newUser.toString();
                    Log.d("TAG", "Value is: " + value);
                    updateDoneFlag(true);

                }

                @Override
                public void onCancelled (DatabaseError error){
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
            while(!doneFlag){}
            return null;
        }


        @Override
        protected void onPostExecute(Void voids) {
            userName.setText(newUser.getUserName());
            emailID.setText(newUser.getUserName());
            phNumber.setText(newUser.getPhoneNumber());
            firstName.setText(newUser.getFirstName());
            lastName.setText(newUser.getLastName());
            showProgress(false);


        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public void updateDoneFlag(boolean dflag){

        doneFlag = dflag;
    }

}
