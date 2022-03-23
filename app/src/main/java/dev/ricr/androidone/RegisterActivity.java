package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

  EditText usernameInput, passwordInput, emailInput;
  TextView loginLink;
  Button registerButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    registerButton = findViewById(R.id.register_button);
    loginLink = findViewById(R.id.login_link);

    setListeners();
  }

  private void doRegister(View view) {
    System.out.println("registering user....");
  }

  private void goToLogin(View view) {
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
  }

  private void setListeners() {
    registerButton.setOnClickListener(this::doRegister);
    loginLink.setOnClickListener(this::goToLogin);
  }
}