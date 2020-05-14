package edu.lehman.team7.newsflash;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FetchTopNews  extends AsyncTask<String, Void, String> {
    private ArticleItem topArticle;
    private Context context;


    FetchTopNews(ArticleItem topArticle, Context context) {
        this.topArticle = topArticle;
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {
        Log.i("getTopNews doing in the background " , strings[0] + " "+  strings[1] );
        String s = NetworkUtils.getHeadlines(strings[0], strings[1]);
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        topArticle = new ArticleItem("No results", "Could not find any articles");

        try {
            JSONObject jsonObject = new JSONObject(s);
            Log.i("topNews jsonObject = ", jsonObject.toString() );
            JSONArray itemsArray = jsonObject.getJSONArray("articles");

            String result = "";
            String headline = "", description = "", author = "", articleURL = "", imgURL = "", content = "";

            if (itemsArray.length()>0){
                JSONObject article = itemsArray.getJSONObject(0);
                headline = article.getString("title");
                description = article.getString("description");
                author = article.getString("author");
                articleURL = article.getString("url");
                imgURL= article.getString("urlToImage");
                content= article.getString("content");
                if (description == "null") description = "";

                ArticleItem articleItem = new ArticleItem(headline, description, author, articleURL, imgURL, content);
                topArticle = articleItem;
            }

            Intent notifPressIntent = new Intent (context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notifPressIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyNewsFlash")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(topArticle.getArticleTitle())
                    .setContentText(topArticle.getContent())
                    .setContentIntent(contentIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


            notificationManager.notify(200, builder.build());

        } catch (JSONException e) {
            topArticle = new ArticleItem("Exception", e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}
