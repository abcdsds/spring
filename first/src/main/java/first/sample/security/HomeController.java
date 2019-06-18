package first.sample.security;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import first.sample.service.SampleService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

     private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

     @Resource(name="homeService")
 	 private HomeService homeService;
     /**
      * Simply selects the home view to render by returning its name.
      */
     @Secured("ROLE_USER")
     @RequestMapping(value = "/sample/aa.do", method = RequestMethod.GET)
     public String home(Locale locale, Model model) {
          logger.info("Welcome home! The client locale is {}.", locale);

          Date date = new Date();
          DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

          String formattedDate = dateFormat.format(date);

          model.addAttribute("serverTime", formattedDate );

          return "home";
     }

     @Secured("ROLE_ADMIN")
     @RequestMapping(value = "/sample/admin.do", method = RequestMethod.GET)
     public String admin() {
          return "admin";
     }

     @Secured("ROLE_USER")
     @RequestMapping(value = "/sample/test.do", method = RequestMethod.GET)
     public String admin_test() {
          return "admin";
     }
     
     @RequestMapping(value = "/sample/aasd.do", method = RequestMethod.GET)
     public String home(Locale locale, Model model,@RequestParam(value="user", defaultValue="", required=true) String user) {
          logger.info("Welcome home! The client locale is {}.", locale);

          // 파라메터 값을 얻어 삽입
          UserVO userVO = new UserVO();
          userVO.setUser_name(user);
//          Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
// 	      roles.add(new SimpleGrantedAuthority("ROLE_USER"));	
// 	      
// 	     UsernamePasswordAuthenticationToken authenticationToken = new 
// 	    		 UsernamePasswordAuthenticationToken( userVO, null, roles);
// 	     
// 	    SecurityContextHolder.getContext().setAuthentication(authenticationToken);



          // 서비스 호출
          homeService.getUser(userVO);

          Date date = new Date();
          DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

          String formattedDate = dateFormat.format(date);

          model.addAttribute("serverTime", formattedDate );

          return "home";
     }


}
