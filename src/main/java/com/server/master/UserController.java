package com.server.master;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.master.repository.KeystrokedataRepository;
import com.server.master.repository.UserRepository;
import com.server.master.models.User;
import com.server.master.models.Keystrokedata;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KeystrokedataRepository keystrokedataRepository;
	
	@RequestMapping("users")
	public @ResponseBody User getUsers(@RequestParam Integer Id) {
		System.out.println("request accept");
		return null;
	}
	
	@PostMapping("users")
	public String addUser(@RequestBody User userdata) {
		System.out.println(userdata.getUsername());
		userRepository.save(userdata);
		return "saved";
	}
	
	@PostMapping("login")
	public @ResponseBody String login(@RequestBody User loginData) {
		System.out.println("\n\nLogin request");
		
		String username = loginData.getUsername();
		String password = loginData.getPassword();
		System.out.println("username= "+username);
		System.out.println("password= "+ password);
		User u = userRepository.findUser(username, password);
		
		
		if(u == null) {
			System.out.println("User not registered");
			return "false";
		}else {
			System.out.println("logged in : " + u.getEmail());
//			System.out.println("keystorkeTimes : " + Arrays.toString(loginData.getKeystorkeTimes()));
//			User.keyDataset(loginData.getKeystorkeTimes());
			u.setDevice(loginData.getDevice());
			u.setUserAgent(loginData.getUserAgent());
			saveKeystorkedata(loginData.getKeystorkeTimes(), u);
			return u.getEmail();
		}
	}
	
	
	public void saveKeystorkedata(String[] keystorkeTimes, User u) {
		Integer userId = u.getId();
		System.out.println("device = "+u.getDevice());
		System.out.println("userAgent = "+u.getUserAgent());
//        try {
//        	String keydataClassName = "com.server.master.Keystrokedata";
//            Class<?> keydataClass = Class.forName(keydataClassName); // convert string classname to class
//			Object keydata = keydataClass.newInstance();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // invoke empty constructor
		
        Keystrokedata keydata = new Keystrokedata();
        keydata.setUserId(userId);
		
		int passwordLength = keystorkeTimes.length/2;
		System.out.println("keystorkeTimes : " + Arrays.toString(keystorkeTimes));
		
		for(int i=0; i<passwordLength;i++) {
			String[] parts = keystorkeTimes[i*2+1].split(":");
			
			try {
				
				float keyup = Float.parseFloat(keystorkeTimes[i*2+1].split(":")[1]);
				float keydown = Float.parseFloat(keystorkeTimes[i*2].split(":")[1]);
				float keydownNxt = Float.parseFloat(keystorkeTimes[i*2+2].split(":")[1]);
			
			
			
			
				Method setNameMethod;
				
				//Hold period
				String methodName  = "setH_period_"+(i+1);
				float Hperiod = (keyup - keydown)/1000;
				System.out.printf("%s : %f \n",methodName, Hperiod);
				setNameMethod = keydata.getClass().getMethod(methodName, float.class);
				setNameMethod.invoke(keydata, Hperiod); // pass arg
			
	        
				//down to down period
				methodName  = "setDD_period_"+(i+1);
				float ddPeriod = (keydownNxt - keydown)/1000;
				System.out.printf("%s : %f \n",methodName, ddPeriod);
				setNameMethod = keydata.getClass().getMethod(methodName, float.class);
				setNameMethod.invoke(keydata, ddPeriod); // pass arg
			
			
				//up to down latency
				methodName  = "setUD_period_"+(i+1);
				float udPeriod = (keydownNxt - keyup)/1000;
				System.out.printf("%s : %f \n",methodName, udPeriod);
				setNameMethod = keydata.getClass().getMethod(methodName, float.class);
				setNameMethod.invoke(keydata, udPeriod); // pass arg
				
				keystrokedataRepository.save(keydata);
				
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
	}
	
}
