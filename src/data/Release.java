package data;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Date;

import net.mircomacrelli.rss.Item;

/**
 * Representation of a release, the team is optional but all the others
 * attributes must be set
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class Release {

  private String name;
  private String seasonAndEpisode;
  private String quality;
  private String source;
  private String codec;
  private boolean subtitled;
  private Date releaseDate;
  private String team;
  private String tracker;

  public Release(String name, String seasonAndEpisode, String quality, String source, boolean subtitled, String codec, Date releaseDate, String team, String tracker) {
    this.name             = name;
    this.seasonAndEpisode = seasonAndEpisode;
    this.quality          = quality;
    this.source           = source;
    this.codec            = codec;
    this.subtitled        = subtitled;
    this.releaseDate      = releaseDate;
    this.team             = team;
    this.tracker          = tracker;
  }

  public String toString() {
    return
      "name: " + name +
      "\nSE: " + seasonAndEpisode +
      "\nquality: " + quality +
      "\nsource: " + source +
      "\ncodec: " + codec +
      "\nsubtitled: " + subtitled +
      "\ndate: " + releaseDate +
      "\nteam: " + team +
      "\ntracker: " + tracker +
      "\n–––––––––––––––––––––––––––––––";
  }

  public static Release parseItem(Item item, String tkSource) throws Exception {
    String fullName = item.getTitle();
    Pattern pattern = Pattern.compile("S\\d+E\\d+");
    Matcher matcher = pattern.matcher(fullName);
    String title = null;
    String seasonAndEpisode = null;
    String information = null;
    Date date = null;
    System.out.println(item.getPublishDate());
    if (matcher.find())
      seasonAndEpisode = matcher.group(0);
    else
      throw new Exception("Invalid release name.");

    title = fullName.split(seasonAndEpisode)[0];
    information = fullName.split(seasonAndEpisode)[1];
    String team  = null;
    String quality;
    String source;
    String codec;
    boolean subtitled = false;

    if(information.contains("-"))
      team  = information.split("-")[1];
    if (information.contains("VOSTFR") || information.contains("MULTi"))
      subtitled = true;

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
    else
      source = null;

    //Codec
    if(information.contains("x264") || information.contains("h264"))
      codec = "x264";
    else if(information.contains("XviD"))
      codec = "XviD";
    else
      codec = null;

    return new Release(title, seasonAndEpisode, quality, source, subtitled, codec, date, team, tkSource);
  }

}
