package first.sample.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.login.naver.User;
import first.sample.service.AdminService;
import first.sample.service.MemberService;
import first.sample.service.SnsLoginService;

@Controller
public class NewLoginController {

	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="snsLoginService")
	private SnsLoginService snsLoginService;

	@Resource(name="memberService")
	private MemberService memberService;

	@Resource(name="adminService")
	private AdminService adminService;

	@ResponseBody
	@RequestMapping(value = "/login/insertuser.do",produces = "application/text; charset=utf8")
	public String InsertCommonUser(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		if(adminService.CheckDuplicateUserID(commandMap.getMap())) {
			memberService.InsertCommonUser(commandMap.getMap());
			return "success";
		}else {

			return "실패 , 실패사유:ID가 중복됩니다.";
		}

	}


	@RequestMapping(value = "/login/simplelogin.do")
	public ModelAndView simplelogin(CommandMap commandMap,HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();

		User memberVO = snsLoginService.memberlogin(commandMap.getMap(),request);
		if(memberVO == null) {

			mv.addObject("msg", "로그인 실패");
			mv.addObject("url", "/first/login/login.do");
			mv.setViewName("/redirect");

		}else {
			memberService.InsertLog(memberVO);
			mv.setViewName("redirect:/board/board.do");
		}

		return mv;
	}


	//로그인 첫 화면 요청 메소드
	@RequestMapping(value = "/login/login.do")
	public ModelAndView Login(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		if(SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			mv.setViewName("login/login");
		}else {
			//mv.setViewName("login/login");
			mv.setViewName("redirect:/board/board.do");
		}
		return mv;
	}




	//로그인 첫 화면 요청 메소드
	@RequestMapping(value = "/login.do")
	public ModelAndView login(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("naver/login");
		return mv;
	}


	@RequestMapping(value = "/Snslogin.do")
	public ModelAndView loginMode(Model model, HttpSession session, @RequestParam String mode) {

		ModelAndView mv = new ModelAndView();
		System.out.println(mode);
		if("face".equals(mode)) {

			mv.setViewName("redirect:"+snsLoginService.getAuthorizationUrl(session,mode));
		}else if("naver".equals(mode)) {

			mv.setViewName("redirect:"+snsLoginService.getAuthorizationUrl(session,mode));

		}else if("kakao".equals(mode)) {

			mv.setViewName("redirect:"+snsLoginService.getAuthorizationUrl(session,mode));

		}else if("insta".equals(mode)) {
			mv.setViewName("redirect:"+snsLoginService.getAuthorizationUrl(session,mode));

		}else if("google".equals(mode)) {

			mv.setViewName("redirect:"+snsLoginService.getAuthorizationUrl(session,mode));
		}else{

			mv.setViewName("naver/login");
		}


		return mv;
	}


	@RequestMapping(value = "/{mode}/callback.do")
	public ModelAndView login2(HttpServletRequest Request, HttpSession session, @RequestParam String code, @PathVariable("mode") String mode, @RequestParam String state) throws Exception {


		ModelAndView mv = new ModelAndView();

		//		if("face".equals(mode)) {
		//
		//			final String access_Token = snsLoginService.getAccessToken(code, state, session,mode);
		//			if(access_Token.length() < 3) {
		//
		//				mv.setViewName("redirect:/first/naver/login.do");
		//
		//			}else {
		//				snsLoginService.getFaceUserInfo(access_Token,new User(),mode);
		//
		//
		//				mv.setViewName("board/board.do");
		//			}
		//
		//
		//
		//		}else 

		if("naver".equals(mode)) {

			final String access_Token = snsLoginService.getAccessToken(code, state, session,mode);
			if(access_Token.length() < 3) {

				mv.setViewName("redirect:/first/login/login.do");

			}else {
				User memberVO = snsLoginService.getNaverUserInfo(access_Token,new User(),mode,Request);
				int checknum = snsLoginService.checkUser(memberVO);

				System.out.println(checknum);

				if(checknum == 0) {
					snsLoginService.InsertUser(memberVO);
					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");

				}else {

					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");
				}
			}

		}else if("kakao".equals(mode)) {


			final String access_Token = snsLoginService.getAccessToken(code, state, session,mode);
			if(access_Token.length() < 3) {

				mv.setViewName("redirect:/first/login/login.do");

			}else {
				User memberVO = snsLoginService.getKakaoUserInfo(access_Token,new User(),mode,Request);
				int checknum = snsLoginService.checkUser(memberVO);

				System.out.println(checknum);

				if(checknum == 0) {
					snsLoginService.InsertUser(memberVO);
					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");

				}else {

					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");
				}
			}

		}else if("insta".equals(mode)) {

			User memberVO = snsLoginService.getInstaAccessToken(code, state, session,mode,new User(), Request);
			if(memberVO == null) {

				mv.setViewName("redirect:/first/login/login.do");
			}else {
				int checknum = snsLoginService.checkUser(memberVO);

				System.out.println(checknum);

				if(checknum == 0) {
					snsLoginService.InsertUser(memberVO);
					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");

				}else {

					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");
				}

			}
		}else if("google".equals(mode)) {

			final String access_Token = snsLoginService.getAccessToken(code, state, session,mode);
			if(access_Token.length() < 3) {

				mv.setViewName("redirect:/first/login/login.do");

			}else {
				User memberVO = snsLoginService.getGoogleUserInfo(access_Token,new User(),mode,Request);
				int checknum = snsLoginService.checkUser(memberVO);

				System.out.println(checknum);

				if(checknum == 0) {
					snsLoginService.InsertUser(memberVO);
					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");

				}else {

					memberService.InsertLog(memberVO);

					mv.setViewName("redirect:/board/board.do");
				}
			}

		}else {

			mv.setViewName("login/login");
		}


		return mv;
	}




}
