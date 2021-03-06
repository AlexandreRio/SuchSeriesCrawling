import data.Crawler;
import data.Logger;
import data.DB;
import data.DiskAccess;

/**
 *
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class Launcher {

  public static void main(String... args) {
    Logger.log("Starting...");
    DB.initDB();
    for (Crawler c : DiskAccess.readTrackerList())
      c.start();

    // For debugging purpose the program ends after some time
    if (args.length == 1 && args[0].equals("-d")) {
      try {
        Thread.sleep(20*1000);
      } catch (Exception e) {}
      Logger.log("Application is shuting down");
      System.exit(0);
    }
  }
}
