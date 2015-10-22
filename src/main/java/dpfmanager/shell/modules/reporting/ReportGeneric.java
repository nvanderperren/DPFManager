package dpfmanager.shell.modules.reporting;

import com.easyinnova.tiff.model.IfdTags;
import com.easyinnova.tiff.model.TagValue;
import com.easyinnova.tiff.model.TiffDocument;
import com.easyinnova.tiff.model.types.IFD;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

/**
 * Created by easy on 19/10/2015.
 */
public class ReportGeneric {

  /**
   * Tiff 2 jpg.
   *
   * @param inputfile the inputfile
   * @param outputfile the outputfile
   * @return true, if successful
   */
  protected static boolean tiff2Jpg(String inputfile, String outputfile) {
    File outfile = new File(outputfile);
    if (outfile.exists()) {
      return true;
    }
    BufferedImage image = null;
    try {
      File input = new File(inputfile);
      image = ImageIO.read(input);

      double factor = 1.0;
      int width = image.getWidth();
      if (width > 500) {
        factor = 500.0 / width;
      }
      int height = (int) (image.getHeight() * factor);
      width = (int) (width * factor);

      BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D graphic = convertedImage.createGraphics();
      graphic.drawImage(image, 0, 0, width, height, null);
      graphic.dispose();

      ImageIO.write(convertedImage, "jpg", new File(outputfile));
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  /**
   * Read showable tags file.
   *
   * @return hashset of tags
   */
  protected static HashSet<String> readShowableTags() {
    HashSet<String> hs = new HashSet<String>();
    try {
      Path path = Paths.get("./src/main/resources/");
      if (Files.exists(path)) {
        // Look in current dir
        FileReader fr = new FileReader("./src/main/resources/htmltags.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
          String[] fields = line.split("\t");
          if (fields.length == 1) {
            hs.add(fields[0]);
          }
          line = br.readLine();
        }
        br.close();
        fr.close();
      } else {
        // Look in JAR
        CodeSource src = ReportHtml.class.getProtectionDomain().getCodeSource();
        if (src != null) {
          URL jar = src.getLocation();
          ZipInputStream zip = new ZipInputStream(jar.openStream());
          ZipEntry zipFile;
          while ((zipFile = zip.getNextEntry()) != null) {
            String name = zipFile.getName();
            if (name.equals("htmltags.txt")) {
              try {
                BufferedReader br = new BufferedReader(new InputStreamReader(zip));
                String line = br.readLine();
                while (line != null) {
                  String[] fields = line.split("\t");
                  if (fields.length == 1) {
                    hs.add(fields[0]);
                  }
                  line = br.readLine();
                }
              } catch (Exception ex) {
                throw new Exception("");
              }
            }
          }
        } else {
          throw new Exception("");
        }
      }
    } catch (Exception ex) {
    }
    return hs;
  }

  /**
   * Show Tag.
   *
   * @return true, if successful
   */
  protected static boolean showTag(TagValue tv) {
    HashSet<String> showableTags = readShowableTags();
    /*showableTags.add("ImageWidth");
    showableTags.add("ImageLength");
    showableTags.add("BitsPerSample");
    showableTags.add("Compression");
    showableTags.add("PhotometricInterpretation");
    showableTags.add("ImageDescription");
    showableTags.add("Make");
    showableTags.add("Model");
    showableTags.add("Orientation");
    showableTags.add("SamplesPerPixel");
    showableTags.add("XResolution");
    showableTags.add("YResolution");
    showableTags.add("ResolutionUnit");
    showableTags.add("PlanarConfiguration");
    showableTags.add("Software");
    showableTags.add("DateTime");
    showableTags.add("Artist");
    showableTags.add("Copyright");
    showableTags.add("DateTimeOriginal");
    showableTags.add("Flash");
    showableTags.add("TIFFEPStandardID");*/
    //if (tv.getName().equals(""+tv.getId())) return false;
    return showableTags.contains(tv.getName());
  }

  protected static ArrayList<ReportTag> getTags(IndividualReport ir) {
    ArrayList<ReportTag> list = new ArrayList<ReportTag>();
    TiffDocument td = ir.getTiffModel();
    IFD ifd = td.getFirstIFD();
    IFD ifdcomp = null;
    if (ir.getCompareReport() != null) {
      ifdcomp = ir.getCompareReport().getTiffModel().getFirstIFD();
    }
    td.getFirstIFD();
    int index = 0;
    boolean expertMode = false;
    while (ifd != null) {
      IfdTags meta = ifd.getMetadata();
      for (TagValue tv : meta.getTags()) {
        if (showTag(tv) || expertMode) {
          ReportTag tag = new ReportTag();
          tag.index = index;
          tag.tv = tv;
          if (ifdcomp != null) {
            if (!ifdcomp.getMetadata().containsTagId(tv.getId()))
              tag.dif = 1;
          }
          list.add(tag);
        }
      }
      if (ifdcomp != null) {
        for (TagValue tv : ifdcomp.getMetadata().getTags()) {
          if (showTag(tv) || expertMode) {
            if (!meta.containsTagId(tv.getId())) {
              ReportTag tag = new ReportTag();
              tag.index = index;
              tag.tv = tv;
              tag.dif = -1;
              list.add(tag);
            }
          }
        }
      }
      ifd = ifd.getNextIFD();
      if (ifdcomp != null) ifdcomp = ifdcomp.getNextIFD();
      index++;
    }
    return list;
  }
}