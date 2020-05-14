package edu.lehman.team7.newsflash;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleItem implements Parcelable {
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

    protected ArticleItem(Parcel in) {
        articleTitle = in.readString();
        articleDesc = in.readString();
        articleUrl = in.readString();
        articleAuthor = in.readString();
        imgUrl = in.readString();
        content = in.readString();
    }

    public static final Creator<ArticleItem> CREATOR = new Creator<ArticleItem>() {
        @Override
        public ArticleItem createFromParcel(Parcel in) {
            return new ArticleItem(in);
        }

        @Override
        public ArticleItem[] newArray(int size) {
            return new ArticleItem[size];
        }
    };

    public String getArticleTitle() { return this.articleTitle; }
  public String getArticleDesc() { return this.articleDesc; }
  public String getArticleAuthor() { return this.articleAuthor; }
  public String getArticleUrl() { return this.articleUrl; }
  public String getImgUrl() { return this.imgUrl; }
  public String getContent() { return this.content; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(articleTitle);
        dest.writeString(articleDesc);
        dest.writeString(articleUrl);
        dest.writeString(articleAuthor);
        dest.writeString(imgUrl);
        dest.writeString(content);
    }
}
