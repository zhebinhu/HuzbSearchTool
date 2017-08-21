package com.example.huzhebin.huzbsearchtool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mean=this.getIntent().getStringExtra("mean");
        //将搜索框向下平移状态栏高度
        Toolbar search_bar = (Toolbar) findViewById(R.id.search_toolbar);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 156);
        lp.setMargins(0, getStatusBarHeight(), 0, 0);
        search_bar.setLayoutParams(lp);
        setSupportActionBar(search_bar);
        //点击空白处返回
        View main_linearlayout = (View) findViewById(R.id.main_linearlayout);
        main_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
        //托管drawer
        final DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView drawer_button = (ImageView) findViewById(R.id.drawer_button);
        drawer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });
        //搜索功能
        final EditText edit_text = (EditText) findViewById(R.id.edit_text);
        edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    String wd = edit_text.getText().toString();
                    if(!wd.equals(null)) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("https://www.baidu.com/s?wd=" + wd);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }
                MainActivity.this.finish();
                return false;
            }
        });
        System.out.println("mean=="+mean);
        if(mean!=null)
        {
            if(mean.equals("123")) {
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        }
    }

    //获取状态栏高度
    protected int getStatusBarHeight(){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;//默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}