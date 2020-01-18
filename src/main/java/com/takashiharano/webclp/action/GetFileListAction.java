package com.takashiharano.webclp.action;

import java.io.File;

import com.takashiharano.util.FileUtil;
import com.takashiharano.util.json.JsonBuilder;
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

      JsonBuilder jb = new JsonBuilder();
      jb.add("name", file.getName());
      jb.add("size", file.length());
      jb.add("lastModified", file.lastModified());

      sb.append(jb.toString());
    }
    sb.append("]");

    String body = sb.toString();
    context.sendJson("OK", body);
  }

}
