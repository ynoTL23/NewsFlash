package edu.lehman.team7.newsflash;

public class ArticleItem {
  private String articleTitle, articleDesc, articleUrl, articleAuthor, imgUrl;

  public ArticleItem() {
    this.articleTitle = "THIS IS THE DEFAULT TITLE";
    this.articleDesc = "This is the default description for the article";
  }

  public ArticleItem(String title, String desc) {
    this.articleTitle = title;
    this.articleDesc = desc;
  }

  public ArticleItem(String title, String desc, String author, String url, String img) {
    this.articleTitle = title;
    this.articleDesc = desc;
    this.articleAuthor = author;
    this.articleUrl = url;
    this.imgUrl = img;
  }

  public String getArticleTitle() { return this.articleTitle; }
  public String getArticleDesc() { return this.articleDesc; }
  public String getArticleAuthor() { return this.articleAuthor; }
  public String getArticleUrl() { return this.articleUrl; }
  public String getImgUrl() { return this.imgUrl; }
}
