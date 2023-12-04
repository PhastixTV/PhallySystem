package me.phastixtv.phallysystemvelocity.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private Connection connection;
    private final String[] informations = new String[5];


    public void connect(String host, String port, String database, String user, String password) {
        if(!isConnected()) {
            try {
                System.out.println("Eigentlich Verbunden!!!!!!!!!!!!!!!");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
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
