package edu.lehman.team7.newsflash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

  private SwipeRefreshLayout swipeRefresh;
  private ArrayList<ArticleItem> articlesList;
  private ArticleAdapter articleAdapter;
  private SearchView searchView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    articlesList = new ArrayList<>();

    articleAdapter = new ArticleAdapter(getActivity(), articlesList);
    recyclerView.setAdapter(articleAdapter);

    initializeArticlesData();

    searchView = getActivity().findViewById(R.id.btnSearchView);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        findArticles(query);
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
        initializeArticlesData();
        return false;
      }
    });

    // when swiping to refresh
    swipeRefresh = view.findViewById(R.id.swipeRefreshLayout);
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        if (searchView.getQuery().toString().isEmpty()) {
          initializeArticlesData();
        } else {
          findArticles(searchView.getQuery().toString());
        }

        if (swipeRefresh.isRefreshing()) {
          swipeRefresh.setRefreshing(false);
        }
      }
    });

    return view;
  }

  private void initializeArticlesData() {
    // What should this do?

    // clear any existing data
    articlesList.clear();

    // 1 Make a call to the api and store the response
    // fetchAPI() or whatever

    new FetchNews(articlesList, articleAdapter).execute("top-headlines", "");
  }

  private void findArticles(String search)
  {
    articlesList.clear();
    new FetchNews(articlesList, articleAdapter).execute("everything", search);
  }
}
