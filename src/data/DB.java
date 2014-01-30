package data;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class DB {

  /**
   * Check if the database exists, if not it is created.
   * @see #createDB()
   */
  public static void initDB() {
    File dbFile = new File(Settings.DB_PATH);
    if (!dbFile.exists())
      DB.createDB();
  }

  /**
   * Re-create the database, be careful, all stored information will be lost.
   */
  private static void createDB() {
    System.out.println("Creating db");
    Connection connection = null;
    Statement statement = null;

    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:"+Settings.DB_PATH);
      statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("drop table if exists release");
      statement.executeUpdate("drop table if exists tracker");
      statement.executeUpdate("drop table if exists team");
      statement.executeUpdate("create table tracker (id integer primary key autoincrement not null, name text)");
      statement.executeUpdate("create table team (id integer primary key autoincrement not null, name text)");
      statement.executeUpdate("create table release (id integer primary key autoincrement not null, name text," +
          " quality text, seasonEpisode text, source text, codec text, subtitled integer," +
          " releaseDate date, addDate date, idTracker references tracker(id), idTeam references team(id))");
    }
    catch(Exception e)
    {
      System.err.println(e.getMessage());
    }finally {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }

  /**
   * Insert a release in the database.
   *
   * @param r : Release to insert in the database
   */
  public synchronized static void insertRelease(Release r) {
    Connection connection = null;
    Statement statement = null;
    PreparedStatement pstmt = null;

    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:"+Settings.DB_PATH);
      statement  = connection.createStatement();
      statement.setQueryTimeout(30);

      int teamID = 0;
      if (r.getTeam() != null)
        teamID = DB.getTeamId(statement, r.getTeam());

      int trackerID = DB.getTrackerId(statement, r.getTracker());

      Date releaseDate = new java.sql.Date(r.getReleaseDate().toDate().getTime()); // Look good, right ?
      Calendar currenttime = Calendar.getInstance();
      Date addDate = new java.sql.Date((currenttime.getTime()).getTime());


      String query = "insert into " + Settings.DB_TABLE_RELEASE + " (name, " +
        "quality, seasonEpisode, source, codec, subtitled, releaseDate, addDate, " +
        "idTracker, idTeam) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      pstmt = connection.prepareStatement(query);
      pstmt.setString(1, r.getName());
      pstmt.setString(2, r.getQuality());
      pstmt.setString(3, r.getSeasonAndEpisode());
      pstmt.setString(4, r.getSource());
      pstmt.setString(5, r.getCodec());
      pstmt.setBoolean(6, r.isSubtitled());
      pstmt.setDate(7, releaseDate);
      pstmt.setDate(8, addDate);
      pstmt.setInt(9, trackerID);
      pstmt.setInt(10, teamID);
      pstmt.executeUpdate();
    }
    catch(Exception e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        System.err.println(e);
      }
    }
  }

  /**
   * Look for the team name in the database, if it's a new one it is inserted.
   * In both case the id of the team is returned.
   *
   * @param statement : Statement instance from the database.
   * @param teamName : Name of the team.
   * @return id of the team in the database.
   * @see #insertRelease(Release) insertRelease
   */
  private static int getTeamId(Statement statement, String teamName) throws SQLException {
    int id = -1;
    ResultSet resultSet = statement.executeQuery("select id from " + Settings.DB_TABLE_TEAM + " where name like '"
        + teamName + "' limit 1"); // SQL injection ?
    if (resultSet.next()) {
      id = resultSet.getInt(1);
    }
    else {
      statement.executeUpdate("insert into " + Settings.DB_TABLE_TEAM + " (name) values ('" + teamName + "')");
      //Now there is a field
      resultSet = statement.executeQuery("select id from " + Settings.DB_TABLE_TEAM + " where name like '"
          + teamName + "'"); // SQL injection ?
      if (resultSet.next())
        id = resultSet.getInt(1);
    }
    return id;
  }

  /**
   * Look for the tracker name in the database, if it's a new one it is inserted.
   * In both case the id of the team is returned.
   *
   * @param statement : Statement instance from the database.
   * @param trackerName : Name of the tracker.
   * @return id of the tracker in the database.
   * @see #insertRelease(Release) insertRelease
   */
  private static int getTrackerId(Statement statement, String trackerName) throws SQLException {
    int id = -1;
    ResultSet resultSet = statement.executeQuery("select id from " + Settings.DB_TABLE_TRACKER + " where name like '"
        + trackerName + "' limit 1"); // SQL injection ?
    if (resultSet.next()) {
      id = resultSet.getInt(1);
    }
    else {
      statement.executeUpdate("insert into " + Settings.DB_TABLE_TRACKER + " (name) values ('" + trackerName + "')");
      //Now there is a field
      resultSet = statement.executeQuery("select id from " + Settings.DB_TABLE_TRACKER + " where name like '"
          + trackerName + "'"); // SQL injection ?
      if (resultSet.next())
        id = resultSet.getInt(1);
    }
    return id;
  }
}
