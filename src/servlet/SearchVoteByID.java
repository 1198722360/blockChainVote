package servlet;

import Dao.VoteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vote.Vote;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SearchVoteByID")
public class SearchVoteByID extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        VoteDao voteDao=new VoteDao();
        try
        {
            Vote vote=voteDao.searchVotes(Integer.parseInt(req.getParameter("id")));
            new BaseServlet().writeValue(vote,resp);//返回通过id查到得数据
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
