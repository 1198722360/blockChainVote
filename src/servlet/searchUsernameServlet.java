package servlet;

import Dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/searchUsernameServlet")
public class searchUsernameServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        UserDao userDao = new UserDao();
        boolean res=false;
        try
        {
            res=userDao.ifUsernameExist(req.getParameter("username"));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        new BaseServlet().writeValue(res,resp);
    }
}
