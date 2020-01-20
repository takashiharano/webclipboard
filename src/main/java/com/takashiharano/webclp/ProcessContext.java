package com.takashiharano.webclp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.takashiharano.util.FileUtil;
import com.takashiharano.util.JsonBuilder;
import com.takashiharano.util.Log;

public class ProcessContext {
  private HttpServletRequest request;
  private HttpServletResponse response;
  private ServletContext servletContext;

  public ProcessContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
    try {
      request.setCharacterEncoding("UTF-8");
    } catch (UnsupportedEncodingException e) {
      Log.e(e);
    }
    this.request = request;
    this.response = response;
    this.servletContext = servletContext;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }

  public ServletContext getServletContext() {
    return servletContext;
  }

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public String getRequestParameter(String key) {
    return request.getParameter(key);
  }

  public String getDecodedRequestParameter(String key) {
    String value = getRequestParameter(key);
    String decodedValue = null;
    try {
      decodedValue = URLDecoder.decode(value, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return decodedValue;
  }

  public void forward(String path) throws IOException, ServletException {
    request.getRequestDispatcher(path).forward(request, response);
  }

  public void sendTextResponse(String text) throws IOException {
    sendResponse("text/plain", text);
  }

  public void sendJsonResponse(String json) throws IOException {
    sendResponse("application/json", json);
  }

  public void sendResponse(String contentType, String text) throws IOException {
    response.setContentType(contentType + ";charset=utf-8");
    PrintWriter writer = response.getWriter();
    writer.write(text);
    writer.close();
  }

  public void sendJson(String status, String body) throws IOException {
    JsonBuilder jb = new JsonBuilder();
    jb.append("status", status);
    jb.appendObject("body", body);
    sendJsonResponse(jb.toString());
  }

  public void sendStreamResponse(String path) throws IOException {
    String fileName = FileUtil.getFileName(path);
    response.setContentType("application/octet-stream");
    String encodedFileName;
    try {
      encodedFileName = URLEncoder.encode(fileName, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      encodedFileName = "download";
    }
    String contentDisposition = "attachment;filename=\"" + fileName + "\";filename*=utf-8''" + encodedFileName;
    response.setHeader("Content-Disposition", contentDisposition);

    try (ServletOutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);) {

      byte[] b = new byte[1 * 1024 * 1024];
      int readSize = 0;

      while (-1 != (readSize = dis.read(b))) {
        os.write(b, 0, readSize);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
  }

}
