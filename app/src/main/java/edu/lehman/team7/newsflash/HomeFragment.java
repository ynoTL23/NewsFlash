package edu.lehman.team7.newsflash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    // when swiping to refresh
    swipeRefresh = view.findViewById(R.id.swipeRefreshLayout);
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        initializeArticlesData();
        if (swipeRefresh.isRefreshing()) {
          swipeRefresh.setRefreshing(false);
        }
      }
    });

    return view;
  }

  private void initializeArticlesData() {
    // What should this do?

    // 1 Make a call to the api and store the response
    // fetchAPI() or whatever

    // clear any existing data
    articlesList.clear();

    // 2 Extract the data into the articles arraylist
    //   - Data includes title, desc, author, etc (Found in constructor of ArticleItem.java
    // 3 For each article, create a new ArticleItem() and add it to the arraylist
    articlesList.add(new ArticleItem("Coronavirus US live: White House cancels briefing amid concerns over Trump remarks", "Coronavirus task force briefing originally scheduled for 5pm ET called off following Trump's suggestion last week that virus be treated with disinfectant"));
    articlesList.add(new ArticleItem("THIS IS A CUSTOM TITLE FOR THE ARTICLE", "This is the description for the article."));
    articlesList.add(new ArticleItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit", "Integer augue ipsum, suscipit eget eros vitae, maximus pharetra odio. Vivamus eget nisi dapibus, vestibulum mauris a, aliquam dui."));
    articlesList.add(new ArticleItem());
    articlesList.add(new ArticleItem());
    articlesList.add(new ArticleItem("THIS IS A CUSTOM TITLE FOR THE ARTICLE", "This is the description for the article."));
    articlesList.add(new ArticleItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque auctor elit nec dignissim semper.", "Nunc sit amet efficitur justo, in gravida tellus. Vivamus eu iaculis leo, in malesuada velit. Suspendisse ut arcu tortor. Maecenas commodo vel mauris at interdum. Morbi arcu mi, ultricies vitae mi in, imperdiet laoreet nulla. Integer quis turpis quis odio hendrerit consectetur et vel elit."));
    articlesList.add(new ArticleItem());
    articlesList.add(new ArticleItem("Coronavirus US live: White House cancels briefing amid concerns over Trump remarks", "Coronavirus task force briefing originally scheduled for 5pm ET called off following Trump's suggestion last week that virus be treated with disinfectant"));
    articlesList.add(new ArticleItem("THIS IS A CUSTOM TITLE FOR THE ARTICLE", "This is the description for the article."));

    // notify and update the view
    articleAdapter.notifyDataSetChanged();
  }
}
