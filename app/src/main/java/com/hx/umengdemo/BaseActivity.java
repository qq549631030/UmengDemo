package com.hx.umengdemo;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by huangxiang on 15/10/9.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
