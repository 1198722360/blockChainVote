package Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import user.User;
import util.JDBCUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao
{
    private JdbcTemplate jdbcTemplate= new JdbcTemplate(JDBCUtils.getDataSource());
    public UserDao()
    {

    }
    public  void regist(User registUser) throws SQLException
    {
        String sql="insert into user(username,password,public_key) values('"+registUser.getUsername()+"','"+registUser.getPassword()+"','"+registUser.getPublicKey()+"')";
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
    public  User searchUser(User loginUser) throws SQLException
    {
        String sql = "select *from user where username='" + loginUser.getUsername() + "' and password='" + loginUser.getPassword()+"'";
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        User user=null;
        if (resultSet.next())
        {
            user=new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
        }
        statement.close();
        connection.close();
        return user;
    }
    public  boolean ifUsernameExist(String username) throws SQLException
    {
        String sql = "select *from user where username = '" + username+"'";
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        boolean res=false;
        if (resultSet.next())
        {
            res = true;

        }
        else res=false;
        connection.close();
        resultSet.close();
        return res;
    }
}
