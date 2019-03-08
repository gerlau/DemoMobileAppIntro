package com.example.android.documentlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// LoginActivity class that extends to dialog_page.xmlactivity_login.xml
public class LoginActivity extends AppCompatActivity {

    // dialog_page
    private AlertDialog dialog;
    private AutoCompleteTextView emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView forgetPasswordView;
    private ImageView closeDialog;
    private View loginProgressView;
    private View loginFormView;
    private int counter = 2;


    // login_main_page.xml
    private TextView companyName;
    private ImageView companyLogo;
    private Button emailSignInButton;
    private Button backButton;


    // new_user_login_in.xml
    private Button newUserButton;


    private FirebaseAuth auth;
    private FirebaseUser user;
    private InputMethodManager inputMethodManager;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }


    @Override
    public void onBackPressed(){
        ActivityCompat.finishAfterTransition(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.login_main_page);

        companyLogo = findViewById(R.id.company_logo);
        companyName = findViewById(R.id.company_name);
        emailSignInButton = findViewById(R.id.email_sign_in_button);
        newUserButton = findViewById(R.id.new_user_button);
        backButton = findViewById(R.id.back_button);

        companyLogo
                .animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(1000)
                .start();


        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){

                }
            }
        };


        emailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                emailSignIn();
            }
        });

        newUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);


            }
        });

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.finishAfterTransition(LoginActivity.this);

            }
        });


        Transition enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(enterTransition);


    }

    // [START]
    // Builds a login form on an AlertDialog that allows user to sign in with their EMAIL account
    private void emailSignIn(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        View view = getLayoutInflater().inflate(R.layout.dialog_page, null);
        builder.setView(view);

        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
        signInButton = view.findViewById(R.id.sign_in_button);
        forgetPasswordView = view.findViewById(R.id.forget_password);
        closeDialog = view.findViewById(R.id.close_dialog);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(1400,1700);

        // [START OnClickListener to close AlertDialog]
        closeDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // [END]

        // [START OnClickListener to provide user with a pathway by sending a password Reset]
        forgetPasswordView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }
        });
        // [END]

        // [START OnClickListener to allow user to login into their account with their EMAIL]
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();

            }
        });
        // [END]

    }
    // [END] emailSignIn()


    /**
     * [START]
     * Method to sign in user with their EMAIL account
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        // Combo Constraints
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Fields are empty!", Toast.LENGTH_SHORT).show();
            emailEditText.requestFocus();

        }

        // Email constraints
        else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please provide an Email!", Toast.LENGTH_SHORT).show();
            emailEditText.requestFocus();
        }

        else if (!isEmailValid(email)) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            emailEditText.requestFocus();
        }

        // Password constraints
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter a password!", Toast.LENGTH_SHORT).show();
            passwordEditText.requestFocus();
        }

        else if (!isPasswordValid((password))) {
            passwordEditText.setError(getString(R.string.error_invalid_password));
            passwordEditText.requestFocus();
        }

        // Authenticate existing user
        else{
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {

                        // There was an error
                        Toast.makeText(LoginActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

                        // After 2 failed attempts, forgetPasswordView will be available
                        counter--;
                        if (counter == 0) {

                            //forgetPasswordView.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Forget password?", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        checkIfEmailVerified();
                    }

                }
            });
        }
    }
    // [END] AttemptLogin()

    // [START]
    // Method to give access to users who verified their registered account
    private void checkIfEmailVerified(){

        user = auth.getInstance().getCurrentUser();

        if(user.isEmailVerified()){

            // user is verified, finish the activity and send it to Main Page
            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
            intent.putExtra("username", auth.getCurrentUser().getEmail());
            startActivity(intent);

            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();

            // When logging out, Dialog is still there.
            // Therefore, to remove it we have to dismiss it before we start the next activity


            dialog.dismiss();



        } else {


            // When email has yet to be verified

            Toast.makeText(LoginActivity.this,"Please verify your email address", Toast.LENGTH_LONG).show();


        }
    }
    // [END] checkIfEmailVerified()


    // [START]
    // Method that allows user to reset their password
    private void forgetPassword() {

        String email = emailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please provide an Email!", Toast.LENGTH_SHORT).show();
            emailEditText.requestFocus();
        }

        else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Password reset email has been sent!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
    // [END] forgetPassword()


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

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

