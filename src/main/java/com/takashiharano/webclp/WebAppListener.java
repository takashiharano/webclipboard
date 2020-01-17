package com.takashiharano.webclp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class WebAppListener implements ServletContextListener, HttpSessionListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    AppManager.onStart();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    AppManager.onStop();
  }

  @Override
  public void sessionCreated(HttpSessionEvent se) {
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
  }

}
