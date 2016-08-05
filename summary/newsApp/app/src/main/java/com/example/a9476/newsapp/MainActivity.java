package com.example.a9476.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] urlList = {"http://gank.io/api/data/Android/10/1","http://gank.io/api/data/iOS/20/2"};
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        PagerAdapter fragmentAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmentAdapter);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new AndroidFragment());
        fragmentList.add(new IosFragment());
    }
}
