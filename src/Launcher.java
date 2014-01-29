import data.Crawler;
import data.DB;
import data.DiskAccess;

import java.util.Date;

/**
 *
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class Launcher {

  public static void main(String... args) {
    System.out.println("Start time: " + (new Date()).toString());
    DB.initDB();
    for (Crawler c : DiskAccess.readTrackerList())
      c.start();

    // For debugging purpose the program ends after some time
    if (args.length == 1 && args[0].equals("-d")) {
      try {
        Thread.sleep(20*1000);
      } catch (Exception e) {}
      System.out.println("End time: " + (new Date()).toString());
      System.exit(0);
    }
  }
}
