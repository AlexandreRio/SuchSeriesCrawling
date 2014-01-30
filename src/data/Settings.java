package data;

/**
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public interface Settings {

  /** Path of the database. */
  public static String TK_FILE_PATH = "res/tk.data";
  public static String LOG_PATH       = "crawler.log";
  public static String ERROR_LOG_PATH = "error.log";

  /**
   * Every {@link data.Release Release} with a validity below this threshold
   * won't be store in the database.
   */
  public static final int VALIDITY_THRESHOLD = 70;
  /** Time the {@link data.Crawler Crawler} sleep between two updates. */
  public static final int CRAWLER_WAITING_TIME = 5*60*1000;

  public static String DB_PATH      = "res/tk.db";
  // -- Database table name
  public static String DB_TABLE_TEAM    = "team";
  public static String DB_TABLE_TRACKER = "tracker";
  public static String DB_TABLE_RELEASE = "release";
}
