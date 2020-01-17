package com.takashiharano.webclp.action.system;

import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.action.Action;

public class HelloAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    context.sendTextResponse("Hello!");
  }

}
