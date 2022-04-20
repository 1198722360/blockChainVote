package servlet;

import Dao.CandidatesDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddCandidateServlet")
public class AddCandidateServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int innerID= Integer.parseInt(req.getParameter("innerID"));
        CandidatesDao candidatesDao=new CandidatesDao();
        try
        {
            candidatesDao.add(id,name,innerID);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
