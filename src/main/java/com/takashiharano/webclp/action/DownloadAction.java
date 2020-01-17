package com.takashiharano.webclp.action;

import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.Config;
import com.takashiharano.webclp.ProcessContext;

public class DownloadAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String uploadDir = AppManager.getAppHome() + "/" + Config.getValue("upload_dir");
    String fileName = context.getRequestParameter("file");
    String path = uploadDir + "/" + fileName;
    context.sendStreamResponse(path);
  }

}
