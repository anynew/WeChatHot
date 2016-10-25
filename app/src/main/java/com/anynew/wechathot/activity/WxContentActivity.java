package com.anynew.wechathot.activity;

import android.os.Bundle;
import android.util.Log;

import com.anynew.wechathot.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anynew on 2016/10/22.
 */

public class WxContentActivity extends BaseActivity {


    @Bind(R.id.mTencentWeb)
    WebView mTencentWeb;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_wx_content);
        ButterKnife.bind(this);

        initWeb();
    }

    private void initWeb() {
        String urlLink = getIntent().getStringExtra("urlLink");
        mTencentWeb.loadUrl(urlLink);
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
        String userAgent = "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/45.0.2454.94 Mobile Safari/537.36";
        WebSettings settings = mTencentWeb.getSettings();
        settings.setUserAgent(userAgent);
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString(userAgent);
        settings.setDomStorageEnabled(true);
        Log.e(TAG, "urlLink: " + urlLink);
    }
}
