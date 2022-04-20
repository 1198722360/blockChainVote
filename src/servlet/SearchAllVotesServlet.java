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
import java.util.ArrayList;
//搜索所有投票
@WebServlet("/SearchAllVotesServlet")
public class SearchAllVotesServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            searchAllVotes(req,resp);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void searchAllVotes(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
    {
        VoteDao voteDao=new VoteDao();
        ArrayList<Vote> votes=voteDao.searchVotes();
        new BaseServlet().writeValue(votes,resp);
    }
}
