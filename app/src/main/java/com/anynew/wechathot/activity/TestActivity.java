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

    }
}
