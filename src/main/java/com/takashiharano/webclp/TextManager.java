package com.takashiharano.webclp;

public class TextManager {
  private static String text = "";

  public static String getText() {
    return text;
  }

  public static void setText(String text) {
    TextManager.text = text;
  }

}
