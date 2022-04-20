package MyBlockChainVote.utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Base64Utils;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class StringUtils
{

    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public  String applySha256(String input)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public StringUtils()
    {
    }

    public byte[] applyECDSASig(PrivateKey privateKey, String input)
    {
        Signature dsa;
        byte[] output = new byte[0];
        try
        {
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
        } catch (Exception e)
        {
            return null;
        }
        return output;
    }

    public boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature)
    {
        try
        {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        } catch (Exception e)
        {
            return false;
        }
    }

    public String getStringFromKey(Key key)
    {
        try
        {
            return Base64.getEncoder().encodeToString(key.getEncoded());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public PrivateKey getPrivateKeyFromString(String privateKey)
    {
        try
        {
            byte[] privateBytes = Base64Utils.decodeFromString(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory = null;
            PrivateKey pri;
            keyFactory = KeyFactory.getInstance("ECDSA","BC");
            pri = keyFactory.generatePrivate(keySpec);
            return pri;
        } catch (Exception e)
        {
            return null;
        }
    }

    public PublicKey getPublicKeyFromString(String publicKey)
    {
        try
        {
            byte[] publicBytes = Base64Utils.decodeFromString(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = null;
            PublicKey pub;
            keyFactory = KeyFactory.getInstance("ECDSA","BC");
            pub = keyFactory.generatePublic(keySpec);
            return pub;
        } catch (Exception e)
        {
            return null;
        }
    }

    /*public static void main(String[] args)
    {
        //测试
        String pub="MEkwEwYHKoZIzj0CAQYIKoZIzj0DAQEDMgAE/lLFVSzBTTRt6e1G6+3G0CTI4YRG3hleglxoZfKqcwyNKsMWWxDAYijT5KGkHgvr";
        String pri="MHsCAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQEEYTBfAgEBBBhjWw3KbgiMW4YtMM+mbhufQRX4YxQKfxWgCgYIKoZIzj0DAQGhNAMyAAT+UsVVLMFNNG3p7Ubr7cbQJMjhhEbeGV6CXGhl8qpzDI0qwxZbEMBiKNPkoaQeC+s=";
        PublicKey publicKey=StringUtil.getPublicKeyFromString(pub);
        PrivateKey privateKey=StringUtil.getPrivateKeyFromString(pri);

        System.out.println(StringUtil.getStringFromKey(privateKey));
        System.out.println(StringUtil.getStringFromKey(publicKey));
        String data = StringUtil.getStringFromKey(publicKey) + StringUtil.getStringFromKey(publicKey);
        byte[]signature = StringUtil.applyECDSASig(privateKey,data);
        System.out.println(StringUtil.verifyECDSASig(publicKey, data, signature));
    }*/
}
