package com.takashiharano.webclp.action;

import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.TextManager;

public class SaveTextAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String text = context.getRequestParameter("text");
    TextManager.setText(text);
    context.sendJson("OK", null);
  }

}
