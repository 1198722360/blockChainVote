package servlet;

import Dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/searchUserServlet")
public class SearchUserServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDao userDao=new UserDao();
        User user =new User(username,password);
        User getUser=null;
        try
        {
            getUser=userDao.searchUser(user);
        } catch (SQLException e)
        {
            new BaseServlet().writeValue(null,resp);
        }
        new BaseServlet().writeValue(getUser,resp);
    }
}
