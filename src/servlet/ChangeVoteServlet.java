package servlet;

import Dao.VoteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/ChangeVoteServlet")
public class ChangeVoteServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        int id= Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String body = req.getParameter("body");
        String startTime = req.getParameter("startTime");
        startTime=startTime.substring(0,10)+" "+startTime.substring(11)+":00";
        String overTime = req.getParameter("overTime");
        overTime=overTime.substring(0,10)+" "+overTime.substring(11)+":00";

        Timestamp start=Timestamp.valueOf(startTime);
        Timestamp over=Timestamp.valueOf(overTime);
        VoteDao voteDao=new VoteDao();
        try
        {
            voteDao.alterVote(id,title,body,start,over);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
