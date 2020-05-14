package edu.lehman.team7.newsflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailedView extends AppCompatActivity {

    ArticleItem currentArticle;
    Article dbArticle;
    ArticleViewModel articleViewModel;
    private TextView tvHeadline, tvAuthor, tvContent;
    private ImageView ivImage;
    private FloatingActionButton fabFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        tvHeadline = findViewById(R.id.detailed_view_title);
        tvAuthor = findViewById(R.id.detailed_view_author);
        tvContent = findViewById(R.id.detailed_view_content);
        ivImage = findViewById(R.id.detailed_view_img);
        fabFavorite = findViewById(R.id.fab_favorites);

        currentArticle = getIntent().getParcelableExtra("CurrentArticle");
        tvHeadline.setText(currentArticle.getArticleTitle());
        tvAuthor.setText(currentArticle.getArticleAuthor());
        tvContent.setText(currentArticle.getContent() + "\n\nRead more: " + currentArticle.getArticleUrl());
        Glide.with(this).load(currentArticle.getImgUrl()).into(ivImage);

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        dbArticle = articleViewModel.getArticle(currentArticle.getArticleTitle());
        if (dbArticle != null) {
            fabFavorite.setImageResource(R.drawable.ic_nav_star);
        }
    }

    public void shareArticle(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "News Flash: " + currentArticle.getArticleTitle() + " " + currentArticle.getArticleUrl());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void favoriteArticle(View view) {
        Article article = new Article(currentArticle);
        dbArticle = articleViewModel.getArticle(currentArticle.getArticleTitle());

        if (dbArticle != null) {
            Toast.makeText(this, "Removed " + article.getTitle() + " Favorites", Toast.LENGTH_SHORT).show();
            articleViewModel.delete(article);
            fabFavorite.setImageResource(R.drawable.ic_nav_star_border);
        }
        else {
            Toast.makeText(this, "Added " + article.getTitle() + " To Favorites", Toast.LENGTH_SHORT).show();
            articleViewModel.insert(article);
            fabFavorite.setImageResource(R.drawable.ic_nav_star);
        }
    }
}
