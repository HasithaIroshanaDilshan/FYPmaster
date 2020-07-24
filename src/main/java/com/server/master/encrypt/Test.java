//package encrypt;
//
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.Signature;
//import java.security.SignatureException;
//import java.security.spec.ECGenParameterSpec;
//
//import javax.crypto.KeyAgreement;
//
//import org.bouncycastle.jce.ECNamedCurveTable;
//import org.bouncycastle.jce.interfaces.ECPrivateKey;
//import org.bouncycastle.jce.interfaces.ECPublicKey;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
//import org.bouncycastle.math.ec.ECCurve;
//
//public class Test {
//
//	private static String message;
//
//	public static void keygen() {
//		 
//
//		        // set provider
//		        Security.addProvider(new BouncyCastleProvider());
//
//		        // transform from hex to ECPublicKey
//		        Object externalRawPublicKey;
//		        
//				byte[] ecRawExternalPublicKey = this.toByte(externalRawPublicKey);
//		        ECPublicKey ecExternalPublicKey = null;
//		        KeyFactory externalKeyFactor = null;
//
//		        ECNamedCurveParameterSpec ecExternalNamedCurveParameterSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
//		        ECCurve curve = ecExternalNamedCurveParameterSpec.getCurve();
//		        EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, ecExternalNamedCurveParameterSpec.getSeed());
//		        java.security.spec.ECPoint ecPoint = ECPointUtil.decodePoint(ellipticCurve, ecRawExternalPublicKey);
//		        java.security.spec.ECParameterSpec ecParameterSpec = EC5Util.convertSpec(ellipticCurve, ecExternalNamedCurveParameterSpec);
//		        java.security.spec.ECPublicKeySpec externalPublicKeySpec = new java.security.spec.ECPublicKeySpec(ecPoint, ecParameterSpec);
//
//		        externalKeyFactor = java.security.KeyFactory.getInstance("EC");
//		        // this is externalPubicKey
//		        ecExternalPublicKey = (ECPublicKey) externalKeyFactor.generatePublic(externalPublicKeySpec);
//
//		        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDH", "BC");
//		        keyGen.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
//
//		        KeyPair pair = keyGen.generateKeyPair();
//		        ECPublicKey pub = (ECPublicKey)pair.getPublic();
//		        ECPrivateKey pvt = (ECPrivateKey)pair.getPrivate();
//
//		        byte[] pubEncoded = pub.getEncoded();
//		        byte[] pvtEncoded = pvt.getEncoded();
//
//		        String sharedKey = null;
//		        
//		        KeyAgreement keyAgree = KeyAgreement.getInstance("ECDH");
//		        keyAgree.init(pvt);
//		        keyAgree.doPhase(ecExternalPublicKey, true);
//		        sharedKey = bytesToHex( keyAgree.generateSecret() );
//		        
//
//		        System.out.println("sharedKey: "+ bytesToHex( keyAgree.generateSecret() ));
//
//		        // internal public key
////		        return "04"+ pub.getW().getAffineX().toString(16) + pub.getW().getAffineY().toString(16)
//
//	}
//	
//	private static String bytesToHex(byte[] hashInBytes) {
//
//        StringBuilder sb = new StringBuilder();
//        for (byte b : hashInBytes) {
//            sb.append(String.format("%02x", b));
//        }
//        return sb.toString();
//
//    }
//	
//	private static void test2() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
//		
//		PublicKey publicKey = null;
//		String message = "sds";
//		byte[] signature = null;
//		
//		Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
//		ecdsaVerify.initVerify(publicKey);
//		ecdsaVerify.update(message.getBytes("UTF-8"));
//		boolean result = ecdsaVerify.verify(signature);
//		
//	}
//}
