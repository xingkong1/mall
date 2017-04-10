package mall.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xingkong
 * 数据库工具类
 */
public class DBUtil {
	private static final String ip="localhost";
	private static final String port="3306";
	private static final String loginName="root";
	private static final String password="1234";
	
   private  DBUtil() {
	}
   
  static{
	   try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
   }
    public static Connection getConnection() throws SQLException{
    	
    	String url="jdbc:mysql://localhost:3306/tmall";
    	return DriverManager.getConnection(url, loginName, password);
    }
    
    public static void main(String[] args) throws SQLException {
		System.out.print(getConnection());
	}
   
}
