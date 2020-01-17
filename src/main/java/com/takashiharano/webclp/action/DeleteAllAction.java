package com.takashiharano.webclp.action;

import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.TextManager;
import com.takashiharano.webclp.logic.DeleteFileLogic;

public class DeleteAllAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    TextManager.setText("");
    DeleteFileLogic deleteFileLogic = new DeleteFileLogic();
    deleteFileLogic.process(context, "*");
    context.sendJson("OK", null);
  }

}
