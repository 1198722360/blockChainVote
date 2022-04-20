package servlet;

import Dao.CandidatesDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vote.Candidate;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteCandidateServlet")
public class DeleteCandidateServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        CandidatesDao candidatesDao=new CandidatesDao();
        int id= Integer.parseInt(req.getParameter("id"));
        int innerID= Integer.parseInt(req.getParameter("innerID"));
        try
        {
            candidatesDao.deleteByID(id,innerID);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
