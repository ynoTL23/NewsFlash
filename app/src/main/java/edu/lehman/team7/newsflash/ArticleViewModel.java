package edu.lehman.team7.newsflash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository articleRepository;
    private LiveData<List<Article>> allArticles;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleRepository = new ArticleRepository(application);
        allArticles = articleRepository.getAllWords();
    }

    LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    Article getArticle(String title) { return articleRepository.getArticle(title); }

    public void insert(Article article) {
        articleRepository.insert(article);
    }

    public void deleteAll() { articleRepository.deleteAll(); }

    public void delete(Article article) { articleRepository.delete(article); }
}
