package com.takashiharano.webclp.action;

import java.io.File;

import com.libutil.FileUtil;
import com.libutil.JsonBuilder;
import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.ProcessContext;

public class GetFileListAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String uploadDir = AppManager.getUploadPath();
    File[] files = FileUtil.listFiles(uploadDir);
    if (files == null) {
      files = new File[0];
    }

    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < files.length; i++) {
      File file = files[i];

      if (i > 0) {
        sb.append(",");
      }

      JsonBuilder jb = new JsonBuilder();
      jb.append("name", file.getName());
      jb.append("size", file.length());
      jb.append("lastModified", file.lastModified());

      sb.append(jb.toString());
    }
    sb.append("]");

    String body = sb.toString();
    context.sendJson("OK", body);
  }

}
