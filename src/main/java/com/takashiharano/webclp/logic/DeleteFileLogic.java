package com.takashiharano.webclp.logic;

import com.takashiharano.util.FileUtil;
import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.ProcessContext;

public class DeleteFileLogic {

  public void process(ProcessContext context, String fileName) throws Exception {
    String uploadDir = AppManager.getUploadPath();
    if ("*".equals(fileName)) {
      deleteAll(uploadDir);
    } else {
      delete(uploadDir, fileName);
    }
  }

  private void deleteAll(String parentDir) {
    String[] files = FileUtil.listFileNames(parentDir);
    for (int i = 0; i < files.length; i++) {
      delete(parentDir, files[i]);
    }
  }

  private void delete(String parentDir, String fileName) {
    String path = parentDir + "/" + fileName;
    if (FileUtil.exists(path)) {
      FileUtil.delete(path);
    }
  }

}
