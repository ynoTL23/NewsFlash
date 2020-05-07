package edu.lehman.team7.newsflash;

public class ArticleItem {
  private String articleTitle, articleDesc, articleUrl, articleAuthor, imgUrl, content;

  public ArticleItem() {
    this.articleTitle = "THIS IS THE DEFAULT TITLE";
    this.articleDesc = "This is the default description for the article";
  }

  public ArticleItem(String title, String desc) {
    this.articleTitle = title;
    this.articleDesc = desc;
  }

  public ArticleItem(String title, String desc, String author, String url, String img, String content) {
    this.articleTitle = title;
    this.articleDesc = desc;
    this.articleAuthor = author;
    this.articleUrl = url;
    this.imgUrl = img;
    this.content = content;
  }

  public String getArticleTitle() { return this.articleTitle; }
  public String getArticleDesc() { return this.articleDesc; }
  public String getArticleAuthor() { return this.articleAuthor; }
  public String getArticleUrl() { return this.articleUrl; }
  public String getImgUrl() { return this.imgUrl; }
  public String getContent() { return this.content; }
}
