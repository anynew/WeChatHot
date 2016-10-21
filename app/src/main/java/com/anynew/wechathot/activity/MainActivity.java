package com.anynew.wechathot.activity;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anynew.wechathot.R;
import com.anynew.wechathot.fragment.TabFragment;
import com.anynew.wechathot.adapter.TabFragmentAdapter;
import com.anynew.wechathot.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, TabFragment.OnNotifyListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initFab();
        initToolbar();
        initTabLayout();

    }


    /**
     * 初始化悬浮Fab按钮
     */
    private void initFab() {
        fab = findView(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(v, "测试弹出提示", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
                snackbar.show();
                snackbar.setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "SnackBar action", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initToolbar() {
        toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("微信精选");
        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setSubtitle("CSDN");
//        toolbar.setLogo(R.drawable.ic_launcher);
//        toolbar.setNavigationIcon(R.drawable.ic_list_black_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(this);
    }

    private void initTabLayout() {
        viewPager = findView(R.id.mViewPager);
        tabLayout = findView(R.id.tabs);
        List<String> tabList = new ArrayList<>();
        tabList.add("热门");
        tabList.add("推荐");
        tabList.add("段子手");
        tabList.add("养生堂");
        tabList.add("私房话");
        tabList.add("八卦精");
        tabList.add("爱生活");
        tabList.add("财经迷");
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        //此处代码设置无效，不知道为啥？？？xml属性是可以的
//        tabLayout.setTabTextColors(android.R.color.white, android.R.color.holo_red_dark);//设置TabLayout两种状态
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(4)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(5)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(6)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(7)));

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < tabList.size(); i++) {
            Fragment f = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("flag", i);
            f.setArguments(bundle);
            fragmentList.add(f);
        }

        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        viewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_me:
                Toast.makeText(this, "我", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onSnack(String tips) {
        CoordinatorLayout v = findView(R.id.main_content);
        final Snackbar snackbar = Snackbar.make(v, tips, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
        snackbar.setAction("点击设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));            }
        });
    }
}
