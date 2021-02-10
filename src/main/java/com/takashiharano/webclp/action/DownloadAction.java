package com.takashiharano.webclp.action;

import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.ProcessContext;

public class DownloadAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String uploadDir = AppManager.getAppHome() + "/" + AppManager.getConfigValue("upload_dir");
    String fileName = context.getRequestParameter("file");
    String path = uploadDir + "/" + fileName;
    context.sendStreamResponse(path);
  }

}
