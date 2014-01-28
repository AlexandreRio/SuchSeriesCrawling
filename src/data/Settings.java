package data;

/**
 *
 * @author Rio Alexandre
 * @version 0.0.1
 */
public interface Settings {

  /** */
  public static String TK_FILE_PATH = "res/tk.data";

  public static final int VALIDITY_THRESHOLD = 70;
  public static final int CRAWLER_WAITING_TIME = 5*60*1000;

  public static String DB_PATH      = "res/tk.db";
  public static String DB_COLUMN_TEAM    = "team";
  public static String DB_COLUMN_TRACKER = "tracker";
  public static String DB_COLUMN_RELEASE = "release";
}
