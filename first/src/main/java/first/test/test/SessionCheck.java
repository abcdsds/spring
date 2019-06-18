package first.test.test;

import java.util.UUID;

import javax.servlet.http.HttpSession;

public class SessionCheck {

	private final static String SESSION_STATE = "oauth_state";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	private void setSession(HttpSession session,String state){
		session.setAttribute(SESSION_STATE, state);     
	}
	
	private String getSession(HttpSession session){
		return (String) session.getAttribute(SESSION_STATE);
	}
}
