package com.example.slider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private Handler sliderHandler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 =findViewById(R.id.viewPagerImageSlider);

        List<slide_item> sliderItems =new ArrayList<>();
        sliderItems.add(new slide_item(R.drawable.kucuk1));
        sliderItems.add(new slide_item(R.drawable.kucuk2));
        sliderItems.add(new slide_item(R.drawable.kucuk3));
        sliderItems.add(new slide_item(R.drawable.kucuk4));
        sliderItems.add(new slide_item(R.drawable.kucuk5));

        viewPager2.setAdapter(new SliderAdepter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r =1-Math.abs(position);
                page.setScaleY(0.85f + r* 0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);



            }
        });
    }
    private Runnable sliderRunnable =new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
    public void onPasue(){
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
    protected void onResumw(){
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,3000);
    }


}