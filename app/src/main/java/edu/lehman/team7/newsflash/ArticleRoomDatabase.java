package edu.lehman.team7.newsflash;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
    private static ArticleRoomDatabase INSTANCE;

    public static ArticleRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ArticleRoomDatabase.class, "article_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private  static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ArticleDao articleDao;
        Article[] articles = {new Article(new ArticleItem("Bob", "Test"))};

        PopulateDbAsync(ArticleRoomDatabase db) {
            articleDao = db.articleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleDao.deleteAll();

            for (int i = 0; i < articles.length; i++) {
                Article article = articles[i];
                articleDao.insert(article);
            }
            return null;
        }
    }
}
