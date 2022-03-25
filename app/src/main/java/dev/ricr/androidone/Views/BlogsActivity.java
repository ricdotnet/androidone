package dev.ricr.androidone.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.ricr.androidone.Fragments.DashboardFragment;
import dev.ricr.androidone.R;

public class BlogsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blogs);

    bottomNavigationView = findViewById(R.id.bottom_navigation);

    bottomNavigationView.setOnNavigationItemSelectedListener(this);
//    bottomNavigationView.setSelectedItemId(R.id.activity_blogs);
  }

  DashboardFragment dashboardFragment = new DashboardFragment();

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_blogs) {
//      System.out.println("going to blogs page");
      Intent intent = new Intent(this, BlogsActivity.class);
      startActivity(intent);
      return true;
    }

    if (item.getItemId() == R.id.menu_dashboard) {
      getSupportFragmentManager().beginTransaction().replace(R.id.container, dashboardFragment).commit();
      return false;
    }

    if (item.getItemId() == R.id.menu_logout) {
      System.out.println("logging out");
//      bottomNavigationView.setSelectedItemId(R.id.menu_logout);
      return false;
    }

    return false;
  }
}