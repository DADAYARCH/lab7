package server.database;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDBManager {
    //language=SQL
    private final static String INSERT_SQL = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    //language=SQL
    private final static String UPDATE_SQL = "UPDATE USERS SET password = ? WHERE username = ?";
    //language=SQL
    private final static String GET_SQL = "SELECT * FROM USERS WHERE username = ?";
    //language=SQL
    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS USERS " +
            "(username VARCHAR(50) PRIMARY KEY, password TEXT NOT NULL);";

    final DatabaseHandler DBHandler;

    public UserDBManager(DatabaseHandler DBHandler) {
        this.DBHandler = DBHandler;
        createTable();
    }

    public void createTable() {
        try {
            Statement statement = DBHandler.getStatement();
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException | DatabaseException ignore) {}
    }

    public boolean checkUser(UserData user) {
        try {
            PreparedStatement preparedStatement = DBHandler.getPreparedStatement(GET_SQL);
            preparedStatement.setString(1, user.username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("password").equals(getHash(user.password));
        } catch (SQLException | DatabaseException e) {
            return false;
        }
    }

    public boolean updateUserPassword(UserData user) {
        try {
            DBHandler.setSavepoint();
            PreparedStatement preparedStatement = DBHandler.getPreparedStatement(UPDATE_SQL);
            preparedStatement.setString(2, user.username);
            preparedStatement.setString(1, getHash(user.password));
            preparedStatement.execute();
            DBHandler.commit();
            return true;
        } catch (DatabaseException | SQLException e) {
            DBHandler.rollback();
            return false;
        }
    }

    public boolean createUser(UserData user) {
        try {
            DBHandler.setSavepoint();
            PreparedStatement preparedStatement = DBHandler.getPreparedStatement(INSERT_SQL);
            preparedStatement.setString(1, user.username);
            preparedStatement.setString(2, getHash(user.password));
            preparedStatement.execute();
            DBHandler.commit();
            return true;
        } catch (DatabaseException | SQLException e) {
            DBHandler.rollback();
            return false;
        }
    }

    private String getHash(String passwordToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes(StandardCharsets.UTF_8));
            return String.format("%040x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA error");
        }
    }
}
