package dpfmanager.commandline;

import static java.io.File.separator;

import dpfmanager.shell.interfaces.console.commandline.CommandLineApp;
import dpfmanager.shell.core.DPFManagerProperties;
import dpfmanager.shell.modules.report.ReportGenerator;
import javafx.application.Application;

import com.easyinnova.tiff.io.TiffInputStream;
import com.easyinnova.tiff.model.TiffDocument;
import com.easyinnova.tiff.reader.TiffReader;
import com.easyinnova.tiff.writer.TiffWriter;

import junit.framework.TestCase;

import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Easy on 20/07/2015.
 */
public class TiffWriterTest extends TestCase {
  TiffReader tr;

  /**
   * Pre test.
   */
  @Before
  public void PreTest() {
    DPFManagerProperties.setFeedback(false);

    boolean ok = true;
    try {
      tr = new TiffReader();
    } catch (Exception e) {
      ok = false;
    }
    assertEquals(ok, true);
  }

  public void testReports1() throws Exception {
    DPFManagerProperties.setFeedback(false);

    String[] args = new String[2];
    args[0] = "src/test/resources/TestWriter";
    args[1] = "-s";

    tr = new TiffReader();
    tr.readFile("src" + separator + "test" + separator + "resources" + separator + "TestWriter" + separator + "Bilevel.tif");
    TiffInputStream ti = new TiffInputStream(new File("src" + separator + "test" + separator + "resources" + separator + "TestWriter" + separator + "Bilevel.tif"));
    TiffDocument td = tr.getModel();

    TiffWriter tw = new TiffWriter(ti);
    tw.SetModel(td);
    tw.write("src" + separator + "test" + separator + "resources" + separator + "TestWriter" + separator + "Bilevel2.tif");

    Application.Parameters params = new Application.Parameters() {
      @Override
      public List<String> getRaw() {
        ArrayList<String> listRaw = new ArrayList<String>();
        listRaw.add(args[0]);
        listRaw.add(args[1]);
        return listRaw;
      }

      @Override
      public List<String> getUnnamed() {
        ArrayList<String> listRaw = new ArrayList<String>();
        listRaw.add(args[0]);
        listRaw.add(args[1]);
        return listRaw;
      }

      @Override
      public Map<String, String> getNamed() {
        return null;
      }
    };


    CommandLineApp cl = new CommandLineApp(params);
    cl.launch();

    String path = getPath();
    String xmlFile = path + "/1-Bilevel.tif.xml";
    String xmlFile2 = path + "/1-Bilevel2.tif.xml";

    assertXML(xmlFile, xmlFile2);
  }

  private void assertXML(String xmlFile, String xmlFile2) throws Exception {
    File fXmlFile = new File(xmlFile);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
    doc.getDocumentElement().normalize();

    File fXmlFil2e = new File(xmlFile2);
    DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder2 = dbFactory.newDocumentBuilder();
    Document doc2 = dBuilder.parse(fXmlFile);
    doc2.getDocumentElement().normalize();


    Node node = doc.getFirstChild().getFirstChild();
    Node node2 = doc2.getFirstChild().getFirstChild();

    while (node.getNextSibling() != null && node2.getNextSibling() != null) {

      assertEquals(node.getNodeName(), node2.getNodeName());
      assertEquals(node.getNodeValue(), node2.getNodeValue());
      if (node.getFirstChild() != null && node2.getFirstChild() != null) {
        getChilds(node.getFirstChild(), node2.getFirstChild());
      }
      node = node.getNextSibling();
      node2 = node2.getNextSibling();
    }
  }


  public void getChilds(Node node, Node node2) {
    while (node.getNextSibling() != null && node2.getNextSibling() != null) {
      assertEquals(node.getNodeName(), node2.getNodeName());
      assertEquals(node.getNodeValue(), node2.getNodeValue());
      if (node.getFirstChild() != null && node2.getFirstChild() != null) {
        getChilds(node.getFirstChild(), node2.getFirstChild());
      }
      node = node.getNextSibling();
      node2 = node2.getNextSibling();
    }
  }

  private String getPath() {
    String path = ReportGenerator.createReportPath(true);
    return path;
  }
}