package no.hvl.dat110.aciotdevice.client;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class RestClient {

	private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	private OkHttpClient client;
	private Gson gson;

	public RestClient() {
		client = new OkHttpClient();
		gson = new Gson();
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		AccessMessage accessMessage = new AccessMessage(message);
		String requestBody = gson.toJson(accessMessage);

		RequestBody body = RequestBody.create(requestBody, JSON);
		Request request = new Request.Builder().url(logpath).post(body).build();

		try {
			client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		Request request = new Request.Builder().url(codepath).build();
		AccessCode code = null;

		try (Response response = client.newCall(request).execute()) {
			String result = response.body().string();
			code = gson.fromJson(result, AccessCode.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return code;
	}
}
