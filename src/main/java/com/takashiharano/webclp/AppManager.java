package com.takashiharano.webclp;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import com.takashiharano.util.Log;

public class AppManager {
  private static final String BASE_PACKAGE_NAME = "com.takashiharano.webclp";
  private static final String MODULE_NAME = "webclp";

  private static AppManager instance;
  private static Config config;
  private static String errorInfo;

  private AppManager() {
  }

  public static AppManager getInstance() {
    if (instance == null) {
      instance = new AppManager();
    }
    return instance;
  }

  public static void onStart() {
    init();
  }

  public static void onStop() {
    Log.i("The app has been stopped.");
  }

  public static String getBasePackageName() {
    return BASE_PACKAGE_NAME;
  }

  public static String getModuleName() {
    return MODULE_NAME;
  }

  public static void reset() {
    Log.i("Resetting app...");
    init();
  }

  public static boolean isReady() {
    return errorInfo == null;
  }

  public static String getErrorInfo() {
    return errorInfo;
  }

  public static String getAppHome() {
    return config.getValue("app_home");
  }

  private static void init() {
    errorInfo = null;
    try {
      _init();
      Log.i("== Ready ==");
    } catch (Exception e) {
      errorInfo = e.getMessage();
    }
  }

  private static void _init() throws Exception {
    String home = System.getenv("HOME");
    if (home == null) {
      throw new Exception("System env \"HOME\" is not defined.");
    }
    int logLevel = Log.LogLevel.DEBUG.getLevel();
    Log.init(logLevel, MODULE_NAME);
    String propFilePath = home + "/webappconf/" + MODULE_NAME + ".properties";
    config = new Config(propFilePath);

    long validity = 0;
    try {
      validity = config.getLongValue("clipboard_validity_sec");
    } catch (Exception e) {
    }
    Clipboard.init(validity);
  }

  public static String getConfigValue(String key) {
    return config.getValue(key);
  }

  public static String getConfigValue(String key, String defaultValue) {
    return config.getValue(key);
  }

  public static int getConfigIntValue(String key) {
    return config.getIntValue(key);
  }

  public static float getConfigFloatValue(String key) {
    return config.getFloatValue(key);
  }

  public static double getConfigDoubleValue(String key) {
    return config.getDoubleValue(key);
  }

  public static boolean getConfigBooleanValue(String key) {
    return config.getBooleanValue(key);
  }

  /**
   * Returns the contents of MANIFEST.MF.
   *
   * @param context
   * @param name
   * @return
   */
  public static String readManifest(ProcessContext context, String name) throws IOException {
    ServletContext servletContext = context.getServletContext();
    String value = null;
    try (InputStream is = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")) {
      Manifest manifest = new Manifest(is);
      Attributes attributes = manifest.getMainAttributes();
      value = attributes.getValue(name);
    } catch (IOException ioe) {
      throw ioe;
    }
    return value;
  }

  /**
   * Returns version info from MANIFEST.MF.
   *
   * @param context
   * @return
   */
  public static String getVersionInfo(ProcessContext context) throws IOException {
    String buildTime = readManifest(context, "Build-Timestamp");
    return buildTime;
  }

}
