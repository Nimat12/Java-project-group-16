package ONCFsite.Admin;

import java.sql.*;

public class ConnectionDB {
    public static final int ORACLE = 1;
    public static final int MYSQL = 2;

    private static final String mysqlBridge = "jdbc:mysql:";
    private static final String mysqlDriver = "com.mysql.cj.jdbc.Driver";

    private static final String oracleBridge = "jdbc:oracle:thin:";
    private static final String oracleDriver = "oracle.jdbc.driver.OracleDriver";

    private String driver, bridge, dbName;
    private Connection con;
    private DatabaseMetaData dbm;

    public ConnectionDB(int type) {
        switch (type) {
            case ORACLE:
                driver = oracleDriver;
                bridge = oracleBridge;
                break;
            case MYSQL:
                driver = mysqlDriver;
                bridge = mysqlBridge;
                break;
        }
    }

    public void connect(String name, String host, int port, String login, String pwd) {
        try {
            System.out.println(driver);
            Class.forName(driver);
            this.dbName = name;
            String url = bridge + "//" + host + ":" + port + "/" + dbName;
            con = DriverManager.getConnection(url, login, pwd);
            System.out.println("connection établie...");
            dbm = con.getMetaData();
            printInfo();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void printInfo() {
        try {
            System.out.println("DriverName = " + dbm.getDriverName());
            System.out.println("DriverVersion = " + dbm.getDriverVersion());
            System.out.println("catalog = " + con.getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String q) {
        try (Statement sql = con.createStatement();
             ResultSet rs = sql.executeQuery(q)) {
            ResultSetMetaData rsm = rs.getMetaData();
            int n = rsm.getColumnCount();
            for (int i = 1; i <= n; i++) System.out.print(rsm.getColumnName(i) + "\t");
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= n; i++)
                    System.out.print(rs.getString(i) + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTables() {
        try {
            ResultSet rs = dbm.getTables(null, null, null, new String[]{"TABLE", "VIEW"});
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

