## FadeTransition ++ 

<a href="https://imgflip.com/gif/2vld69"><img src="https://i.imgflip.com/2vld69.gif" title="made at imgflip.com"/></a>

## Fade transition from one activity to another

1) Create a button & set a listener to it | refer to `FragmentMainActivity.java`
```java
private final View.OnClickListener onNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FragmentMainActivity.this);
            Intent intent = new Intent(FragmentMainActivity.this, LoginActivity.class);
            startActivity(intent, options.toBundle());

        }
    };
```

2) On `onCreate` | refer to `LoginActivity.java`
```java
getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

Transition enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade);
getWindow().setEnterTransition(enterTransition);
```

3) To return to the previous activity. Either create a button or `onBackPressed`
```java
backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.finishAfterTransition(LoginActivity.this);

            }
        });
```

## Image popping out (Notice the dog logo zooming out)

```java
companyLogo = findViewById(R.id.company_logo);

companyLogo
                .animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(1000)
                .start();
```

To continue through the application, click https://github.com/gerlau/howToMakeYourScreenMove/blob/master/README3.md

