package edu.lehman.team7.newsflash;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FetchNews extends AsyncTask<String, Void, String> {
    private WeakReference<ArrayList<ArticleItem>> articlesList;
    private WeakReference<ArticleAdapter> articleAdapter;

    FetchNews(ArrayList<ArticleItem> articlesList, ArticleAdapter articleAdapter) {
        this.articlesList = new WeakReference<>(articlesList);
        this.articleAdapter = new WeakReference<>(articleAdapter);
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = NetworkUtils.getHeadlines(strings[0], strings[1]);
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("articles");

            String result = "";
            String headline = "", description = "";

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject article = itemsArray.getJSONObject(i);
                headline = article.getString("title");
                description = article.getString("description");
                if (description == "null") description = "";

                articlesList.get().add(new ArticleItem(headline, description));
                //result += currentHeadline + "\n\n";
            }

            if (articlesList.get().isEmpty()) { //No results
                articlesList.get().add(new ArticleItem("No results", "Could not find any articles"));
            }
        } catch (JSONException e) {
            articlesList.get().add(new ArticleItem("Exception", e.getMessage()));
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        articleAdapter.get().notifyDataSetChanged();
    }
}
