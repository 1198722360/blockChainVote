package servlet;

import Dao.CandidatesDao;
import Dao.UserDao;
import Dao.VoteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteTheVoteServlet")
public class DeleteTheVoteServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int id= Integer.parseInt(req.getParameter("id"));
        VoteDao voteDao=new VoteDao();
        CandidatesDao candidatesDao=new CandidatesDao();
        try
        {
            voteDao.deleteTheVote(id);
            candidatesDao.delete(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
