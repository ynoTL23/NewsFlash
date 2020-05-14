package edu.lehman.team7.newsflash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private Toolbar toolbar;
  private NavigationView navigationView;
  private FragmentManager fragmentManager;
  private FragmentTransaction fragmentTransaction;
  private ArticleViewModel articleViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    createNotificationChannel();

    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    drawerLayout = findViewById(R.id.drawer_layout);
    navigationView = findViewById(R.id.navView);

    navigationView.setNavigationItemSelectedListener(this);

    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
    drawerLayout.addDrawerListener(drawerToggle);
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerToggle.syncState();

    // load default fragment
    fragmentManager = getSupportFragmentManager();
    fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.frameLayout, new HomeFragment());
    fragmentTransaction.commit();

    articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

    //Here and beyond until the end of onCreate is Notification

    Intent intent = new Intent(MainActivity.this, NotificationBroadcast.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    long timeAtAppStart = System.currentTimeMillis();
    long tenSecondsInMillis = 1000 * 10;//actually about 2 mins
    long oneMinute = 1000 * 20;
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeAtAppStart + tenSecondsInMillis, oneMinute * 5, pendingIntent);


  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    // setup new fragment
    fragmentManager = getSupportFragmentManager();
    fragmentTransaction = fragmentManager.beginTransaction();

    // change the fragment
    switch (item.getItemId()) {
      case R.id.nav_home:
        setTitle(item.getTitle());
        fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
        break;
      case R.id.nav_favorites:
        setTitle(item.getTitle());
        fragmentTransaction.replace(R.id.frameLayout, new FavoritesFragment());
        break;
      case R.id.nav_settings:
        setTitle(item.getTitle());
        fragmentTransaction.replace(R.id.frameLayout, new SettingsFragment());
        break;
    }

    fragmentTransaction.commit(); // init the new fragment
    drawerLayout.closeDrawer(GravityCompat.START); // close the nav drawer

    return true;
  }

  private void createNotificationChannel() {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "LemubitReminderChannel";
      String description = "Channel for Lemubit Reminder";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel("notifyNewsFlash", name, importance);
      channel.setDescription(description);

      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

}
