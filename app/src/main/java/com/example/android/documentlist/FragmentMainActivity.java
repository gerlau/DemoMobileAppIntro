package com.example.android.documentlist;

import android.animation.ArgbEvaluator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;


public class FragmentMainActivity extends FragmentActivity {

    SmartFragmentStatePagerAdapter pagerAdapter;
    ViewPager mViewPager;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private Button nextButton;
    private WormDotsIndicator wormDotsIndicator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_navigate);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.pager);

        mViewPager.addOnPageChangeListener(new CustomOnPageChangeListener());

        mViewPager.setAdapter(pagerAdapter);

        setUpColors();

        // Page Indicator
        wormDotsIndicator = findViewById(R.id.worm_dots_indicator);
        wormDotsIndicator.setViewPager(mViewPager);


        // Set up next Button
        nextButton = findViewById(R.id.next_button);
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setOnClickListener(onNextClickListener);

    }

    /**
     * OnClickListener for the "Next" / "Done" button.
     */
    private final View.OnClickListener onNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FragmentMainActivity.this);
            Intent intent = new Intent(FragmentMainActivity.this, LoginActivity.class);
            startActivity(intent, options.toBundle());

        }
    };


    private void setUpColors(){

        Integer color1 = getResources().getColor(R.color.white);
        Integer color2 = getResources().getColor(R.color.white);
        Integer color3 = getResources().getColor(R.color.white);

        Integer[] colors_temp = {color1, color2, color3};
        colors = colors_temp;

    }


    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if(position < (pagerAdapter.getCount() -1) && position < (colors.length - 1)) {

                mViewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));

            } else {

                // the last page color

                mViewPager.setBackgroundColor(colors[colors.length - 1]);


            }

        }

        @Override
        public void onPageSelected(int position) {

            // Check if the current ViewPager position is at the end
            if (position == pagerAdapter.getCount() - 1) {

                // Show the "Done" button
                nextButton.setVisibility(View.VISIBLE);

            }

            else{

                nextButton.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }

    }
}
