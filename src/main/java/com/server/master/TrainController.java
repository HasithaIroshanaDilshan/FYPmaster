package com.server.master;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.master.repository.TrainRepository;
import com.server.master.repository.UserRepository;

import com.server.master.models.TrainEntry;

@RestController
public class TrainController {
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("trainset")
	public @ResponseBody String Trainset(@RequestBody User loginData) {
		System.out.println("\n train request");
		
		String username = loginData.getUsername();
		String password = loginData.getPassword();
		System.out.println("username= "+username);
		System.out.println("password= "+ password);
		User u = userRepository.findUser(username, password);
		if(u == null) {
			System.out.println("User not registered");
			return "User not registered";
		}else {
			System.out.println("logged in : " + u.getEmail());
			loginData.setEmail(u.getEmail());
			loginData.setId(u.getId());
			try {
				saveKeystorkedata(loginData);
			}catch (Exception e) {
				System.out.println("data cannot saved");
				e.printStackTrace();
				return "data cannot saved";
			}
			
		}
		User udata = new User();
		udata.setUsername(u.getUsername());
		udata.setUsername(u.getEmail());
		System.out.println(u.getUsername().concat(":").concat(u.getEmail()));
		return u.getUsername().concat(":").concat(u.getEmail()); 
	}
	
	
	public void saveKeystorkedata( User u) throws Exception {
		String[] keystorkeTimes = u.getKeystorkeTimes();
		System.out.println("device = "+u.getDevice());
		System.out.println("userAgent = "+u.getUserAgent());
		System.out.println(keystorkeTimes);

		
        TrainEntry keydata = new TrainEntry();
        keydata.setUserId(u.getId());
        keydata.setDevice(u.getDevice());
        keydata.setUsername(u.getUsername());
        keydata.setPassword(u.getPw());
        
        
		int passwordLength = keystorkeTimes.length/2;
		System.out.println("keystorkeTimes : " + Arrays.toString(keystorkeTimes));
		
		for(int i=0; i<passwordLength;i++) {
			String[] parts = keystorkeTimes[i*2+1].split(":");
			
//			try {
				
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
				
				TrainEntry addedEntry = trainRepository.save(keydata);
				System.out.println("entered id= "+addedEntry.getId());
				
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//			}
			
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
