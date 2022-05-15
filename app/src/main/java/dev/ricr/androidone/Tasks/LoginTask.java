package dev.ricr.androidone.Tasks;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Helpers.HttpHandler;
import dev.ricr.androidone.LoginActivity;

public class LoginTask implements Executor {

  public void doLogin(String username, String password, LoginActivity c) {
    new Thread(() -> execute(() -> handleLogin(username, password, c))).start();
  }

  private void handleLogin(String username, String password, LoginActivity c) {
    String queryParams = "&username=" + username + "&password=" + password;
    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=login" + queryParams, "POST", "");

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
