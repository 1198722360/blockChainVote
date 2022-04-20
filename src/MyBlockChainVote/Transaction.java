package MyBlockChainVote;

import MyBlockChainVote.utils.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction
{
    StringUtils stringUtils =new StringUtils();//工具类非静态，确保线程安全
    //将投票模拟为交易，投票者发送一个投票，相当于投票者向投票创建者发起交易
    PublicKey sender;//发起者，即投票者公钥
    PublicKey receiver;//接受者，即发起投票者的公钥
    String id;//投票id
    byte[] signature;
    public Transaction(PublicKey sender, PublicKey receiver,String id)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.id=id;
    }

    public boolean signature(PublicKey sender)//对投票进行签名
    {
        if(verifySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }
        return true;
    }

    public void generateSignature(PrivateKey privateKey) {//用privateKey签名
        String data = stringUtils.getStringFromKey(sender) + stringUtils.getStringFromKey(receiver);
        signature = stringUtils.applyECDSASig(privateKey,data);
    }

    public boolean verifySignature() {//验证签名是否成功(投票者私钥是否与投票者公钥相匹配)
        String data = stringUtils.getStringFromKey(sender) + stringUtils.getStringFromKey(receiver);
        return stringUtils.verifyECDSASig(sender, data, signature);
    }
}
