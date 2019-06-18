package first.test.test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.apis.NaverApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class testlogin_insta {

	private final static String CLIENT_ID = "663d0c4426f54233828e0eb4f4e74037";
	private final static String CLIENT_SECRET = "1757ee5061e148b79a6b523da4d3e872";
	private final static String REDIRECT_URI = "https://127.0.0.1:8443/first/insta/callback.do";
	private final static String SESSION_STATE = "oauth_state";

	private static final String PROTECTED_RESOURCE_URL = "https://openapi.naver.com/v1/nid/me";


	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {


		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)                                                   
				.callback(REDIRECT_URI)
				.build(InstaLoginApi.instance());

		System.out.println(oauthService.getAuthorizationUrl());

		final Scanner in = new Scanner(System.in, "UTF-8");
		final String code = in.nextLine();
		
		
		OAuthRequest request = new OAuthRequest(InstaLoginApi.instance().getAccessTokenVerb(),InstaLoginApi.instance().getAccessTokenEndpoint());
		request.addParameter("grant_type", "authorization_code");
		request.addParameter("client_id", CLIENT_ID);
		request.addParameter("client_secret", CLIENT_SECRET);
		request.addParameter("redirect_uri", REDIRECT_URI);
		request.addParameter("code", code);
		request.setCharset("euc-kr");
		
		final Response response = oauthService.execute(request);
		
		
		System.out.println(response.getBody());
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		String access_token = jsonObject.get("access_token").toString().replaceAll("\"","");
		System.out.println(access_token);
		jsonObject = (JsonObject) jsonObject.get("user");
		System.out.println(jsonObject.get("id").toString().replaceAll("\"",""));
		System.out.println(jsonObject.get("username").toString().replaceAll("\"",""));
		System.out.println(jsonObject.get("full_name").toString().replaceAll("\"",""));
		


//		final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//		oauthService.signRequest(accessToken, request);
//		final Response response = oauthService.execute(request);
//		System.out.println("Got it! Lets see what we found...");
//		System.out.println();
//		System.out.println(response.getCode());
//		System.out.println(response.getBody());
//
//		JsonParser jsonParser = new JsonParser();
//		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
//		jsonObject = (JsonObject) jsonObject.get("response");
//		System.out.println(jsonObject.get("name"));
//		System.out.println(jsonObject.get("nickname"));
//		System.out.println(jsonObject.get("email"));
		



	}

}
