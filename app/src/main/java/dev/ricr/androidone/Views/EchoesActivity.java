package dev.ricr.androidone.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.ricr.androidone.Fragments.EchoesFragment;
import dev.ricr.androidone.Fragments.DashboardFragment;
import dev.ricr.androidone.Fragments.NewEchoFragment;
import dev.ricr.androidone.LoginActivity;
import dev.ricr.androidone.R;

public class EchoesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_blogs);

    bottomNavigationView = findViewById(R.id.bottom_navigation);

    bottomNavigationView.setOnNavigationItemSelectedListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    getSupportFragmentManager().beginTransaction().replace(R.id.container, echoesFragment).commit();
  }

  EchoesFragment echoesFragment = new EchoesFragment();
  NewEchoFragment newEchoFragment = new NewEchoFragment();
  DashboardFragment dashboardFragment = new DashboardFragment();

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_home) {
      getSupportFragmentManager().beginTransaction().replace(R.id.container, echoesFragment).commit();
      return true;
    }

    if (item.getItemId() == R.id.menu_new) {
      getSupportFragmentManager().beginTransaction().replace(R.id.container, newEchoFragment).commit();
      return true;
    }

    if (item.getItemId() == R.id.menu_dashboard) {
      getSupportFragmentManager().beginTransaction().replace(R.id.container, dashboardFragment).commit();
      return true;
    }

    // logout/clear user data and go to login activity
    if (item.getItemId() == R.id.menu_logout) {
      SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);

      userData.edit().clear().apply();
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      return false;
    }

    return false;
  }
}