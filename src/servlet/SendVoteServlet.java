package servlet;

import Dao.CandidatesDao;

import Dao.VoteDao;
import MyBlockChainVote.Block;
import MyBlockChainVote.Transaction;
import MyBlockChainVote.utils.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;

import static MyBlockChainVote.BlockChain.blockChain;
import static MyBlockChainVote.BlockChain.isValid;

@WebServlet("/SendVoteServlet")
public class SendVoteServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        StringUtils stringUtil=new StringUtils();
        String receiver=req.getParameter("receiver");
        String sender=req.getParameter("sender");
        String privateKey=req.getParameter("privateKey");
        String voteID=req.getParameter("id");
        PublicKey receiver1=stringUtil.getPublicKeyFromString(receiver);
        PublicKey sender1=stringUtil.getPublicKeyFromString(sender);
        PrivateKey privateKey1= stringUtil.getPrivateKeyFromString(privateKey);

        Transaction transaction=new Transaction(sender1,receiver1,voteID);//创建交易类
        transaction.generateSignature(privateKey1);
        if (transaction.signature(sender1))//签名成功
        {
            //签名验证通过，则创建区块,并将投票信息添加到区块中
            Block preBlock=blockChain.lastElement();
            Block block=new Block(preBlock.calculateHash(),transaction);
            //验证区块是否有效
            if (isValid(block))
            {
                //有效则添加区块并计数
                blockChain.add(block);
                String hash=block.calculateHash();
                new BaseServlet().writeValue(hash,resp);
                CandidatesDao candidatesDao=new CandidatesDao();
                int id= Integer.parseInt(req.getParameter("id"));
                int innerID= Integer.parseInt(req.getParameter("innerID"));
                try
                {
                    candidatesDao.plusplus(id,innerID);
                    new VoteDao().sumPlusPlus(id);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else new BaseServlet().writeValue("notValid",resp);
        }
        else
        {
            new BaseServlet().writeValue("false",resp);
        }
    }

}
