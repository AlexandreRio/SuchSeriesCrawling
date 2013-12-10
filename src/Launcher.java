import java.util.ArrayList;
import java.util.List;

public class Launcher {
  public static void main(String... args) {
    List<Crawler> list = DiskAccess.read();
    System.out.println(list.size() + " crawler created.");
  }
}
