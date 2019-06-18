package first.login.naver;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Service
public class NaverService {

	private static final Logger logger = LoggerFactory.getLogger(NaverService.class);

	public void onAuthenticationBindingNaver(User memberVO, String result) throws NullPointerException {

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
		jsonObject = (JsonObject) jsonObject.get("response");

		Role role = new Role();
		role.setName("ROLE_USER");

		List<Role> roles = new ArrayList<Role>();
		roles.add(role);

		memberVO.setSsoNumber(jsonObject.get("id").toString().replaceAll("\"", ""));
		memberVO.setEmail(jsonObject.get("email").toString().replaceAll("\"", ""));
		memberVO.setUsername(jsonObject.get("name").toString().replaceAll("\"", ""));
		memberVO.setNickname(jsonObject.get("nickname").toString().replaceAll("\"", ""));
		memberVO.setAuthorities(roles);
		memberVO.setAccountNonExpired(true);
		memberVO.setAccountNonLocked(true);
		memberVO.setCredentialsNonExpired(true);
		memberVO.setEnabled(true);

		//
		//		// Token 积己窍绊 肺弊牢 技记 积己
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				memberVO, null, roles
				);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			String username = ((User) principal).getUsername();
			System.out.println("username1 : "+username);
			System.out.println(((User) principal).getAuthorities());
		} else {
			String username = principal.toString();
			System.out.println("username2 : "+username);
		}
				
		
	}
	
	public void onAuthenticationBindingGoogle(User memberVO, String result, String result2) throws NullPointerException {

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
		
		JsonParser jsonParser2 = new JsonParser();
		JsonObject jsonObject2 = (JsonObject) jsonParser2.parse(result2);

		Role role = new Role();
		role.setName("ROLE_USER");

		List<Role> roles = new ArrayList<Role>();
		roles.add(role);

		memberVO.setSsoNumber(jsonObject2.get("kid").toString().replaceAll("\"", ""));
		memberVO.setEmail("");
		memberVO.setUsername(jsonObject.get("name").toString().replaceAll("\"", ""));
		memberVO.setNickname(jsonObject.get("name").toString().replaceAll("\"", ""));
		memberVO.setAuthorities(roles);
		memberVO.setAccountNonExpired(true);
		memberVO.setAccountNonLocked(true);
		memberVO.setCredentialsNonExpired(true);
		memberVO.setEnabled(true);

		//
		//		// Token 积己窍绊 肺弊牢 技记 积己
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				memberVO, null, roles
				);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			String username = ((User) principal).getUsername();
			System.out.println("username1 : "+username);
			System.out.println(((User) principal).getAuthorities());
		} else {
			String username = principal.toString();
			System.out.println("username2 : "+username);
		}
				
		
	}
}
