package vote;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Vote
{
    private int id;
    private String title;
    private String body;
    private int sumOfVotes;
    private Timestamp startTime;
    private Timestamp overTime;
    private String whoCreated;
    private String publicKey;
    private ArrayList<Candidate> candidates=new ArrayList<>();

    public Vote(int id, String title, String body, int sumOfVotes, Timestamp startTime, Timestamp overTime,String whoCreated,String publicKey)
    {
        this.id = id;
        this.title = title;
        this.body = body;
        this.sumOfVotes = sumOfVotes;
        this.startTime = startTime;
        this.overTime = overTime;
        this.whoCreated=whoCreated;
        this.publicKey=publicKey;
    }

    public void addCandidate(Candidate candidate)
    {
        this.candidates.add(candidate);
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getBody()
    {
        return body;
    }

    public int getSumOfVotes()
    {
        return sumOfVotes;
    }

    public String getStartTime()
    {
        return String.valueOf(startTime);
    }

    public void setStartTime(Timestamp startTime)
    {
        this.startTime = startTime;
    }

    public void setOverTime(Timestamp overTime)
    {
        this.overTime = overTime;
    }

    public String getOverTime()
    {
        return String.valueOf(overTime);
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public void setSumOfVotes(int sumOfVotes)
    {
        this.sumOfVotes = sumOfVotes;
    }


    public void setCandidates(ArrayList<Candidate> candidates)
    {
        this.candidates = candidates;
    }

    public ArrayList<Candidate> getCandidates()
    {
        return candidates;
    }

    public String getWhoCreated()
    {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated)
    {
        this.whoCreated = whoCreated;
    }

    public String getPublicKey()
    {
        return publicKey;
    }

    @Override
    public String toString()
    {
        return "Vote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", sumOfVotes=" + sumOfVotes +
                ", startTime=" + startTime +
                ", overTime=" + overTime +
                ", whoCreated='" + whoCreated + '\'' +
                ", candidates=" + candidates +
                '}';
    }
}
