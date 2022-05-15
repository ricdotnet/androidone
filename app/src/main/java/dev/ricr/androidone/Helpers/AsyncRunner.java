package dev.ricr.androidone.Helpers;

import java.util.concurrent.Executor;

public class AsyncRunner<T> implements Executor {

  public void runner(T c) {
    new Thread(() -> execute(() -> handler(c))).start();
  }

  private void handler(T c) {
    System.out.println("something ran...");
  }

  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

}
