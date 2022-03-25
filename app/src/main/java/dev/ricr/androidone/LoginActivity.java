package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import dev.ricr.androidone.Auth.LoginTask;
import dev.ricr.androidone.Helpers.InputHelper;
import dev.ricr.androidone.Views.BlogsActivity;
import dev.ricr.androidone.Views.DashboardActivity;
import dev.ricr.androidone.Views.RecoverPasswordActivity;

public class LoginActivity extends AppCompatActivity {

  TextView registerLink, recoverPasswordLink;
  EditText usernameInput, passwordInput;
  Button loginButton;

  SharedPreferences userData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    registerLink = findViewById(R.id.register_link);
    recoverPasswordLink = findViewById(R.id.recover_password_link);
    usernameInput = findViewById(R.id.username_input);
    passwordInput = findViewById(R.id.password_input);
    loginButton = findViewById(R.id.login_button);

    userData = getSharedPreferences("userData", Context.MODE_PRIVATE);

    setListeners();
  }

  @Override
  public void onResume() {
    super.onResume();
    String currentUser = userData.getString("username", null);

    if (currentUser != null) {
      Intent intent = new Intent(this, BlogsActivity.class);
      startActivity(intent);
    }
  }

  public void onDoLogin(View view) {
    InputHelper.closeKeyboard(this, view);

    String username = usernameInput.getText().toString();
    String password = passwordInput.getText().toString();

    if (username.isEmpty()) {
      Snackbar.make(view, "Enter a username.", 5000).show();
      return;
    }

    if (password.isEmpty()) {
      Snackbar.make(view, "Enter a password.", 5000).show();
      return;
    }

    new LoginTask().doLogin(username, password, this);
  }

  public void onLoginSuccess(String string) {
    Snackbar.make(getCurrentFocus(), "Logged with success", 5000).show();
    Intent intent = new Intent(this, BlogsActivity.class);

    SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
    userData.edit().putString("username", usernameInput.getText().toString()).apply();

    intent.putExtra("response", string);
    startActivity(intent);
  }

  public void onLoginError(String string) {
    Snackbar.make(getCurrentFocus(), string, 5000).show();
  }

  public void onRegisterClick(View view) {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  public void onRecoverClick(View view) {
    Intent intent = new Intent(this, RecoverPasswordActivity.class);
    startActivity(intent);
  }

  private void setListeners() {
    loginButton.setOnClickListener(this::onDoLogin);
    registerLink.setOnClickListener(this::onRegisterClick);
    recoverPasswordLink.setOnClickListener(this::onRecoverClick);
  }
}