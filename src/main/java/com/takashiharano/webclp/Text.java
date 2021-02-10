package com.takashiharano.webclp;

public class Text {

  private String content;
  private long time;
  private long validityMillis;

  public Text(long validitySec) {
    this.content = "";
    this.validityMillis = validitySec * 1000;
  }

  public String getContent() {
    long now = System.currentTimeMillis();
    if ((validityMillis > 0) && (now - time > validityMillis)) {
      this.content = "";
    }
    return content;
  }

  public void setContent(String content) {
    this.time = System.currentTimeMillis();
    this.content = content;
  }

}
