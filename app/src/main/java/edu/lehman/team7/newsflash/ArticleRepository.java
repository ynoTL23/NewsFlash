package edu.lehman.team7.newsflash;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ArticleRepository {
    private ArticleDao articleDao;
    private LiveData<List<Article>> allWords;
    private Article article;

    ArticleRepository(Application application) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getDatabase(application);
        articleDao = db.articleDao();
        allWords = articleDao.getAllArticles();
    }

    LiveData<List<Article>> getAllWords() {
        return allWords;
    }

    Article getArticle(String title) {
        Article article = null;
        try {
            article = new getArticleAsyncTask(articleDao).execute(title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return article;
    }

    private static class getArticleAsyncTask extends AsyncTask<String, Void, Article> {
        private ArticleDao asyncArticleDao;
        private Article article;

        getArticleAsyncTask(ArticleDao dao) {
            asyncArticleDao = dao;
        }

        @Override
        protected Article doInBackground(String... strings) {
            return asyncArticleDao.getArticle(strings[0]);
        }

        @Override
        protected void onPostExecute(Article article) {
            super.onPostExecute(article);
        }
    }

    public void insert(Article article) {
        new insertAsyncTask(articleDao).execute(article);
    }

    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao asyncArticleDao;

        insertAsyncTask(ArticleDao dao) {
            asyncArticleDao = dao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            asyncArticleDao.insert(articles[0]);
            return null;
        }
    }

    public void delete(Article article) {
        new deleteAsyncTask(articleDao).execute(article);
    }

    private static class deleteAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao asyncArticleDao;

        deleteAsyncTask(ArticleDao dao) {
            asyncArticleDao = dao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            asyncArticleDao.delete(articles[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllAsyncTask(articleDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArticleDao asyncArticleDao;

        deleteAllAsyncTask(ArticleDao dao) {
            asyncArticleDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncArticleDao.deleteAll();
            return null;
        }
    }
}
