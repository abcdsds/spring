package first.login.naver;


public class SnsLoginConfig {

	//구글
	private static final String GoogleRedirect = "https://127.0.0.1:8443/first/google/callback.do";
	private static final String GoogleClientKey = "1061092847052-bpmv5dn0eab6itfpe602t3bk9mv7t7hl.apps.googleusercontent.com";
	private static final String GoogleSecretKey = "fWFIKvoIM_b4kTlDpVpHDZs0";
	private static final String GoogleProtected_resource_url = "https://www.googleapis.com/oauth2/v3/userinfo";

	//네이버
	private static final String NaverRedirect = "https://127.0.0.1:8443/first/naver/callback.do";
	private static final String NaverClientKey = "temcEQpAbYpSd1sk8fR3";
	private static final String NaverSecretKey = "o4d2SXMrTL";
	private static final String NaverProtected_resource_url = "https://openapi.naver.com/v1/nid/me";

	//인스타그램
	private static final String InstaRedirect = "https://127.0.0.1:8443/first/insta/callback.do";
	private static final String InstaClientKey = "663d0c4426f54233828e0eb4f4e74037";
	private static final String InstaSecretKey = "1757ee5061e148b79a6b523da4d3e872";
	private static final String InstaProtected_resource_url = "";

	//페이스북
	private static final String FaceRedirect = "https://local.testeotlrdhkd.com:8443/first/face/callback.do";
	private static final String FaceClientKey = "529558797448226";
	private static final String FaceSecretKey = "51ee1074eb123f1e216bc52309a6636a";
	private static final String FaceProtected_resource_url = "https://graph.facebook.com/v3.2/me?fields=email,id,name";

	//카카오
	private static final String KakaoRedirect = "https://127.0.0.1:8443/first/kakao/callback.do";
	private static final String KakaoClientKey = "a9f835378116265fec2b736b661cca50";
	private static final String KakaoSecretKey = "";
	private static final String KakaoProtected_resource_url = "https://kapi.kakao.com/v2/user/me";


	public static String getGoogleredirect() {
		return GoogleRedirect;
	}
	public static String getGoogleclientkey() {
		return GoogleClientKey;
	}
	public static String getGooglesecretkey() {
		return GoogleSecretKey;
	}
	public static String getNaverredirect() {
		return NaverRedirect;
	}
	public static String getNaverclientkey() {
		return NaverClientKey;
	}
	public static String getNaversecretkey() {
		return NaverSecretKey;
	}
	public static String getInstaredirect() {
		return InstaRedirect;
	}
	public static String getInstaclientkey() {
		return InstaClientKey;
	}
	public static String getInstasecretkey() {
		return InstaSecretKey;
	}
	public static String getFaceredirect() {
		return FaceRedirect;
	}
	public static String getFaceclientkey() {
		return FaceClientKey;
	}
	public static String getFacesecretkey() {
		return FaceSecretKey;
	}
	public static String getKakaoredirect() {
		return KakaoRedirect;
	}
	public static String getKakaoclientkey() {
		return KakaoClientKey;
	}
	public static String getKakaosecretkey() {
		return KakaoSecretKey;
	}
	public static String getGoogleprotectedResourceUrl() {
		return GoogleProtected_resource_url;
	}
	public static String getNaverprotectedResourceUrl() {
		return NaverProtected_resource_url;
	}
	public static String getInstaprotectedResourceUrl() {
		return InstaProtected_resource_url;
	}
	public static String getFaceprotectedResourceUrl() {
		return FaceProtected_resource_url;
	}
	public static String getKakaoprotectedResourceUrl() {
		return KakaoProtected_resource_url;
	}





}
