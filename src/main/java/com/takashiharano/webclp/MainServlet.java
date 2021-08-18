package com.takashiharano.webclp;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.libutil.JsonBuilder;
import com.takashiharano.webclp.action.Action;

@WebServlet(name = "MainServlet", urlPatterns = ("/main"))
@MultipartConfig(fileSizeThreshold = 0, location = "", maxFileSize = -1L, maxRequestSize = -1L)
public class MainServlet extends HttpServlet {

  private static final long serialVersionUID = -5960501271818225697L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ServletContext servletContext = getServletContext();
    ProcessContext context = new ProcessContext(request, response, servletContext);

    String paramVersion = context.getRequestParameter("version");
    if (paramVersion != null) {
      sendVersionInfo(context);
      return;
    }

    try {
      String actionName = context.getRequestParameter("action");
      Action.exec(context, actionName);
    } catch (Exception e) {
      context.sendJson("ERROR", e.toString());
    }
  }

  private void sendVersionInfo(ProcessContext context) throws IOException {
    String moduleName = AppManager.getModuleName();
    String version = null;
    try {
      version = AppManager.getVersionInfo(context);
    } catch (IOException e) {
      e.printStackTrace();
    }
    JsonBuilder jb = new JsonBuilder();
    jb.append("module", moduleName);
    jb.append("version", version);
    context.sendJson(jb);
  }

}
