# 1. What is this project about?

<a href="https://imgflip.com/gif/2vl9pi"><img src="https://i.imgflip.com/2vl9pi.gif" title="made at imgflip.com"/></a>

## 2. How are we going to do it? 

1) Create a Linear layout XML file | refer to `activity_fragment_navigate.xml`
```java
Add 'android.support.v4.view.ViewPager'
```

2) Create an adapter java class so that information can be passed through to the view pager 
```java
'PagerAdapter.java'
```

3) In your main activity, set the adapter to the viewpager | refer to `FragmentMainActivity.java`
```java
pagerAdapter = new PagerAdapter(getSupportFragmentManager());

mViewPager = findViewById(R.id.pager);

mViewPager.setAdapter(pagerAdapter);
```

4) Create a Fragment class that extends 'Fragment' | refer to `FragmentA.java`

- Create a new instance 'public static FragmentA new instance' & declare your arguments 
I declared three arguments, a drawableId, a title, a description

```java
public static FragmentA newInstance(int drawableId, String title, String description){
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putInt(DRAWABLE_ID, drawableId);
        args.putString(TITLE, title);
        args.putString(DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }
```

- `onCreateView` find all your views & attach your arguments to the views

Example
```java
ImageView imageView = rootView.findViewById(R.id.imageview);
imageView.setImageResource(getArguments().getInt(DRAWABLE_ID));
```

For drawable, please refer to `res/drawable`

5)  Go back to `PagerAdapter.java` & return new instances (each will represent a new page on the viewpager)
```java
@Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return FragmentA.newInstance(R.drawable.ic_calendar, "ONLINE RESERVATION ", "Fast & reliable reservation");
            case 1:
                return FragmentA.newInstance(R.drawable.ic_taxi, "TRANSPORT", "Safe and reliable rides are provided by our experienced and pet friendly drivers");
            case 2:
                return FragmentA.newInstance(R.drawable.ic_dog, "COMFORT", "Comfortable environment for your dog’s needs and wants");
            default:
                return null;
        }
    }
```
- Update according to how many pages you want your viewpager to have, the number must tally with the amount of cases you declared
```java
@Override
    public int getCount() {
        return 3;
    }
```

## DotsIndicator
Refer to https://github.com/tommybuonomo/dotsindicator

To continue with the application, click https://github.com/gerlau/howToMakeYourScreenMove/blob/master/README2.md
