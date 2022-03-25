package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import dev.ricr.androidone.Auth.RegisterTask;
import dev.ricr.androidone.Helpers.InputHelper;

public class RegisterActivity extends AppCompatActivity {

  EditText usernameInput, passwordInput, emailInput;
  TextView loginLink;
  Button registerButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    usernameInput = findViewById(R.id.register_username);
    passwordInput = findViewById(R.id.register_password);
    emailInput = findViewById(R.id.register_email);

    registerButton = findViewById(R.id.register_button);
    loginLink = findViewById(R.id.login_link);

    setListeners();
  }

  private void doRegister(View view) {
    String username = usernameInput.getText().toString();
    String password = passwordInput.getText().toString();
    String email = emailInput.getText().toString();

    InputHelper.closeKeyboard(this, view);

    if (username.isEmpty()) {
      Snackbar.make(view, "Enter a username.", 5000).show();
      return;
    }

    if (password.isEmpty()) {
      Snackbar.make(view, "Enter a password.", 5000).show();
      return;
    }

    if (email.isEmpty()) {
      Snackbar.make(view, "Enter an email.", 5000).show();
      return;
    }

    new RegisterTask().doRegister(username, password, email, this);
  }

  private void goToLogin(View view) {
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
  }

  private void setListeners() {
    registerButton.setOnClickListener(this::doRegister);
    loginLink.setOnClickListener(this::goToLogin);
  }

  public void onRegisterSuccess(String response) {
    Snackbar.make(getCurrentFocus(), response, 5000).show();
  }

  public void onRegisterError(String response) {
    Snackbar.make(getCurrentFocus(), response, 5000).show();
  }
}