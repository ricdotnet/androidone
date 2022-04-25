package dev.ricr.androidone.Auth;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Helpers.HttpHandler;
import dev.ricr.androidone.RegisterActivity;

public class RegisterTask implements Executor {

  public void doRegister(String username, String password, String email, RegisterActivity c) {
    new Thread(() -> execute(() -> handleRegister(username, password, email, c))).start();
  }

  private void handleRegister(String username, String password, String email, RegisterActivity c) {
    String queryParams = "&username=" + username + "&password=" + password + "&email=" + email;
    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=register" + queryParams, "GET", "");

    if (http.getErrorCode() == 200) {
      c.onRegisterSuccess(http.getServerResponse());
    } else {
      c.onRegisterError(http.getServerResponse());
    }
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

}
