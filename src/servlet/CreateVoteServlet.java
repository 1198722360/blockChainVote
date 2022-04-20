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
import java.sql.Timestamp;

@WebServlet("/CreateVoteServlet")
public class CreateVoteServlet  extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //$.post("CreateVoteServlet", {"title":$("#title").val(),
        // "startTime":$("#startTime").val(),"overTime":$("#overTime").val(),"body":$("#body").val()});
        String title = req.getParameter("title");
        String startTime = req.getParameter("startTime");
        startTime=startTime.substring(0,10)+" "+startTime.substring(11)+":00";
        String overTime = req.getParameter("overTime");
        overTime=overTime.substring(0,10)+" "+overTime.substring(11)+":00";
        String body = req.getParameter("body");
        Timestamp start=Timestamp.valueOf(startTime);
        Timestamp over=Timestamp.valueOf(overTime);
        Vote vote=new Vote(-1,title,body,0,start,over,req.getParameter("username"),req.getParameter("sender"));
        VoteDao voteDao=new VoteDao();
        try
        {
            voteDao.createVote(vote);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
