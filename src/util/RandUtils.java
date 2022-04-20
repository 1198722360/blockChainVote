package util;

import vote.Vote;

import java.util.ArrayList;

public class RandUtils//排序工具类
{
    public ArrayList<Vote> randByHot(ArrayList<Vote> votes)
    {
        for (int i=0;i<votes.size();i++)
        {
            for (int j=votes.size()-1;j>i;j--)
            {
                if (votes.get(j).getSumOfVotes()>votes.get(j-1).getSumOfVotes())
                {
                    Vote tmp=votes.get(j);
                    votes.set(j,votes.get(j-1));
                    votes.set(j-1,tmp);
                }
            }
        }
        return votes;
    }
}
