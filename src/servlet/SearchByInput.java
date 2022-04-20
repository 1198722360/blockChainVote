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

//根据用户输入来搜索符合的投票
@WebServlet("/SearchByInput")
public class SearchByInput extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String input=req.getParameter("input");
        VoteDao voteDao=new VoteDao();
        try
        {
            ArrayList<Vote> votes=voteDao.searchVotes(input);
            new BaseServlet().writeValue(votes,resp);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
