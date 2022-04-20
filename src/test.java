import Dao.UserDao;
import user.User;

import java.sql.SQLException;

public class test
{
    public static void main(String[] args) throws SQLException
    {
        UserDao userDao=new UserDao();
        userDao.regist(new User("11987223601@qq.com","Shijie11"));
    }
}
