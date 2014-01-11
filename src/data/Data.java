package data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Data {

  /** TODO use a sqlite db instead. */
  public static ArrayList<Release> data = new ArrayList<Release>();

  /**
   *
   */
  public static void initDB() {
    File dbFile = new File(Settings.DB_PATH);
    if (!dbFile.exists())
      Data.createDB();
  }

  private static void createDB() {
    System.out.println("Creating db");
    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;

    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:"+Settings.DB_PATH);
      statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("drop table if exists release");
      statement.executeUpdate("drop table if exists tracker");
      statement.executeUpdate("drop table if exists team");
      statement.executeUpdate("create table tracker (id integer primary key, name text)");
      statement.executeUpdate("create table team (id integer primary key, name text)");
      statement.executeUpdate("create table release (id integer primary key, name text," +
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
}
