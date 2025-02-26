package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

// Nguyen Manh Duc - 20173039
public class API {

	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	public static String get(String url, String token) throws Exception {
		HttpURLConnection conn = setupConnection(url, token, token, "GET");
		
	return readResponseString(conn);
	}

	int var;
// Nguyen Manh Duc - 20173039
	
	public static String post(String url, String data, String token ) throws IOException {
		allowMethods("PATCH");
		
		//setup connection
		HttpURLConnection conn = setupConnection(url, data, token, "PATCH");
		
		//send data to server
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		//read response
		return readResponseString(conn);
	}
// Nguyen Manh Duc - 20173039
private static String readResponseString(HttpURLConnection conn) throws IOException {
	BufferedReader in;
	String inputLine;
	if (conn.getResponseCode() / 100 == 2) {
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	} else {
		in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	}
	StringBuilder response = new StringBuilder();
	while ((inputLine = in.readLine()) != null)
		response.append(inputLine);
	in.close();
	LOGGER.info("Respone Info: " + response.toString());
	return response.toString();
}

private static HttpURLConnection setupConnection(String url, String data, String token, String method)
		throws MalformedURLException, IOException, ProtocolException {
	URL line_api_url = new URL(url);
	
	//Nguyen Manh Duc - 20173039
	
	//LOGGER.info("Request Info:\nRequest URL: " + url + "\n" + "Payload Data: " + data + "\n");
	HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
	conn.setDoInput(true);
	conn.setDoOutput(true);
	conn.setRequestMethod("method");
	conn.setRequestProperty("Content-Type", "application/json");
//		conn.setRequestProperty("Authorization", "Bearer " + token);
	return conn;
}

	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
