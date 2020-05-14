package edu.lehman.team7.newsflash;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Article {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "img")
    private String img;

    @ColumnInfo(name = "content")
    private String content;

    public Article(@NonNull String title, String description, String author, String url, String img, String content) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.url = url;
        this.img = img;
        this.content = content;
    }

    public Article(ArticleItem article) {
        this.title = article.getArticleTitle();
        this.description = article.getArticleDesc();
        this.author = article.getArticleAuthor();
        this.url = article.getArticleUrl();
        this.img = article.getImgUrl();
        this.content = article.getContent();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }
}
