package com.example.huzhebin.huzbsearchtool;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by huzhebin on 17-8-19.
 */

public class WidgetProvider extends AppWidgetProvider {
    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_CANCEL_CURRENT);/*去掉动画效果要用FLAG_CANCEL_CURRENT*/
        remoteViews.setOnClickPendingIntent(R.id.search_bar,pendingIntent);
        for(int i= 0;i<appWidgetIds.length;i++) {
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

//    /**
//     * 接收窗口小部件点击时发送的广播
//     */
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//
//        if (CLICK_ACTION.equals(intent.getAction())) {
////            Toast.makeText(context, "hello dog!", Toast.LENGTH_SHORT).show();
//            Intent intent2 = new Intent();
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            intent2.setClass(context,MainActivity.class);
//            context.startActivity(intent2);
//        }
//    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

}
