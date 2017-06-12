package com.anynew.wechathot.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.anynew.wechathot.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anynew on 2016/10/22.
 */

public class WxContentActivity extends BaseActivity {

    @Bind(R.id.mWebview)
    WebView mWebview;
    @Bind(R.id.mToolBar)
    Toolbar mToolBar;
    @Bind(R.id.mTopImg)
    ImageView mTopImg;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.mAppBar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;

    private String TAG = this.getClass().getSimpleName();

    private WxContentActivity context = WxContentActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

        setContentView(R.layout.activity_wx_content);
        ButterKnife.bind(this);
        mAppBarLayout.setVisibility(View.INVISIBLE);
        initToolBar();
        initTopImg();
        initWeb();

    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolBar.setTitle(getIntent().getStringExtra("source"));
        Log.e(TAG, "source: "+    getIntent().getStringExtra("source"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimary));

    }

    /**
     * 设置ToolBar背景图片
     */
    private void initTopImg() {
        Log.e(TAG, "img: " +getIntent().getStringExtra("img"));
        Glide.with(this)
                .load(getIntent().getStringExtra("img"))
                .into(mTopImg);
    }


    /**
     * 加载WebView
     */
    private void initWeb() {

        String urlLink = getIntent().getStringExtra("urlLink");
        mWebview.loadUrl(urlLink);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        Log.e(TAG, "urlLink: " + urlLink);
    }

    @TargetApi(21)
    private void setStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // If both system bars are black, we can remove these from our layout,
            // removing or shrinking the SurfaceFlinger overlay required for our views.
            Window window = this.getWindow();
            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(statusBarColor);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_out_to_left_from_right);

    }
}
