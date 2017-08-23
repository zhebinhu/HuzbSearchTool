package com.example.huzhebin.huzbsearchtool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.List;
import com.example.huzhebin.huzbsearchtool.constant;

import static android.R.attr.resource;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar search_bar;
    View main_linearlayout;
    DrawerLayout drawer_layout;
    ImageView drawer_button;
    EditText edit_text;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将搜索框向下平移状态栏高度
        search_bar = (Toolbar) findViewById(R.id.search_toolbar);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 156);
        lp.setMargins(0, getStatusBarHeight(), 0, 0);
        search_bar.setLayoutParams(lp);
        setSupportActionBar(search_bar);
        //点击空白处返回
        main_linearlayout = (View) findViewById(R.id.main_linearlayout);
        main_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
        //托管drawer
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_button = (ImageView) findViewById(R.id.drawer_button);
        edit_text = (EditText) findViewById(R.id.edit_text);
        drawer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });
        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //隐藏键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //弹出键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(edit_text, 0);
                SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //搜索功能
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
                    if (!wd.equals(null)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
                        int flag = sharedPreferences.getInt("flag", R.id.nav_baidu);
                        Menu menu = navigationView.getMenu();
                        menu.findItem(flag).setChecked(true);
                        int eid = getEngineId(flag);
                        if(eid==5&&isAppInstalled("com.autonavi.minimap"))
                        {
                            startGdMap("Huzb搜索工具",wd);
                        }
                        else {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(constant.eurl[eid] + wd);
                            intent.setData(content_url);
                            startActivity(intent);
                        }
                    }
                    MainActivity.this.finish();
                }
                return false;
            }
        });
        //初始化抽屉导航栏
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        int flag = sharedPreferences.getInt("flag", R.id.nav_baidu);
        Menu menu = navigationView.getMenu();
        menu.findItem(flag).setChecked(true);
        int eid = getEngineId(flag);
        edit_text.setHint("使用\"" + constant.ename[eid] + "\"搜索...");

    }

    //获取状态栏高度
    protected int getStatusBarHeight() {
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

    //获得搜索引擎序号
    protected int getEngineId(int flag) {
        switch (flag) {
            case R.id.nav_baidu:
                return 0;
            case R.id.nav_google:
                return 1;
            case R.id.nav_translation:
                return 2;
            case R.id.nav_zhihu:
                return 3;
            case R.id.nav_weibo:
                return 4;
            case R.id.nav_gaodemap:
                return 5;
            case R.id.nav_tmall:
                return 6;
            default:
                return 0;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("flag", item.getItemId());
        item.setCheckable(true).setChecked(true);
        editor.commit();
        int eid = getEngineId(item.getItemId());
        edit_text.setHint("使用\"" + constant.ename[eid] + "\"搜索...");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startGdMap(String appName, String dname) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //将功能Scheme以URI的方式传入data
        Uri uri = Uri.parse("androidamap://poi?sourceApplication=" + appName + "&keywords=" + dname + "&dev=0");
        intent.setData(uri);
        //启动该页面即可
        startActivity(intent);
    }
    private boolean isAppInstalled(String uri){
        PackageManager pm = getPackageManager();
        boolean installed =false;
        try{
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            installed =true;
        }catch(PackageManager.NameNotFoundException e){
            installed =false;
        }
        return installed;
    }
}