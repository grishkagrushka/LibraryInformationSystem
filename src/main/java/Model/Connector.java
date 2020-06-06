package Model;

import java.sql.*;

public class Connector {
    String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&serverTimezone=UTC";
    String user = "root";
    String pass = "mmm333";
    Connection connection;
    Statement statement;

    public void startConnection(){
        try{
            this.connection = DriverManager.getConnection(url, user, pass);
            this.statement = connection.createStatement();
        }
        catch (SQLException throwable) {
        throwable.printStackTrace();
        }
    }

    public void closeConnection(Connection connection, Statement statement){
        try {
            statement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
