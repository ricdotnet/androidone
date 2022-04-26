package dev.ricr.androidone.Types;

import java.util.Date;

public class Echo {

  // final because they are immutable
  private final String content;
  private final String username;
  private final String createdAt;

  public Echo(String username, String content, String createdAt) {
    this.username = username;
    this.content = content;
    this.createdAt = createdAt;
  }

  public String getContent() {
    return this.content;
  }

  public String getUsername() {
    return "@" + this.username;
  }

  public String getCreatedAt() {
    return this.createdAt;
  }

}
