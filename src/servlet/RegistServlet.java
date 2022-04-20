package servlet;

import Dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        User user=new User(username,password);
        user.generateKeyPair();//生成公钥和私钥
        UserDao userDao=new UserDao();
        try
        {
            userDao.regist(user);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        SendMailServlet sendMailServlet=new SendMailServlet();

        sendMailServlet.sendKeys(username,user.getPublicKey(),user.getPrivateKey());
    }
}
