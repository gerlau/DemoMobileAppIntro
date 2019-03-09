## MainPage -> Register | `RegisterActivity.java`

<a href="https://imgflip.com/gif/2vleey"><img src="https://i.imgflip.com/2vleey.gif" title="made at imgflip.com"/></a>

Set up firebase to the project https://firebase.google.com/docs/android/setup

1) Create a button with a listener & create a new method `attemptSignUp()'
```java
 // [START OnClickListener to sign up the new user with an EMAIL and a PASSWORD]
        newUserSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });
```        
      
2) In `attemptSignUp()`, implement this method `auth.createUserWithEmailAndPassword` to create a new user
```java
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
```


## MainPage -> Log In `LoginActivity.java` -> `MainPageActivity.java`

<a href="https://imgflip.com/gif/2vleq6"><img src="https://i.imgflip.com/2vleq6.gif" title="made at imgflip.com"/></a>

1) Create a button with a listener. `emailSignInButton.setOnClickListener()`, it will the method `emailSignIn()`. 
- Inside `emailSignIn()`, it will build up an `AlertDialog`

```java
emailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                emailSignIn();
            }
        });
 ```
 - Set the `findViewById` in this method if not the dialog will not be able to find the views.
 ```java
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

```  

2) In `attemptLogin()`, implement this method `auth.signInWithEmailAndPassword()` to sign in exisiting `user.isEmailVerified()` users.

```java
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
 ```
 
 ```java
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
    
   ``` 
            


    

