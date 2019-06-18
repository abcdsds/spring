package first.test.test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class testlogin_google {
	
	private final static String CLIENT_ID = "1061092847052-bpmv5dn0eab6itfpe602t3bk9mv7t7hl.apps.googleusercontent.com";
    private final static String CLIENT_SECRET = "fWFIKvoIM_b4kTlDpVpHDZs0";
    private final static String REDIRECT_URI = "https://127.0.0.1:8443/first/naver/googlecallback.do";
    
    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		
		
		 OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)                                                   
	                .apiSecret(CLIENT_SECRET)
	                .callback(REDIRECT_URI)
	                .defaultScope("openid profile email") 
	                .build(GoogleApi20.instance());
		 
		 System.out.println(oauthService.getAuthorizationUrl());
		 
		 final Scanner in = new Scanner(System.in, "UTF-8");
		 final String code = in.nextLine();
		 final String accessToken = oauthService.getAccessToken(code).getAccessToken();
		 System.out.println(accessToken);
		 //System.out.println(oauthService.getAccessToken(code).getAccessToken());

		 while (true) {
	            System.out.println("Paste fieldnames to fetch (leave empty to get profile, 'exit' to stop example)");
	            System.out.print(">>");
	            final String query = in.nextLine();
	            System.out.println();

	            final String requestUrl;
	            if ("exit".equals(query)) {
	                break;
	            } else if (query == null || query.isEmpty()) {
	                requestUrl = PROTECTED_RESOURCE_URL;
	            } else {
	                requestUrl = PROTECTED_RESOURCE_URL + "?fields=" + query;
	            }

	            final OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl);
	            oauthService.signRequest(accessToken, request);
	            final Response response = oauthService.execute(request);
	            System.out.println();
	            System.out.println(response.getCode());
	            System.out.println(response.getBody());
	            
	            JsonParser jsonParser = new JsonParser();
	    		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
	    		System.out.println(jsonObject.get("sub"));
	    		System.out.println(jsonObject.get("name"));
	    		System.out.println(jsonObject.get("email"));



	            System.out.println();
	        }
		
		
	}

}
