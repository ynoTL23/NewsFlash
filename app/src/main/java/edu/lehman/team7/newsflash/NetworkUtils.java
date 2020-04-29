package edu.lehman.team7.newsflash;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/";
    private static final String QUERY_PARAM = "q";
    private static final String COUNTRY_PARAM = "country";

    protected static String getHeadlines(String endpoint, String query) {
        String baseURL = NEWS_BASE_URL + endpoint;
        String newsJSONString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            Uri builtURI = Uri.parse(baseURL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, query)
                    .build();
            if (endpoint != "everything") builtURI = builtURI.buildUpon().appendQueryParameter(COUNTRY_PARAM, "us").build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-Api-Key", "871f772349d04336b74ac38e2dcab420");
            urlConnection.connect();

            InputStream inStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inStream));

            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() > 0) newsJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newsJSONString;
    }
}
