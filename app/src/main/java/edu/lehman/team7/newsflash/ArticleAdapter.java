package edu.lehman.team7.newsflash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

  private ArrayList<ArticleItem> articlesList;
  private Context articleContext;

  public ArticleAdapter(Context context, ArrayList<ArticleItem> articlesList) {
    this.articlesList = articlesList;
    this.articleContext = context;
  }

  @Override
  public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(articleContext).inflate(R.layout.card_view, parent, false));
  }

  @Override
  public void onBindViewHolder(final ArticleAdapter.ViewHolder holder, int position) {
    // get current article
    ArticleItem currentArticle = articlesList.get(position);

    // Populate the views with data
    holder.bindTo(currentArticle);

    holder.mTextViewTitle.setText(articlesList.get(position).getArticleTitle());
    holder.mTextViewDesc.setText(articlesList.get(position).getArticleDesc());
  }

  @Override
  public int getItemCount() {
    return this.articlesList.size();
  }

  public ArticleItem getArticleAtPosition(int position){
    return articlesList.get(position);
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextViewTitle, mTextViewDesc;

    public ViewHolder(View itemView) {
      super(itemView);

      // init views
      mTextViewTitle = itemView.findViewById(R.id.articleTitle);
      mTextViewDesc = itemView.findViewById(R.id.articleDesc);

      // set onclick listeners
      itemView.setOnClickListener(this);
    }

    public void bindTo(ArticleItem currentArticle) {
      // Populate the views with data
      mTextViewTitle.setText(currentArticle.getArticleTitle());
      mTextViewDesc.setText(currentArticle.getArticleDesc());
    }

    @Override
    public void onClick(View v) {
      ArticleItem currentArticle = articlesList.get(getAdapterPosition());

      // when tapping an article, open detailed view.
      Intent detailedIntent = new Intent(articleContext, DetailedView.class);
      detailedIntent.putExtra("CurrentArticle", currentArticle);
      articleContext.startActivity(detailedIntent);
    }
  }
}