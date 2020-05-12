package edu.lehman.team7.newsflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailedView extends AppCompatActivity {

    private TextView tvHeadline, tvAuthor, tvContent;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        tvHeadline = findViewById(R.id.detailed_view_title);
        tvAuthor = findViewById(R.id.detailed_view_author);
        tvContent = findViewById(R.id.detailed_view_content);
        ivImage = findViewById(R.id.detailed_view_img);

        tvHeadline.setText(getIntent().getStringExtra("headline"));
        tvAuthor.setText(getIntent().getStringExtra("author"));
        tvContent.setText(getIntent().getStringExtra("content"));
        Glide.with(this).load(getIntent().getStringExtra("img")).into(ivImage);
    }

    public void shareArticle(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("img"));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }

    public void favoriteArticle(View view) {
        Toast.makeText(this, "favorite", Toast.LENGTH_SHORT).show();
    }
}
