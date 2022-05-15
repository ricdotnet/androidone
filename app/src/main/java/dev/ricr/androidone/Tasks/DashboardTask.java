package dev.ricr.androidone.Tasks;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Fragments.DashboardFragment;
import dev.ricr.androidone.Helpers.HttpHandler;

public class DashboardTask implements Executor {

  public void doSaveProfile(String username, String password, String email, DashboardFragment c) {
    new Thread(() -> execute(() -> handleSaveProfile(username, password, email, c))).start();
  }

  private void handleSaveProfile(String username, String password, String email, DashboardFragment c) {
    String queryParams = "&username=" + username + "&password=" + password + "&email=" + email;
    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=register" + queryParams, "GET", "");

    if (http.getErrorCode() == 200) {
      c.onUpdateSuccess(http.getServerResponse());
    } else {
      c.onUpdateError(http.getServerResponse());
    }
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

}
