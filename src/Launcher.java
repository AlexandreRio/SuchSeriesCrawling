import data.Crawler;
import data.DiskAccess;

/**
 *
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class Launcher {

  public static void main(String... args) {
    for (Crawler c : DiskAccess.read())
      c.start();
  }
}
