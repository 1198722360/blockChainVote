package Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidatesDao
{
    private JdbcTemplate jdbcTemplate= new JdbcTemplate(JDBCUtils.getDataSource());
    public CandidatesDao(){};
    public void deleteByID(int id,int innerID) throws SQLException
    {
        String sql="delete from candidates where id='"+id+"' and inner_id='"+innerID+"'";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
    public void add(int id,String name,int innerID) throws SQLException
    {
        String sql="insert into candidates(id,name,sum,inner_id) values("+id+",'"+name+"',0,"+innerID+")";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
    public void plusplus(int id,int innerID) throws SQLException
    {
        String sql="update candidates set sum = sum+1 where id='"+id+"' and inner_id='"+innerID+"'";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
    public void delete(int id) throws SQLException
    {
        String sql="delete from candidates where id = "+id;
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
}
