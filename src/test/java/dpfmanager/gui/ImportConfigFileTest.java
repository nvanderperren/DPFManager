package dpfmanager.gui;

import dpfmanager.shell.application.app.GuiApp;
import dpfmanager.shell.interfaces.gui.workbench.GuiWorkbench;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Created by Adrià Llorens on 07/01/2016.
 */
public class ImportConfigFileTest extends ApplicationTest {

  private String inputPath = "src/test/resources/ConfigFiles/config.dpf";

  Stage stage = null;

  @Override
  public void init() throws Exception {
    stage = launch(GuiApp.class, "-gui", "-test");
    scene = stage.getScene();
  }

  @Test
  public void testImportConfigFile() throws Exception {
    //Wait for async events
    WaitForAsyncUtils.waitForFxEvents();
    System.out.println("Running import and edit config file test...");

    //Set the input path
    GuiWorkbench.setTestParam("import", inputPath);

    //Import it
    clickOnScroll("#importButton");
    clickOnImportedConfig(inputPath);

    //Edit
    clickOnAndReload("#editButton","#pane-config");

    //Test step buttons
    clickOnAndReload("#step3");
    clickOnAndReload("#step2");
    clickOnAndReload("#step4");
    clickOnAndReload("#step6");
    clickOnAndReload("#step3");
    clickOnAndReload("#step1");

    //Go to summary and compare
    clickOnAndReload("#step6");
    FxAssert.verifyThat("#labIsos", NodeMatchers.hasText("Tiff/EP, Tiff/IT-1"));
    FxAssert.verifyThat("#labRules", NodeMatchers.hasText("ImageHeight < 1000"));
    FxAssert.verifyThat("#labReports", NodeMatchers.hasText("HTML, PDF"));
    FxAssert.verifyThat("#labFixes", NodeMatchers.hasText("Add Tag Artist 'EasyTest'"));
  }


}
