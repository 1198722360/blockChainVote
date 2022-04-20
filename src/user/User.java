package user;


import MyBlockChainVote.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

public class User
{
    String username;
    String password;
    String publicKey = null;
    String privateKey = null;
    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    public User(String username, String password, String publicKey)
    {
        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
    }
    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPublicKey()
    {
        return publicKey;
    }

    public String getPrivateKey()
    {
        return privateKey;
    }

    public void generateKeyPair()
    {
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            this.privateKey = new StringUtils().getStringFromKey(keyPair.getPrivate());
            this.publicKey = new StringUtils().getStringFromKey(keyPair.getPublic());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
