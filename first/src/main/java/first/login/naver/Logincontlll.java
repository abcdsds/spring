package first.login.naver;
//package first.login.naver;
//import java.io.IOException;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.social.google.connect.GoogleConnectionFactory;
//import org.springframework.social.google.connect.GoogleOAuth2Template;
//import org.springframework.social.oauth2.GrantType;
//import org.springframework.social.oauth2.OAuth2Operations;
//import org.springframework.social.oauth2.OAuth2Parameters;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.scribejava.apis.GoogleApi20;
//import com.github.scribejava.core.builder.ServiceBuilder;
//import com.github.scribejava.core.model.OAuth2AccessToken;
//import com.github.scribejava.core.oauth.OAuth20Service;
//import com.github.scribejava.core.oauth.OAuthService;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import first.common.util.AuthInfo;
//import first.sample.security.HomeService;
//
///**
// * Handles requests for the application home page.
// */
//@Controller
//public class LoginController {
//
//	/* NaverLoginBO */
//	private NaverLoginBO naverLoginBO;
//	private String apiResult = null;
//	
//	private final static String CLIENT_ID = "1061092847052-bpmv5dn0eab6itfpe602t3bk9mv7t7hl.apps.googleusercontent.com";
//    private final static String CLIENT_SECRET = "fWFIKvoIM_b4kTlDpVpHDZs0";
//    private final static String REDIRECT_URI = "https://127.0.0.1:8443/first/naver/googlecallback.do";
//    private OAuth20Service oauthService;
//
//	@Autowired
//	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
//		this.naverLoginBO = naverLoginBO;
//	}
//	
//	@Resource(name="naverService")
//	private NaverService naverService;
//
//	
//	//로그인 첫 화면 요청 메소드
//	@RequestMapping(value = "/login.do", method = { RequestMethod.GET, RequestMethod.POST })
//	public String login(Model model, HttpSession session) {
//
//	
//		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
//		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
//
//		//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
//		//redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
//		System.out.println("네이버:" + naverAuthUrl);
//		String url = googleOAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
//		
//		String url2 = oauthService.getAuthorizationUrl();
//		System.out.println("/googleLogin, url : " + url);
//		System.out.println("/googleLogin, url2 : " + url2);
//		//네이버 
//		model.addAttribute("url", naverAuthUrl);
//		//구글
//		model.addAttribute("google_url", url);
//
//		/* 생성한 인증 URL을 View로 전달 */
//		return "naver/login";
//	}
//	
//	@RequestMapping(value = "/loginSNS.do")
//	public String loginsns(Model model, HttpSession session, @RequestParam String mode) {
//		
//		
//		
//		
//		
//		return mode;
//	}
//	
//
//	//네이버 로그인 성공시 callback호출 메소드
//	@RequestMapping(value = "/naver/callback.do", method = { RequestMethod.GET, RequestMethod.POST })
//	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
//			throws IOException {
//		System.out.println("여기는 callback");
//		OAuth2AccessToken oauthToken;
//		System.out.println(session);
//		System.out.println(code);
//		System.out.println(state);
//		oauthToken = naverLoginBO.getAccessToken(session, code, state);
//		System.out.println(oauthToken);
//		//로그인 사용자 정보를 읽어온다.
//		apiResult = naverLoginBO.getUserProfile(oauthToken);
//		naverService.onAuthenticationBindingNaver(new User(), apiResult);
//
//		System.out.println(apiResult);
//		JsonParser jsonParser = new JsonParser();
//		JsonObject jsonObject = (JsonObject) jsonParser.parse(apiResult);
//		jsonObject = (JsonObject) jsonObject.get("response");
//
//
//
//		model.addAttribute("name", jsonObject.get("name"));
//		model.addAttribute("email", jsonObject.get("email"));
//
//		/* 네이버 로그인 성공 페이지 View 호출 */
//		return "naver/naverSuccess";
//	}
//	
//	@RequestMapping(value = "/naver/googlecallback.do")
//    public String doSessionAssignActionPage(Model model,HttpServletRequest request) throws Exception {
// 
//        String code = request.getParameter("code");
//        System.out.println(code);
//        OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
//        System.out.println(accessToken.getAccessToken());
//        
//        //RestTemplate을 사용하여 Access Token 및 profile을 요청한다.
//        RestTemplate restTemplate = new RestTemplate();
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//        parameters.add("code", code);
//        parameters.add("client_id", authInfo.getClientId());
//        parameters.add("client_secret", authInfo.getClientSecret());
//        parameters.add("redirect_uri", googleOAuth2Parameters.getRedirectUri());
//        parameters.add("grant_type", "authorization_code");
// 
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
//        ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);
//        Map<String, Object> responseMap = responseEntity.getBody();
// 
//        // id_token 라는 키에 사용자가 정보가 존재한다.
//        // 받아온 결과는 JWT (Json Web Token) 형식으로 받아온다. 콤마 단위로 끊어서 첫 번째는 현 토큰에 대한 메타 정보, 두 번째는 우리가 필요한 내용이 존재한다.
//        // 세번째 부분에는 위변조를 방지하기 위한 특정 알고리즘으로 암호화되어 사이닝에 사용한다.
//        //Base 64로 인코딩 되어 있으므로 디코딩한다.
// 
//        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
//        Base64 base64 = new Base64(true);
//        String body = new String(base64.decode(tokens[1]));
//        String body2 = new String(base64.decode(tokens[0]));
//        
//        System.out.println(tokens.length);
//        System.out.println(new String(Base64.decodeBase64(tokens[0]), "utf-8"));
//        System.out.println(new String(Base64.decodeBase64(tokens[1]), "utf-8"));
//        naverService.onAuthenticationBindingGoogle(new User(), body, body2);
//        
//        //Jackson을 사용한 JSON을 자바 Map 형식으로 변환
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> result = mapper.readValue(body, Map.class);
//        
//        System.out.println(result.get("name"));
//        model.addAttribute("name", result.get("name"));
//        
//        return "naver/naverSuccess";
// 
//    }
//}
//
//
