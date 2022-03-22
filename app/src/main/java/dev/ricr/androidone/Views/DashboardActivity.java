package dev.ricr.androidone.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import dev.ricr.androidone.R;

public class DashboardActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    Intent intent = getIntent();
    System.out.println(intent.getStringExtra("response"));
  }
}