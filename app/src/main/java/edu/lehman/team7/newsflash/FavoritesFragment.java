package edu.lehman.team7.newsflash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

  public static final int NEW_ARTICLE_ACTIVITY_REQUEST_CODE = 1;
  private ArticleViewModel articleViewModel;
  private ArrayList<ArticleItem> articlesList;
  private ArticleAdapter articleAdapter;
  private SearchView searchView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_favorites, container, false);

    final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    articlesList = new ArrayList<>();

    articleAdapter = new ArticleAdapter(getActivity(), articlesList);
    recyclerView.setAdapter(articleAdapter);

    articlesList.add(new ArticleItem("Title here", "Description here"));
    articleAdapter.notifyDataSetChanged();

    articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
    articleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
      @Override
      public void onChanged(List<Article> articles) {
        ArrayList<ArticleItem> articleItems = new ArrayList<>();
        for (Article article : articles) {
          ArticleItem articleItem = new ArticleItem(article.getTitle(), article.getDescription(), article.getAuthor(), article.getUrl(), article.getImg(), article.getContent());
          articleItems.add(articleItem);
        }
        articlesList.clear();
        articlesList.addAll(articleItems);
        articleAdapter.notifyDataSetChanged();
      }
    });

    searchView = getActivity().findViewById(R.id.btnSearchView);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {

        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {

        return false;
      }
    });

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
              @Override
              public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
              }

              @Override
              public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Article article = new Article(articleAdapter.getArticleAtPosition(position));
                Toast.makeText(getActivity(), "will delete " + article.getTitle(), Toast.LENGTH_SHORT).show();
                articleViewModel.delete(article);

              }
            }
    );

    itemTouchHelper.attachToRecyclerView(recyclerView);

    return view;
  }

  private void clearList() {
    articlesList.clear();
    articleAdapter.notifyDataSetChanged();
  }
}
