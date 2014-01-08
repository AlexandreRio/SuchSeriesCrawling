package data;

import java.io.File;
import java.io.FileNotFoundException;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Provides method for disk access.
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class DiskAccess {

  /**
   * Scan data file with one site information per line suiting the pattern:
   * <pre>code rss_url</pre>
   * If a contains a mistake, like an invalid http form it is just skipped.
   *
   * @see data.Settings#TK_FILE_PATH
   * @return List of valid Crawler instance.
   */
  public static List<Crawler> readTrackerList() {
    Scanner s = null;
    List<Crawler> ret = new ArrayList<Crawler>();
    try {
      s = new Scanner(new File(Settings.TK_FILE_PATH));
    } catch (FileNotFoundException e) {
      System.err.println("File not found check your configuration!");
    }
    while (s.hasNextLine()) {
      try {
        String[] attributes = s.nextLine().split(" ");
        ret.add(new Crawler(attributes[0], attributes[1]));
      } catch (MalformedURLException e) {
        //Skip this crawler
        continue;
      }
    }
    s.close();
    return ret;
  }

}
