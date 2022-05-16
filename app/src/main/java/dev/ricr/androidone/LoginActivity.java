package dev.ricr.androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import dev.ricr.androidone.Tasks.LoginTask;
import dev.ricr.androidone.Helpers.InputHelper;
import dev.ricr.androidone.Views.EchoesActivity;
import dev.ricr.androidone.Views.RecoverPasswordActivity;

public class LoginActivity extends AppCompatActivity {

  TextView registerLink, recoverPasswordLink;
  EditText usernameInput, passwordInput;
  Button loginButton;

  SharedPreferences userData;

  private String firstName;
  private String lastName;
  private String avatar;

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
      Intent intent = new Intent(this, EchoesActivity.class);
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
    this.readUserData(string);

    Snackbar.make(getCurrentFocus(), "Logged with success", 5000).show();
    Intent intent = new Intent(this, EchoesActivity.class);

    SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
    userData.edit().putString("username", usernameInput.getText().toString()).apply();

    if (!this.firstName.isEmpty() && !this.lastName.isEmpty()) {
      userData.edit().putString("firstName", this.firstName).apply();
      userData.edit().putString("lastName", this.lastName).apply();
    }

    if (!this.avatar.isEmpty()) {
      userData.edit().putString("avatar", this.avatar).apply();
    }

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

  private void readUserData(String response) {
    InputStream in = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

    JsonReader jsonReader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));

    try {
      jsonReader.beginObject();
      while (jsonReader.hasNext()) {
        String key = jsonReader.nextName();
        if (key.equals("username")) {
          jsonReader.skipValue();
          //          if (!jsonReader.nextString().isEmpty()) {
          //          }
        } else if (key.equals("first_name")) {
          firstName = jsonReader.nextString();
        } else if (key.equals("last_name")) {
          lastName = jsonReader.nextString();
        } else if (key.equals("avatar")) {
          avatar = jsonReader.nextString();
        } else {
          jsonReader.skipValue();
        }
      }
      jsonReader.endObject();
      jsonReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}