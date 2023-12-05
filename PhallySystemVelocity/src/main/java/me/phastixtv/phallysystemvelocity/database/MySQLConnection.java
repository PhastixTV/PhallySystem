package me.phastixtv.phallysystemvelocity.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private Connection connection;
    private final String[] informations = new String[5];

    public MySQLConnection(String host, int port, String database, String user, String password) {
        informations[0] = host;
        informations[1] = port + "";
        informations[2] = database;
        informations[3] = user;
        informations[4] = password;
        connect();
    }

    public void connect() {
        if(!isConnected()) {
            // load the MySQL-Connect-Driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            // load the MySQL-connection
            try {
            System.out.println("[MySQL] connected!");
                connection = DriverManager.getConnection("jdbc:mysql://" + informations[0] + ":" + informations[1] + "/" + informations[2], informations[3], informations[4]);
            } catch (SQLException e) {
                System.out.println("Leider nicht Verbunden!!!!!!!!!");
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                System.out.println("[MySQL] disconnected");
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}
