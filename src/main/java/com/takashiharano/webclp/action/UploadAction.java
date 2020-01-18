package com.takashiharano.webclp.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.takashiharano.util.FileUtil;
import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.Config;
import com.takashiharano.webclp.ProcessContext;

public class UploadAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    HttpServletRequest request = context.getRequest();
    Collection<Part> parts = request.getParts();
    for (Part part : parts) {
      String name = part.getName();
      if (name.equals("files")) {
        String fileName = getFilename(part);
        if ((fileName != null) && !fileName.equals("")) {
          String uploadDir = AppManager.getAppHome() + "/" + Config.getValue("upload_dir");
          FileUtil.mkdir(uploadDir);
          String filePath = uploadDir + "/" + fileName;
          part.write(filePath);
        }
      }
    }
    context.forward("index.html");
  }

  private String getFilename(Part part) {
    String contentDisposition = part.getHeader("Content-Disposition");
    if (contentDisposition == null) {
      return null;
    }

    String[] cds = contentDisposition.split(";");
    for (String cd : cds) {
      if (cd.trim().startsWith("filename")) {
        String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
        if (fileName.contains("\\")) {
          fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
        }
        return fileName;
      }
    }

    return null;
  }

}
