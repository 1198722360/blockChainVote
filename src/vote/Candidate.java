package vote;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Candidate
{
    private int id;
    private String name;
    private int sum;
    private int innerID;
    private static String root;
    static {
        try
        {
            Properties pro=new Properties();
            InputStream is = Candidate.class.getClassLoader().getResourceAsStream("coverSavedPosition.properties");
            pro.load(is);
            root=pro.getProperty("root");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public Candidate(int id, String name,int sum,int innerID)
    {
        this.id = id;
        this.name = name;
        this.sum=sum;
        this.innerID=innerID;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public static void setRoot(String root)
    {
        Candidate.root = root;
    }

    @Override
    public String toString()
    {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sum=" + sum +
                '}';
    }
}
