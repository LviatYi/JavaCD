import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBase {
    static Connection con;
    static PreparedStatement sql;
    static ResultSet res;

    public Connection getConnection() {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url1 = "jdbc:mysql://localhost:3306/cd";
        String url2 = "?user=root&password=010521";
        String url3 = "&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String url = url1 + url2 + url3;

        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(url);
            //Connection con= DriverManager.getConnection(url,userName,userPsw);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("数据库连接失败");
        }
        return con;
    }

    public void saveString(String mes,String host){
        DataBase dataBase = new DataBase();
        con = dataBase.getConnection();
        try {

            sql = con.prepareStatement("insert into message(Mes,Client,time) values(?,?,?)");
            sql.setString(1,mes);
            sql.setString(2,host);
            sql.setString(3,"2021-6-2");
            //TODO
            //时间获取
            //来源获取
            sql.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readString(String target){
        String mes = new String();
        DataBase dataBase = new DataBase();
        con = dataBase.getConnection();
        try {
            //TODO
            //对接dbms
        }catch (Exception e){
            e.printStackTrace();
        }
        return mes;
    }
}