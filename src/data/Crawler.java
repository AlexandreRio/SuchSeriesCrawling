package data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
      parseItem(item);
    }
  }

  //TODO continue using Matcher
  private Crawler parseItem(Item item) {
    String fullName = item.getTitle();
    Pattern pat = Pattern.compile("S\\d+E\\d+");
    Matcher m = pat.matcher(fullName);
    String title = fullName;
    String information = fullName;
    if (m.find()) {
      System.out.println("Matching pattern " + fullName);
  //    title = fullName.split(regex)[0];
  //    information = fullName.split(regex)[1];
    }
    else
      System.out.println("Non matching pattern " + fullName);
    String team  = null;
    String quality;
    String source;
    String codec;
    boolean subinformationd = false;

    if(information.contains("-"))
      team  = information.split("-")[1];
    if (information.contains("VOSTFR") || information.contains("MULTi"))
      subinformationd = true;

    //Quality
    if(information.contains("720p"))
      quality = "720p";
    else if (information.contains("1080p"))
      quality = "1080p";
    else
      quality = "LD";

    //Source
    if(information.contains("HDTV"))
      source = "HDTV";
    else if (information.contains("WEB"))
      source = "WEB-DL";
    else if(information.contains("BDRip"))
      source = "BDRip";
    else if(information.contains("DVDRip"))
      source = "DVDRip";

    //Codec
    if(information.contains("x264") || information.contains("h264"))
      codec = "x264";
    else if(information.contains("XviD"))
      codec = "XviD";

    //Season and Episode
    return null;
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
