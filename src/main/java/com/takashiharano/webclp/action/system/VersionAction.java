package com.takashiharano.webclp.action.system;

import com.takashiharano.util.Util;
import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.action.Action;

public class VersionAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String moduleName = AppManager.getModuleName();
    String version = AppManager.getVersionInfo(context);
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append(Util.quoteString("module"));
    sb.append(":");
    sb.append(Util.quoteString(moduleName));
    sb.append(",");
    sb.append(Util.quoteString("version"));
    sb.append(":");
    sb.append(Util.quoteString(version));
    sb.append("}");
    String json = sb.toString();
    context.sendJsonResponse(json);
  }

}
