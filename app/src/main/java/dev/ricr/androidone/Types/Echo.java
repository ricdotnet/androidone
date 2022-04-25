package dev.ricr.androidone.Types;

import java.util.Date;

public class Echo {

  // final because they are immutable
  private final String content;
  private final String username;
  private final Date createdAt;

  public Echo(String content, String username, Date createdAt) {
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

  public Date getCreatedAt() {
    return this.createdAt;
  }

}
