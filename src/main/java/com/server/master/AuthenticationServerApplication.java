package com.server.master;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.server.master.repository.Decrypt;
import com.server.master.repository.Encrypt;

import encrypt.KeyGenerator;


@SpringBootApplication
public class AuthenticationServerApplication {

	static boolean showKey = false;
	
	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServerApplication.class, args);
		
		KeyPair keypair = KeyGenerator.generateKeys("server");
		
		if(showKey)
		System.out.println("Master server Generated Key Pair");
		dumpKeyPair(keypair);
		
//		KeyPair keypair2 = KeyGenerator.generateKeys("user");
//		System.out.println("main Generated Key Pair user");
//		dumpKeyPair(keypair2);
		
//		byte[] encText = Encrypt.encryptor("i wont encrypt", "user");
//		String encodedData = Base64.encodeBase64String(encText);
//		System.out.println("encodedData= "+encodedData);
		
		
		
//		String decryptedData = Decrypt.decryptor(encodedData, "server");
//		System.out.println("decryptedData= "+decryptedData);

		
	}
	
	
	  private static void dumpKeyPair(KeyPair keyPair) {
	        PublicKey pub = keyPair.getPublic();
//	        System.out.println("Public Key: " + getHexString(pub.getEncoded()));
	        if(showKey)
	        System.out.println("Public Key: MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE1xhNa5MJRF1ti2hi+Xfvuclb2j2M" + 
	        		"212MiAh3MCLgS9X1gl+\nmDrGLuMhE1wDSsjgBKdNriYN3Ky+m+HZgACr/0w==");

	        PrivateKey priv = keyPair.getPrivate();
//	        System.out.println("Private Key: " + getHexString(priv.getEncoded()));
	       if(showKey)
	        System.out.println("Private Key: MHcCAQEEIH+F7bJCIodytWW55dmooUjjCaWgHQ3BBXCszfBTyHXroAoGCCqGSM49" + 
	        		"AwEHoUQDQgAE1xhNa5\nMJRF1ti2hi+Xfvuclb2j2M212MiAh3MCLgS9X1gl+mDrGL" + 
	        		"uMhE1wDSsjgBKdNriYN3Ky+m+HZgACr/0w==");
	    }
	  
	  private static String getHexString(byte[] b) {
	        String result = "";
	        for (int i = 0; i < b.length; i++) {
	            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
	        }
	        return result;
	    }

}


