package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class BaseServlet extends HttpServlet
{
    //返回json数据
    public  void writeValue(Object obj, HttpServletResponse resp) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),obj);
    }
}
