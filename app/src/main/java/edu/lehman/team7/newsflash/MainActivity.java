package edu.lehman.team7.newsflash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
}
