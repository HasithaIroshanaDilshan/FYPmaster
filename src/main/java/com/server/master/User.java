package com.server.master;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String userAgent;
	private String device;
	private String[] keystorkeTimes;
	private String pw;
	
	
	public User() {
		super();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String[] getKeystorkeTimes() {
		return keystorkeTimes;
	}
	public void setKeystorkeTimes(String[] keystorkeTimes) {
		this.keystorkeTimes = keystorkeTimes;
	}
	
	public static void keyDataset(String[] keystorkeTimes) {
		int passwordLength = keystorkeTimes.length/2;
		System.out.println("keystorkeTimes : " + Arrays.toString(keystorkeTimes));
		for(int i=0; i<passwordLength;i++) {
			System.out.println("keystorkeTime : "+keystorkeTimes[i*2+1]);
			String[] parts = keystorkeTimes[i*2+1].split(":");
			System.out.println("parts0: "+ parts[0]);
			System.out.printf("parts1: %f\n",  Float.parseFloat(parts[1]));
			
			
			
			float keyup = Float.parseFloat(keystorkeTimes[i*2+1].split(":")[1]);
			System.out.printf("keyup : %f %d\n",keyup,i*2+1);
			float keydown = Float.parseFloat(keystorkeTimes[i*2].split(":")[1]);
			System.out.printf("keydown : %f %d\n",keydown,i*2);
			
			float Hperiod = (keyup - keydown);
			System.out.printf("Hperiod : %f \n",Hperiod);
			System.out.printf("Hperiod : %f \n",Hperiod/1000);
		}
		
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
	
}
