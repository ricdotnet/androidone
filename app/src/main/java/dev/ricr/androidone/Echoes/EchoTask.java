package dev.ricr.androidone.Echoes;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Fragments.NewEchoFragment;
import dev.ricr.androidone.Helpers.HttpHandler;

public class EchoTask implements Executor {

  public void postEcho(String username, String content, NewEchoFragment c) {
    new Thread(() -> execute(() -> handlePosting(username, content, c))).start();
  }

  private void handlePosting(String username, String content, NewEchoFragment c) {
    String body = "{\"username\": \"" + username + "\", \"content\": \"" + content + "\"}";

    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=echo&action=post", "POST", body);

    if (http.getErrorCode() == 200) {
      c.onPostSuccess(http.getServerResponse());
    } else {
      c.onPostError(http.getServerResponse());
    }
  }

  public void fetchEchoes() {
    new Thread(() -> execute(this::handleFetch)).start();
  }

  private void handleFetch() {
    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=echo&action=get", "POST", "");

    if (http.getErrorCode() == 200) {
      System.out.println(http.getServerResponse());
    } else {
      System.out.println("something went wrong...");
    }

  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

}
