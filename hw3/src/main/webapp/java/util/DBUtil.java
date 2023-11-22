package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    public static Connection getConnection() throws SQLException {

            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "root");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet_test", properties);
    }


}
