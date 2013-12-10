import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiskAccess {

  public static List<Crawler> read() {
    Scanner s = null;
    List<Crawler> ret = new ArrayList<Crawler>();
    try {
      s = new Scanner(new File("res/tk.data"));
    } catch (FileNotFoundException e) {
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
