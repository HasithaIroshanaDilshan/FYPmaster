package encrypt;

/* Code for Generating key Pair */
import java.io.*;
import java.security.*;
import java.security.spec.*;

public class KeyGenerator {

//private static String usage = "user";
private static String privatekeyPath = "";
private static String publickeyPath = "";

//    public KeyGenerator(String usage) {
//        this.usage = usage;
//    }

    public static KeyPair generateKeys(String usage) {
        KeyGenerator keyGenerator = new KeyGenerator();
        try {
            if(usage == "user"){
                privatekeyPath = "D:\\FYP\\user";
                publickeyPath = "D:\\FYP\\server";
            }else {
                privatekeyPath = "D:\\FYP\\server";
                publickeyPath = "D:\\FYP\\user";
            }
            //String path = "D:\\FYP";
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
            keyGen.initialize(1024);
            KeyPair generatedKeyPair = keyGen.genKeyPair();

            //System.out.println("Generated Key Pair");
            //keyGenerator.dumpKeyPair(generatedKeyPair);
            keyGenerator.SaveKeyPair(generatedKeyPair);

//            KeyPair loadedKeyPair = keyGenerator.LoadKeyPair("DH");
//            System.out.println("Loaded Key Pair");
//            keyGenerator.dumpKeyPair(loadedKeyPair);
            
            return generatedKeyPair;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

    private void dumpKeyPair(KeyPair keyPair) {
        PublicKey pub = keyPair.getPublic();
        System.out.println("Public Key: " + getHexString(pub.getEncoded()));

        PrivateKey priv = keyPair.getPrivate();
        System.out.println("Private Key: " + getHexString(priv.getEncoded()));
    }

    private String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public void SaveKeyPair(KeyPair keyPair) throws IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Store Public Key.
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                publicKey.getEncoded());
        FileOutputStream fos = new FileOutputStream(publickeyPath + "/public.key");
        fos.write(x509EncodedKeySpec.getEncoded());
        fos.close();

        // Store Private Key.
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey.getEncoded());
        fos = new FileOutputStream(privatekeyPath + "/private.key");
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();
    }

    public KeyPair LoadKeyPair(String algorithm)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        // Read Public Key.
        File filePublicKey = new File(publickeyPath + "/public.key");
        FileInputStream fis = new FileInputStream(publickeyPath + "/public.key");
        byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
        fis.read(encodedPublicKey);
        fis.close();

        // Read Private Key.
        File filePrivateKey = new File(privatekeyPath + "/private.key");
        fis = new FileInputStream(privatekeyPath + "/private.key");
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();

        // Generate KeyPair.
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return new KeyPair(publicKey, privateKey);
    }
    
}
