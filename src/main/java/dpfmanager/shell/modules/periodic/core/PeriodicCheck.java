package dpfmanager.shell.modules.periodic.core;


import java.util.ResourceBundle;

/**
 * Created by Adrià Llorens on 01/07/2016.
 */
public class PeriodicCheck {

  private static String EL = "\n";

  private String uuid;
  private String input;
  private String configuration;
  private Periodicity periodicity;

  public PeriodicCheck() {
    uuid = "dpf-" + System.currentTimeMillis();
    input = null;
    configuration = null;
    periodicity = null;
  }

  public PeriodicCheck(String uuid) {
    this.uuid = uuid;
    input = null;
    configuration = null;
    periodicity = null;
  }

  public PeriodicCheck(String uuid, String input, String configuration, Periodicity periodicity) {
    this.uuid = uuid;
    this.input = input;
    this.configuration = configuration;
    this.periodicity = periodicity;
  }

  /**
   * Getters and setters
   */

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  public Periodicity getPeriodicity() {
    return periodicity;
  }

  public void setPeriodicity(Periodicity periodicity) {
    this.periodicity = periodicity;
  }

  /**
   * To String
   */
  public String toString(ResourceBundle bundle) {
    String text = "ID: " + uuid + EL;
    text += "   " + bundle.getString("input") + " " + input + EL;
    text += "   " + bundle.getString("configuration") + " " + configuration + EL;
    text += "   " + bundle.getString("periodicity") + " " + periodicity.toString(bundle) + EL;
    return text;
  }
}
