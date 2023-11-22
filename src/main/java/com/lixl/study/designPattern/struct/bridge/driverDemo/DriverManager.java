/*
package leven.texous.testmail.designPattern.struct.bridge.driverDemo;

import sun.reflect.Reflection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

public class DriverManager {
    private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<DriverInfo>();

    //...
    static {
        loadInitialDrivers();
        System.out.println("JDBC DriverManager initialized");
    }

    private static void loadInitialDrivers() {

    }
    //...

    public static synchronized void registerDriver(java.sql.Driver driver) throws SQLException {
        if (driver != null) {
            registeredDrivers.addIfAbsent(new DriverInfo(driver));
        } else {
            throw new NullPointerException();
        }
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        java.util.Properties info = new java.util.Properties();
        if (user != null) {
            info.put("user", user);
        }
        if (password != null) {
            info.put("password", password);
        }
        return (getConnection(url, info, Reflection.getCallerClass()));
    }
    //...
}
*/
