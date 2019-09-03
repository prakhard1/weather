package weatherform.core.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
// don't use deprecated API
import org.apache.http.impl.client.DefaultHttpClient;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weatherform.core.services.Temperature;




@Component(name = "Temperature Service", immediate = true, service = Temperature.class)
public class TemperatureImpl implements Temperature {
	
	private static final Logger LOG = LoggerFactory.getLogger(TemperatureImpl.class);

	// This service will create run Mode specific URL. Create OSGI config for environment base //
	
	@Override
	public String getTemperature(String cityName) {
		
		LOG.debug("Inside Temperature Method");
		
		// don't use String class for concatenation. Use StringBuilder append method
		StringBuilder json = new StringBuilder();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			
			// 

			// Refactor this class and create new Service RestService
			
			// getExecute(String URL , String contentType ) and it will return JSON
			HttpGet getRequest = new HttpGet(
					"http://api.apixu.com/v1/current.json?key=baca22203dce4e0087495805192608&q="
							+ cityName);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
						

			while ((output = br.readLine()) != null) {				
				json.append(output);				
			}
			

		} catch (Exception e) {
			// Always log this exception by using e.getMessage() Implement Logger
			e.getMessage();
			LOG.error("Threw a BadException in MyClass::MyMethod, full stack trace follows:", e);
		}
		return json.toString();
	}
}
