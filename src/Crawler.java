import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLConnection;

import net.mircomacrelli.rss.Item;
import net.mircomacrelli.rss.ParserException;
import net.mircomacrelli.rss.RSS;
import net.mircomacrelli.rss.RSSFactory;

public class Crawler implements Runnable {

  /** Thread instance of the crawler. */
  private Thread runner;
  /** Name of the crawler, it should be unique. */
  private String name;
  /** Feed URL of a particular website. */
  private URL feedURL;

  public Crawler(String name, String feed) throws MalformedURLException {
    this.name = name;
    this.feedURL = new URL(feed);
    this.runner = new Thread(this, this.name);
  }

  public Crawler(String feed) throws MalformedURLException {
    this.name = null;
    this.feedURL = new URL(feed);
    this.runner = new Thread(this, this.feedURL.toString());
  }

  public void start() {
    this.runner.start();
  }
  public void storeRelease(RSS feed) {
    for (Item item : feed.getChannel().getItems()) {
      System.out.println(item.getTitle() + " release date: " + item.getPublishDate());
    }
  }

  @Override
  public void run() {
    System.out.println("Connecting");
    RSSFactory factory = RSSFactory.newFactory();
    try {
      URLConnection conn = this.feedURL.openConnection();
      RSS feed = factory.parse(conn.getInputStream());
      storeRelease(feed);
    } catch (IOException e) {
      System.err.println("IO");
    } catch (ParserException e) {
      System.err.println("PARSER");
    }
  }
}
