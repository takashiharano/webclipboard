package com.takashiharano.webclp.action;

import com.takashiharano.webclp.AppManager;
import com.takashiharano.webclp.ProcessContext;

public abstract class Action {

  public static void exec(ProcessContext context, String name) throws Exception {
    Action action = getActionInstance(name);
    if (action == null) {
      context.sendJson("ACTION_NOT_FOUND", null);
      return;
    }
    action.process(context);
  }

  private static Action getActionInstance(String actionName) {
    String appBasePkgName = AppManager.getBasePackageName();
    String actionPkgName = appBasePkgName + ".action";

    actionName = actionName.substring(0, 1).toUpperCase() + actionName.substring(1);
    String classFullName = actionPkgName + "." + actionName + "Action";
    Action action = getBean(classFullName);
    if (action != null) {
      return action;
    }

    String[] subPackages = { "system" };
    for (int i = 0; i < subPackages.length; i++) {
      String subPkgName = subPackages[i];
      classFullName = actionPkgName + "." + subPkgName + "." + actionName + "Action";
      action = getBean(classFullName);
      if (action != null) {
        return action;
      }
    }

    return null;
  }

  private static Action getBean(String className) {
    try {
      Class<?> c = Class.forName(className);
      Action bean = (Action) c.getDeclaredConstructor().newInstance();
      return bean;
    } catch (Exception e) {
      return null;
    }
  }

  public abstract void process(ProcessContext context) throws Exception;

}
