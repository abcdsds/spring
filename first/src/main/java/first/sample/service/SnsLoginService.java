package first.sample.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.scribejava.core.oauth.OAuth20Service;

import first.common.common.CommandMap;
import first.login.naver.User;

public interface SnsLoginService {
	

	
	int checkUser(User memberVO) throws Exception;
	OAuth20Service getOauth(String mode);
	String getAuthorizationUrl(HttpSession session, String mode);
	String getAccessToken(String code, String state, HttpSession session, String mode)
			throws IOException, InterruptedException, ExecutionException;
	User getNaverUserInfo(String access_token,User memberVO,String mode,HttpServletRequest req) throws InterruptedException, ExecutionException, IOException;
	User getKakaoUserInfo(String access_token, User memberVO,String mode,HttpServletRequest req) throws InterruptedException, ExecutionException, IOException;
	User getGoogleUserInfo(String access_token,User memberVO,String mode,HttpServletRequest req) throws InterruptedException, ExecutionException, IOException;
	void getFaceUserInfo(String access_token,User memberVO,String mode) throws InterruptedException, ExecutionException, IOException;
	User getInstaAccessToken(String code, String state, HttpSession session, String mode, User MemberVO,HttpServletRequest req)
			throws IOException, InterruptedException, ExecutionException;
	void InsertUser(User memberVO) throws Exception;
	void getRole(User memberVO) throws Exception;
	User memberlogin(Map<String, Object> map,HttpServletRequest req) throws Exception;

}
