package data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.mircomacrelli.rss.Item;
import net.mircomacrelli.rss.ParserException;
import net.mircomacrelli.rss.RSS;
import net.mircomacrelli.rss.RSSFactory;

/**
 *
 *
 * @author Rio Alexandre
 * @version 0.1
 */
public class Crawler implements Runnable {

  /** Thread instance of the crawler. */
  private Thread runner;
  /** Name of the crawler, it should be unique. */
  private String name;
  /** Feed URL of a particular website. */
  private URL feedURL;
  /** */
  private String lastSeenTitle;

  public Crawler(String name, String feed) throws MalformedURLException {
    this.name = name;
    this.feedURL = new URL(feed);
    this.runner = new Thread(this, this.name);
  }

  /**
   * Start the thread and begin to fetch release.
   */
  public void start() {
    this.runner.start();
  }

  /**
   *
   *
   * @param feed :
   */
  public void storeRelease(RSS feed) {
    Release r;
    String firstSeen = null;
    for (Item item : feed.getChannel().getItems()) {
      try {
        r = Release.parseItem(item, this.name);
        if (firstSeen == null)
          firstSeen = r.getName();
        if (this.lastSeenTitle == null)
          this.lastSeenTitle = r.getName();
        else if (this.lastSeenTitle.equals(r.getName()))
          break;
        else
          Data.data.add(r);
      } catch (Exception e) {
        // Simply skip this release
      }
    }
    this.lastSeenTitle = firstSeen;
  }

  @Override
  public void run() {
    while (true) {
      System.out.println("Connecting to " + this.name);
      RSSFactory factory = RSSFactory.newFactory();
      try {
        URLConnection conn = this.feedURL.openConnection();
        RSS feed = factory.parse(conn.getInputStream());
        storeRelease(feed);
        //TODO wait ~5 or 10 minutes
        Thread.sleep(10*1000);
      } catch (IOException e) {
        System.err.println("IO error");
      } catch (ParserException e) {
        System.err.println("Paser error");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}