package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mircomacrelli.rss.Item;

import org.joda.time.DateTime;

/**
 * Representation of a release, the team is optional but all the others
 * attributes must be set
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public class Release {

  /** Name of the release, with spaces and uppercase. */
  private String name;
  /** Season and episode of the release, match the regex S\dE\d. */
  private String seasonAndEpisode;
  /** Quality of the release, 720p, 1080p or LD. */
  private String quality;
  /** Source of the media, HDTV, BluRay or DVD. */
  private String source;
  /** Video codec used for the release, usually x264. */
  private String codec;
  /** If the release contains subtitle track. */
  private boolean subtitled;
  /** UTC date of release. */
  private DateTime releaseDate;
  /** Name of the team that publish this release. */
  private String team;
  /** Name of the tracker of origin of the release. */
  private String tracker;
  /** Percentage of validity of parsed information, {@see #validity()}. */
  private int validity;

  /** Minimum number of attributes needed for releases. */
  private static final int NEEDED_ATTRIBUTES = 6;

  public Release(String name, String seasonAndEpisode, String quality,
      String source, boolean subtitled, String codec, DateTime releaseDate,
      String team, String tracker) {
    this.name             = name;
    this.seasonAndEpisode = seasonAndEpisode;
    this.quality          = quality;
    this.source           = source;
    this.codec            = codec;
    this.subtitled        = subtitled;
    this.releaseDate      = releaseDate;
    this.team             = team;
    this.tracker          = tracker;
    this.validity         = validity();
  }

  @Override
  public String toString() {
    return
      "name: "        + name             +
      "\nSE: "        + seasonAndEpisode +
      "\nquality: "   + quality          +
      "\nsource: "    + source           +
      "\ncodec: "     + codec            +
      "\nsubtitled: " + subtitled        +
      "\ndate: "      + releaseDate      +
      "\nteam: "      + team             +
      "\ntracker: "   + tracker          +
      "\nvalidity: "  + validity         +
      "\n–––––––––––––––––––––––––––––––";
  }

  /**
   * Create a release for an item of a RSS feed and a tracker name.
   *
   * @param item Item of a RSS feed, it contains at least the complete name of
   * the release and a UTC date.
   * @param tkSource Name of the tracker of origin.
   * @return Release representing the parsed information.
   *
   * @throws Exception If the release does not contain the pattern
   * <pre>S\dE\d</pre>.
   */
  //TODO Use enumeration for accepted codec, source etc
  public static Release parseItem(Item item, String tkSource) throws Exception {
    String fullName = item.getTitle();
    Pattern pattern = Pattern.compile("S\\d+E\\d+");
    Matcher matcher = pattern.matcher(fullName);
    String title = null;
    String seasonAndEpisode = null;
    String information = null;
    if (matcher.find())
      seasonAndEpisode = matcher.group(0);
    else
      throw new Exception("Invalid release name.");

    fullName = replacePatterns(fullName);
    title = fullName.split(seasonAndEpisode)[0];
    information = fullName.split(seasonAndEpisode)[1];
    String team  = null;
    String quality;
    String source;
    String codec;
    boolean subtitled = false;

    if(information.contains("-")) {
      team  = information.split("-")[1];
      if (team.contains(" "))
        team = team.split(" ")[0];
    }

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

    if (tkSource.contains("_"))
      tkSource = tkSource.split("_")[0];

    return new Release(title, seasonAndEpisode, quality, source, subtitled,
        codec, item.getPublishDate(), team, tkSource);
  }

  /**
   * Replace various patterns to homogenize names.
   *
   * @param fullName : String to clear
   */
  private static String replacePatterns(String fullName) {
    fullName.replace("\\[\\w*\\]", "");
    fullName = fullName.replace('.', ' ');
    fullName = fullName.trim();
    fullName = fullName.replace("WEB-DL", "WEBDL");
    return fullName;
  }

  /**
   * Compute the percentage validity of the release, a null for the source
   * will decrease the percentage for example.
   * On another hand the team is not a required attributes, it has no effect
   * on the validity.
   *
   * @return The percentage of validity of the release.
   */
  public int validity() {
    int specifiedAttributes = 0;
    if (name != null)
      specifiedAttributes++;
    if (quality!= null)
      specifiedAttributes++;
    if (source != null)
      specifiedAttributes++;
    if (codec != null)
      specifiedAttributes++;
    if (releaseDate != null)
      specifiedAttributes++;
    if (seasonAndEpisode != null)
      specifiedAttributes++;
    return (specifiedAttributes*100/NEEDED_ATTRIBUTES);
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the seasonAndEpisode
   */
  public String getSeasonAndEpisode() {
    return seasonAndEpisode;
  }

  /**
   * @return the quality
   */
  public String getQuality() {
    return quality;
  }

  /**
   * @return the source
   */
  public String getSource() {
    return source;
  }

  /**
   * @return the codec
   */
  public String getCodec() {
    return codec;
  }

  /**
   * @return the subtitled
   */
  public boolean isSubtitled() {
    return subtitled;
  }

  /**
   * @return the releaseDate
   */
  public DateTime getReleaseDate() {
    return releaseDate;
  }

  /**
   * @return the team
   */
  public String getTeam() {
    return team;
  }

  /**
   * @return the tracker
   */
  public String getTracker() {
    return tracker;
  }

  /**
   * @return the validity
   */
  public int getValidity() {
    return validity;
  }
}
