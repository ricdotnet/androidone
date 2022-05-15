package dev.ricr.androidone.Echoes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

import dev.ricr.androidone.Fragments.EchoesFragment;
import dev.ricr.androidone.Fragments.NewEchoFragment;
import dev.ricr.androidone.Helpers.HttpHandler;

public class EchoTask implements Executor {

  public void postEcho(String username, String content, NewEchoFragment c) {
    new Thread(() -> execute(() -> handlePosting(username, content, c))).start();
  }

  private void handlePosting(String username, String content, NewEchoFragment c) {
    String[] strings = content.split("\n");

    StringBuilder finalContent = new StringBuilder();
    for (String s : strings) {
      finalContent.append(s).append("\n");
    }

    String body = "";
    JSONObject json = new JSONObject();
    try {
      json.put("username", username);
      json.put("content", content);
      body = json.toString();
    } catch (JSONException e) {
      e.printStackTrace();
    }

    if (body.isEmpty()) {
      c.onPostError("Something went wrong.");
    }

    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=echo&action=post", "POST", body);

    if (http.getErrorCode() == 200) {
      c.onPostSuccess(http.getServerResponse());
    } else {
      c.onPostError(http.getServerResponse());
    }
  }

  public void fetchEchoes(EchoesFragment c) {
    new Thread(() -> execute(() -> handleFetch(c))).start();
  }

  private void handleFetch(EchoesFragment c) {
    HttpHandler http = new HttpHandler("http://10.0.2.2:4001/index.php?type=echo&action=get", "POST", "");

    if (http.getErrorCode() == 200) {
      c.onFetchSuccess(http.getServerResponse());
    } else {
      c.onFetchError(http.getServerResponse());
    }
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

}
