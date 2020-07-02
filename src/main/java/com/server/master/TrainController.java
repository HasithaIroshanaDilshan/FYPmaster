package com.server.master;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.master.repository.TrainRepository;
import com.server.master.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		
//		try {
//			getPrediction();
//		}catch (Exception e) {
//			System.out.println("prediction error");
//			e.printStackTrace();
//			return "data cannot saved";
//		}
			
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
				DataToPredict saved = saveKeystorkedata(loginData);
				updateCSV(saved.getKeyStrokeData(), u.getUsername(), saved.getId(), "1  ");
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
	
	public void updateCSV(float[] keydata, String userName, String sessionIndex, String rep) throws IOException {
		// C:/Users/"Hasitha Iroshana"/PycharmProjects/untitled/venv/Model
		FileWriter pw = new FileWriter("D:/FYP/data/DSL-StrongPasswordData.csv", true);
        pw.append("\n");
        pw.append(userName);
        pw.append(",");
        pw.append(sessionIndex);
        pw.append(",");
        pw.append(rep);
        String TimeValue;
        float t;
		for(float i : keydata) {
			t = i;
			TimeValue = Float.toString(t);
			pw.append(",");
			pw.append(TimeValue);
		}
		pw.flush();
		pw.close();
		
	}
	
	
	
	/*
	 * get prediction from python server
	 */
	public String getPrediction() throws IOException, InterruptedException {			
		System.out.println("getPrediction");
		double[] keyStrokeData = {0.1491,0.3979,0.2488,0.1069,0.1674,0.0605,0.1169,0.2212,0.1043,0.1417,1.1885,1.0468,0.1146,1.6055,1.4909,0.1067,0.7590,0.6523,0.1016,0.2136,0.1120,0.1349,0.1484,0.0135,0.0932,0.3515,0.2583,0.1338,0.3509,0.2171,0.0742};
		String data = Arrays.toString(keyStrokeData);
        data = data.replace("[", ""); 
        data = data.replace("]", ""); 
        StringBuilder sb = new StringBuilder("{\"keyStrokeData\":\"");
        sb.append(data);
        sb.append("\"}");
        String keydata = sb.toString();
        System.out.println(keydata);
		
		URL url = new URL ("http://127.0.0.1:5000/predict");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		
		String jsonInputString = keydata;
		try(OutputStream os = con.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    os.write(input, 0, input.length);           
		}
		
		try(BufferedReader br = new BufferedReader(
		  new InputStreamReader(con.getInputStream(), "utf-8"))) {
		    StringBuilder response = new StringBuilder();
		    String responseLine = null;
		    while ((responseLine = br.readLine()) != null) {
		        response.append(responseLine.trim());
		    }
		    System.out.println(response.toString());
		}
		
		return null;
	}
	
	
	
	
	
	public DataToPredict saveKeystorkedata( User u) throws Exception {
		DataToPredict savedData = new DataToPredict();
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
		
		float[] keystrokeData = new float[30];
		
		for(int i=0; i<passwordLength;i++) {
			String[] parts = keystorkeTimes[i*2+1].split(":");
			
			//			try {
				
			float keyup = Float.parseFloat(keystorkeTimes[i*2+1].split(":")[1]);
			float keydown = Float.parseFloat(keystorkeTimes[i*2].split(":")[1]);
			float keydownNxt = Float.parseFloat(keystorkeTimes[i*2+2].split(":")[1]);
			float Hperiod;
			float ddPeriod;
			float udPeriod;
			
			Method setNameMethod;
			
			//Hold period
			String methodName  = "setH_period_"+(i+1);
			Hperiod = (keyup - keydown)/1000;
			System.out.printf("%s : %f \n",methodName, Hperiod);
			setNameMethod = keydata.getClass().getMethod(methodName, float.class);
			setNameMethod.invoke(keydata, Hperiod); // pass arg
			keystrokeData[i*3] = Hperiod;
        
			//down to down period
			methodName  = "setDD_period_"+(i+1);
			ddPeriod = (keydownNxt - keydown)/1000;
			System.out.printf("%s : %f \n",methodName, ddPeriod);
			setNameMethod = keydata.getClass().getMethod(methodName, float.class);
			setNameMethod.invoke(keydata, ddPeriod); // pass arg
			keystrokeData[i*3+1] = ddPeriod;
		
			//up to down latency
			methodName  = "setUD_period_"+(i+1);
			udPeriod = (keydownNxt - keyup)/1000;
			System.out.printf("%s : %f \n",methodName, udPeriod);
			setNameMethod = keydata.getClass().getMethod(methodName, float.class);
			setNameMethod.invoke(keydata, udPeriod); // pass arg
			keystrokeData[i*3+2] = udPeriod;
			
			
				
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//			}
		}	
		TrainEntry addedEntry = trainRepository.save(keydata);
		String id = Integer.toString(addedEntry.getId());
		System.out.println("entered id= "+id);	
		System.out.println(keystrokeData[1]);	
		System.out.println(keystrokeData[1]);
		
		savedData.setId(id);
		savedData.setKeyStrokeData(keystrokeData);
		return savedData;	
	}
	
	
	
	
	
	
	
	
	
}



class DataToPredict {
	float[] keyStrokeData;
	String id;
	
   //generate setter and getters
	public float[] getKeyStrokeData() {
		return keyStrokeData;
	}
	public void setKeyStrokeData(float[] keyStrokeData) {
		this.keyStrokeData = keyStrokeData;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
   
   
   
   
   
   

