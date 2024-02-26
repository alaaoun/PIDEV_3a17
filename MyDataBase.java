package utils;
import java.sql.*;
public class MyDataBase {
   private Connection conn;
   final String url="jdbc:mysql://localhost:3306/user";
   final String user="root";
  final  String pwd="";
  static MyDataBase instance;
private MyDataBase() {
    try {
        conn = DriverManager.getConnection(url, user, pwd);
        System.out.println("Connected");
    } catch (SQLException s) {
        System.out.println(s.getMessage());
    }
}
public static MyDataBase getInstance(){
    if (instance==null)
        instance=new MyDataBase();
    return instance;
    }
 public Connection getConn(){
    return conn;
 }
}
