package edu.lehman.team7.newsflash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class NotificationBroadcast extends BroadcastReceiver {

    private ArticleItem topArticle;
    private ArticleAdapter articleAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {

        new FetchTopNews(topArticle, context).execute("top-headlines", "");

        if (topArticle!=null) {
            Log.i("Top article =", topArticle.toString());
        }else{
            Log.i("Top article =", " is null");
        }

/*
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyNewsFlash")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(" Title ")
                .setContentText(" blablabla ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        notificationManager.notify(200, builder.build());

 */
    }
}
