package dev.ricr.androidone.Auth;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Helpers.HttpHandler;
import dev.ricr.androidone.LoginActivity;

public class LoginTask implements Executor {

  public void doLogin(String username, String password, LoginActivity c) {
    new Thread(() -> execute(() -> handleLogin(username, password, c))).start();
  }

  private void handleLogin(String username, String password, LoginActivity c) {
    String body = "{\"username\":\"" + username + "\", \"password\": \"" + password + "\" }";
    HttpHandler http = new HttpHandler("https://api.unispaces.uk/login", "POST", body);

    if (http.getErrorCode() == 200) {
      c.onLoginSuccess(http.getServerResponse());
    } else {
      c.onLoginError(http.getServerResponse());
    }
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }
}
