package com.takashiharano.webclp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import com.libutil.FileUtil;

public class Config {
  private Properties properties;

  public Config(String fipePath) {
    properties = new Properties();
    if (FileUtil.exists(fipePath)) {
      load(fipePath);
    } else {
      Log.i("Config file not found. Skip loading.");
    }
  }

  private void load(String fipePath) {
    Log.i("config load: " + fipePath);
    properties = new Properties();
    try (FileInputStream fis = new FileInputStream(fipePath)) {
      properties.load(fis);
    } catch (IOException ioe) {
      String message = "Failed to load properties: " + fipePath;
      Log.e(message);
      throw new RuntimeException(message, ioe);
    }
  }

  /**
   * Searches for the property with the specified key in this property list.If the
   * key is not found in this property list, the default property list,and its
   * defaults, recursively, are then checked. The method returns null if the
   * property is not found.
   *
   * @param key
   *          Key
   * @return the value in this property list with the specified key value.
   */
  public String getValue(String key) {
    String value = properties.getProperty(key);
    return value;
  }

  public String getValue(String key, String defaultValue) {
    String value = properties.getProperty(key);
    if (value == null) {
      value = defaultValue;
    }
    return value;
  }

  /**
   * Returns the integer value.
   *
   * @param key
   *          key
   * @return the value in this property list with the specified key value.
   */
  public int getIntValue(String key) {
    String value = getValue(key);
    int v = Integer.parseInt(value);
    return v;
  }

  /**
   * Returns the integer value.
   *
   * @param key
   *          key
   * @param defaultValue
   *          the value if specified key is not found
   * @return the value in this property list with the specified key value.
   */
  public int getIntValue(String key, int defaultValue) {
    try {
      return getIntValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Returns the long value.
   *
   * @param key
   *          key
   * @return the value in this property list with the specified key value.
   */
  public long getLongValue(String key) {
    String value = getValue(key);
    long v = Long.parseLong(value);
    return v;
  }

  /**
   * Returns the long value.
   *
   * @param key
   *          key
   * @param defaultValue
   *          the value if specified key is not found
   * @return the value in this property list with the specified key value.
   */
  public long getLongValue(String key, long defaultValue) {
    try {
      return getLongValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Returns the float value.
   *
   * @param key
   *          key
   * @return the value in this property list with the specified key value.
   */
  public float getFloatValue(String key) {
    String value = getValue(key);
    float v = Float.parseFloat(value);
    return v;
  }

  /**
   * Returns the float value.
   *
   * @param key
   *          key
   * @param defaultValue
   *          the value if specified key is not found
   * @return the value in this property list with the specified key value.
   */
  public float getFloatValue(String key, float defaultValue) {
    try {
      return getFloatValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Returns the double value.
   *
   * @param key
   *          key
   * @return the value in this property list with the specified key value.
   */
  public double getDoubleValue(String key) {
    String value = getValue(key);
    double v = Double.parseDouble(value);
    return v;
  }

  /**
   * Returns the double value.
   *
   * @param key
   *          key
   * @param defaultValue
   *          the value if specified key is not found
   * @return the value in this property list with the specified key value.
   */
  public double getDoubleValue(String key, double defaultValue) {
    try {
      return getDoubleValue(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Returns the boolean value.
   *
   * @param key
   *          key
   * @return the value in this property list with the specified key value.
   */
  public boolean getBooleanValue(String key) {
    return getBooleanValue(key, "true");
  }

  /**
   * Returns the boolean value.
   *
   * @param key
   *          key
   * @param trueValue
   *          the value if specified key is not found
   * @return the value in this property list with the specified key value.
   */
  public boolean getBooleanValue(String key, String trueValue) {
    String value = getValue(key);
    return trueValue.equals(value);
  }

  public void dumpAllProperties() {
    Enumeration<?> nanmes = properties.propertyNames();
    while (nanmes.hasMoreElements()) {
      String name = (String) nanmes.nextElement();
      String value = getValue(name);
      Log.i(name + "=" + value);
    }
  }

}
