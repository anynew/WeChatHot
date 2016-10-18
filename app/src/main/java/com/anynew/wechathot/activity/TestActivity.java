package com.anynew.wechathot.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anynew.wechathot.R;
import com.anynew.wechathot.ui.Kanner;

/**
 * Created by anynew on 2016/10/18.
 */

public class TestActivity extends AppCompatActivity {
    Kanner kanner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        kanner = (Kanner)findViewById(R.id.kanner);
        kanner.setImagesUrl(new String[] {
                "http://img03.muzhiwan.com/2015/06/05/upload_557165f4850cf.png",
                "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg",
                "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png",
                "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg" });
    }
}
