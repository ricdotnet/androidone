package dev.ricr.androidone.Auth;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Helpers.HttpHandler;
import dev.ricr.androidone.RegisterActivity;

public class RegisterTask implements Executor {

  public void doRegister(String username, String password, String email, RegisterActivity c) {
    new Thread(() -> execute(() -> handleRegister(username, password, email, c))).start();
  }

  private void handleRegister(String username, String password, String email, RegisterActivity c) {
    String body = "{\"username\":\"" + username + "\", \"password\": \"" + password + "\" }";
    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/user/register", "POST", body);

    if (http.getErrorCode() == 200) {
      c.onRegisterSuccess();
    } else {
      c.onRegisterError();
    }
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

}
