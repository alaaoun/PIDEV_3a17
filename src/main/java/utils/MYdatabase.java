package utils;
import java.sql.*;
public class MYdatabase {
    final String url = "jdbc:mysql://localhost:3306/gestion-equipements";
 final String user = "root";
 final String pwd = "";
    Statement statement;
 Connection connection;
 private  static MYdatabase instance;
private MYdatabase(){
     try {
         connection = DriverManager.getConnection(url,user,pwd);
         System.out.println("connected");
     } catch (SQLException e) {
         System.err.println(e.getMessage());
     }
 }
 public static  MYdatabase getInstance(){
     if(instance == null)
         instance=new MYdatabase();
         return instance;
     }
     public Connection getConnection(){
         return connection;
     }
 }

