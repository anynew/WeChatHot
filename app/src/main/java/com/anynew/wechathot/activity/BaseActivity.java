package com.anynew.wechathot.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by anynew on 2016/10/14.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
    }

    /**
     * 查找view
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();

    public boolean isDestroy = false;
    public void showToast(String msg){
        if (isDestroy)
            return;
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String msg){
        if (isDestroy)
            return;
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
