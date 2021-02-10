package com.takashiharano.webclp.action;

import com.takashiharano.webclp.Clipboard;
import com.takashiharano.webclp.ProcessContext;

public class SaveTextAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String text = context.getRequestParameter("text");
    Clipboard.setText(text);
    context.sendJson("OK", null);
  }

}
