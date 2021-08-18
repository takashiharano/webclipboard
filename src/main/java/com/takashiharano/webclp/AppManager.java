package com.takashiharano.webclp;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import com.libutil.FileUtil;
import com.libutil.StrUtil;

public class AppManager {

  private static final String APPHOME_BASENAME = "webapphome";
  private static final String PROPERTIES_FILENAME = "app.properties";
  private static final String CONFIGKEY_WORKSPACE = "app_workspace";

  private static AppManager instance;
  private static Config config;
  private static String errorInfo;
  private static String appHomePath;
  private static String appWorkspacePath;

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
    return AppInfo.BASE_PACKAGE_NAME;
  }

  public static String getModuleName() {
    return AppInfo.MODULE_NAME;
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

  public static String getAppWorkspacePath() {
    return appWorkspacePath;
  }

  private static void init() {
    errorInfo = null;
    try {
      _init();
      Log.i("[OK] ==> APP READY");
    } catch (Exception e) {
      errorInfo = e.getMessage();
      Log.i("[NG] ==> APP INIT ERROR : " + errorInfo);
    }
  }

  private static void _init() throws Exception {
    String homePath = System.getenv("HOME");
    if (homePath == null) {
      throw new Exception("System env \"HOME\" is not defined.");
    }
    int logLevel = Log.LogLevel.DEBUG.getLevel();
    Log.setup(logLevel, AppInfo.MODULE_NAME);
    appHomePath = homePath + "/" + APPHOME_BASENAME + "/" + AppInfo.MODULE_NAME;
    Log.i("WebAppHome: " + appHomePath);

    String propFilePath = appHomePath + "/" + PROPERTIES_FILENAME;
    if (FileUtil.notExist(propFilePath)) {
      throw new Exception("App config not found: path=" + propFilePath);
    }
    config = new Config(propFilePath);

    appWorkspacePath = config.getValue(CONFIGKEY_WORKSPACE);
    if (StrUtil.isEmpty(appWorkspacePath)) {
      appWorkspacePath = appHomePath;
    }
    Log.i("WebAppWorkspace: " + appWorkspacePath);

    long validity = 0;
    try {
      validity = config.getLongValue("clipboard_validity_sec", 300);
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
  public static String getManifestEntry(ProcessContext context, String name) throws IOException {
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
    String buildTime = getManifestEntry(context, "Build-Timestamp");
    return buildTime;
  }

  public static String getUploadPath() {
    return getAppWorkspacePath() + "/upload";
  }

}
