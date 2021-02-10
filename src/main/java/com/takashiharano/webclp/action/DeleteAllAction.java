package com.takashiharano.webclp.action;

import com.takashiharano.webclp.Clipboard;
import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.logic.DeleteFileLogic;

public class DeleteAllAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    Clipboard.setText("");
    DeleteFileLogic deleteFileLogic = new DeleteFileLogic();
    deleteFileLogic.process(context, "*");
    context.sendJson("OK", null);
  }

}
