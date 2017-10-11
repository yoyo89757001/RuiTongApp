package com.example.ruitongapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ruitongapp.R;
import com.example.ruitongapp.fargments.Fragment1;
import com.example.ruitongapp.fargments.Fragment2;
import com.example.ruitongapp.fargments.Fragment3;
import com.example.ruitongapp.fargments.Fragment4;
import com.example.ruitongapp.fargments.Fragment5;
import com.example.ruitongapp.view.ViewPagerFragmentAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout r1,r2,r3,r4,rzhong;
    private ViewPager mViewPager;
    private ViewPagerFragmentAdapter mViewPagerFragmentAdapter;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ImageView tabIm,tabIm2,tabIm3,tabIm4,tabZhong;
    private TextView tabText,tabText2,tabText3,tabText4,tabTextZhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_home_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.lanse);
        }



        initFragmetList();

        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager,mFragmentList);

        initView();
        initViewPager();

    }

    private void initViewPager() {

        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setCurrentItem(0);
        updateBottomLinearLayoutSelect(0);
    }

    private void initFragmetList() {
        Fragment fragment1 = new Fragment1();
        Fragment fragment2 = new Fragment2();
        Fragment fragment3 = new Fragment3();
        Fragment fragment4 = new Fragment4();
        Fragment fragment5 = new Fragment5();
        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
        mFragmentList.add(fragment4);
        mFragmentList.add(fragment5);
    }

    private void initView() {
        mViewPager= (ViewPager) findViewById(R.id.viewpage);
        r1= (RelativeLayout) findViewById(R.id.homeLayout);
        r1.setOnClickListener(this);
        r2= (RelativeLayout) findViewById(R.id.chosenLayout);
        r2.setOnClickListener(this);
        r3= (RelativeLayout) findViewById(R.id.localLayout);
        r3.setOnClickListener(this);
        r4= (RelativeLayout) findViewById(R.id.settingLayout);
        r4.setOnClickListener(this);
        rzhong= (RelativeLayout) findViewById(R.id.zhongLayout);
        rzhong.setOnClickListener(this);
        tabIm= (ImageView) findViewById(R.id.tabImg);
        tabIm2= (ImageView) findViewById(R.id.tabImg2);
        tabIm3= (ImageView) findViewById(R.id.tabImg3);
        tabIm4= (ImageView) findViewById(R.id.tabImg4);
        tabZhong= (ImageView) findViewById(R.id.tabzhong);
        tabText= (TextView) findViewById(R.id.tabText);
        tabText2= (TextView) findViewById(R.id.tabText2);
        tabText3= (TextView) findViewById(R.id.tabText3);
        tabText4= (TextView) findViewById(R.id.tabText4);
        tabTextZhong= (TextView) findViewById(R.id.tabzhong_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeLayout:
                mViewPager.setCurrentItem(0);
                updateBottomLinearLayoutSelect(0);
                break;
            case R.id.chosenLayout:
                mViewPager.setCurrentItem(1);
                updateBottomLinearLayoutSelect(1);
                break;
            case R.id.localLayout:
                mViewPager.setCurrentItem(3);
                updateBottomLinearLayoutSelect(3);
                break;
            case R.id.settingLayout:
                mViewPager.setCurrentItem(4);
                updateBottomLinearLayoutSelect(4);
                break;

            case R.id.zhongLayout:

                mViewPager.setCurrentItem(2);
                updateBottomLinearLayoutSelect(2);
                break;
            default:
                break;
        }
    }
    private void  updateBottomLinearLayoutSelect(int position) {
        tabText.setTextColor(Color.parseColor("#8c050505"));
        tabText2.setTextColor(Color.parseColor("#8c050505"));
        tabText3.setTextColor(Color.parseColor("#8c050505"));
        tabText4.setTextColor(Color.parseColor("#8c050505"));
        tabTextZhong.setTextColor(Color.parseColor("#8c050505"));

        switch (position){
            case 0:
                tabIm.setImageResource(R.drawable.yg2_03);
                tabIm2.setImageResource(R.drawable.fk1_03);
                tabZhong.setImageResource(R.drawable.hmd1_03);
                tabIm3.setImageResource(R.drawable.fw1_03);
                tabIm4.setImageResource(R.drawable.gr1_03);
                tabText.setTextColor(Color.parseColor("#FF1c97fe"));

                break;
            case 1:
                tabText2.setTextColor(Color.parseColor("#FF1c97fe"));
                tabIm.setImageResource(R.drawable.yg1_03);
                tabIm2.setImageResource(R.drawable.fk2_03);
                tabZhong.setImageResource(R.drawable.hmd1_03);
                tabIm3.setImageResource(R.drawable.fw1_03);
                tabIm4.setImageResource(R.drawable.gr1_03);

                break;
            case 2:
                tabTextZhong.setTextColor(Color.parseColor("#FF1c97fe"));
                tabIm.setImageResource(R.drawable.yg1_03);
                tabIm2.setImageResource(R.drawable.fk1_03);
                tabZhong.setImageResource(R.drawable.hmd2_03);
                tabIm3.setImageResource(R.drawable.fw1_03);
                tabIm4.setImageResource(R.drawable.gr1_03);
                break;
            case 3:
                tabText3.setTextColor(Color.parseColor("#FF1c97fe"));
                tabIm.setImageResource(R.drawable.yg1_03);
                tabIm2.setImageResource(R.drawable.fk1_03);
                tabZhong.setImageResource(R.drawable.hmd1_03);
                tabIm3.setImageResource(R.drawable.fw2_03);
                tabIm4.setImageResource(R.drawable.gr1_03);
                break;
            case 4:
                tabText4.setTextColor(Color.parseColor("#FF1c97fe"));
                tabIm.setImageResource(R.drawable.yg1_03);
                tabIm2.setImageResource(R.drawable.fk1_03);
                tabZhong.setImageResource(R.drawable.hmd1_03);
                tabIm3.setImageResource(R.drawable.fw1_03);
                tabIm4.setImageResource(R.drawable.gr2_03);
                break;

        }

    }
   private class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG,"onPageScrooled");
        }
        @Override
        public void onPageSelected(int position) {

            updateBottomLinearLayoutSelect(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("home","onPageScrollStateChanged");
        }
    }
}
