package com.example.bsteam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bsteam.R;
import com.example.bsteam.fragment.CardFragment;
import com.example.bsteam.fragment.ClockInFragment;
import com.example.bsteam.fragment.ChooseFragment;
import com.example.bsteam.fragment.MoreFragment;
import com.example.bsteam.util.ExitApplication;
import com.example.bsteam.view.TabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindArray(R.array.tab_array)
    String[] mTabTitles;

    @BindView(R.id.tab_clock_in)
    TabView mTabClockIn;

    @BindView(R.id.tab_choose)
    TabView mTabChoose;

    @BindView(R.id.tab_card)
    TabView mTabCard;

    @BindView(R.id.tab_more)
    TabView mTabMore;

    private List<TabView> mTabViews = new ArrayList<>();

    private static final int INDEX_CLOCKIN = 0;
    private static final int INDEX_CHOOSE = 1;
    private static final int INDEX_CARD = 2;
    private static final int INDEX_MORE = 3;

    private String userId = "";
//    public static FirstActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ExitApplication.getInstance().addActivity(this);
//        instance = this;
        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        ButterKnife.bind(this);

        mTabViews.add(mTabClockIn);
        mTabViews.add(mTabChoose);
        mTabViews.add(mTabCard);
        mTabViews.add(mTabMore);

        mViewPager.setOffscreenPageLimit(mTabTitles.length - 1);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /**
             * @param position 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param positionOffset 左边View位移的比例
             * @param positionOffsetPixels 左边View位移的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 左边View进行动画
                mTabViews.get(position).setXPercentage(1 - positionOffset);
                // 如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset > 0) {
                    mTabViews.get(position + 1).setXPercentage(positionOffset);
                }
            }
        });
    }


    private void updateCurrentTab(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }

    @OnClick({R.id.tab_clock_in, R.id.tab_choose, R.id.tab_card, R.id.tab_more})
    public void onClickTab(View v) {
        switch (v.getId()) {
            case R.id.tab_clock_in:
                mViewPager.setCurrentItem(INDEX_CLOCKIN, false);
                updateCurrentTab(INDEX_CLOCKIN);
                break;
            case R.id.tab_choose:
                mViewPager.setCurrentItem(INDEX_CHOOSE, false);
                updateCurrentTab(INDEX_CHOOSE);
                break;

            case R.id.tab_card:
                mViewPager.setCurrentItem(INDEX_CARD, false);
                updateCurrentTab(INDEX_CARD);
                break;

            case R.id.tab_more:
                mViewPager.setCurrentItem(INDEX_MORE, false);
                updateCurrentTab(INDEX_MORE);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return getTabFragment(i, mTabTitles[i]);
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }
    }


    private Fragment getTabFragment(int index, String title) {//title为每个界面显示的文字，这里没有使用
        Fragment fragment = null;
        switch (index) {
            case INDEX_CLOCKIN:
                fragment = ClockInFragment.newInstance(userId);
                break;

            case INDEX_CHOOSE:
                fragment = ChooseFragment.newInstance(userId);
                break;

            case INDEX_CARD:
                fragment = CardFragment.newInstance(userId);
                break;

            case INDEX_MORE:
                fragment = MoreFragment.newInstance(userId);
                break;
        }
        return fragment;
    }
}
