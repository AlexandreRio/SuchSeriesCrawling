import data.Crawler;
import data.DiskAccess;

public class Launcher {
  public static void main(String... args) {
    for (Crawler c : DiskAccess.read())
      c.start();
  }
}
