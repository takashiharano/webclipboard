package com.takashiharano.webclp.action;

import com.takashiharano.webclp.ProcessContext;
import com.takashiharano.webclp.logic.DeleteFileLogic;

public class DeleteFileAction extends Action {

  @Override
  public void process(ProcessContext context) throws Exception {
    String fileName = context.getRequestParameter("file");
    DeleteFileLogic deleteFileLogic = new DeleteFileLogic();
    deleteFileLogic.process(context, fileName);
    Action.exec(context, "GetFileList");
  }

}
