package first.sample.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.common.common.CommandMap;
import first.login.naver.User;
import first.sample.service.AdminService;
import first.sample.service.MemberService;
import first.sample.service.SampleService;


@Controller
public class AdminController {

	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="adminService")
	private AdminService adminService;

	@Resource(name="sampleService")
	private SampleService sampleService;


	@ResponseBody
	@RequestMapping(value = "/admin/updatereply.do",produces = "application/text; charset=utf8")
	public String UpdateReply(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		adminService.UpdateReply(commandMap.getMap());
		return "Success";
	}

	@RequestMapping(value = "/admin/postupdate.do")
	public ModelAndView UpdatePost(CommandMap commandMap,HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		adminService.UpdatePost(commandMap.getMap(),request);
		mv.setViewName("redirect:/admin/board.do");
		return mv;

	}


	@RequestMapping(value = "/admin/postedit.do")
	public ModelAndView postedit(CommandMap commandMap,HttpServletRequest request,HttpServletResponse response) throws Exception {

		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap(),request,response);
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());
		Map<String,Object> dashinfo = adminService.GetCountInfo();

		ModelAndView mv = new ModelAndView();

		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("menulist",menulist.get("menulist"));
		mv.setViewName("admin/postedit");
		return mv;
	}


	@RequestMapping(value = "/admin/main.do")
	public ModelAndView login(HttpServletRequest request) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;



		ModelAndView mv = new ModelAndView();
		Map<String,Object> dashinfo = adminService.GetCountInfo();
		Map<String,Object> list = adminService.getUserLog10();
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("list", list.get("list"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());
		mv.setViewName("admin/main");
		return mv;
	}


	//@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/admin/user.do")
	public ModelAndView UserList(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {

			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));

		}

		Map<String,Object> dashinfo = adminService.GetCountInfo();
		Map<String,Object> list = adminService.getUser(commandMap.getMap());

		mv.addObject("paginationInfo", (PaginationInfo)list.get("paginationInfo"));
		mv.addObject("list", list.get("result"));
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());

		mv.setViewName("admin/user");
		return mv;
	}

	@RequestMapping(value = "/admin/log.do")
	public ModelAndView LogList(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {
			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));
		}


		Map<String,Object> dashinfo = adminService.GetCountInfo();
		Map<String,Object> list = adminService.getLog(commandMap.getMap());

		mv.addObject("paginationInfo", (PaginationInfo)list.get("paginationInfo"));
		mv.addObject("list", list.get("result"));
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());

		mv.setViewName("admin/log");
		return mv;
	}

	@RequestMapping(value = "/admin/board.do")
	public ModelAndView BoardList(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {
			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));
		}

		Map<String,Object> dashinfo = adminService.GetCountInfo();
		Map<String,Object> list = adminService.getBoard(commandMap.getMap());

		mv.addObject("paginationInfo", (PaginationInfo)list.get("paginationInfo"));
		mv.addObject("list", list.get("result"));
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());

		mv.setViewName("admin/boardconfig");
		return mv;
	}

	@RequestMapping(value = "/admin/boardcontents.do")
	public ModelAndView ContentsList(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {

			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));

			if(commandMap.get("SCH_TYPE").equals("TITLE_CONTENTS")) {
				commandMap.remove("SCH_TYPE");
				commandMap.put("SCH_TYPE", "BOARD_CONTENTS||BOARD_TITLE");

			}
		}

		Map<String,Object> dashinfo = adminService.GetCountInfo();
		Map<String,Object> list = adminService.getContents(commandMap.getMap());


		mv.addObject("paginationInfo", (PaginationInfo)list.get("paginationInfo"));
		mv.addObject("list", list.get("result"));
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());

		mv.setViewName("admin/post");
		return mv;
	}

	@RequestMapping(value = "/admin/reply.do")
	public ModelAndView ReplysList(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {
			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));
		}

		Map<String,Object> dashinfo = adminService.GetCountInfo();
		Map<String,Object> list = adminService.getReply(commandMap.getMap());

		mv.addObject("paginationInfo", (PaginationInfo)list.get("paginationInfo"));
		mv.addObject("list", list.get("result"));
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());

		mv.setViewName("admin/reply");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/insertboard.do",produces = "application/text; charset=utf8")
	public String Insertboard(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		System.out.println(commandMap.get("board_id"));
		if(adminService.CheckDuplicateBoardID(commandMap.getMap())) {

			adminService.InsertBoard(commandMap.getMap());
			return "Success";
		}else {

			return "실패 , 실패사유:ID가 중복됩니다.";
		}

	}

	@ResponseBody
	@RequestMapping(value = "/admin/insertuser.do",produces = "application/text; charset=utf8")
	public String InsertUser(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		if(adminService.CheckDuplicateUserID(commandMap.getMap())) {
			adminService.InsertUser(commandMap.getMap());
			return "Success";
		}else {

			return "실패 , 실패사유:ID가 중복됩니다.";
		}

	}

	@RequestMapping(value = "/admin/usereditform.do")
	public ModelAndView UpdateUserform(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();
		Map<String,Object> resultmap = adminService.getUserInfo(commandMap.getMap());
		Map<String,Object> dashinfo = adminService.GetCountInfo();
		
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("map", resultmap);
		mv.addObject("USER_NICKNAME", memberVO.getNickname());

		mv.setViewName("admin/useredit");
		return mv;

	}

	@RequestMapping(value = "/admin/useredit.do")
	public ModelAndView UpdateUser(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		System.out.println(commandMap.get("user_id"));
		adminService.UpdateUser(commandMap.getMap());
		Map<String,Object> dashinfo = adminService.GetCountInfo();
		
		mv.setViewName("redirect:/admin/usereditform.do?id="+commandMap.get("user_id"));
		return mv;

	}

	@RequestMapping(value = "/admin/boardeditform.do")
	public ModelAndView UpdateBoardform(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		ModelAndView mv = new ModelAndView();
		Map<String,Object> resultmap = adminService.UpdateBoardForm(commandMap.getMap());
		Map<String,Object> dashinfo = adminService.GetCountInfo();
		
		mv.addObject("map", resultmap);		
		mv.addObject("dashinfo", dashinfo.get("map"));
		mv.addObject("USER_NICKNAME", memberVO.getNickname());
		mv.setViewName("admin/boardedit");
		return mv;

	}


	@RequestMapping(value = "/admin/boardedit.do")
	public ModelAndView UpdateBoard(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		adminService.UpdateBoard(commandMap.getMap());
		mv.setViewName("redirect:/admin/boardeditform.do?bo_num="+commandMap.get("bo_num"));
		return mv;

	}

	@RequestMapping(value = "/admin/deleteUserID.do")
	public ModelAndView deleteUserID(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		adminService.deleteUserId(commandMap.getMap());
		mv.setViewName("redirect:/admin/user.do");
		return mv;

	}



	@RequestMapping(value = "/admin/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		adminService.deleteBoard(commandMap.getMap());
		mv.setViewName("redirect:/admin/board.do");
		return mv;

	}

	@RequestMapping(value = "/admin/deleteContents.do")
	public ModelAndView deleteContents(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView();
		adminService.deleteContents(commandMap.getMap());
		mv.setViewName("redirect:/admin/boardcontents.do");
		return mv;

	}

	@RequestMapping(value = "/admin/deleteReply.do")
	public ModelAndView deleteReply(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		commandMap.put("USER_ID", memberVO.getId());

		ModelAndView mv = new ModelAndView();
		adminService.deleteReply(commandMap.getMap());
		mv.setViewName("redirect:/admin/reply.do");
		return mv;

	}
}

