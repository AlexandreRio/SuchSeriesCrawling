import java.util.List;

import data.Crawler;
import data.DiskAccess;

public class Launcher {
  public static void main(String... args) {
    List<Crawler> list = DiskAccess.read();
    for (Crawler c : list)
      c.start();
  }
}
