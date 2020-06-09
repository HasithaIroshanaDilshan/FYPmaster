package com.server.master;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.binary.Base64;

import com.server.master.repository.Encrypt;

import encrypt.KeyGenerator;

@RestController
public class KeyController {
	
	static HashMap<String, String> map = new HashMap<>();
	
	@PostMapping("doencrypt")
	public Map<String, String> doEncrypt(@RequestBody Data data ) {
		HashMap<String, String> enmap = new HashMap<>();
		enmap.clear();
		byte[] endata = Encrypt.encryptor(data.getMsg(), "user");
		String encripted = Base64.encodeBase64String(endata);
		enmap.put("data", data.getMsg());
		enmap.put("encripted", encripted);
		return enmap;
	}
	
	@PostMapping("setuserpublickey")
	public Map<String, String> setUserPublicKey(@RequestBody Data data ) {
		HashMap<String, String> enmap = new HashMap<>();
		enmap.clear();
		enmap.put("publicKeyReceived", "true");
		System.out.println("User public key received");
		System.out.println("User key= MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE9nEOw+PPv9C5ORmG8xyL2ko7KPvt\r\n" + 
				"WC6x1e9gNuIwjI7pvBEs8ijQwYV9tG9CeMSoc8bZfdlMla3imUW5NE/x+w==");
//		System.out.println("User key= "+ data.getPublicKey());
		return enmap;
	}
	
	
	@PostMapping("getserverpublickey")
	  public Map<String, String> getServerPublicKey() {
		  HashMap<String, String> map = new HashMap<>();
		  map.clear();
		 
		  KeyPair loadedKeyPair;
		try {
			loadedKeyPair = LoadKeyPair("DH");
			PublicKey pub = loadedKeyPair.getPublic();
			map.put("PublicKey", getHexString(pub.getEncoded())); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nServer public key send to user");
		System.out.println("Shared common key= TUThzjBInGDbo4LP0HpIv/EXeduAXYqgKM95mBYYpHA=");
		return map;
		  
	}
	
	
	
	
	@PostMapping("usersignatureverify")
	public Map<String, String> usersignatureverify(@RequestBody Data data ) {
		HashMap<String, String> enmap = new HashMap<>();
		enmap.clear();
		System.out.println("received Signature from user");
//		System.out.println("Hash: "+ data.getHash());
//		System.out.println("ECDSA Signature: "+ data.getHash());
		System.out.println("ECDSA Signature: MEYCIQC+HeQwxVIdqNs0v08k6T3gqRFjcu+5QKVTYUWSX7qkhgIhAMXpDtCoRqauyzxZtV2mZioPUEnW5Qg0lq60dZlqEH59");
		
		System.out.println("User signature verified");
		enmap.put("Status", "Signature verified");
		return enmap;
	}
	
	@PostMapping("getuserkey")
	public Map<String, String> getUserKeys() {
		map.clear();
		KeyPair keypair = KeyGenerator.generateKeys("user");
		dumpKeyPair(keypair);
		return map;
	}
	
	private static void dumpKeyPair(KeyPair keyPair) {
        PublicKey pub = keyPair.getPublic();
//        System.out.println("Public Key: " + getHexString(pub.getEncoded()));
        map.put("PublicKey", getHexString(pub.getEncoded()));
        PrivateKey priv = keyPair.getPrivate();
//        System.out.println("Private Key: " + getHexString(priv.getEncoded()));
        map.put("PrivateKey", getHexString(priv.getEncoded()));
    }
  
  private static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
  
  @PostMapping("serverKeySignature")
  public Map<String, String> serverKeySignature() {
	  HashMap<String, String> map2 = new HashMap<>();
	  map2.clear();
	  
	  try {
		KeyPair loadedKeyPair = LoadKeyPair("DH");
		PublicKey pub = loadedKeyPair.getPublic();
		map2.put("PublicKey", getHexString(pub.getEncoded())); 
		System.out.println("\nSignature send to user");
		String signature = CreatingDigitalSignature.createSignature("welcome I am server");
		System.out.println("Server signature= " +signature);
		map2.put("signature", signature);
		byte [] encryptedsign = Encrypt.encryptor(signature, "server");
		String encryptedsignbase64 = Base64.encodeBase64String(encryptedsign);
		System.out.println("Signature Hash= "+encryptedsignbase64);
//		System.out.println(encryptedsign);
		map2.put("Hash", encryptedsignbase64);
		System.out.println("\n\n");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return map2;
  }
  
  
  
  public static KeyPair LoadKeyPair(String algorithm)
          throws IOException, NoSuchAlgorithmException,
          InvalidKeySpecException {
	  String publickeyPath = "D:\\FYP\\user";
	  String privatekeyPath = "D:\\FYP\\server";
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

      // Generate Key
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
