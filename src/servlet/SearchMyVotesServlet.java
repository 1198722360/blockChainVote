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

@WebServlet("/SearchMyVotesServlet")
public class SearchMyVotesServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            VoteDao voteDao=new VoteDao();
            ArrayList<Vote> votes= null;
            votes = voteDao.searchMyVotes(req.getParameter("username"));
            new BaseServlet().writeValue(votes,resp);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
