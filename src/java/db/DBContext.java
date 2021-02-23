package db;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hieupnh
 */
public class DBContext {
    private final String serverName;
    private final String database;
    private final String port;
    private final String username;
    private final String password;

    public DBContext() throws Exception {
        InitialContext initCxt = new InitialContext();
        Context ctx = (Context) initCxt.lookup("java:/comp/env");
        serverName = (String) ctx.lookup("severName");
        port = (String) ctx.lookup("port");
        username = (String) ctx.lookup("username");
        password = (String) ctx.lookup("password");
        database = (String) ctx.lookup("database");
    }

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + port + ";databaseName=" + database;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, username, password);
    }

    public void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (ps != null && !ps.isClosed()) {
            ps.close();
        }
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    public String getResource() {
        return "./public/image/";
    }
}
