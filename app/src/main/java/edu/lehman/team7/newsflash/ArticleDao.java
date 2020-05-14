package edu.lehman.team7.newsflash;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Query("DELETE FROM favorites")
    void deleteAll();

    @Query("SELECT * FROM favorites")
    LiveData<List<Article>> getAllArticles();

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM favorites WHERE title = :title LIMIT 1")
    Article getArticle(String title);
}
