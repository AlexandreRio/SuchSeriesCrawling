import data.Crawler;
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
    DB.initDB();
    for (Crawler c : DiskAccess.readTrackerList())
      c.start();

    // For debugging purpose the program ends after some time
    /*try {
      Thread.sleep(20000);
    } catch (Exception e) {
    }
    System.exit(0);*/
  }
}
