package data;

import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Logger {

  /**
   * Log a message.
   *
   * @param : Message to log.
   */
  public static void log(String toLog) {
    log(toLog, false);
  }

  /**
   * Log a message as an error.
   *
   * @param toLog : Message to log
   */
  public static void logError(String toLog) {
    log(toLog, true);
  }

  /**
   * Log a timestamped message or error message into a file.
   *
   * @param toLog : Message to log
   * @param isError : If true the message is logged in a different file that it
   * would be if failse.
   */
  private static void log(String toLog, boolean isError) {
    String print = (new Date()).toString();
    print.concat(" : " + toLog);
    String filePath;
    if (isError)
      filePath = Settings.ERROR_LOG_PATH;
    else
      filePath = Settings.LOG_PATH;
    try {
      FileWriter writer = new FileWriter(filePath);
      writer.append(print);
      writer.close();
    } catch (IOException e) {
      System.err.println("Something went wrong when writing in the log file : "
          + filePath);
    }
  }
}
