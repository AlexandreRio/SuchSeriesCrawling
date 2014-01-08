import data.Crawler;
import data.Data;
import data.DiskAccess;

/**
 *
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class Launcher {

  public static void main(String... args) {
    //for (Crawler c : DiskAccess.readTrackerList())
    //  c.start();

    Data.createDB();

    // For debugging purpose the program ends after some time
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
    }
    System.exit(0);
  }
}
