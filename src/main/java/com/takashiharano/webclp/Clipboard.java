package com.takashiharano.webclp;

public class Clipboard {
  private static Text text;

  public static void init(long validity) {
    text = new Text(validity);
  }

  public static String getText() {
    return text.getContent();
  }

  public static void setText(String content) {
    text.setContent(content);
  }

}
