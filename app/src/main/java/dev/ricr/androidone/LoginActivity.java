package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import dev.ricr.androidone.Auth.LoginTask;
import dev.ricr.androidone.Views.DashboardActivity;
import dev.ricr.androidone.Views.RecoverPasswordActivity;

public class LoginActivity extends AppCompatActivity {

  TextView registerLink, recoverPasswordLink;
  EditText usernameInput, passwordInput;
  Button loginButton;

  View currentView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    currentView = findViewById(R.id.relativeLayout);

    registerLink = findViewById(R.id.register_link);
    recoverPasswordLink = findViewById(R.id.recover_password_link);
    usernameInput = findViewById(R.id.username_input);
    passwordInput = findViewById(R.id.password_input);
    loginButton = findViewById(R.id.login_button);

    SharedPreferences data = getSharedPreferences("data", Context.MODE_PRIVATE);
    data.edit().putString("username", "ricdotnet").apply();
    System.out.println(data.getString("username", null));

    setListeners();
  }

  public void onDoLogin(View view) {
    String username = usernameInput.getText().toString();
    String password = passwordInput.getText().toString();

    if (username.isEmpty()) {
      Snackbar.make(view, "Invalid username.", 5000).show();
      return;
    }

    if (password.isEmpty()) {
      Snackbar.make(view, "Invalid password.", 5000).show();
      return;
    }

    new LoginTask().doLogin(username, password, this);
  }

  public void onLoginSuccess(String string) {
    Snackbar.make(currentView, "Logged with success", 5000).show();
    Intent intent = new Intent(this, DashboardActivity.class);
    intent.putExtra("response", string);
    startActivity(intent);
  }

  public void onLoginError(String string) {
    Snackbar.make(currentView, string, 5000).show();
  }

  public void onRegisterClick(View view) {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  public void onRecoverClick(View view) {
//    System.out.println("clicked the recover password link");
    Intent intent = new Intent(this, RecoverPasswordActivity.class);
    startActivity(intent);
  }

  private void setListeners() {
    loginButton.setOnClickListener(this::onDoLogin);
    registerLink.setOnClickListener(this::onRegisterClick);
    recoverPasswordLink.setOnClickListener(this::onRecoverClick);
  }
}