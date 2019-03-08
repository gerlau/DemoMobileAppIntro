package com.example.android.documentlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // new_user_login_in.xml
    private AutoCompleteTextView userEmail;
    private EditText userPassword;
    private Button newUserSignUpButton;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private View userLoginProgressView;
    private View userLoginFormView;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_user_login_in);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6099f5")));
        actionBar.setTitle("Register");

        auth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.new_user_email);
        userPassword = findViewById(R.id.new_user_password);
        newUserSignUpButton = findViewById(R.id.new_user_sign_up_button);

        // [START OnClickListener to sign up the new user with an EMAIL and a PASSWORD]
        newUserSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });
        // [END]



    }

    // Activity is created before this method is implemented which is not what we want.
    // We want it to finish or pause
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Activity is paused when back Button on action bar is selected
        switch (item.getItemId()){
            case android.R.id.home:
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // [START]
    // Allow user to sign up with an EMAIL & PASSWORD
    // Sends verification email if successful
    private void attemptSignUp() {

        final String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();

        // Combo Constraints

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Fields are empty!", Toast.LENGTH_SHORT).show();
            userEmail.requestFocus();
        }

        // Email constraints

        else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please provide an Email!", Toast.LENGTH_SHORT).show();
            userEmail.requestFocus();

        } else if (!isEmailValid(email)) {
            userEmail.setError(getString(R.string.error_invalid_email));
            userEmail.requestFocus();
        }

        // Password constraints

        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter a password!", Toast.LENGTH_SHORT).show();
            userPassword.requestFocus();

        } else if (!isPasswordValid((password))) {
            userPassword.setError(getString(R.string.error_invalid_password));
            userPassword.requestFocus();

        } else {
            // Create new user
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {

                        // Verification Email sent
                        sendVerificationEmail();

                        finish();

                    }

                }
            });

        }
    }
    // [END] attemptSignUp()


    // [START]
    // Method sends a verification email upon successful sign up
    private void sendVerificationEmail(){
        user = auth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){

                        // Email not send
                        Toast.makeText(RegisterActivity.this, "Verification email failed to send!", Toast.LENGTH_LONG).show();

                    } else {

                        // After email is sent, logout the user and finish the activity
                        auth.getInstance().signOut();

                        Toast.makeText(RegisterActivity.this, "Successfully Registered, Verification email sent!", Toast.LENGTH_LONG).show();


                    }
                }
            });
        }
    }
    // [END] sendVerificationEmail()

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            userLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            userLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    userLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            userLoginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            userLoginProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    userLoginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            userLoginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            userLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
