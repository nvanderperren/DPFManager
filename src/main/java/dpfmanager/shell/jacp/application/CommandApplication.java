package dpfmanager.shell.jacp.application;

import dpfmanager.shell.jacp.application.launcher.noui.CommandLauncher;
import dpfmanager.shell.jacp.core.workbench.ConsoleWorkbench;
import javafx.stage.Stage;

import org.jacpfx.api.annotations.Resource;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.workbench.FXWorkbench;

/**
 * Created by Adrià Llorens on 01/03/2016.
 */
public abstract class CommandApplication extends CommandLauncher {

  @Resource
  public Context context;

  @Override
  protected Class<? extends FXWorkbench> getWorkbenchClass() {
    return ConsoleWorkbench.class;
  }

  @Override
  protected String[] getBasePackages() {
    return new String[]{
        "dpfmanager.shell.jacp.modules",                    // Dpf Modules
        "dpfmanager.shell.jacp.interfaces.console"          // Console Prespective
    };
  }

  @Override
  protected void postInit(Stage stage) {

  }

  @Override
  public String getXmlConfig() {
    return "DpfSpring.xml";
  }
}
