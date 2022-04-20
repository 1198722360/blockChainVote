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

@WebServlet("/loadCandidates")
public class LoadCandidates extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            this.loadCandidates(req,resp);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req,resp);
    }

    public void loadCandidates(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException
    {
        VoteDao voteDao=new VoteDao();
        Vote vote=voteDao.searchVotes(Integer.parseInt(req.getParameter("id")));
        new BaseServlet().writeValue(vote,resp);
    }
}
