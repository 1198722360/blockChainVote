package MyBlockChainVote;


import MyBlockChainVote.utils.StringUtils;

import java.util.Date;

public class Block
{
    StringUtils stringUtils =new StringUtils();
    long timestamp;
    String previousHash;
    String hash;
    Transaction transaction;//区块中包含"交易"信息，即投票
    public String calculateHash() {
        String calculatedhash = stringUtils.applySha256(
                previousHash +transaction.toString()+Long.toString(timestamp)//当前区块的hash值由上一区块的hash值、投票信息和时间戳生成
        );
        return calculatedhash;
    }
    public Block(String previousHash,Transaction transaction)
    {
        this.previousHash=previousHash;
        this.timestamp= new Date().getTime();
        this.transaction=transaction;
        this.hash=calculateHash();
    }
}
