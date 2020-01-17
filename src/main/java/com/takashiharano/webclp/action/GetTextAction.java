package com.takashiharano.webclp.action;

import com.takashiharano.util.Util;
import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.TextManager;

public class GetTextAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String text = TextManager.getText();
    String body = Util.quoteString(text);
    context.sendJson("OK", body);
  }

}
