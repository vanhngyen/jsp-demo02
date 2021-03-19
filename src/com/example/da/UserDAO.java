package com.example.da;

import com.example.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/t1907m?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private  static final  String INSERT_USERS_SQL = "INSERT INTO users" + "(name, email, country) VALUES" + "(?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT id,name,email,country FORM users WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM user ";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE users set name = ?, email = ? , country = ? WHERE id = ?;";

    public UserDAO() {
    }

    protected Connection getConnection()  throws ClassNotFoundException, SQLException{
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        return connection;
    }

    public void insertUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2,user.getEmail());
        preparedStatement.setString(3,user.getCountry());
        preparedStatement.executeUpdate();
    }

    public User selectUser(int id) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            String name = rs.getString("name");
            String email = rs.getString("email");
            String country = rs.getString("country");
            user = new User(id, name, email, country);

        }
        return user;
    }

    public List<User> selectAllUser() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String country = rs.getString("country");
            users.add(new User(id, name, email,country));
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException, ClassNotFoundException {
        boolean rowDeleted;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);
        statement.setInt(1,id);
        rowDeleted = statement.executeUpdate() >0;
        return rowDeleted;
    }

    public boolean UpdateUser(User user) throws SQLException, ClassNotFoundException {
        boolean rowUpdated;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);
        statement.setString(1,user.getName());
        statement.setString(2,user.getEmail());
        statement.setString(3,user.getCountry());
        statement.setInt(4,user.getId());
        rowUpdated = statement.executeUpdate() >0;
        return rowUpdated;
    }
}
