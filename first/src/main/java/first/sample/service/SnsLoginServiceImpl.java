package first.sample.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.Log4jConfigListener;

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

import first.common.common.CommandMap;
import first.common.util.FileUtils;
import first.login.naver.Role;
import first.login.naver.SnsLoginConfig;
import first.login.naver.User;
import first.sample.dao.MemberDAO;
import first.test.test.InstaLoginApi;
import first.test.test.KaKaoLoginApi;

@Service("snsLoginService")
public class SnsLoginServiceImpl implements SnsLoginService{

	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="memberDAO")
	private MemberDAO memberDAO;

	@Resource(name="fileUtils")
	private FileUtils fileUtils;

	@Override
	public User memberlogin(Map<String, Object> map,HttpServletRequest req) throws Exception {

		User memberVO = new User();

		memberVO = memberDAO.memberlogin(map);
		if(memberVO == null) {

			return null;

		}else {

			String Userrole = memberVO.getLevel();
			memberVO.setIp(req.getRemoteAddr());
			memberVO.setUseragent(req.getHeader("user-agent").toString());
			
			if(Userrole.equals("2")) {
				Role role = new Role();
				role.setName("ROLE_USER");

				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				memberVO.setLevel("2");
				memberVO.setAuthorities(roles);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						memberVO, null, roles
						);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}else if(Userrole.equals("1")) {
				Role role = new Role();
				role.setName("ROLE_VIP");

				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				memberVO.setLevel("1");
				memberVO.setAuthorities(roles);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						memberVO, null, roles
						);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}else if(Userrole.equals("0")) {
				Role role = new Role();
				role.setName("ROLE_ADMIN");

				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				memberVO.setLevel("0");
				memberVO.setAuthorities(roles);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						memberVO, null, roles
						);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

			return memberVO;
		}

	}

	@Override
	public String getAuthorizationUrl(HttpSession session,String mode) {
		// TODO Auto-generated method stub


		OAuth20Service oauthService = getOauth(mode);

		oauthService.getAuthorizationUrl();


		String state = UUID.randomUUID().toString();
		session.setAttribute("oauth_state", state);

		final String authorizationUrl = oauthService.createAuthorizationUrlBuilder()
				.state(state)
				.build();

		log.info(authorizationUrl);
		return authorizationUrl;
	}

	@Override
	public String getAccessToken(String code,String state,HttpSession session,String mode) throws IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub

		String access_Token = "";
		if(session.getAttribute("oauth_state").equals(state)) {

			if(mode.equals("kakao")) {

				OAuth20Service oauthService = getOauth(mode);

				OAuthRequest request = new OAuthRequest(KaKaoLoginApi.instance().getAccessTokenVerb(),KaKaoLoginApi.instance().getAccessTokenEndpoint());
				request.addParameter("grant_type", "authorization_code");
				request.addParameter("client_id", SnsLoginConfig.getKakaoclientkey());
				request.addParameter("redirect_uri", SnsLoginConfig.getKakaoredirect());
				request.addParameter("code", code);

				final Response response = oauthService.execute(request);

				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
				access_Token = jsonObject.get("access_token").toString().replaceAll("\"","");

				System.out.println(access_Token);

			}else {
				OAuth20Service oauthService = getOauth(mode);
				access_Token = oauthService.getAccessToken(code).getAccessToken();

			}
		}

		return access_Token;
	}

	@Override
	public User getInstaAccessToken(String code, String state, HttpSession session, String mode,User MemberVO,HttpServletRequest req)
			throws IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		boolean check = false;
		if(session.getAttribute("oauth_state").equals(state)) {

			OAuth20Service oauthService = getOauth(mode);

			OAuthRequest request = new OAuthRequest(InstaLoginApi.instance().getAccessTokenVerb(),InstaLoginApi.instance().getAccessTokenEndpoint());
			request.addParameter("grant_type", "authorization_code");
			request.addParameter("client_id", SnsLoginConfig.getInstaclientkey());
			request.addParameter("client_secret", SnsLoginConfig.getInstasecretkey());
			request.addParameter("redirect_uri", SnsLoginConfig.getInstaredirect());
			request.addParameter("code", code);
			request.setCharset("euc-kr");

			final Response response = oauthService.execute(request);


			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
			//String access_token = jsonObject.get("access_token").toString().replaceAll("\"","");

			jsonObject = (JsonObject) jsonObject.get("user");
			MemberVO.setSsoNumber(jsonObject.get("id").toString().replaceAll("\"",""));
			MemberVO.setUsername(jsonObject.get("username").toString().replaceAll("\"",""));
			MemberVO.setNickname(jsonObject.get("full_name").toString().replaceAll("\"",""));
			MemberVO.setEmail(jsonObject.get("id").toString().replaceAll("\"","")+"@"+mode+".com");
			MemberVO.setSsoType(mode);
			MemberVO.setId(mode+jsonObject.get("id").toString().replaceAll("\"", ""));
			MemberVO.setUseragent(req.getHeader("user-agent").toString());
			MemberVO.setIp(req.getRemoteAddr());
			
			return MemberVO;
		}

		return null;

	}


	@Override
	public User getNaverUserInfo(String access_token, User memberVO,String mode,HttpServletRequest req) throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub


		OAuth20Service oauthService = getOauth(mode);

		final OAuthRequest request = new OAuthRequest(Verb.GET, SnsLoginConfig.getNaverprotectedResourceUrl());
		oauthService.signRequest(access_token, request);

		final Response response = oauthService.execute(request);
		System.out.println(response.getBody());


		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());

		jsonObject = (JsonObject) jsonObject.get("response");

		log.debug(jsonObject.get("id").toString());
		log.debug(mode);
		memberVO.setSsoNumber(jsonObject.get("id").toString().replaceAll("\"", ""));
		memberVO.setUsername(jsonObject.get("name").toString().replaceAll("\"", ""));
		memberVO.setNickname(jsonObject.get("nickname").toString().replaceAll("\"", ""));
		memberVO.setEmail(jsonObject.get("email").toString().replaceAll("\"", ""));
		memberVO.setSsoType(mode);
		memberVO.setId(mode+jsonObject.get("id").toString().replaceAll("\"", ""));
		memberVO.setUseragent(req.getHeader("user-agent").toString());
		memberVO.setIp(req.getRemoteAddr());
		//		log.info(req.getHeader("user-agent").toString());
		//		log.info(req.getRemoteAddr());
		//		log.info(fileUtils.getIp(req));
		//		log.info(req.getHeader("x-forwarded-for"));
		//		log.info(req.getRemoteHost());
		return memberVO;

	}

	@Override
	public User getKakaoUserInfo(String access_token, User memberVO,String mode,HttpServletRequest req) throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub


		OAuth20Service oauthService = getOauth(mode);

		final OAuthRequest request = new OAuthRequest(Verb.GET, SnsLoginConfig.getKakaoprotectedResourceUrl());
		oauthService.signRequest(access_token, request);

		final Response response = oauthService.execute(request);
		System.out.println(response.getBody());


		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());

		memberVO.setSsoNumber(jsonObject.get("id").toString().replaceAll("\"", ""));
		memberVO.setId(mode+jsonObject.get("id").toString().replaceAll("\"", ""));
		memberVO.setEmail(jsonObject.get("id").toString().replaceAll("\"", "")+"@"+mode+".com");
		jsonObject = (JsonObject) jsonObject.get("properties");
		memberVO.setNickname(jsonObject.get("nickname").toString().replaceAll("\"", ""));
		memberVO.setUsername(jsonObject.get("nickname").toString().replaceAll("\"", ""));
		memberVO.setSsoType(mode);
		memberVO.setUseragent(req.getHeader("user-agent").toString());
		memberVO.setIp(req.getRemoteAddr());

		return memberVO;
	}


	@Override
	public void getRole (User memberVO) throws Exception {
		// TODO Auto-generated method stub
		memberVO = memberDAO.getUserInfo(memberVO);
		String Userrole = memberVO.getLevel();
		if(Userrole.equals("1")) {
			Role role = new Role();
			role.setName("ROLE_USER");

			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			memberVO.setLevel("1");
			memberVO.setAuthorities(roles);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					memberVO, null, roles
					);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		}else if(Userrole.equals("0")) {
			Role role = new Role();
			role.setName("ROLE_ADMIN");

			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			memberVO.setLevel("0");
			memberVO.setAuthorities(roles);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					memberVO, null, roles
					);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

	}



	@Override
	public void InsertUser(User memberVO) throws Exception {
		// TODO Auto-generated method stub
		memberVO.setLevel("1");
		memberDAO.insertsocialId(memberVO);
		getRole(memberVO);

	}


	@Override
	public int checkUser(User memberVO) throws Exception {
		// TODO Auto-generated method stub

		int checknum = memberDAO.selectsocialId(memberVO);

		if(checknum > 0) {
			getRole(memberVO);
		}
		return checknum;
	}

	@Override
	public OAuth20Service getOauth(String mode) {
		// TODO Auto-generated method stub

		if(mode.equals("naver")) {

			return new ServiceBuilder(SnsLoginConfig.getNaverclientkey())                                                   
					.apiSecret(SnsLoginConfig.getNaversecretkey())
					.callback(SnsLoginConfig.getNaverredirect())
					.build(NaverApi.instance());


		}else if(mode.equals("kakao")) {


			return new ServiceBuilder(SnsLoginConfig.getKakaoclientkey())                                                   
					.callback(SnsLoginConfig.getKakaoredirect())
					.build(KaKaoLoginApi.instance());

		}else if(mode.equals("insta")) {

			return new ServiceBuilder(SnsLoginConfig.getInstaclientkey())                                                   
					.callback(SnsLoginConfig.getInstaredirect())
					.build(InstaLoginApi.instance());

		}else if(mode.equals("face")) {

			return new ServiceBuilder(SnsLoginConfig.getFaceclientkey())                                                   
					.apiSecret(SnsLoginConfig.getFacesecretkey())
					.callback(SnsLoginConfig.getFaceredirect())
					.defaultScope("email")
					.build(FacebookApi.instance());

		}else if(mode.equals("google")) {

			return new ServiceBuilder(SnsLoginConfig.getGoogleclientkey())                                                   
					.apiSecret(SnsLoginConfig.getGooglesecretkey())
					.callback(SnsLoginConfig.getGoogleredirect())
					.defaultScope("openid profile email") 
					.build(GoogleApi20.instance());

		}

		return null;
	}

	@Override
	public User getGoogleUserInfo(String access_token, User memberVO, String mode,HttpServletRequest req)
			throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub

		OAuth20Service oauthService = getOauth(mode);

		final OAuthRequest request = new OAuthRequest(Verb.GET, SnsLoginConfig.getGoogleprotectedResourceUrl());
		oauthService.signRequest(access_token, request);
		final Response response = oauthService.execute(request);

		System.out.println(response.getCode());
		System.out.println(response.getBody());

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		memberVO.setUsername(jsonObject.get("name").toString().replaceAll("\"", ""));
		memberVO.setNickname(jsonObject.get("name").toString().replaceAll("\"", ""));
		memberVO.setSsoNumber(jsonObject.get("sub").toString().replaceAll("\"", ""));
		memberVO.setEmail(jsonObject.get("email").toString().replaceAll("\"", ""));
		memberVO.setSsoType(mode);
		memberVO.setId(mode+jsonObject.get("sub").toString().replaceAll("\"", ""));
		memberVO.setUseragent(req.getHeader("user-agent").toString());
		memberVO.setIp(req.getRemoteAddr());

		return memberVO;
	}

	@Override
	public void getFaceUserInfo(String access_token, User memberVO, String mode)
			throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub

		OAuth20Service oauthService = getOauth(mode);

		final OAuthRequest request = new OAuthRequest(Verb.GET, SnsLoginConfig.getFaceprotectedResourceUrl());
		oauthService.signRequest(access_token, request);
		final Response response = oauthService.execute(request);

		System.out.println(response.getCode());
		System.out.println(response.getBody());

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		memberVO.setUsername(jsonObject.get("name").toString());
		memberVO.setSsoNumber(jsonObject.get("id").toString());
		memberVO.setEmail(jsonObject.get("email").toString());


	}




}
