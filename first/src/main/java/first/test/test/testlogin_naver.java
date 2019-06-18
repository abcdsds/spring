package first.test.test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.apis.NaverApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





public class testlogin_naver {

	private final static String CLIENT_ID = "temcEQpAbYpSd1sk8fR3";
	private final static String CLIENT_SECRET = "o4d2SXMrTL";
	private final static String REDIRECT_URI = "https://127.0.0.1:8443/first/naver/callback.do";
	private final static String SESSION_STATE = "oauth_state";

	private static final String PROTECTED_RESOURCE_URL = "https://openapi.naver.com/v1/nid/me";


	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		String state = UUID.randomUUID().toString();

		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)                                                   
				.apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI)
				.build(NaverApi.instance());

		System.out.println(oauthService.getAuthorizationUrl());



		final Scanner in = new Scanner(System.in, "UTF-8");

		final String authorizationUrl = oauthService.createAuthorizationUrlBuilder()
				.state(state)
				.build();

		System.out.println(authorizationUrl);
		System.out.println(state);

		final String code = in.nextLine();
		final String accessToken = oauthService.getAccessToken(code).getAccessToken();
		System.out.println(accessToken);
		//System.out.println(oauthService.getAccessToken(code).getAccessToken());

		final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		oauthService.signRequest(accessToken, request);
		final Response response = oauthService.execute(request);
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		jsonObject = (JsonObject) jsonObject.get("response");
		System.out.println(jsonObject.get("name"));
		System.out.println(jsonObject.get("nickname"));
		System.out.println(jsonObject.get("email"));




	}


}
