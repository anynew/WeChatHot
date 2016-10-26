package com.anynew.wechathot.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_wx_content);
        ButterKnife.bind(this);
        initToolBar();
        initTopImg();
        initWeb();
    }

    private void initTopImg() {
        Glide.with(this)
                .load(getIntent().getStringExtra("img"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mTopImg);
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setTitle(getIntent().getStringExtra("source"));
//        mToolBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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
}
