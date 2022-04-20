package MyBlockChainVote;

import java.util.Vector;

public class BlockChain
{
    public static Vector<Block> blockChain=new Vector<>();

    static
    {
        //创世纪区块
        Transaction transaction=new Transaction(null,null,null);
        Block genesis=new Block("0",transaction);
        blockChain.add(genesis);
    }

    public synchronized static boolean isValid(Block block)//验证区块是否有效
    {
        Block currentBlock;
        Block previousBlock;
        for (int i=1;i<blockChain.size();i++)
        {
            //验证当前区块
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("#Current Hashes not equal");
                return false;
            }
            //上一区块
            if(!previousBlock.hash.equals(currentBlock.previousHash) )
            {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            //已投过票
            if (block.transaction.id.equals(currentBlock.transaction.id )&&block.transaction.sender.equals(currentBlock.transaction.sender))
            {
                System.out.println("#had voted in this vote");
                return false;
            }


        }
        return true;
    }
}
