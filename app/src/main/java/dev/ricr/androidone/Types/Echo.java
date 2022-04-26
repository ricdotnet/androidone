package dev.ricr.androidone.Types;

import java.util.Date;

public class Echo {

  // final because they are immutable
  private final String content;
  private final String username;
  private final String createdAt;

  public Echo(String content, String username, String createdAt) {
    this.content = content;
    this.username = username;
    this.createdAt = createdAt;
  }

  public String getContent() {
    return this.content;
  }

  public String getUsername() {
    return this.username;
  }

  public String getCreatedAt() {
    return this.createdAt;
  }

}
