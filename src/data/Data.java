package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Data {

  /** TODO use a db instead. */
  public static ArrayList<Release> data = new ArrayList<Release>();

  public static void createDB() {
    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;

    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
      statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("drop table if exists tracker");
      statement.executeUpdate("drop table if exists team");
      statement.executeUpdate("create table tracker (id integer, name string)");
      statement.executeUpdate("create table team (id integer, name string)");

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
