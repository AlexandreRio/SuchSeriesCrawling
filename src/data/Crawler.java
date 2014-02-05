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
 * @version 0.0.1
 */
public class Crawler implements Runnable {

  /** Thread instance of the crawler. */
  private Thread runner;
  /** Name of the crawler, it should be unique. */
  private String name;
  /** Feed URL of a particular website. */
  private URL feedURL;
  /**
   * Name of the last release seen by the crawler, used to stop parsing at
   * the right time after an update.
   */
  private String lastSeenTitle;

  public Crawler(String name, String feed) throws MalformedURLException {
    this.name = name;
    this.feedURL = new URL(feed);
    this.runner  = new Thread(this, this.name);
  }

  /**
   * Start the thread and begin to fetch release.
   */
  public void start() {
    this.runner.start();
  }

  /**
   * Parse the feed and get {@link data.Release Releases}, if the release is
   * valid it is added to the database, if it is not the release is ignored.
   *
   * @param feed : RSS feed containing release to store.
   */
  public void storeRelease(RSS feed) {
    Release r;
    String firstSeen = null;
    for (Item item : feed.getChannel().getItems()) {
      try {
        r = Release.parseItem(item, this.name);
        if (firstSeen == null)
          firstSeen = r.toString();

        if (lastSeenTitle!=null && lastSeenTitle.equals(r.toString()))
          break;

        if (r.getValidity() > Settings.VALIDITY_THRESHOLD) {
          //This gonna be verbose
          Logger.log("Insert release: " + r);
          DB.insertRelease(r);
        }

      } catch (Exception e) {
        // Simply skip this release
        Logger.log("Skip release: " + name + " " + e.getMessage());
      }
    }
    this.lastSeenTitle = firstSeen;
  }

  @Override
  public void run() {
    while (true) {
      Logger.log("Connecting to " + this.name);
      RSSFactory factory = RSSFactory.newFactory();
      try {
        URLConnection conn = this.feedURL.openConnection();
        RSS feed = factory.parse(conn.getInputStream());
        this.storeRelease(feed);
      } catch (IOException e) {
        Logger.logError("IO error in crawler: " + this.name);
      } catch (ParserException e) {
        Logger.logError("Paser error in crawler: " + this.name);
      }
      finally {
        try {
          Thread.sleep(Settings.CRAWLER_WAITING_TIME);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
