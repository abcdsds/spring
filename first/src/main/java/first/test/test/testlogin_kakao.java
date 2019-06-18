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




public class testlogin_kakao {

	private final static String CLIENT_ID = "a9f835378116265fec2b736b661cca50";
	//private final static String CLIENT_SECRET = "o4d2SXMrTL";
	private final static String REDIRECT_URI = "https://127.0.0.1:8443/first/kakao/callback.do";
	//private final static String SESSION_STATE = "oauth_state";

	private static final String PROTECTED_RESOURCE_URL = "https://kapi.kakao.com/v2/user/me";


	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {

			
		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)   
				.callback(REDIRECT_URI)
				.build(KaKaoLoginApi.instance());

		System.out.println(oauthService.getAuthorizationUrl());

		final Scanner in = new Scanner(System.in, "UTF-8");
		final String code = in.nextLine();
	    
		OAuthRequest request = new OAuthRequest(KaKaoLoginApi.instance().getAccessTokenVerb(),KaKaoLoginApi.instance().getAccessTokenEndpoint());
		request.addParameter("grant_type", "authorization_code");
		request.addParameter("client_id", CLIENT_ID);
		request.addParameter("redirect_uri", REDIRECT_URI);
		request.addParameter("code", code);
		
		final Response response = oauthService.execute(request);
	
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		String access_token = jsonObject.get("access_token").toString().replaceAll("\"","");
		System.out.println(access_token);

		
		//System.out.println(oauthService.getAccessToken(code).getAccessToken());

		final OAuthRequest requestData = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);	
		oauthService.signRequest(access_token, requestData);
		
		final Response responseData = oauthService.execute(requestData);
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(responseData.getCode());
		System.out.println(responseData.getBody());

		
//		JsonParser jsonParser = new JsonParser();
//		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
//		jsonObject = (JsonObject) jsonObject.get("response");
//		System.out.println(jsonObject.get("name"));
//		System.out.println(jsonObject.get("nickname"));
//		System.out.println(jsonObject.get("email"));
		



	}

}
