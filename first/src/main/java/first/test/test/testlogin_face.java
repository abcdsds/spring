package first.test.test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.apis.NaverApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class testlogin_face {

	private final static String CLIENT_ID = "529558797448226";
	private final static String CLIENT_SECRET = "51ee1074eb123f1e216bc52309a6636a";
	private final static String REDIRECT_URI = "https://local.testeotlrdhkd.com:8443/first/face/callback.do";
	private final static String SESSION_STATE = "oauth_state";

	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v3.2/me";


	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {


		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)                                                   
				.apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI)
				.defaultScope("email")
				.build(FacebookApi.instance());

		System.out.println(oauthService.getAuthorizationUrl());

		final Scanner in = new Scanner(System.in, "UTF-8");
		final String code = in.nextLine();
		final String accessToken = oauthService.getAccessToken(code).getAccessToken();
		System.out.println(accessToken);
		//System.out.println(oauthService.getAccessToken(code).getAccessToken());

		final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL+"?fields=email,id,name");
		oauthService.signRequest(accessToken, request);
		final Response response = oauthService.execute(request);
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		System.out.println(jsonObject.get("name"));
		System.out.println(jsonObject.get("id"));
		System.out.println(jsonObject.get("email"));
		



	}

}
