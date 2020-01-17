package com.takashiharano.webclp.action;

import java.io.File;

import com.takashiharano.util.FileUtil;
import com.takashiharano.util.Util;
import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.Config;
import com.takashiharano.webclp.ProcessContext;

public class GetFileListAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String uploadDir = AppManager.getAppHome() + "/" + Config.getValue("upload_dir");
    File[] files = FileUtil.listFiles(uploadDir);
    if (files == null) {
      context.sendJson("ERR_FILE_LIST_IS_NULL", null);
      return;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < files.length; i++) {
      File file = files[i];

      if (i > 0) {
        sb.append(",");
      }
      sb.append("{");
      sb.append(Util.quoteString("name"));
      sb.append(":");
      sb.append(Util.quoteString(file.getName()));
      sb.append(",");

      sb.append(Util.quoteString("size"));
      sb.append(":");
      sb.append(file.length());
      sb.append(",");

      sb.append(Util.quoteString("lastModified"));
      sb.append(":");
      sb.append(file.lastModified());

      sb.append("}");
    }
    sb.append("]");

    String body = sb.toString();
    context.sendJson("OK", body);
  }

}
