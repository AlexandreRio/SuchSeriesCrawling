import java.util.Date;

/**
 * Representation of a release, the team is optional but all the others
 * attributes must be set
 *
 * @author Rio Alexandre
 * @version 1.0
 */
public class Release {

  private String name;
  private String quality;
  private Date releaseDate;
  private String team;

  /**
   *
   *
   * @param name
   * @param quality
   * @param releaseDate
   * @param team
   */
  public Release(String name, String quality, Date releaseDate, String team) {
    this.name        = name;
    this.quality     = quality;
    this.releaseDate = releaseDate;
    this.team        = team;
  }

  /**
   *
   *
   * @param name
   * @param quality
   * @param releaseDate
   */
  public Release(String name, String quality, Date releaseDate) {
    this(name, quality, releaseDate, null);
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the quality
   */
  public String getQuality() {
    return quality;
  }

  /**
   * @return the releaseDate
   */
  public Date getReleaseDate() {
    return releaseDate;
  }

  /**
   * @return the team
   */
  public String getTeam() {
    return team;
  }
}
