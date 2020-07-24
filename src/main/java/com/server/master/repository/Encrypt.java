package com.server.master.repository;

import org.apache.commons.codec.binary.Base64;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.*;
import java.util.Scanner;


public class Encrypt {
    KeyPairGenerator kpg;
    EllipticCurve curve;
    ECParameterSpec ecSpec;
    KeyPair aKeyPair;
    static KeyAgreement aKeyAgree;
    KeyPair bKeyPair;
    KeyAgreement bKeyAgree;
    KeyFactory keyFac;
    static String msg;



    public static byte[] encryptor(String text, String usage){
    	
//        Security.addProvider(new BouncyCastleProvider());
        Scanner ss=new Scanner(System.in);


        try{
            String path = "";
            if(usage == "user")path = "D:\\FYP\\user";
            else path = "D:\\FYP\\server";
            File filePublicKey = new File(path+"\\public.key");
            FileInputStream fis = new FileInputStream(path+"\\public.key");
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Read Private Key.
            File filePrivateKey = new File(path+"\\private.key");
            fis = new FileInputStream(path+"\\private.key");
            byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
            fis.read(encodedPrivateKey);
            fis.close();


            // Generate KeyPair.
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);





            aKeyAgree = KeyAgreement.getInstance("DH");
            aKeyAgree.init(privateKey);
            aKeyAgree.doPhase(publicKey, true);




            byte[] aBys = aKeyAgree.generateSecret();
            KeySpec aKeySpec = new DESKeySpec(aBys);
            SecretKeyFactory aFactory = SecretKeyFactory.getInstance("DES");
            Key aSecretKey = aFactory.generateSecret(aKeySpec);

            Cipher aCipher = Cipher.getInstance(aSecretKey.getAlgorithm());
            aCipher.init(Cipher.ENCRYPT_MODE, aSecretKey);
            byte[] encText = aCipher.doFinal(text.getBytes());

//            System.out.println(Base64.encodeBase64String(encText));
//
//            System.out.println(encText);

            return encText;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
