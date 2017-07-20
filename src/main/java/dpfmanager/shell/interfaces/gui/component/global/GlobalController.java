/**
 * <h1>ReportsController.java</h1> <p> This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version; or, at your
 * choice, under the terms of the Mozilla Public License, v. 2.0. SPDX GPL-3.0+ or MPL-2.0+. </p>
 * <p> This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License and the Mozilla Public License for more details. </p> <p> You should
 * have received a copy of the GNU General Public License and the Mozilla Public License along with
 * this program. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>
 * and at <a href="http://mozilla.org/MPL/2.0">http://mozilla.org/MPL/2.0</a> . </p> <p> NB: for the
 * © statement, include Easy Innova SL or other company/Person contributing the code. </p> <p> ©
 * 2015 Easy Innova, SL </p>
 *
 * @author Adria Llorens
 * @version 1.0
 * @since 23/7/2015
 */

package dpfmanager.shell.interfaces.gui.component.global;

import dpfmanager.conformancechecker.configuration.Configuration;
import dpfmanager.shell.core.config.BasicConfig;
import dpfmanager.shell.core.config.GuiConfig;
import dpfmanager.shell.core.mvc.DpfController;
import dpfmanager.shell.interfaces.gui.component.global.comparators.IndividualComparator;
import dpfmanager.shell.interfaces.gui.component.global.messages.GuiGlobalMessage;
import dpfmanager.shell.modules.conformancechecker.messages.ConformanceMessage;
import dpfmanager.shell.modules.messages.messages.AlertMessage;
import dpfmanager.shell.modules.report.core.GlobalReport;
import dpfmanager.shell.modules.report.core.SmallIndividualReport;
import dpfmanager.shell.modules.report.util.ReportIndividualGui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adria Llorens on 07/03/2016.
 */
public class GlobalController extends DpfController<GlobalModel, GlobalView> {

  public static Integer itemsPerPage = 12;

  private List<ReportIndividualGui> individuals;

  public GlobalController() {
  }

  public void readIndividualReports(String internal, Configuration config){
    individuals = new ArrayList<>();
    Integer count = 0;
    File serializedDirectory = new File(internal + "/serialized");
    if (serializedDirectory.exists() && serializedDirectory.isDirectory()){
      for (File individualSer : serializedDirectory.listFiles()){
        if (individualSer.exists() && individualSer.isFile() && individualSer.getName().endsWith(".ser")){
          individuals.add(new ReportIndividualGui(individualSer.getAbsolutePath(), config, count));
          count++;
        }
      }
    }
    sortIndividuals();
  }

  public void sortIndividuals(){
    individuals.sort(new IndividualComparator(getView().getCurrentMode(), getView().getCurrentOrder()));
  }

  public void loadAndPrintIndividuals(String vboxId, int page){
    int init = page * itemsPerPage;
    int end = init + itemsPerPage;
    int i = init;
    while (i < individuals.size() && i < end) {
      ReportIndividualGui rig = individuals.get(i);
      rig.setLast(i == individuals.size()-1 || i == end-1);
      getContext().send(GuiConfig.COMPONENT_GLOBAL, new GuiGlobalMessage(GuiGlobalMessage.Type.ADD_INDIVIDUAL, vboxId, rig));
      i++;
    }
  }

  public List<ReportIndividualGui> getIndividualReports(){
    return individuals;
  }

  public Integer getPagesCount(){
    int size = individuals.size();
    int pages = size / itemsPerPage;
    if (size % itemsPerPage > 0) pages++;
    return pages;
  }

  /**
   * Starts a new full check based on this quick check.
   */
  public boolean transformReport(){
    GlobalReport global = getView().getInfo().getGlobalReport();
    if (global.getConfig().isQuick()){
      List<String> inputFiles = getInputFiles(getView().isErrors(), getView().isWarnings(), getView().isCorrect(), true);
      List<String> inputNotFound = getInputFiles(getView().isErrors(), getView().isWarnings(), getView().isCorrect(), false);
      if (inputFiles.size() == 0) {
        getContext().send(BasicConfig.MODULE_MESSAGE, new AlertMessage(AlertMessage.Type.INFO, getBundle().getString("filesEmpty")));
      } else {
        getContext().send(BasicConfig.MODULE_CONFORMANCE, new ConformanceMessage(String.join(";", inputFiles), getView().getInfo().getGlobalReport().getConfig(), 100, false, false));
        return true;
      }
      if (inputNotFound.size() > 0) {
        getContext().send(BasicConfig.MODULE_MESSAGE, new AlertMessage(AlertMessage.Type.ALERT, getBundle().getString("filesNotFound"), String.join("\n", inputNotFound)));
      }
    }
    return false;
  }

  private List<String> getInputFiles(boolean err, boolean war, boolean pas, boolean found){
    List<String> inputFiles = new ArrayList<>();
    GlobalReport global = getView().getInfo().getGlobalReport();
    for (SmallIndividualReport individual : global.getIndividualReports()){
      int errors = 0;
      int warnings = 0;
      for (String iso : global.getSelectedIsos()){
        if (err && individual.getNErrors(iso) > 0)  errors++;
        else if (individual.getNWarnings(iso) > 0) warnings++;
      }
      if ((err && errors > 0) || (war && errors == 0 && warnings > 0) || (pas && errors == 0 && warnings == 0)) {
        String filePath = individual.getFilePath();
        if (new File(filePath).exists() == found){
          inputFiles.add(filePath);
        }
      }
    }
    return inputFiles;
  }

}