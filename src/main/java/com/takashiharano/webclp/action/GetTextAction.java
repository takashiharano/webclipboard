package com.takashiharano.webclp.action;

import com.libutil.StrUtil;
import com.takashiharano.webclp.Clipboard;
import com.takashiharano.webclp.ProcessContext;

public class GetTextAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String text = Clipboard.getText();
    String body = StrUtil.quote(text);
    context.sendJson("OK", body);
  }

}
