package com.takashiharano.webclp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import com.takashiharano.util.Log;

public class Config {
  private static Properties properties;

  public static void load(String fipePath) throws Exception {
    Log.i("config load: " + fipePath);
    properties = new Properties();
    try (FileInputStream fis = new FileInputStream(fipePath)) {
      properties.load(fis);
    } catch (IOException ioe) {
      String message = "Failed to load properties: " + fipePath;
      throw new Exception(message, ioe);
    }
  }

  /**
   * Searches for the property with the specified key in this property list.If the
   * key is not found in this property list, the default property list,and its
   * defaults, recursively, are then checked. The method returns null if the
   * property is not found.
   *
   * @param key
   * @return
   */
  public static String getValue(String key) {
    String value = properties.getProperty(key);
    return value;
  }

  public static int getIntValue(String key) {
    String value = getValue(key);
    int v = Integer.parseInt(value);
    return v;
  }

  public static int getIntValue(String key, int defaultValue) {
    try {
      return getIntValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static long getLongValue(String key) {
    String value = getValue(key);
    long v = Long.parseLong(value);
    return v;
  }

  public static long getLongValue(String key, long defaultValue) {
    try {
      return getLongValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static float getFloatValue(String key) {
    String value = getValue(key);
    float v = Float.parseFloat(value);
    return v;
  }

  public static float getFloatValue(String key, float defaultValue) {
    try {
      return getFloatValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static double getDoubleValue(String key) {
    String value = getValue(key);
    double v = Double.parseDouble(value);
    return v;
  }

  public static double getDoubleValue(String key, double defaultValue) {
    try {
      return getDoubleValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static boolean getBooleanValue(String key) {
    return getBooleanValue(key, "true");
  }

  public static boolean getBooleanValue(String key, String trueValue) {
    String value = getValue(key);
    return trueValue.equals(value);
  }

  public static void dumpAllProperties() {
    Enumeration<?> nanmes = properties.propertyNames();
    while (nanmes.hasMoreElements()) {
      String name = (String) nanmes.nextElement();
      String value = getValue(name);
      Log.i(name + "=" + value);
    }
  }

}
