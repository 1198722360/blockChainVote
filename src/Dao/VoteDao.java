package Dao;

import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import util.RandUtils;
import vote.Candidate;
import vote.Vote;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VoteDao
{
    private JdbcTemplate jdbcTemplate= new JdbcTemplate(JDBCUtils.getDataSource());
    public VoteDao(){};
    public ArrayList<Vote> searchVotes() throws SQLException
    {
        String sql="select * from votes order by id desc";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Vote> votes = new ArrayList<>();
        while (resultSet.next())
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            votes.add(new Vote(resultSet.getInt(1),resultSet.getString(2),//id,title
                    resultSet.getString(3),resultSet.getInt(4),            //body,参与人数
                    resultSet.getTimestamp(5),resultSet.getTimestamp(6),    //起始时间，结束时间
                    resultSet.getString(7),resultSet.getString(8)           //创建者,公钥
            ));
        }
        statement.close();
        connection.close();
        votes=new RandUtils().randByHot(votes);
        return votes;
    }
    public Vote searchVotes(int id) throws SQLException//通过id来查找指定投票
    {
        String sql="select * from votes where id="+id+"";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Vote vote=null;
        resultSet.next();
        vote = new Vote(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getTimestamp(5),resultSet.getTimestamp(6),resultSet.getString(7),resultSet.getString(8));
        String sql2="select * from candidates where id="+id;
        resultSet =statement.executeQuery(sql2);
        while (resultSet.next())
        {
            Candidate candidate=new Candidate(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4));
            vote.addCandidate(candidate);
        }
        statement.close();
        connection.close();
        return vote;
    }
    public ArrayList<Vote> searchVotes(String input) throws SQLException//通过id来查找指定投票
    {
        ArrayList<Vote> votes=new ArrayList<>();
        String sql="select * from votes where id = '"+input+"'";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            votes.add(new Vote(resultSet.getInt(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getInt(4),
                    resultSet.getTimestamp(5),resultSet.getTimestamp(6),
                    resultSet.getString(7),resultSet.getString(8)
            ));
        }
        String sql2="select * from votes where title like '%"+input+"%'";
        resultSet=statement.executeQuery(sql2);
        while (resultSet.next())
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            votes.add(new Vote(resultSet.getInt(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getInt(4),
                    resultSet.getTimestamp(5),resultSet.getTimestamp(6),
                    resultSet.getString(7),resultSet.getString(8)
            ));
        }
        connection.close();
        statement.close();
        return votes;
    }
    public ArrayList<Vote> searchMyVotes(String username) throws SQLException
    {
        String sql="select * from votes where who_created='"+username+"'";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Vote> votes = new ArrayList<>();
        while (resultSet.next())
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            votes.add(new Vote(resultSet.getInt(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getInt(4),
                    resultSet.getTimestamp(5),resultSet.getTimestamp(6),
                    resultSet.getString(7),resultSet.getString(8)
            ));
        }
        statement.close();
        connection.close();
        return votes;
    }
    public void createVote(Vote vote) throws SQLException//创建投票
    {
        String sql="insert into votes (title,body,num_of_joined,start_time,over_time,who_created,public_key) values('"+
                vote.getTitle()+"','"+vote.getBody()+"','"+vote.getSumOfVotes()+"','"+vote.getStartTime()+"','"+vote.getOverTime()+"','"+vote.getWhoCreated()+"','"+vote.getPublicKey()+"')";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
    public void alterVote(int id, String title, String body, Timestamp start,Timestamp over) throws SQLException
    {
        String sql="update votes set title='"+title+"',body='"+body+"',start_time='"+String.valueOf(start)+ "',over_time='"+String.valueOf(over)+"' where id="+id;
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
    public void sumPlusPlus(int id) throws SQLException
    {
        String sql="update votes set num_of_joined=num_of_joined+1 where id= '"+id+"'";
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
    public void deleteTheVote(int id) throws SQLException
    {
        String sql="delete from votes where id = "+id;
        Connection connection=JDBCUtils.getConnection();
        Statement statement=connection.createStatement();
        statement.execute(sql);
        connection.close();
        statement.close();
    }
}
