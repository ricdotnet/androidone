package dev.ricr.androidone.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.ricr.androidone.LoginActivity;
import dev.ricr.androidone.R;

public class DashboardActivity extends AppCompatActivity {

  TextView textExample;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
    System.out.println(userData.getString("username", null));

    textExample = findViewById(R.id.text_example);
    textExample.setText("some weird text");

    textExample.setOnClickListener((View) -> {
      userData.edit().clear().apply();
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
    });
  }
}